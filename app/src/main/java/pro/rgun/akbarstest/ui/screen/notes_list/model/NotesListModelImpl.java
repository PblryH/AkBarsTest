package pro.rgun.akbarstest.ui.screen.notes_list.model;

import pro.rgun.akbarstest.domain.model.StorageType;

/**
 * Created by rgun on 10.09.16.
 */
public class NotesListModelImpl implements NotesListModel {

    private StorageType mStorageType = StorageType.SHARED_PREFERENCES;

    @Override
    public StorageType getCurrentStorageType() {
        return mStorageType;
    }

    @Override
    public void setCurrentStorageType(StorageType storageType) {
        mStorageType = storageType;
    }
}
