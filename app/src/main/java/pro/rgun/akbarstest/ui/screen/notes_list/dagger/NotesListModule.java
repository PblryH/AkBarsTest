package pro.rgun.akbarstest.ui.screen.notes_list.dagger;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;
import pro.rgun.akbarstest.ui.extras.architecture.PerActivity;
import pro.rgun.akbarstest.ui.screen.notes_list.model.NotesListModel;
import pro.rgun.akbarstest.ui.screen.notes_list.model.NotesListModelImpl;
import pro.rgun.akbarstest.ui.screen.notes_list.presenter.NotesListPresenter;
import pro.rgun.akbarstest.ui.screen.notes_list.presenter.NotesListPresenterImpl;
import pro.rgun.akbarstest.ui.screen.notes_list.view.NotesListView;
import pro.rgun.akbarstest.ui.screen.notes_list.view.NotesListViewImpl;

/**
 * Created by rgun on 10.09.16.
 */
@Module
public class NotesListModule {

    @PerActivity
    @Provides
    NotesListPresenter provideNotesListPresenter(NotesListView view, NotesListModel model){
        return new NotesListPresenterImpl(view, model);
    }

    @PerActivity
    @Provides
    @Inject
    NotesListModel provideNotesListModel(){
        return new NotesListModelImpl();
    }

    @PerActivity
    @Provides
    NotesListView provideNotesListView(){
        return new NotesListViewImpl();
    }
}
