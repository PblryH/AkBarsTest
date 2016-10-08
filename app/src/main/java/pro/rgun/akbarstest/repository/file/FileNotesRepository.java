package pro.rgun.akbarstest.repository.file;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pro.rgun.akbarstest.domain.model.Note;
import pro.rgun.akbarstest.domain.repository.NotesRepository;
import pro.rgun.akbarstest.domain.repository.ResponseListener;

/**
 * Created by railkamalov on 06.10.16.
 */

public class FileNotesRepository implements NotesRepository {


    private final FileStorageHelper mFileStorageHelper;

    private Gson mGson = new GsonBuilder().create();

    public FileNotesRepository(Context context) {
        mFileStorageHelper = new FileStorageHelper(context);
    }

    @Override
    public void getNote(String id, ResponseListener<Note> listener) {
        Map<String, Note> notes = getNotes();
        Note note = null;
        if (notes != null) {
            note = getNotes().get(id);
        }
        listener.onGetResponse(note);
    }

    @Override
    public void saveNote(Note note, ResponseListener<Void> listener) {
        Map<String, Note> notes = getNotes();
        if (notes == null) {
            notes = new HashMap<>();
        }
        notes.put(note.getId(), note);
        String jsonNotes = mGson.toJson(notes);
        mFileStorageHelper.writeToFile(jsonNotes,true);
        listener.onGetResponse(null);
    }

    @Override
    public void deleteNote(String id, ResponseListener<Void> listener) {
        Map<String, Note> notes = getNotes();
        notes.remove(id);
        String jsonNotes = mGson.toJson(notes);
        mFileStorageHelper.writeToFile(jsonNotes,true);
        listener.onGetResponse(null);
    }

    @Override
    public void getAllNotes(ResponseListener<List<Note>> listener) {
        Map<String, Note> notes = getNotes();
        List<Note> list = new ArrayList<>();
        if (notes != null) {
            list = new ArrayList(getNotes().values());
        }
        listener.onGetResponse(list);
    }

    private Map<String, Note> getNotes(){
        String s = mFileStorageHelper.readFromFile();
        Type type = new TypeToken<Map<String, Note>>(){}.getType();
        Map<String, Note> notes = mGson.fromJson(s, type);
        return  notes;
    }

}
