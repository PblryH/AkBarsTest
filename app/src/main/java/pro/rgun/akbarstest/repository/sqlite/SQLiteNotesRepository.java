package pro.rgun.akbarstest.repository.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import pro.rgun.akbarstest.domain.model.Note;
import pro.rgun.akbarstest.domain.repository.NotesRepository;
import pro.rgun.akbarstest.domain.repository.ResponseListener;
import timber.log.Timber;

import static pro.rgun.akbarstest.repository.sqlite.DBHelper.COL_ID;
import static pro.rgun.akbarstest.repository.sqlite.DBHelper.COL_JSON;
import static pro.rgun.akbarstest.repository.sqlite.DBHelper.COL_UUID;
import static pro.rgun.akbarstest.repository.sqlite.DBHelper.TABLE;

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
        Cursor c = db.rawQuery("select * from " + TABLE + " where " + COL_UUID + "='" + id + "'", null);
        if (c.moveToFirst()) {
            int idColIndex = c.getColumnIndex(COL_ID);
            int uuidColIndex = c.getColumnIndex(COL_UUID);
            int jsonColIndex = c.getColumnIndex(COL_JSON);
            Timber.d(
                    "ID = %d, uuid = %s, json = %s",
                    c.getInt(idColIndex),
                    c.getString(uuidColIndex),
                    c.getString(jsonColIndex));
            note = mGson.fromJson(c.getString(jsonColIndex), Note.class);
        } else {
            Timber.d("0 rows");
        }
        c.close();

        if (listener != null) {
            listener.onGetResponse(note);
        }
    }

    @Override
    public void saveNote(Note note, ResponseListener<Void> listener) {
        String jsonNote = mGson.toJson(note);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        getNote(note.getId(), note1 -> {
            ContentValues cv = new ContentValues();
            cv.put(COL_UUID, note.getId());
            cv.put(COL_JSON, jsonNote);
            long rowID;
            if(note1 == null) {
                rowID = db.insert(TABLE, null, cv);
            }
            else{
                rowID = db.update(TABLE, cv, COL_UUID + "=?", new String[]{note.getId()});
            }
            Timber.d("row inserted, ID = %d",rowID);
        });

        if (listener != null) {
            listener.onGetResponse(null);
        }
    }

    @Override
    public void deleteNote(String id, ResponseListener<Void> listener) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TABLE, COL_UUID + "=?", new String[]{id});

        if (listener != null) {
            listener.onGetResponse(null);
        }
    }

    @Override
    public void getAllNotes(ResponseListener<List<Note>> listener) {
        ArrayList<Note> notes = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.query(TABLE, null, null, null, null, null, null);
        if (c.moveToFirst()) {
            int idColIndex = c.getColumnIndex(COL_ID);
            int uuidColIndex = c.getColumnIndex(COL_UUID);
            int jsonColIndex = c.getColumnIndex(COL_JSON);

            do {
                Timber.d(
                        "ID = %d, uuid = %s, json = %s",
                        c.getInt(idColIndex),
                        c.getString(uuidColIndex),
                        c.getString(jsonColIndex));
                Note note = mGson.fromJson(c.getString(jsonColIndex), Note.class);
                notes.add(note);
            } while (c.moveToNext());
        } else {
            Timber.d("0 rows");
        }
        c.close();

        if (listener != null) {
            listener.onGetResponse(notes);
        }
    }

}
