package pro.rgun.akbarstest;

import pro.rgun.akbarstest.domain.use_case.NotesCurrentRepository;
import pro.rgun.akbarstest.domain.use_case.NotesCurrentRepositoryImpl;
import timber.log.Timber;

/**
 * Created by rgun on 10.09.16.
 */
public class Application extends android.app.Application {

    private NotesCurrentRepository mNotesCurrentRepository;
    private boolean mIsCurrentScreenNotesList;

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            initTimber();
        }
        mNotesCurrentRepository = new NotesCurrentRepositoryImpl(this);
    }

    private void initTimber(){
        Timber.plant(new Timber.DebugTree(){
            @Override
            protected String createStackElementTag(StackTraceElement element) {
                return super.createStackElementTag(element) + "/APP";
            }
        });
    }

    public NotesCurrentRepository getNotesCurrentRepository(){
        return mNotesCurrentRepository;
    }

    public void setIsCurrentScreenNotesList(boolean isCurrentScreenNotesList){
        mIsCurrentScreenNotesList = isCurrentScreenNotesList;
    }

    public boolean isCurrentScreenNotesList(){
        return mIsCurrentScreenNotesList;
    }

}
