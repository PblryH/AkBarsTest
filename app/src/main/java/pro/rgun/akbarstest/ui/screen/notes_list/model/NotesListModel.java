package pro.rgun.akbarstest.ui.screen.notes_list.model;

import pro.rgun.akbarstest.domain.model.StorageType;

/**
 * Created by rgun on 10.09.16.
 */
public interface NotesListModel {

    StorageType getCurrentStorageType();

    void setCurrentStorageType(StorageType storageType);

}
