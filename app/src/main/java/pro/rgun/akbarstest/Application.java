package pro.rgun.akbarstest;

import android.content.Intent;
import android.widget.Toast;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKAccessTokenTracker;
import com.vk.sdk.VKSdk;

import pro.rgun.akbarstest.domain.use_case.NotesCurrentRepository;
import pro.rgun.akbarstest.domain.use_case.NotesCurrentRepositoryImpl;
import pro.rgun.akbarstest.ui.screen.auth.AuthActivity;
import timber.log.Timber;

/**
 * Created by rgun on 10.09.16.
 */
public class Application extends android.app.Application {

    private NotesCurrentRepository mNotesCurrentRepository;
    private boolean mIsCurrentScreenNotesList;

    VKAccessTokenTracker vkAccessTokenTracker = new VKAccessTokenTracker() {
        @Override
        public void onVKAccessTokenChanged(VKAccessToken oldToken, VKAccessToken newToken) {
            if (newToken == null) {
                Toast.makeText(Application.this, "AccessToken invalidated", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Application.this, AuthActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }
    };


    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            initTimber();
        }
        vkAccessTokenTracker.startTracking();
        VKSdk.initialize(this);
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
