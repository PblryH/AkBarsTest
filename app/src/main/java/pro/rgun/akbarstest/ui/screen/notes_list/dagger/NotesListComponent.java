package pro.rgun.akbarstest.ui.screen.notes_list.dagger;

import dagger.Component;
import pro.rgun.akbarstest.ui.extras.architecture.PerActivity;
import pro.rgun.akbarstest.ui.screen.notes_list.NotesListFragment;

/**
 * Created by rgun on 10.09.16.
 */
@PerActivity
@Component(modules = NotesListModule.class)
public interface NotesListComponent {
    void inject(NotesListFragment fragment);
}
