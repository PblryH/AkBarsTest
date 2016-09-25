package pro.rgun.akbarstest.ui.extras.architecture;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by railkamalov on 05.08.16.
 */
abstract public class BaseVH {

    protected Unbinder unbinder;
    private View mView;

    public BaseVH(LayoutInflater inflater, ViewGroup view, int layout) {
        mView = inflater.inflate(layout, view, false);
        unbinder = ButterKnife.bind(this, mView);
    }

    public void unbind() {
        unbinder.unbind();
        unbindAll();
    }

    public View getView() {
        return mView;
    }

    protected abstract void unbindAll();
}
