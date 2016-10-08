package pro.rgun.akbarstest.ui.screen.notes_list.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import pro.rgun.akbarstest.Application;
import pro.rgun.akbarstest.R;
import pro.rgun.akbarstest.domain.model.Note;
import pro.rgun.akbarstest.domain.model.StorageType;
import pro.rgun.akbarstest.ui.extras.recycler.DividerItemDecoration;
import pro.rgun.akbarstest.ui.screen.note_detail.NoteDetailActivity;
import pro.rgun.akbarstest.ui.screen.notes_list.NotesListActivity;
import pro.rgun.akbarstest.ui.screen.notes_list.presenter.NotesListPresenter;
import pro.rgun.akbarstest.ui.screen.notes_list.view.recycler.CheckListItemModel;
import pro.rgun.akbarstest.ui.screen.notes_list.view.recycler.NotesListAdapter;
import rx.Observable;
import timber.log.Timber;


/**
 * Created by rgun on 10.09.16.
 */
public class NotesListViewImpl implements
        NotesListView,
        SwipeRefreshLayout.OnRefreshListener {

    /**
     * Scope is set of required permissions for your application
     *
     * @see <a href="https://vk.com/dev/permissions">vk.com api permissions documentation</a>
     */
    public static final String[] mScope = new String[]{
            VKScope.WALL
    };

    private AlertDialog chooseStorageDialog;
    private NotesListPresenter mPresenter;
    private AppCompatActivity mActivity;
    private NotesListVH vh;
    private NotesListAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private CallBack mCallback;

    @Override
    public void setPresenter(NotesListPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void bindActivity(AppCompatActivity activity) {
        mActivity = activity;
    }

    @Override
    public void unbindActivity() {
        vh.unbind();
    }

    @Override
    public View getView() {
        return vh.getView();
    }

    @Override
    public void initView(LayoutInflater inflater, ViewGroup view) {
        vh = new NotesListVH(inflater, view);
        initToolbar();
        initAdapter();
        initRecyclerView();
        RxView.clicks(vh.recycler.addNote).subscribe(aVoid -> mPresenter.onAddClick());
        requestPermissions();
        mPresenter.onInitViewComplete();
    }


    private void requestPermissions() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(mActivity,
                Manifest.permission.RECEIVE_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity,
                    Manifest.permission.RECEIVE_SMS)) {
                Timber.d("We should show an explanation");

            } else {
                Timber.d("No explanation needed, we can request the permission.");
                ActivityCompat.requestPermissions(mActivity,
                        new String[]{Manifest.permission.RECEIVE_SMS},
                        NotesListActivity.MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case NotesListActivity.MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_notes_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.chooseStorage) {
            mPresenter.onMenuChooseStorageClicked();
            return true;
        }
        return false;
    }

    @Override
    public void onPause() {
        ((Application) mActivity.getApplication()).setIsCurrentScreenNotesList(false);
    }

    @Override
    public void onResume() {
        ((Application) mActivity.getApplication()).setIsCurrentScreenNotesList(true);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(mActivity, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showChooseStorageDialog(StorageType currentStorageType, ChooserStorageDialogListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle(R.string.chooseStorage);
        builder.setSingleChoiceItems(getStorageTypeNames(), currentStorageType.ordinal(), (dialog, item) -> {
            listener.onStorageSelected(StorageType.values()[item]);
            chooseStorageDialog.dismiss();
        });
        chooseStorageDialog = builder.create();
        chooseStorageDialog.show();
    }

    @Override
    public void setCurrentStorageInfoInToolbarSubtitle(StorageType currentStorageType) {
        mActivity.getSupportActionBar()
                .setSubtitle(String.format(mActivity.getString(R.string.storageIn), getStorageTypeNames()[currentStorageType.ordinal()]));
    }

    @Override
    public void fillNotes(List<Note> notes) {
        mAdapter.clear();
        Observable.from(notes)
                .map(note -> {
                    CheckListItemModel checkListItemModel = new CheckListItemModel();
                    checkListItemModel.id = note.getId();
                    checkListItemModel.title = note.getTitle();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(mActivity.getString(R.string.dateFormat));
                    checkListItemModel.createdDate = simpleDateFormat.format(new Date(note.getDateTimeTS()));
                    return checkListItemModel;
                })
                .toList()
                .subscribe(checkListItemModels -> mAdapter.addAll(checkListItemModels));
        if(vh.recycler.swipeRefreshLayout != null)
        vh.recycler.swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showCreateNoteScreen() {
        mActivity.startActivity(new Intent(mActivity, NoteDetailActivity.class));
    }

    @Override
    public void showNoteDetailScreen(String id) {
        Intent intent = new Intent(mActivity, NoteDetailActivity.class);
        intent.putExtra(NoteDetailActivity.EXTRA_NOTE_ID, id);
        mActivity.startActivity(intent);
    }

    @Override
    public void openVkAuthScreen() {
        VKSdk.login(mActivity, mScope);
    }

    private VKCallback<VKAccessToken> callback = new VKCallback<VKAccessToken>() {
        @Override
        public void onResult(VKAccessToken res) {
            // User passed Authorization
            mCallback.onAuthorized();
        }

        @Override
        public void onError(VKError error) {
            // User didn't pass Authorization
            mCallback.onAuthorizationError(error);
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        VKSdk.onActivityResult(requestCode, resultCode, data, callback);
    }

    @Override
    public void setCallback(CallBack callback) {
        mCallback = callback;
    }

    private String[] getStorageTypeNames() {
        StorageType[] values = StorageType.values();
        String[] names = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            StorageType storageType = values[i];
            String name = mActivity.getString(R.string.unknownStorage);
            switch (storageType) {
                case SHARED_PREFERENCES:
                    name = mActivity.getString(R.string.sharedPreferences);
                    break;
                case SQLITE:
                    name = mActivity.getString(R.string.SQLite);
                    break;
                case FILE:
                    name = mActivity.getString(R.string.File);
                    break;
                case VKWALL:
                    name = mActivity.getString(R.string.vkWall);
                    break;
            }
            names[i] = name;
        }
        return names;
    }

    private void initToolbar() {
        // настраиваем action bar
        mActivity.setSupportActionBar(vh.toolbar);
        mActivity.getSupportActionBar().setTitle(R.string.notes);
    }


    private void initAdapter() {
        mAdapter = new NotesListAdapter();
        mAdapter.setItemClickListener((view, position, model) -> mPresenter.onItemClick(model.id));
        mAdapter.setDeleteListener((position, model) -> mPresenter.onItemDelete(model.id));
        mAdapter.setUndoOn(true);
    }

    private void initRecyclerView() {
        mLayoutManager = new LinearLayoutManager(mActivity);
        vh.recycler.recyclerView.setEmptyView(vh.recycler.empty);
        vh.recycler.recyclerView.addItemDecoration(new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL_LIST));
        vh.recycler.recyclerView.setLayoutManager(mLayoutManager);
        vh.recycler.recyclerView.setAdapter(mAdapter);
        setUpItemTouchHelper();
        setUpAnimationDecoratorHelper();
        vh.recycler.swipeRefreshLayout.setOnRefreshListener(this);
    }


    /**
     * This is the standard support library way of implementing "swipe to deleteNote" feature. You can do custom drawing in onChildDraw method
     * but whatever you draw will disappear once the swipe is over, and while the items are animating to their new position the recycler view
     * background will be visible. That is rarely an desired effect.
     */
    private void setUpItemTouchHelper() {

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            // we want to cache these and not allocate anything repeatedly in the onChildDraw method
            Drawable background;
            Drawable xMark;
            int xMarkMargin;
            boolean initiated;

            private void init() {
                background = new ColorDrawable(Color.RED);
                xMark = ContextCompat.getDrawable(mActivity, R.drawable.ic_delete_white_24dp);
                xMark.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                xMarkMargin = (int) mActivity.getResources().getDimension(R.dimen.medium);
                initiated = true;
            }

            // not important, we don't want drag & drop
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public int getSwipeDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                int position = viewHolder.getAdapterPosition();
                NotesListAdapter testAdapter = (NotesListAdapter) recyclerView.getAdapter();
                if (testAdapter.isUndoOn() && testAdapter.isPendingRemoval(position)) {
                    return 0;
                }
                return super.getSwipeDirs(recyclerView, viewHolder);
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int swipedPosition = viewHolder.getAdapterPosition();
                NotesListAdapter adapter = (NotesListAdapter) vh.recycler.recyclerView.getAdapter();
                boolean undoOn = adapter.isUndoOn();
                if (undoOn) {
                    adapter.pendingRemoval(swipedPosition);
                } else {
                    adapter.delete(swipedPosition);
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                View itemView = viewHolder.itemView;

                // not sure why, but this method get's called for viewholder that are already swiped away
                if (viewHolder.getAdapterPosition() == -1) {
                    // not interested in those
                    return;
                }

                if (!initiated) {
                    init();
                }

                // draw red background
                background.setBounds(itemView.getRight() + (int) dX, itemView.getTop(), itemView.getRight(), itemView.getBottom());
                background.draw(c);

                // draw x mark
                int itemHeight = itemView.getBottom() - itemView.getTop();
                int intrinsicWidth = xMark.getIntrinsicWidth();
                int intrinsicHeight = xMark.getIntrinsicWidth();

                int xMarkLeft = itemView.getRight() - xMarkMargin - intrinsicWidth;
                int xMarkRight = itemView.getRight() - xMarkMargin;
                int xMarkTop = itemView.getTop() + (itemHeight - intrinsicHeight) / 2;
                int xMarkBottom = xMarkTop + intrinsicHeight;
                xMark.setBounds(xMarkLeft, xMarkTop, xMarkRight, xMarkBottom);

                xMark.draw(c);

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }

        };
        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        mItemTouchHelper.attachToRecyclerView(vh.recycler.recyclerView);
    }

    /**
     * We're gonna setup another ItemDecorator that will draw the red background in the empty space while the items are animating to thier new positions
     * after an item is removed.
     */
    private void setUpAnimationDecoratorHelper() {
        vh.recycler.recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {

            // we want to cache this and not allocate anything repeatedly in the onDraw method
            Drawable background;
            boolean initiated;

            private void init() {
                background = new ColorDrawable(Color.RED);
                initiated = true;
            }

            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {

                if (!initiated) {
                    init();
                }

                // only if animation is in progress
                if (parent.getItemAnimator().isRunning()) {

                    // some items might be animating down and some items might be animating up to close the gap left by the removed item
                    // this is not exclusive, both movement can be happening at the same time
                    // to reproduce this leave just enough items so the first one and the last one would be just a little off screen
                    // then remove one from the middle

                    // find first child with translationY > 0
                    // and last one with translationY < 0
                    // we're after a rect that is not covered in recycler-view views at this point in time
                    View lastViewComingDown = null;
                    View firstViewComingUp = null;

                    // this is fixed
                    int left = 0;
                    int right = parent.getWidth();

                    // this we need to find out
                    int top = 0;
                    int bottom = 0;

                    // find relevant translating views
                    int childCount = parent.getLayoutManager().getChildCount();
                    for (int i = 0; i < childCount; i++) {
                        View child = parent.getLayoutManager().getChildAt(i);
                        if (child.getTranslationY() < 0) {
                            // view is coming down
                            lastViewComingDown = child;
                        } else if (child.getTranslationY() > 0) {
                            // view is coming up
                            if (firstViewComingUp == null) {
                                firstViewComingUp = child;
                            }
                        }
                    }

                    if (lastViewComingDown != null && firstViewComingUp != null) {
                        // views are coming down AND going up to fill the void
                        top = lastViewComingDown.getBottom() + (int) lastViewComingDown.getTranslationY();
                        bottom = firstViewComingUp.getTop() + (int) firstViewComingUp.getTranslationY();
                    } else if (lastViewComingDown != null) {
                        // views are going down to fill the void
                        top = lastViewComingDown.getBottom() + (int) lastViewComingDown.getTranslationY();
                        bottom = lastViewComingDown.getBottom();
                    } else if (firstViewComingUp != null) {
                        // views are coming up to fill the void
                        top = firstViewComingUp.getTop();
                        bottom = firstViewComingUp.getTop() + (int) firstViewComingUp.getTranslationY();
                    }

                    background.setBounds(left, top, right, bottom);
                    background.draw(c);

                }
                super.onDraw(c, parent, state);
            }

        });
    }

    @Override
    public void onRefresh() {
        vh.recycler.swipeRefreshLayout.setRefreshing(true);
        mPresenter.onPullToRefresh();
    }
}
