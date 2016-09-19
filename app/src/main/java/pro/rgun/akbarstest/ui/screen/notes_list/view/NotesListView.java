package pro.rgun.akbarstest.ui.screen.notes_list.view;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

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
     * Показать тоаст
     * @param message сообщение
     */
    void showToast(String message);
}
