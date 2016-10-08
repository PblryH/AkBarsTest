package pro.rgun.akbarstest.ui.screen.notes_list.view;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

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
     * При создании меню
     * @param menu
     * @param inflater
     * @return
     */
    boolean onCreateOptionsMenu(Menu menu, MenuInflater inflater);

    /**
     * При выборе пункта меню
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
     * При запросе разрешения
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
     * Колбэк выбора хранилища
     */
    interface ChooserStorageDialogListener {

        void onStorageSelected(StorageType storageType);

    }
}
