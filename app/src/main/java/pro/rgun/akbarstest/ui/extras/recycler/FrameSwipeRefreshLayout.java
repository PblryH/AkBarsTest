package pro.rgun.akbarstest.ui.extras.recycler;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * SwipeRefreshLayout with correct behavior for cases with empty view support
 * Created by rgun on 27.08.16.
 */
public class FrameSwipeRefreshLayout  extends SwipeRefreshLayout {

    private ViewGroup listView;

    public FrameSwipeRefreshLayout(Context context) {
        super(context);
    }

    public FrameSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setListView(ViewGroup list) {
        listView = list;
    }


    @Override
    public boolean canChildScrollUp() {
        if (listView != null && listView.getVisibility() == View.VISIBLE) {
            View child = listView.getChildAt(0);
            return child != null && child.getTop() != 0;
        }
        return super.canChildScrollUp();
    }
}