package pro.rgun.akbarstest.ui.extras.architecture;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 */
abstract public class BaseRetainFragment<V extends BaseView> extends Fragment {

    @Inject
    protected V mView;

    public BaseRetainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        initInjection();
        mView.bindActivity((AppCompatActivity) getActivity());
    }

    abstract protected void initInjection();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (mView != null) {
            mView.bindActivity((AppCompatActivity) context);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mView != null) {
            mView.unbindActivity();
        }
    }

}
