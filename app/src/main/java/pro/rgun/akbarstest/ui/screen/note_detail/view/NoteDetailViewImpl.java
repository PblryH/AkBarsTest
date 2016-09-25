package pro.rgun.akbarstest.ui.screen.note_detail.view;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;

import pro.rgun.akbarstest.R;
import pro.rgun.akbarstest.ui.screen.note_detail.presenter.NoteDetailPresenter;
import timber.log.Timber;

/**
 * Created by rgun on 10.09.16.
 */
public class NoteDetailViewImpl implements NoteDetailView {

    private NoteDetailPresenter mPresenter;
    private AppCompatActivity mActivity;
    private NoteDetailVH vh;

    @Override
    public void setPresenter(NoteDetailPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void bindActivity(AppCompatActivity activity) {
        mActivity = activity;
    }

    @Override
    public void unbindActivity() {
        vh.unbind();
    }

    @Override
    public void initView(LayoutInflater inflater, ViewGroup view) {
        vh = new NoteDetailVH(inflater, view);
        initToolbar();
        initEditor();
        initRichTextPanel();
    }

    private void initToolbar() {
        mActivity.setSupportActionBar(vh.toolbar);
        mActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mActivity.getSupportActionBar().setTitle("Заметка");
    }

    @Override
    public View getView() {
        return vh.getView();
    }



    ///////////////////////////////////////////////////////////////////////////
    // RichText
    ///////////////////////////////////////////////////////////////////////////

    private void initEditor() {
        vh.note.editor.setPlaceholder("Текст заметки ...");
        vh.note.editor.setHideKeyboardListener(() -> vh.note.noteContent.requestFocus());
        vh.note.editor.setHtml("");
    }

    private void initRichTextPanel() {
        vh.note.richTextPanel.setVisibility(View.GONE);
        vh.note.editor.setOnFocusChangeListener((view, b) -> {
            vh.note.richTextPanel.setVisibility(b ? View.VISIBLE : View.GONE);
            Timber.d("setOnFocusChangeListener %s", b);
        });
        RxView.clicks(vh.note.heading).subscribe(aVoid -> mPresenter.onHeadingClick());
        RxView.clicks(vh.note.bold).subscribe(aVoid -> mPresenter.onBoldClick());
        RxView.clicks(vh.note.italic).subscribe(aVoid -> mPresenter.onItalicClick());
        RxView.clicks(vh.note.unorderedList).subscribe(aVoid -> mPresenter.onUnorderedListClick());
        RxView.clicks(vh.note.orderedList).subscribe(aVoid -> mPresenter.onOrderedListClick());
        RxView.clicks(vh.note.undo).subscribe(aVoid -> mPresenter.onUndoClick());
        RxView.clicks(vh.note.redo).subscribe(aVoid -> mPresenter.onRedoClick());
    }

    @Override
    public void setHeading() {
        vh.note.editor.setHeading(1);
    }

    @Override
    public void setBold() {
        vh.note.editor.setBold();
    }

    @Override
    public void setItalic() {
        vh.note.editor.setItalic();
    }

    @Override
    public void setUnorderedList() {
        vh.note.editor.setBullets();
    }

    @Override
    public void setOrderedList() {
        vh.note.editor.setNumbers();
    }

    @Override
    public void undo() {
        vh.note.editor.undo();
    }

    @Override
    public void redo() {
        vh.note.editor.redo();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_detail, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            mPresenter.onHomeClicked();
            return true;
        }
        if (id == R.id.delete) {
            mPresenter.onMenuDeleteClicked();
            return true;
        }
        return false;
    }

    @Override
    public void back() {
        mActivity.onBackPressed();
    }

    @Override
    public void showDeleteDialog() {
        AlertDialog.Builder adb = new AlertDialog.Builder(mActivity);
        adb.setTitle("Вы действительно хотите удалить заметку?")
                .setPositiveButton("Да", (dialogInterface, i) -> Toast.makeText(mActivity, "Удалить", Toast.LENGTH_SHORT).show())
                .setNegativeButton("Нет", null)
                .create().show();
    }
}
