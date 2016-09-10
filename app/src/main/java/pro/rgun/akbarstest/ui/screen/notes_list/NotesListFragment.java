package pro.rgun.akbarstest.ui.screen.notes_list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import pro.rgun.akbarstest.ui.extras.architecture.BaseRetainFragment;
import pro.rgun.akbarstest.ui.screen.notes_list.dagger.DaggerNotesListComponent;
import pro.rgun.akbarstest.ui.screen.notes_list.dagger.NotesListComponent;
import pro.rgun.akbarstest.ui.screen.notes_list.dagger.NotesListModule;
import pro.rgun.akbarstest.ui.screen.notes_list.model.NotesListModel;
import pro.rgun.akbarstest.ui.screen.notes_list.presenter.NotesListPresenter;
import pro.rgun.akbarstest.ui.screen.notes_list.view.NotesListView;

/**
 * Created by rgun on 10.09.16.
 */
public class NotesListFragment extends BaseRetainFragment<NotesListView> {

    @Inject
    protected NotesListModel mModel;

    @Inject
    protected NotesListPresenter mPresenter;

    @Inject
    protected NotesListView mView;
    
    @Override
    protected void initInjection() {
        NotesListComponent component = DaggerNotesListComponent.builder()
                .notesListModule(new NotesListModule())
                .build();
        component.inject(this);
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
//        mView.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        return mView.onOptionsItemSelected(item);
        return false;
    }

}
