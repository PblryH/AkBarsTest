package pro.rgun.akbarstest;

import timber.log.Timber;

/**
 * Created by rgun on 10.09.16.
 */
public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree(){
                @Override
                protected String createStackElementTag(StackTraceElement element) {
                    return super.createStackElementTag(element) + "/APP";
                }
            });
        }
    }

}
