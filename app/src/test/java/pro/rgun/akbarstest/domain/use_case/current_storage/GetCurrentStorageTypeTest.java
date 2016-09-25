package pro.rgun.akbarstest.domain.use_case.current_storage;

import org.junit.Assert;
import org.junit.Test;

import pro.rgun.akbarstest.domain.model.StorageType;
import pro.rgun.akbarstest.domain.repository.StorageTypeRepository;

/**
 * Created by rgun on 25.09.16.
 */
public class GetCurrentStorageTypeTest {


    private static final StorageType STORAGE_TYPE_FOR_ASSERT = StorageType.VKWALL;


    @Test
    public void CurrentStorage_Get_Success(){
        // Arrange
        StorageTypeRepository currentStorageTypeRepository = new StorageTypeRepository() {
            @Override
            public StorageType getStorageType() {
                return STORAGE_TYPE_FOR_ASSERT;
            }

            @Override
            public void setStorageType(StorageType storageType) {

            }
        };
        GetCurrentStorageType getCurrentStorageType = new GetCurrentStorageType(currentStorageTypeRepository);
        // Act
        StorageType storageType = getCurrentStorageType.getStorageType();
        // Assert
        Assert.assertEquals(STORAGE_TYPE_FOR_ASSERT, storageType);
    }
}