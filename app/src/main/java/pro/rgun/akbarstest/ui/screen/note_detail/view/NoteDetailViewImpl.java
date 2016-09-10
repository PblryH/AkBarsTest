package pro.rgun.akbarstest.ui.screen.note_detail.view;

import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pro.rgun.akbarstest.ui.screen.note_detail.presenter.NoteDetailPresenter;

/**
 * Created by rgun on 10.09.16.
 */
public class NoteDetailViewImpl implements NoteDetailView {

    private NoteDetailPresenter mPresenter;
    private AppCompatActivity mActivity;
    private NoteDetailVH vh;

    @Override
    public void setPresenter(NoteDetailPresenter presenter) {
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
        vh = new NoteDetailVH(inflater, view);
    }

    @Override
    public View getView() {
        return vh.getView();
    }
}
