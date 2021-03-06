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
    private boolean isFirst = true;
    private String mText = "";

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
        if(isFirst) {
            mPresenter.onInitViewComplete();
            isFirst = false;
        }
    }

    private void initToolbar() {
        mActivity.setSupportActionBar(vh.toolbar);
        mActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mActivity.getSupportActionBar().setTitle(R.string.note);
    }

    @Override
    public View getView() {
        return vh.getView();
    }


    ///////////////////////////////////////////////////////////////////////////
    // RichText
    ///////////////////////////////////////////////////////////////////////////

    private void initEditor() {
        vh.note.editor.setPlaceholder(mActivity.getString(R.string.noteText));
        vh.note.editor.setHideKeyboardListener(() -> vh.note.noteContent.requestFocus());
        vh.note.editor.setHtml(mText);
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
            mActivity.onBackPressed();
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
        mActivity.finish();
    }

    @Override
    public void showDeleteDialog() {
        AlertDialog.Builder adb = new AlertDialog.Builder(mActivity);
        adb.setTitle(R.string.deleteDialogTitle)
                .setPositiveButton(R.string.yes, (dialogInterface, i) -> mPresenter.deleteNote())
                .setNegativeButton(R.string.no, null)
                .create().show();
    }

    @Override
    public void showSaveDialog() {
        new AlertDialog.Builder(mActivity)
                .setTitle(R.string.saveDialogTitle)
                .setPositiveButton(R.string.yes, (dialogInterface, i) -> mPresenter.saveNote())
                .setNegativeButton(R.string.no, (dialogInterface, i) -> back())
                .setNeutralButton(R.string.cancel, null)
                .create()
                .show();

    }

    @Override
    public String getTitle() {
        return vh.note.title.getText().toString();
    }

    @Override
    public void setTitle(String title) {
        vh.note.title.setText(title);
    }

    @Override
    public String getText() {
        return vh.note.editor.getHtml();
    }

    @Override
    public void setText(String text) {
        vh.note.editor.setHtml(text);
        mText = text;
    }

    @Override
    public void showEmptyTitleDialog() {
        new AlertDialog.Builder(mActivity)
                .setTitle(R.string.emptyTitleDialogTitle)
                .setPositiveButton(R.string.ok, null)
                .create()
                .show();
    }

    @Override
    public void onPause() {
        mText = getText();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(mActivity, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showToast(int resString) {
        Toast.makeText(mActivity, resString, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void finish() {
        mActivity.finish();
    }

    @Override
    public void showErrorSavingDialog() {
        new AlertDialog.Builder(mActivity)
                .setTitle(R.string.errorSavingDialogTitle)
                .setPositiveButton(R.string.yes, (dialogInterface, i) -> mActivity.finish())
                .setNeutralButton(R.string.cancel, null)
                .create()
                .show();
    }
}
