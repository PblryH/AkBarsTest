package pro.rgun.akbarstest.domain.use_case;

import android.content.Context;

import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import pro.rgun.akbarstest.domain.model.Note;
import pro.rgun.akbarstest.domain.model.StorageType;
import pro.rgun.akbarstest.domain.repository.NotesRepository;
import pro.rgun.akbarstest.domain.repository.ResponseListener;
import pro.rgun.akbarstest.repository.file.FileNotesRepository;
import pro.rgun.akbarstest.repository.shares_preferences.SharedPreferencesNotesRepository;
import pro.rgun.akbarstest.repository.sqlite.SQLiteNotesRepository;
import pro.rgun.akbarstest.repository.vk_wall.VkWallNotesRepository;
import timber.log.Timber;

/**
 * Created by rgun on 29.09.16.
 */

public class NotesCurrentRepositoryImpl extends Observable implements NotesCurrentRepository {


    private final StorageTypeHolder storageTypeHolder;
    private Context mContext;

    public NotesCurrentRepositoryImpl(Context context) {
        mContext = context;
        storageTypeHolder = new StorageTypeHolder(context);
    }

    public StorageType getCurrentStorageType() {
        return storageTypeHolder.getType();
    }

    public void setCurrentStorageType(StorageType currentStorageType) {
        storageTypeHolder.setType(currentStorageType);
    }

    @Override
    public void getNotes(ResponseListener<List<Note>> listener) {
        getNotesRepository().getAllNotes(new ResponseListener<List<Note>>() {
            @Override
            public void onGetResponse(List<Note> noteList) {
                Collections.sort(noteList, (note, note2) -> {
                    if (note.getDateTimeTS() < note2.getDateTimeTS()) return 1;
                    if (note.getDateTimeTS() > note2.getDateTimeTS()) return -1;
                    else return 0;
                });
                listener.onGetResponse(noteList);
            }

            @Override
            public void onError() {
                listener.onError();
            }
        });
    }

    @Override
    public void getNote(String id, ResponseListener<Note> listener) {
        getNotesRepository().getNote(id, listener);
    }

    @Override
    public void saveNote(Note note, ResponseListener<Void> listener) {
        getNotesRepository().saveNote(note, new ResponseListener<Void>() {
            @Override
            public void onGetResponse(Void response) {
                listener.onGetResponse(null);
                setChanged();
                notifyObservers();
            }

            @Override
            public void onError() {
                Timber.d("onError");
                listener.onError();
            }
        });
    }

    @Override
    public void deleteNote(String id, ResponseListener<Void> listener, boolean isNeedUpdate) {
        getNotesRepository().deleteNote(id, new ResponseListener<Void>() {
            @Override
            public void onGetResponse(Void response) {
                listener.onGetResponse(null);
                if (isNeedUpdate) {
                    setChanged();
                    notifyObservers();
                }
            }

            @Override
            public void onError() {
                Timber.d("onError");
                listener.onError();
            }
        });
    }

    @Override
    public void subscribeToNotesUpdate(Observer observer) {
        addObserver(observer);
    }

    private NotesRepository getNotesRepository() {

        NotesRepository notesRepository;

        switch (getCurrentStorageType()) {
            case SHARED_PREFERENCES:
                notesRepository = new SharedPreferencesNotesRepository(mContext);
                break;
            case SQLITE:
                notesRepository = new SQLiteNotesRepository(mContext);
                break;
            case FILE:
                notesRepository = new FileNotesRepository(mContext);
                break;
            case VKWALL:
                notesRepository = new VkWallNotesRepository(mContext);
                break;
            default:
                throw new RuntimeException(
                        String.format("Repository %s not yet implemented", getCurrentStorageType().name()));
        }

        return notesRepository;
    }

}
