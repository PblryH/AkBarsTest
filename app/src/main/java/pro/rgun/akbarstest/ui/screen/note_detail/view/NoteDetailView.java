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

    void setHeading();

    void setBold();

    void setItalic();

    void setUnorderedList();

    void setOrderedList();

    void undo();

    void redo();

    void onCreateOptionsMenu(Menu menu, MenuInflater inflater);

    boolean onOptionsItemSelected(MenuItem item);

    void back();

    void showDeleteDialog();

    void showSaveDialog();

    String getTitle();

    String getText();

    void setTitle(String title);

    void setText(String text);
}
