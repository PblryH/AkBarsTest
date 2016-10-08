package pro.rgun.akbarstest.ui.screen.note_detail.presenter;

/**
 * Created by rgun on 10.09.16.
 */
public interface NoteDetailPresenter {

    /**
     * При нажатии на H
     */
    void onHeadingClick();

    /**
     * При нажатии на B
     */
    void onBoldClick();

    /**
     * При нажатии на i
     */
    void onItalicClick();

    /**
     * При нажатии на Bullets
     */
    void onUnorderedListClick();

    /**
     * При нажатии на Numbers
     */
    void onOrderedListClick();

    /**
     * При нажатии на Undo
     */
    void onUndoClick();

    /**
     * При нажатии на Redo
     */
    void onRedoClick();

    /**
     * При нажатии на кнопку удаления заметки
     */
    void onMenuDeleteClicked();

    /**
     * Сохранить заметку
     */
    void saveNote();

    /**
     * При завершении инициализации вью
     */
    void onInitViewComplete();

    /**
     * Удалить заметку
     */
    void deleteNote();

    /**
     * При нажатии "назад"
     */
    void onBackPressed();
}
