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


    void onPause();

    void onResume();

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

    void fillNotes(List<Note> notes);

    void showNoteDetailScreen();

    void showNoteDetailScreen(String id);

    /**
     * Колбэк выбора хранилища
     */
    interface ChooserStorageDialogListener {

        void onStorageSelected(StorageType storageType);

    }
}
