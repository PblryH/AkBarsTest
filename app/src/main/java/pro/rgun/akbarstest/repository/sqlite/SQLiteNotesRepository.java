package pro.rgun.akbarstest.repository.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import pro.rgun.akbarstest.domain.model.Note;
import pro.rgun.akbarstest.domain.repository.NotesRepository;
import pro.rgun.akbarstest.domain.repository.ResponseListener;
import timber.log.Timber;

/**
 * Created by rgun on 29.09.16.
 */

public class SQLiteNotesRepository implements NotesRepository {

    private Gson mGson = new GsonBuilder().create();
    private DBHelper dbHelper;

    public SQLiteNotesRepository(Context context) {
        dbHelper = new DBHelper(context);
    }

    @Override
    public void getNote(String id, ResponseListener<Note> listener) {
        Note note = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("select * from notes where uuid='" + id + "'" , null);
        if (c.moveToFirst()) {
            int idColIndex = c.getColumnIndex("id");
            int uuidColIndex = c.getColumnIndex("uuid");
            int jsonColIndex = c.getColumnIndex("json");
            Timber.d(
                    "ID = %d, uuid = %s, json = %s",
                    c.getInt(idColIndex),
                    c.getString(uuidColIndex),
                    c.getString(jsonColIndex));
            note = mGson.fromJson(c.getString(jsonColIndex), Note.class);
        }
        else {
            Timber.d("0 rows");
        }
        c.close();
        listener.onGetResponse(note);
    }

    @Override
    public void saveNote(Note note, ResponseListener<Void> listener) {
        String jsonNote = mGson.toJson(note);
        ContentValues cv = new ContentValues();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        cv.put("uuid", note.getId());
        cv.put("json", jsonNote);
        long rowID = db.insert("notes", null, cv);
        Timber.d("row inserted, ID = %d",rowID);
        listener.onGetResponse(null);
    }

    @Override
    public void deleteNote(String id, ResponseListener<Void> listener) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("notes", "uuid=?", new String[]{id});
        listener.onGetResponse(null);
    }

    @Override
    public void getAllNotes(ResponseListener<List<Note>> listener) {
        ArrayList<Note> notes = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.query("notes", null, null, null, null, null, null);
        if (c.moveToFirst()) {
            int idColIndex = c.getColumnIndex("id");
            int uuidColIndex = c.getColumnIndex("uuid");
            int jsonColIndex = c.getColumnIndex("json");

            do {
                Timber.d(
                        "ID = %d, uuid = %s, json = %s",
                        c.getInt(idColIndex),
                        c.getString(uuidColIndex),
                        c.getString(jsonColIndex));
                Note note = mGson.fromJson(c.getString(jsonColIndex), Note.class);
                notes.add(note);
            } while (c.moveToNext());
        }
        else {
            Timber.d("0 rows");
        }
        c.close();
        listener.onGetResponse(notes);
    }


    class DBHelper extends SQLiteOpenHelper {

        public static final int VERSION = 1;

        public DBHelper(Context context) {
            super(context, "NotesDB", null, VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // создаем таблицу с полями
            db.execSQL("create table notes ("
                    + "id integer primary key autoincrement,"
                    + "uuid text,"
                    + "json text" + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
