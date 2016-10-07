package pro.rgun.akbarstest.domain.use_case;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import pro.rgun.akbarstest.domain.model.StorageType;



/**
 * Created by rgun on 29.09.16.
 */
public class StorageTypeHolder {

    public static final String STORAGE_TYPE = "StorageType";
    private final SharedPreferences pref;

    public StorageTypeHolder(Context context) {
        pref = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public StorageType getType() {
        int code = pref.getInt(STORAGE_TYPE, StorageType.SHARED_PREFERENCES.getCode());
        StorageType type = StorageType.parse(code);
        return type;
    }

    public void setType(StorageType type) {
        SharedPreferences.Editor edit = pref.edit();
        edit.putInt(STORAGE_TYPE, type.getCode());
        edit.apply();
    }
}
