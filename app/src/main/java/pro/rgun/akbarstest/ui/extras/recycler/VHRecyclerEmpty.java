package pro.rgun.akbarstest.ui.extras.recycler;

import android.support.design.widget.FloatingActionButton;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import pro.rgun.akbarstest.R;


/**
 * Common View Holder for RecyclerViewEmptySupport
 * Created by rgun on 22.06.16.
 */
public class VHRecyclerEmpty {

    @BindView(R.id.list)
    public RecyclerViewEmptySupport recyclerView;

    @BindView(R.id.list_empty)
    public View empty;

    @BindView(R.id.addNote)
    public FloatingActionButton addNote;

    private Unbinder unbinder;

    public VHRecyclerEmpty(View v) {
        unbinder = ButterKnife.bind(this, v);
    }

    public void unbind() {
        unbinder.unbind();
    }
}
