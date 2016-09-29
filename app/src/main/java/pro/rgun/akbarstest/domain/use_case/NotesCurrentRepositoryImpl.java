package pro.rgun.akbarstest.domain.use_case;

import android.content.Context;

import java.util.Collections;
import java.util.List;

import pro.rgun.akbarstest.domain.model.Note;
import pro.rgun.akbarstest.domain.model.StorageType;
import pro.rgun.akbarstest.domain.repository.NotesRepository;
import pro.rgun.akbarstest.domain.repository.ResponseListener;
import pro.rgun.akbarstest.repository.shares_preferences.SharedPreferencesNotesRepository;
import pro.rgun.akbarstest.repository.sqlite.SQLiteNotesRepository;

/**
 * Created by rgun on 29.09.16.
 */

public class NotesCurrentRepositoryImpl implements NotesCurrentRepository {


    private Context mContext;

    public NotesCurrentRepositoryImpl(Context context){
        mContext = context;
    }

    public StorageType getCurrentStorageType() {
        return StorageTypeHolder.getInstance().getType();
    }

    public void setCurrentStorageType(StorageType currentStorageType) {
        StorageTypeHolder.getInstance().setType(currentStorageType);
    }

    @Override
    public void getNotes(ResponseListener<List<Note>> listener) {
        getNotesRepository().getAllNotes(noteList -> {
                    Collections.sort(noteList, (note, note2) -> {
                        if (note.getDateTimeTS() < note2.getDateTimeTS()) return 1;
                        if (note.getDateTimeTS() > note2.getDateTimeTS()) return -1;
                        else return 0;
                    });
                    listener.onGetResponse(noteList);
                });
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

        switch (getCurrentStorageType()){
            case SHARED_PREFERENCES:
                notesRepository = new SharedPreferencesNotesRepository(mContext);
                break;
            case SQLITE:
                notesRepository = new SQLiteNotesRepository(mContext);
                break;
            case FILE:
            case VKWALL:
            default:
                throw new RuntimeException(
                        String.format("Repository %s not yet implemented", getCurrentStorageType().name()));
        }

        return notesRepository;
    }

}
