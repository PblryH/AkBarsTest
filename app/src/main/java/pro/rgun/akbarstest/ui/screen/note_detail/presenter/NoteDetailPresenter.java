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
     * При нажатии на кнопку назад
     */
    void onHomeClicked();

    /**
     * При нажатии на кнопку удаления заметки
     */
    void onMenuDeleteClicked();

    void saveNote();

    void onInitViewComplete();

    void deleteNote();
}
