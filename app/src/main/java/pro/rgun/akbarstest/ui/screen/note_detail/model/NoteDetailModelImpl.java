package pro.rgun.akbarstest.ui.screen.note_detail.model;

import android.content.Context;

import java.util.UUID;

import pro.rgun.akbarstest.domain.model.Note;
import pro.rgun.akbarstest.domain.repository.NotesRepository;
import pro.rgun.akbarstest.repository.shares_preferences.SharedPreferencesNotesRepository;

/**
 * Created by rgun on 10.09.16.
 */
public class NoteDetailModelImpl implements NoteDetailModel {


    private Note mNote;
    private Context mContext;

    public NoteDetailModelImpl(Context context) {
        mContext = context;
    }

    @Override
    public void initNote(String id) {
        NotesRepository notesRepository = new SharedPreferencesNotesRepository(mContext);
        mNote = notesRepository.getNote(id);
        if(mNote == null){
            mNote = new Note();
            mNote.setId(UUID.randomUUID().toString());
            mNote.setDateTimeTS(System.currentTimeMillis());
            this.mNote.setTitle("");
            this.mNote.setText("");
        }
    }

    @Override
    public Note getNote() {
        return mNote;
    }

    @Override
    public void saveNote(Note note) {
        NotesRepository notesRepository = new SharedPreferencesNotesRepository(mContext);
        notesRepository.addNote(note);
    }

    @Override
    public void deleteNote() {
        NotesRepository notesRepository = new SharedPreferencesNotesRepository(mContext);
        notesRepository.deleteNote(mNote.getId());
    }
}
