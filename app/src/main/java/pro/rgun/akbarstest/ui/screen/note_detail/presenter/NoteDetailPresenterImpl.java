package pro.rgun.akbarstest.ui.screen.note_detail.presenter;

import pro.rgun.akbarstest.ui.screen.note_detail.model.NoteDetailModel;
import pro.rgun.akbarstest.ui.screen.note_detail.view.NoteDetailView;
import timber.log.Timber;

/**
 * Created by rgun on 10.09.16.
 */
public class NoteDetailPresenterImpl implements NoteDetailPresenter {

    private final NoteDetailView mView;
    private final NoteDetailModel mModel;

    public NoteDetailPresenterImpl(NoteDetailView view, NoteDetailModel model) {
        mView = view;
        mView.setPresenter(this);
        mModel = model;
    }

    @Override
    public void onHeadingClick() {
        mView.setHeading();
    }

    @Override
    public void onBoldClick() {
        mView.setBold();
    }

    @Override
    public void onItalicClick() {
        mView.setItalic();
    }

    @Override
    public void onUnorderedListClick() {
        mView.setUnorderedList();
    }

    @Override
    public void onOrderedListClick() {
        mView.setOrderedList();
    }

    @Override
    public void onUndoClick() {
        mView.undo();
    }

    @Override
    public void onRedoClick() {
        mView.redo();
    }

    @Override
    public void onHomeClicked() {
        mModel.getNote(note -> {
            if (!note.getTitle().equals(mView.getTitle()) || !note.getText().equals(mView.getText())) {
                if (mView.getTitle().isEmpty()) {
                    mView.showEmptyTitleDialog();
                } else {
                    mView.showSaveDialog();
                }
            } else {
                mView.back();
            }
        });
    }

    @Override
    public void onMenuDeleteClicked() {
        mView.showDeleteDialog();
    }

    @Override
    public void saveNote() {
        mModel.getNote(note -> {
            note.setTitle(mView.getTitle());
            note.setText(mView.getText());
            mModel.saveNote(note,obj -> Timber.d("Note was saved"));
            mView.back();
        });
    }

    @Override
    public void onInitViewComplete() {
        mModel.getNote(note -> {
            mView.setTitle(note.getTitle());
            mView.setText(note.getText());
        });
    }

    @Override
    public void deleteNote() {
        mModel.deleteNote(obj -> mView.back());
    }
}
