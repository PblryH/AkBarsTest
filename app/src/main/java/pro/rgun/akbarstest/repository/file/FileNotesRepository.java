package pro.rgun.akbarstest.repository.file;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pro.rgun.akbarstest.domain.model.Note;
import pro.rgun.akbarstest.domain.repository.NotesRepository;
import pro.rgun.akbarstest.domain.repository.ResponseListener;
import timber.log.Timber;

/**
 * Created by railkamalov on 06.10.16.
 */

public class FileNotesRepository implements NotesRepository {

    public static final String STORAGE_FILE = "storage";

    private Gson mGson = new GsonBuilder().create();
    private Context mContext;

    public FileNotesRepository(Context context) {
        mContext = context;
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
        writeToFile(jsonNotes,true);
        listener.onGetResponse(null);
    }

    @Override
    public void deleteNote(String id, ResponseListener<Void> listener) {
        Map<String, Note> notes = getNotes();
        notes.remove(id);
        String jsonNotes = mGson.toJson(notes);
        writeToFile(jsonNotes,true);
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
        String s = readFromFile();
        Type type = new TypeToken<Map<String, Note>>(){}.getType();
        Map<String, Note> notes = mGson.fromJson(s, type);
        return  notes;
    }

    private String readFromFile(){
        String text = "";
        try {
            BufferedReader input = new BufferedReader(
                    new InputStreamReader(mContext.openFileInput(STORAGE_FILE)));
            String line;
            StringBuffer buffer = new StringBuffer();
            while ((line = input.readLine()) != null) {
                buffer.append(line + "\n");
            }
            text = buffer.toString();
        } catch (FileNotFoundException e) {
            Timber.e(e, "readFromFile");
        } catch (IOException e) {
            Timber.e(e, "readFromFile");
        }
        Timber.d(text);
        return text;
    }

    private void writeToFile(String content, boolean createFileIfNotExists){
        FileOutputStream fos = null;
        try {
            fos = mContext.openFileOutput(STORAGE_FILE, mContext.MODE_WORLD_WRITEABLE);
        } catch (FileNotFoundException e) {
            if(createFileIfNotExists) {
                createFile();
                writeToFile(content, false);
            }
            return;
        }
        try {
            // Create buffered writer
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos));
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            Timber.e(e, "writeToFile");
        }
    }

    private void createFile(){
        File storageFile = new File(STORAGE_FILE);
        try {
            storageFile.createNewFile();
        } catch (IOException e) {
            Timber.e(e,"onStorageFileCreate");
        }
    }
}
