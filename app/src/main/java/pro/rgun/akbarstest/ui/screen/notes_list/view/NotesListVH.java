package pro.rgun.akbarstest.ui.screen.notes_list.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import pro.rgun.akbarstest.R;
import pro.rgun.akbarstest.ui.extras.recycler.VHRecyclerEmptyWithRefresh;
import pro.rgun.akbarstest.ui.extras.view_holder.VHContentWithToolbar;

/**
 * Created by rgun on 10.09.16.
 */
public class NotesListVH extends VHContentWithToolbar {

    public static final int LAYOUT = R.layout.fragment_notes_list;

    public VHRecyclerEmptyWithRefresh recycler;

    public NotesListVH(LayoutInflater inflater, ViewGroup view) {
        super(inflater, view);
        recycler = new VHRecyclerEmptyWithRefresh(getView());
    }

    @Override
    public int getLayoutRes() {
        return LAYOUT;
    }
}
