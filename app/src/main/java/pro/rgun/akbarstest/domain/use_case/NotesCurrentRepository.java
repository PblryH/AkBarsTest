package pro.rgun.akbarstest.domain.use_case;

import java.util.List;

import pro.rgun.akbarstest.domain.model.Note;
import pro.rgun.akbarstest.domain.model.StorageType;
import pro.rgun.akbarstest.domain.repository.ResponseListener;

/**
 * Created by rgun on 29.09.16.
 */
public interface NotesCurrentRepository {

    StorageType getCurrentStorageType();

    void setCurrentStorageType(StorageType storageType);

    void getNotes(ResponseListener<List<Note>> listener);

    void getNote(String id, ResponseListener<Note> listener);

    void saveNote(Note note, ResponseListener<Void> listener);

    void deleteNote(String id, ResponseListener<Void> listener);

}
