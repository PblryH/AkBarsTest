package pro.rgun.akbarstest.ui.extras.recycler;

import android.view.View;
import butterknife.BindView;
import pro.rgun.akbarstest.R;

/**
 * Common View Holder for RecyclerViewEmptySupport  with pull-to-refresh
 * Created by rgun on 25.05.16.
 */
public class VHRecyclerEmptyWithRefresh extends VHRecyclerEmpty {

    @BindView(R.id.swipeRefreshLayout)
    public FrameSwipeRefreshLayout swipeRefreshLayout;

    public VHRecyclerEmptyWithRefresh(View v) {
        super(v);
        swipeRefreshLayout.setListView(recyclerView);
    }
}
