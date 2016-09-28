package pro.rgun.akbarstest.ui.screen.notes_list.model;

import android.content.Context;

import java.util.List;

import pro.rgun.akbarstest.domain.model.Note;
import pro.rgun.akbarstest.domain.model.StorageType;
import pro.rgun.akbarstest.domain.repository.NotesRepository;
import pro.rgun.akbarstest.repository.shares_preferences.SharedPreferencesNotesRepository;

/**
 * Created by rgun on 10.09.16.
 */
public class NotesListModelImpl implements NotesListModel {

    private StorageType mStorageType = StorageType.SHARED_PREFERENCES;
    private Context mContext;

    public NotesListModelImpl(Context context) {
        mContext = context;
    }

    @Override
    public StorageType getCurrentStorageType() {
        return mStorageType;
    }

    @Override
    public void setCurrentStorageType(StorageType storageType) {
        mStorageType = storageType;
    }

    @Override
    public void requestNotes(ResponseListener<List<Note>> listener) {
        List<Note> allNotes = getNotesRepository().getAllNotes();
        listener.onGetResponse(allNotes);
    }

    @Override
    public void deleteNote(String id) {
        getNotesRepository().deleteNote(id);
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
