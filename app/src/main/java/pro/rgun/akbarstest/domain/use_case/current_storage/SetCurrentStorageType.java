package pro.rgun.akbarstest.domain.use_case.current_storage;

import pro.rgun.akbarstest.domain.model.StorageType;
import pro.rgun.akbarstest.domain.repository.StorageTypeRepository;

/**
 * Created by rgun on 24.09.16.
 */

public class SetCurrentStorageType {

    private StorageTypeRepository mRepository;

    public SetCurrentStorageType(StorageTypeRepository repository) {
        mRepository = repository;
    }

    public void setStorageType(StorageType storageType) {
        mRepository.setStorageType(storageType);
    }
}
