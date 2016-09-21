package pro.rgun.akbarstest.ui.screen.notes_list.view.recycler;

import android.graphics.Color;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pro.rgun.akbarstest.R;
import pro.rgun.akbarstest.ui.extras.recycler.RecyclerViewAdapter;

/**
 * Created by rgun on 03.12.15.
 */
public class NotesListAdapter extends RecyclerViewAdapter<CheckListItemModel, NotesListAdapter.CheckListItemViewHolder> {


    private static final int PENDING_REMOVAL_TIMEOUT = 3000; // 3sec


    private List<CheckListItemModel> itemsPendingRemoval = new ArrayList<>();
    private OnItemClickListener mItemClickListener;
    private boolean mUndoOn;


    private Handler handler = new Handler(); // hanlder for running delayed runnables
    HashMap<CheckListItemModel, Runnable> pendingRunnables = new HashMap<>(); // map of items to pending runnables, so we can cancel a removal if need be

    public NotesListAdapter() {
        super(new ArrayList<CheckListItemModel>());
    }

    @Override
    protected CheckListItemViewHolder createNormalViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(CheckListItemViewHolder.LAYOUT, null);
//        ViewGroup view = new RippleEffectWrapper().addWrapper(CheckListItemViewHolder.LAYOUT, parent);
        return new CheckListItemViewHolder(this, view);
    }

    @Override
    protected void bindNormalViewHolder(CheckListItemViewHolder vh, int position) {
        CheckListItemModel item = getItemAt(position);

        vh.title.setText(item.title);
        vh.createdDate.setText(item.createdDate);

        if (itemsPendingRemoval.contains(item)) {
            // we need to show the "undo" state of the row
            vh.itemView.setBackgroundColor(Color.RED);
            vh.mainContent.setVisibility(View.GONE);
            vh.undoButton.setVisibility(View.VISIBLE);
            vh.undoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // user wants to undo the removal, let's cancel the pending task
                    Runnable pendingRemovalRunnable = pendingRunnables.get(item);
                    pendingRunnables.remove(item);
                    if (pendingRemovalRunnable != null) handler.removeCallbacks(pendingRemovalRunnable);
                    itemsPendingRemoval.remove(item);
                    // this will rebind the row in "normal" state
                    notifyItemChanged(mDataList.indexOf(item));
                }
            });
        } else {
            // we need to show the "normal" state
            vh.itemView.setBackgroundColor(Color.WHITE);
            vh.mainContent.setVisibility(View.VISIBLE);
            vh.undoButton.setVisibility(View.GONE);
            vh.undoButton.setOnClickListener(null);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mItemClickListener = onItemClickListener;
    }

    public void setUndoOn(boolean undoOn) {
        mUndoOn = undoOn;
    }

    public boolean isUndoOn() {
        return mUndoOn;
    }

    public void pendingRemoval(int position) {
        final CheckListItemModel item = mDataList.get(position);
        if (!itemsPendingRemoval.contains(item)) {
            itemsPendingRemoval.add(item);
            // this will redraw row in "undo" state
            notifyItemChanged(position);
            // let's create, store and post a runnable to remove the item
            Runnable pendingRemovalRunnable = new Runnable() {
                @Override
                public void run() {
                    removeItem(mDataList.indexOf(item));
                }
            };
            handler.postDelayed(pendingRemovalRunnable, PENDING_REMOVAL_TIMEOUT);
            pendingRunnables.put(item, pendingRemovalRunnable);
        }
    }

    public boolean isPendingRemoval(int position) {
        return itemsPendingRemoval.contains(mDataList.get(position));
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, CheckListItemModel model);
    }

    ///////////////////////////////////////////////////////////////////////////
    // Static classes
    ///////////////////////////////////////////////////////////////////////////

    public static class CheckListItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private static final int LAYOUT = R.layout.list_item_notes_list;
        private final NotesListAdapter mNotesListAdapter;

        @BindView(R.id.mainContent)
        View mainContent;

        @BindView(R.id.title)
        public TextView title;

        @BindView(R.id.createdDate)
        public TextView createdDate;

        @BindView(R.id.undoButton)
        public Button undoButton;

        public CheckListItemViewHolder(NotesListAdapter notesListAdapter, View itemView) {
            super(itemView);
            mNotesListAdapter = notesListAdapter;
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mNotesListAdapter.mItemClickListener != null) {
                mNotesListAdapter.mItemClickListener.onItemClick(view, getLayoutPosition(), mNotesListAdapter.getItemAt(getLayoutPosition()));
            }
        }
    }
}
