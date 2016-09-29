package pro.rgun.akbarstest.ui.screen.note_detail.model;

import android.content.Context;

import java.util.UUID;

import pro.rgun.akbarstest.domain.model.Note;
import pro.rgun.akbarstest.domain.use_case.note.NotesCurrentRepository;
import pro.rgun.akbarstest.ui.screen.notes_list.model.ResponseListener;

/**
 * Created by rgun on 10.09.16.
 */
public class NoteDetailModelImpl implements NoteDetailModel {


    private Note mNote;
    private final NotesCurrentRepository mNotesCurrentRepository;

    public NoteDetailModelImpl(Context context) {
        mNotesCurrentRepository = new NotesCurrentRepository(context);
    }

    @Override
    public void initNote(String id) {
        mNote = mNotesCurrentRepository.getNote(id);
        if(mNote == null){
            mNote = new Note();
            mNote.setId(UUID.randomUUID().toString());
            mNote.setDateTimeTS(System.currentTimeMillis());
            this.mNote.setTitle("");
            this.mNote.setText("");
        }
    }

    @Override
    public void getNote(ResponseListener<Note> listener) {
        listener.onGetResponse(mNote);
    }

    @Override
    public void saveNote(Note note, ResponseListener<Void> listener) {
        mNotesCurrentRepository.saveNote(note);
        listener.onGetResponse(null);
    }

    @Override
    public void deleteNote(ResponseListener<Void> listener) {
        mNotesCurrentRepository.deleteNote(mNote.getId());
        listener.onGetResponse(null);
    }
}
