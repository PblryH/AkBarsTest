package pro.rgun.akbarstest.ui.screen.notes_list.view;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import pro.rgun.akbarstest.R;
import pro.rgun.akbarstest.ui.extras.recycler.DividerItemDecoration;
import pro.rgun.akbarstest.ui.screen.notes_list.presenter.NotesListPresenter;
import pro.rgun.akbarstest.ui.screen.notes_list.view.recycler.CheckListItemModel;
import pro.rgun.akbarstest.ui.screen.notes_list.view.recycler.NotesListAdapter;


/**
 * Created by rgun on 10.09.16.
 */
public class NotesListViewImpl implements NotesListView {

    private NotesListPresenter mPresenter;
    private AppCompatActivity mActivity;
    private NotesListVH vh;
    private NotesListAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

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
    public void showToast(String message) {
        Toast.makeText(mActivity, message, Toast.LENGTH_SHORT).show();
    }

    private void initToolbar() {
        // настраиваем action bar
        mActivity.setSupportActionBar(vh.toolbar);
        mActivity.getSupportActionBar().setTitle("Заметки");
    }



    private void initAdapter() {
        mAdapter = new NotesListAdapter();
        mAdapter.setOnItemClickListener((view, position, model) -> showToast(model.title));
        ArrayList<CheckListItemModel> list = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            CheckListItemModel checkListItemModel = new CheckListItemModel();
            checkListItemModel.id = i;
            checkListItemModel.title = "My note " + i;
            checkListItemModel.createdDate = "14:00 January 14";
            list.add(checkListItemModel);
        }
        mAdapter.addAll(list);
    }

    private void initRecyclerView() {
        mLayoutManager = new LinearLayoutManager(mActivity);
        vh.recycler.recyclerView.setEmptyView(vh.recycler.empty);
        vh.recycler.recyclerView.addItemDecoration(new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL_LIST));
        vh.recycler.recyclerView.setLayoutManager(mLayoutManager);
        vh.recycler.recyclerView.setAdapter(mAdapter);
    }
}
