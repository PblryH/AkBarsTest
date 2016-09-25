package pro.rgun.akbarstest.ui.extras.view_holder;

import android.os.Build;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import butterknife.BindView;
import pro.rgun.akbarstest.R;
import pro.rgun.akbarstest.ui.extras.architecture.BaseVH;

/**
 * Common View Holder with toolbar
 * Created by rgun on 22.06.16.
 */
abstract public class VHContentWithToolbar extends BaseVH {

    public static final int layout = R.layout.template_content_with_toolbar;

    @BindView(R.id.content)
    protected FrameLayout content;

    @BindView(R.id.toolbar)
    public Toolbar toolbar;


    public VHContentWithToolbar(LayoutInflater inflater, ViewGroup view){
        this(inflater, view, true);
    }

    public VHContentWithToolbar(LayoutInflater inflater, ViewGroup view, boolean isElevated) {
        super(inflater, view, layout);
        if (!isElevated && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setElevation(0);
        }
        inflateLayoutIntoContent(inflater);
    }

    private void inflateLayoutIntoContent(LayoutInflater inflater) {
        inflater.inflate(getLayoutRes(), content);
    }

    abstract public int getLayoutRes();
}
