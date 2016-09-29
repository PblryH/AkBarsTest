package pro.rgun.akbarstest.repository.shares_preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import pro.rgun.akbarstest.domain.model.Note;
import pro.rgun.akbarstest.domain.repository.NotesRepository;

/**
 * Created by rgun on 28.09.16.
 */

public class SharedPreferencesNotesRepository implements NotesRepository {

    private Gson mGson = new GsonBuilder().create();
    private SharedPreferences pref;

    public SharedPreferencesNotesRepository(Context context) {
        pref = PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Override
    public void saveNote(Note note) {
        String jsonNote = mGson.toJson(note);
        SharedPreferences.Editor edit = pref.edit();
        edit.putString(note.getId(), jsonNote);
        edit.apply();
    }

    @Override
    public Note getNote(String id) {
        String jsonNote = pref.getString(id, "");
        Note note = mGson.fromJson(jsonNote, Note.class);
        return note;
    }

    @Override
    public void deleteNote(String id) {
        SharedPreferences.Editor edit = pref.edit();
        edit.remove(id);
        edit.apply();
    }

    @Override
    public List<Note> getAllNotes() {
        ArrayList<Note> notes = new ArrayList<>();
        Map<String, ?> all = pref.getAll();
        Collection<?> values = all.values();
        for (Object o: values){
            if(o instanceof String) {
                try {
                    Note note = mGson.fromJson((String) o, Note.class);
                    notes.add(note);
                }
                catch (JsonSyntaxException e){
                    // do nothing
                }
            }
        }
        return notes;
    }
}
