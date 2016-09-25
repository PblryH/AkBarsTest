package pro.rgun.akbarstest.domain.use_case.current_storage;

import org.junit.Assert;
import org.junit.Test;

import pro.rgun.akbarstest.domain.model.StorageType;
import pro.rgun.akbarstest.domain.repository.StorageTypeRepository;

/**
 * Created by rgun on 25.09.16.
 */
public class SetCurrentStorageTypeTest {


    private static final StorageType STORAGE_TYPE_FOR_ASSERT = StorageType.VKWALL;

    @Test
    public void CurrentStorage_Set_Success(){
        // Arrange
        StorageTypeRepository currentStorageTypeRepository = new StorageTypeRepository() {

            public StorageType mStorageType;

            @Override
            public StorageType getStorageType() {
                return mStorageType;
            }

            @Override
            public void setStorageType(StorageType storageType) {
                mStorageType = storageType;
            }
        };
        SetCurrentStorageType getCurrentStorageType = new SetCurrentStorageType(currentStorageTypeRepository);
        // Act
        getCurrentStorageType.setStorageType(STORAGE_TYPE_FOR_ASSERT);
        // Assert
        Assert.assertEquals(STORAGE_TYPE_FOR_ASSERT, currentStorageTypeRepository.getStorageType());
    }
}