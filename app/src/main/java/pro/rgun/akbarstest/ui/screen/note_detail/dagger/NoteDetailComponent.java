package pro.rgun.akbarstest.ui.screen.note_detail.dagger;


import dagger.Component;
import pro.rgun.akbarstest.ui.extras.architecture.PerActivity;
import pro.rgun.akbarstest.ui.screen.note_detail.NoteDetailFragment;

/**
 * Created by rgun on 10.09.16.
 */
@PerActivity
@Component(modules = NoteDetailModule.class)
public interface NoteDetailComponent {
    void inject(NoteDetailFragment fragment);
}
