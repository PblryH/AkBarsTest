package pro.rgun.akbarstest.ui.screen.notes_list.view.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import pro.rgun.akbarstest.R;
import pro.rgun.akbarstest.ui.extras.recycler.RecyclerViewAdapter;
import timber.log.Timber;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(CheckListItemViewHolder.LAYOUT, parent, false);
        RxView.clicks(view).subscribe(aVoid -> Timber.d("itemView click1"));
        Timber.d("itemView click1");
        return new CheckListItemViewHolder(this, view);
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
            RxView.clicks(itemView).subscribe(aVoid -> Timber.d("itemView click1"));
            itemView.setOnClickListener(view -> Timber.d("itemView click2"));
//            title.setOnClickListener(view -> Timber.d("title click"));
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
