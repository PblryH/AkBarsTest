package pro.rgun.akbarstest.domain.use_case;

import android.content.Context;

import java.util.List;

import pro.rgun.akbarstest.domain.model.Note;
import pro.rgun.akbarstest.domain.model.StorageType;
import pro.rgun.akbarstest.domain.repository.NotesRepository;
import pro.rgun.akbarstest.domain.repository.ResponseListener;
import pro.rgun.akbarstest.repository.shares_preferences.SharedPreferencesNotesRepository;

/**
 * Created by rgun on 29.09.16.
 */

public class NotesCurrentRepositoryImpl implements NotesCurrentRepository {

    private StorageType mStorageType = StorageType.SHARED_PREFERENCES;

    private Context mContext;

    public NotesCurrentRepositoryImpl(Context context) {
        mContext = context;
    }

    public StorageType getCurrentStorageType() {
        return mStorageType;
    }

    public void setCurrentStorageType(StorageType currentStorageType) {
        mStorageType = currentStorageType;
    }

    @Override
    public void getNotes(ResponseListener<List<Note>> listener) {
        getNotesRepository().getAllNotes(listener);
    }

    @Override
    public void getNote(String id, ResponseListener<Note> listener) {
        getNotesRepository().getNote(id, listener);
    }

    @Override
    public void saveNote(Note note, ResponseListener<Void> listener) {
        getNotesRepository().saveNote(note, listener);
    }

    @Override
    public void deleteNote(String id, ResponseListener<Void> listener) {
        getNotesRepository().deleteNote(id, listener);
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

}
