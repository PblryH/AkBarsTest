package pro.rgun.akbarstest.domain.use_case;

import pro.rgun.akbarstest.domain.model.StorageType;

/**
 * Created by rgun on 29.09.16.
 */
public class StorageTypeHolder {

    private StorageType mStorageType = StorageType.SHARED_PREFERENCES;

    private static StorageTypeHolder ourInstance = new StorageTypeHolder();

    public static StorageTypeHolder getInstance() {
        return ourInstance;
    }

    private StorageTypeHolder() {
    }

    public StorageType getType() {
        return mStorageType;
    }

    public void setType(StorageType type) {
        mStorageType = type;
    }
}
