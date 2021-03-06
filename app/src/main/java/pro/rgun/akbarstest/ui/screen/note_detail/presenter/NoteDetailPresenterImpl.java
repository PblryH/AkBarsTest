package pro.rgun.akbarstest.ui.screen.note_detail.presenter;

import pro.rgun.akbarstest.R;
import pro.rgun.akbarstest.domain.model.Note;
import pro.rgun.akbarstest.domain.repository.ResponseListener;
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
    public void onMenuDeleteClicked() {
        mView.showDeleteDialog();
    }

    @Override
    public void saveNote() {
        mModel.getNote(new ResponseListener<Note>() {
                           @Override
                           public void onGetResponse(Note note) {
                               note.setTitle(mView.getTitle());
                               note.setText(mView.getText());
                               mModel.saveNote(note, new ResponseListener<Void>() {
                                           @Override
                                           public void onGetResponse(Void response) {
                                               Timber.d("Note was saved");
                                           }

                                           @Override
                                           public void onError() {
                                               mView.showErrorSavingDialog();
                                           }
                                       });
                               mView.back();
                           }

                           @Override
                           public void onError() {
                               mView.showErrorSavingDialog();
                           }
                       });
    }

    @Override
    public void onInitViewComplete() {
        mModel.getNote(new ResponseListener<Note>() {
            @Override
            public void onGetResponse(Note note) {
                mView.setTitle(note.getTitle());
                mView.setText(note.getText());
            }

            @Override
            public void onError() {
                mView.showToast(R.string.dataReceiveError);
                mView.finish();
            }
        });
    }

    @Override
    public void deleteNote() {
        mModel.deleteNote(new ResponseListener<Void>() {
            @Override
            public void onGetResponse(Void response) {
                mView.back();
            }

            @Override
            public void onError() {
                mView.showToast(R.string.dataReceiveError);
            }
        });
    }

    @Override
    public void onBackPressed() {
        mModel.getNote(new ResponseListener<Note>() {
            @Override
            public void onGetResponse(Note note) {
                if (!note.getTitle().equals(mView.getTitle()) || !note.getText().equals(mView.getText())) {
                    if (mView.getTitle().isEmpty()) {
                        mView.showEmptyTitleDialog();
                    } else {
                        mView.showSaveDialog();
                    }
                } else {
                    mView.back();
                }
            }

            @Override
            public void onError() {
                mView.showErrorSavingDialog();
            }
        });
    }
}
