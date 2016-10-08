package pro.rgun.akbarstest.ui.screen.note_detail.view;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import pro.rgun.akbarstest.ui.extras.architecture.BaseView;
import pro.rgun.akbarstest.ui.screen.note_detail.presenter.NoteDetailPresenter;

/**
 * Created by rgun on 10.09.16.
 */
public interface NoteDetailView extends BaseView<NoteDetailPresenter> {

    /**
     * Установка форматирования Heading
     */
    void setHeading();

    /**
     * Установка форматирования Bold
     */
    void setBold();

    /**
     * Установка форматирования Italic
     */
    void setItalic();

    /**
     * Установка форматирования UnorderedList
     */
    void setUnorderedList();

    /**
     * Установка форматирования OrderedList
     */
    void setOrderedList();

    /**
     * Отмена форматирования
     */
    void undo();

    /**
     * Возврат отмененного форматирования
     */
    void redo();

    /**
     * При создании меню
     * @param menu
     * @param inflater
     */
    void onCreateOptionsMenu(Menu menu, MenuInflater inflater);

    /**
     * При нажатии на пункт меню
     * @param item
     * @return
     */
    boolean onOptionsItemSelected(MenuItem item);

    /**
     * Назад
     */
    void back();

    /**
     * Показать диалог удаления
     */
    void showDeleteDialog();

    /**
     * Показать диалог сохранения
     */
    void showSaveDialog();

    /**
     * Получить заголовок заметки
     * @return - текст заголовка
     */
    String getTitle();

    /**
     * Получить текст заметки
     * @return - основной текст
     */
    String getText();

    /**
     * Установить заголовок заметки
     * @param title -текст заголовка
     */
    void setTitle(String title);

    /**
     * Установить основной текст заметки
     * @param text - основной текст
     */
    void setText(String text);

    /**
     * Показать алерт о пустом заголовке
     */
    void showEmptyTitleDialog();

    /**
     * При паузе
     */
    void onPause();

    /**
     * Показать тоаст
     * @param message сообщение
     */
    void showToast(String message);

    /**
     * Завершает активити
     */
    void finish();
}
