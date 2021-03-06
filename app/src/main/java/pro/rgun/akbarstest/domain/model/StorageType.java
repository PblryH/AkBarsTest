package pro.rgun.akbarstest.domain.model;

/**
 * Created by rgun on 24.09.16.
 */

public enum StorageType {

    SHARED_PREFERENCES(1),
    SQLITE(2),
    FILE(3),
    VKWALL(4);

    private int mCode;

    StorageType(int code) {
        mCode = code;
    }

    public int getCode() {
        return mCode;
    }

    public static StorageType parse(int code) {
        for(StorageType st : values()){
            if(st.getCode() == code) return st;
        }
        return null;
    }
}
