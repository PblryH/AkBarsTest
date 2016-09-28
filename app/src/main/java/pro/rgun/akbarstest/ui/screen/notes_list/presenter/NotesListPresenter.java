package pro.rgun.akbarstest.ui.screen.notes_list.presenter;

/**
 * Created by rgun on 10.09.16.
 */
public interface NotesListPresenter {

    void onMenuChooseStorageClicked();

    void onInitViewComplete();

    void onAddClick();

    void onItemClick(String id);

    void onItemDelete(String id);

    void onPullToRefresh();
}
