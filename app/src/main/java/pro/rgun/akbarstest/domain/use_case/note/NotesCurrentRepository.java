package pro.rgun.akbarstest.domain.use_case.note;

import android.content.Context;

import java.util.List;

import pro.rgun.akbarstest.domain.model.Note;
import pro.rgun.akbarstest.domain.model.StorageType;
import pro.rgun.akbarstest.domain.repository.NotesRepository;
import pro.rgun.akbarstest.repository.shares_preferences.SharedPreferencesNotesRepository;

/**
 * Created by rgun on 29.09.16.
 */

public class NotesCurrentRepository {

    private StorageType mStorageType = StorageType.SHARED_PREFERENCES;

    private Context mContext;

    public NotesCurrentRepository(Context context) {
        mContext = context;
    }

    public StorageType getCurrentStorageType() {
        return mStorageType;
    }

    public void setCurrentStorageType(StorageType currentStorageType) {
        mStorageType = currentStorageType;
    }

    public List<Note> getNoteList() {
        return new GetNoteList(getNotesRepository()).getNoteList();
    }

    public Note getNote(String id) {
        return new GetNote(getNotesRepository()).getNote(id);
    }

    public void deleteNote(String id) {
        new DeleteNote(getNotesRepository()).deleteNote(id);
    }

    private NotesRepository getNotesRepository(){

        NotesRepository notesRepository;

        switch (mStorageType){
            case SHARED_PREFERENCES:
                notesRepository = new SharedPreferencesNotesRepository(mContext);
                break;
            case SQLITE:
            case FILE:
            case VKWALL:
            default:
                throw new RuntimeException(
                        String.format("Repository %s not yet implemented", mStorageType.name()));
        }

        return notesRepository;
    }

    public void saveNote(Note note) {
        new SaveNote(getNotesRepository()).saveNote(note);
    }
}
