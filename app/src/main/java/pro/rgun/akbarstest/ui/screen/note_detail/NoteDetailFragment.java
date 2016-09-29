package pro.rgun.akbarstest.ui.screen.note_detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import pro.rgun.akbarstest.ui.extras.architecture.BaseRetainFragment;
import pro.rgun.akbarstest.ui.screen.note_detail.dagger.DaggerNoteDetailComponent;
import pro.rgun.akbarstest.ui.screen.note_detail.dagger.NoteDetailComponent;
import pro.rgun.akbarstest.ui.screen.note_detail.dagger.NoteDetailModule;
import pro.rgun.akbarstest.ui.screen.note_detail.model.NoteDetailModel;
import pro.rgun.akbarstest.ui.screen.note_detail.presenter.NoteDetailPresenter;
import pro.rgun.akbarstest.ui.screen.note_detail.view.NoteDetailView;

/**
 * Created by rgun on 10.09.16.
 */
public class NoteDetailFragment extends BaseRetainFragment<NoteDetailView> {

    @Inject
    protected NoteDetailModel mModel;

    @Inject
    protected NoteDetailPresenter mPresenter;

    @Inject
    protected NoteDetailView mView;
    
    @Override
    protected void initInjection() {
        NoteDetailComponent component = DaggerNoteDetailComponent.builder()
                .noteDetailModule(new NoteDetailModule(getActivity()))
                .build();
        component.inject(this);
        String noteId = getActivity().getIntent().getStringExtra(NoteDetailActivity.EXTRA_NOTE_ID);
        mModel.initNote(noteId);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        mView.initView(inflater, container);
        return mView.getView();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        mView.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mView.onOptionsItemSelected(item);
    }

    @Override
    public void onPause() {
        super.onPause();
        mView.onPause();
    }
}
