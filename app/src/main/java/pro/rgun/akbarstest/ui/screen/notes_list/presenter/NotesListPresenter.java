package pro.rgun.akbarstest.ui.screen.notes_list.presenter;

/**
 * Created by rgun on 10.09.16.
 */
public interface NotesListPresenter {

    /**
     * При нажатии на кнопку выбора хранилища
     */
    void onMenuChooseStorageClicked();

    /**
     * При завершении инициализации вью
     */
    void onInitViewComplete();

    /**
     * При нажатии на кнопку добавить
     */
    void onAddClick();

    /**
     * При нажатии на элемент списка
     * @param id - идентификатор заметки
     */
    void onItemClick(String id);

    /**
     * При удалении элемента списка
     * @param id - идентификатор заметки
     */
    void onItemDelete(String id);

    /**
     * При pull-to-refresh
     */
    void onPullToRefresh();
}
