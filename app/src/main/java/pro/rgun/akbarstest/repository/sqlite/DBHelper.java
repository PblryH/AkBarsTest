package pro.rgun.akbarstest.repository.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by rgun on 08.10.16.
 */
class DBHelper extends SQLiteOpenHelper {

    public static final int VERSION = 1;

    public static final String TABLE = "notes";
    public static final String COL_ID = "id";
    public static final String COL_UUID = "uuid";
    public static final String COL_JSON = "json";

    public DBHelper(Context context) {
        super(context, "NotesDB", null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // создаем таблицу с полями
        db.execSQL("create table " + TABLE + " ("
                + COL_ID + " integer primary key autoincrement,"
                + COL_UUID + " text UNIQUE,"
                + COL_JSON + " text" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
