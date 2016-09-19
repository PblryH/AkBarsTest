package pro.rgun.akbarstest.ui.screen.notes_list.view;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import butterknife.BindView;
import pro.rgun.akbarstest.R;
import pro.rgun.akbarstest.ui.extras.architecture.BaseVH;

/**
 * Created by rgun on 10.09.16.
 */
public class NotesListVH extends BaseVH {

    public static final int LAYOUT = R.layout.fragment_notes_list;


    @BindView(R.id.toolbar)
    public Toolbar toolbar;

    @BindView(R.id.recycler)
    public RecyclerView recycler;
//    public VHRecyclerEmptyWithRefresh recycler;

    public NotesListVH(LayoutInflater inflater, ViewGroup view) {
        super(inflater, view, LAYOUT);

//        recycler = new VHRecyclerEmptyWithRefresh(getView());
    }

//    @Override
//    public int getLayoutRes() {
//        return LAYOUT;
//    }
}
