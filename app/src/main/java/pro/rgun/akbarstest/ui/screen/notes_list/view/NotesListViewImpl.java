package pro.rgun.akbarstest.ui.screen.notes_list.view;

import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pro.rgun.akbarstest.ui.screen.notes_list.presenter.NotesListPresenter;


/**
 * Created by rgun on 10.09.16.
 */
public class NotesListViewImpl implements NotesListView {

    private NotesListPresenter mPresenter;
    private AppCompatActivity mActivity;
    private NotesListVH vh;

    @Override
    public void setPresenter(NotesListPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void bindActivity(AppCompatActivity activity) {
        mActivity = activity;
    }

    @Override
    public void unbindActivity() {
        vh.unbind();
    }

    @Override
    public void initView(LayoutInflater inflater, ViewGroup view) {
        vh = new NotesListVH(inflater, view);
    }

    @Override
    public View getView() {
        return vh.getView();
    }
}
