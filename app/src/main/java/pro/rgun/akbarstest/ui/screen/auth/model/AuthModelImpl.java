package pro.rgun.akbarstest.ui.screen.auth.model;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

/**
 * Created by rgun on 22.08.16.
 */
public class AuthModelImpl implements AuthModel {

    /**
     * Scope is set of required permissions for your application
     *
     * @see <a href="https://vk.com/dev/permissions">vk.com api permissions documentation</a>
     */
    public static final String[] mScope = new String[]{
            VKScope.WALL
    };

    private boolean mIsResumed = false;
    private AppCompatActivity mActivity;
    private CallBack mCallback;


    private VKCallback<VKAccessToken> callback = new VKCallback<VKAccessToken>() {
        @Override
        public void onResult(VKAccessToken res) {
            // User passed Authorization
            mCallback.onAuthorized();
        }

        @Override
        public void onError(VKError error) {
            // User didn't pass Authorization
            mCallback.onAuthorizationError(error);
        }
    };

    public AuthModelImpl(AppCompatActivity activity) {
        mActivity = activity;
        vkWakeUp();
    }

    @Override
    public void onResume() {
        mIsResumed = true;
        if (VKSdk.isLoggedIn()) {
            mCallback.onAuthorized();
        } else {
            mCallback.mustShowLogin();
        }
    }

    @Override
    public void onPause() {
        mIsResumed = false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        VKSdk.onActivityResult(requestCode, resultCode, data, callback);
    }

    @Override
    public void setCallback(CallBack callback) {
        mCallback = callback;
    }

    private void vkWakeUp(){
        VKSdk.wakeUpSession(mActivity, new VKCallback<VKSdk.LoginState>() {
            @Override
            public void onResult(VKSdk.LoginState res) {
                if (mIsResumed) {
                    switch (res) {
                        case LoggedOut:
                            mCallback.mustShowLogin();
                            break;
                    }
                }
            }

            @Override
            public void onError(VKError error) {
                mCallback.onAuthorizationError(error);
            }
        });
    }

}
