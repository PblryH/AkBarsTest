package pro.rgun.akbarstest.ui.screen.auth.model;

import android.content.Intent;

import com.vk.sdk.api.VKError;

/**
 * Created by rgun on 22.08.16.
 */
public interface AuthModel {

    /**
     * Вызывается при событии Activity#onResume
     */
    void onResume();

    /**
     * Вызывается при событии Activity#onPause
     */
    void onPause();

    /**
     * Вызывается при событии Activity#onActivityResult
     */
    void onActivityResult(int requestCode, int resultCode, Intent data);

    /**
     * Устанавливает колбэк авторизации через VK
     * @param callback {@link CallBack}
     */
    void setCallback(CallBack callback);

    interface CallBack {

        /**
         * Необходимо показать экран авторизации
         */
        void mustShowLogin();

        /**
         * Вызывается когда имеется авторизация
         */
        void onAuthorized();

        /**
         * При ошибке авторизации
         * @param error {@link VKError}
         */
        void onAuthorizationError(VKError error);
    }
}
