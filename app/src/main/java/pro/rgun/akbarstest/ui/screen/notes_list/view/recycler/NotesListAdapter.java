package pro.rgun.akbarstest.ui.screen.notes_list.view.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import pro.rgun.akbarstest.R;
import pro.rgun.akbarstest.ui.extras.recycler.RecyclerViewAdapter;
import pro.rgun.akbarstest.ui.extras.tools.RippleEffectWrapper;

/**
 * Created by rgun on 03.12.15.
 */
public class NotesListAdapter extends RecyclerViewAdapter<CheckListItemModel, NotesListAdapter.CheckListItemViewHolder> {

    private OnItemClickListener mItemClickListener;

    public NotesListAdapter() {
        super(new ArrayList<CheckListItemModel>());
    }

    @Override
    protected CheckListItemViewHolder createNormalViewHolder(ViewGroup parent, int viewType) {
        ViewGroup viewGroup = new RippleEffectWrapper().addWrapper(CheckListItemViewHolder.LAYOUT, parent);
        return new CheckListItemViewHolder(this, viewGroup);
    }

    @Override
    protected void bindNormalViewHolder(CheckListItemViewHolder vh, int position) {
        vh.title.setText(getItemAt(position).title);
        vh.createdDate.setText(getItemAt(position).createdDate);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mItemClickListener = onItemClickListener;
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

        @BindView(R.id.title)
        public TextView title;

        @BindView(R.id.createdDate)
        public TextView createdDate;

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
