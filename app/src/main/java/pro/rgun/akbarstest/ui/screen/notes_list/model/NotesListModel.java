package pro.rgun.akbarstest.ui.screen.notes_list.model;

import java.util.List;

import pro.rgun.akbarstest.domain.model.Note;
import pro.rgun.akbarstest.domain.model.StorageType;

/**
 * Created by rgun on 10.09.16.
 */
public interface NotesListModel {

    StorageType getCurrentStorageType();

    void setCurrentStorageType(StorageType storageType);

    void requestNotes(ResponseListener<List<Note>> listener);

    void deleteNote(String id, ResponseListener<Void> listener);

}
