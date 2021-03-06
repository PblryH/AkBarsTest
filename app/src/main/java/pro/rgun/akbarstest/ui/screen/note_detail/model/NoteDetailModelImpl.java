package pro.rgun.akbarstest.ui.screen.note_detail.model;

import android.content.Context;

import java.util.UUID;

import pro.rgun.akbarstest.Application;
import pro.rgun.akbarstest.domain.model.Note;
import pro.rgun.akbarstest.domain.repository.ResponseListener;
import pro.rgun.akbarstest.domain.use_case.NotesCurrentRepository;

/**
 * Created by rgun on 10.09.16.
 */
public class NoteDetailModelImpl implements NoteDetailModel {


    private final NotesCurrentRepository mNotesCurrentRepository;
    private Note mNote;
    private String mNoteId;

    public NoteDetailModelImpl(Context context) {
        mNotesCurrentRepository = ((Application) context.getApplicationContext()).getNotesCurrentRepository();
    }

    @Override
    public void initNote(String id) {
        mNoteId = id;
    }

    @Override
    public void getNote(ResponseListener<Note> listener) {
        mNotesCurrentRepository.getNote(mNoteId, new ResponseListener<Note>() {
                    @Override
                    public void onGetResponse(Note note) {
                        mNote = note;
                        if (mNote == null) {
                            mNote = new Note();
                            mNote.setId(UUID.randomUUID().toString());
                            mNote.setDateTimeTS(System.currentTimeMillis());
                            mNote.setTitle("");
                            mNote.setText("");
                        }
                        listener.onGetResponse(mNote);
                    }

                    @Override
                    public void onError() {
                        listener.onError();
                    }
                });
    }

    @Override
    public void saveNote(Note note, ResponseListener<Void> listener) {
        mNotesCurrentRepository.saveNote(note, listener);
    }

    @Override
    public void deleteNote(ResponseListener<Void> listener) {
        mNotesCurrentRepository.deleteNote(mNote.getId(), listener, true);
    }
}
