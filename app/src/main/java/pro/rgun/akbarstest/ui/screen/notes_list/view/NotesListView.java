package pro.rgun.akbarstest.ui.screen.notes_list.view;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.vk.sdk.api.VKError;

import java.util.List;

import pro.rgun.akbarstest.domain.model.Note;
import pro.rgun.akbarstest.domain.model.StorageType;
import pro.rgun.akbarstest.ui.extras.architecture.BaseView;
import pro.rgun.akbarstest.ui.screen.notes_list.presenter.NotesListPresenter;

/**
 * Created by rgun on 10.09.16.
 */
public interface NotesListView extends BaseView<NotesListPresenter> {

    /**
     * Вызывается при событии Activity#onCreateOptionsMenu
     * @param menu
     * @param inflater
     * @return
     */
    boolean onCreateOptionsMenu(Menu menu, MenuInflater inflater);

    /**
     * Вызывается при событии Activity#onOptionsItemSelected
     * @param item
     * @return
     */
    boolean onOptionsItemSelected(MenuItem item);

    /**
     * При паузе
     */
    void onPause();

    /**
     * При восстановлении
     */
    void onResume();

    /**
     * Вызывается при событии Activity#onRequestPermissionsResult
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults);

    /**
     * Показать тоаст
     * @param message сообщение
     */
    void showToast(String message);

    /**
     * Показать тоаст
     * @param resString сообщение
     */
    void showToast(int resString);

    /**
     * Показать диалог выбора хранилища
     * @param currentStorageType
     */
    void showChooseStorageDialog(StorageType currentStorageType, ChooserStorageDialogListener listener);

    /**
     * Устанавливает в подзаголовок окна информацию о текущем хранилище
     * @param currentStorageType
     */
    void setCurrentStorageInfoInToolbarSubtitle(StorageType currentStorageType);

    /**
     * Заполнить список заметками
     * @param notes - заметки
     */
    void fillNotes(List<Note> notes);

    /**
     * Установка прогресса pull-to-refresh
     * @param b
     */
    void setRefreshing(boolean b);

    /**
     * Открыть экран создания заметки
     */
    void showCreateNoteScreen();

    /**
     * Открыть экран редактирования заметки
     * @param id
     */
    void showNoteDetailScreen(String id);

    /**
     * Открыть экран авторизации ВК
     */
    void openVkAuthScreen();

    /**
     * Вызывается при событии Activity#onActivityResult
     */
    void onActivityResult(int requestCode, int resultCode, Intent data);

    /**
     * Колбэк выбора хранилища
     */
    interface ChooserStorageDialogListener {

        void onStorageSelected(StorageType storageType);

    }


    /**
     * Устанавливает колбэк авторизации через VK
     * @param callback {@link CallBack}
     */
    void setCallback(CallBack callback);

    interface CallBack {

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
