package pro.rgun.akbarstest.repository.vk_wall;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import pro.rgun.akbarstest.domain.model.Note;
import pro.rgun.akbarstest.domain.repository.NotesRepository;
import pro.rgun.akbarstest.domain.repository.ResponseListener;
import timber.log.Timber;


/**
 * Created by railkamalov on 06.10.16.
 */

public class VkWallNotesRepository implements NotesRepository {


    private final VkWallStorageHelper mVkWallStorageHelper;
    private Gson mGson = new GsonBuilder().create();

    public VkWallNotesRepository(Context context) {
        mVkWallStorageHelper = new VkWallStorageHelper(context);
    }

    @Override
    public void getNote(String id, ResponseListener<Note> listener) {
        getNotesMap(new ResponseListener<Map<String, Note>>() {
            @Override
            public void onGetResponse(Map<String, Note> notes) {
                listener.onGetResponse(notes.get(id));
            }

            @Override
            public void onError() {
                listener.onError();
            }
        });
    }

    @Override
    public void saveNote(Note note, ResponseListener<Void> listener) {
        getNotesMap(new ResponseListener<Map<String, Note>>() {
            @Override
            public void onGetResponse(Map<String, Note> notes) {
                notes.put(note.getId(), note);
                String jsonNotes = mGson.toJson(notes);
                mVkWallStorageHelper.write(jsonNotes, new VkCallback<Integer>() {
                    @Override
                    public void onSuccess(Integer result) {
                        listener.onGetResponse(null);
                    }

                    @Override
                    public void onError(MyVkError error) {
                        listener.onError();
                    }
                });
            }

            @Override
            public void onError() {
                listener.onError();
            }
        });
    }

    @Override
    public void deleteNote(String id, ResponseListener<Void> listener) {
        getNotesMap(new ResponseListener<Map<String, Note>>() {
            @Override
            public void onGetResponse(Map<String, Note> notes) {
                notes.remove(id);
                String jsonNotes = mGson.toJson(notes);
                mVkWallStorageHelper.write(jsonNotes, new VkCallback<Integer>() {
                    @Override
                    public void onSuccess(Integer result) {
                        listener.onGetResponse(null);
                    }

                    @Override
                    public void onError(MyVkError error) {
                        listener.onError();
                    }
                });
            }

            @Override
            public void onError() {
                listener.onError();
            }
        });
    }

    @Override
    public void getAllNotes(ResponseListener<List<Note>> listener) {
        getNotesMap(new ResponseListener<Map<String, Note>>() {
            @Override
            public void onGetResponse(Map<String, Note> notes) {
                List<Note> list = new ArrayList<>();
                if (notes != null) {
                    list = new ArrayList(notes.values());
                }
                listener.onGetResponse(list);
            }

            @Override
            public void onError() {
                listener.onError();
            }
        });
    }

    public void getNotesMap(ResponseListener<Map<String, Note>> listener) {
        mVkWallStorageHelper.read(new VkCallback<String>() {
            @Override
            public void onSuccess(String string) {
                listener.onGetResponse(getNotes(string));
            }

            @Override
            public void onError(MyVkError error) {
                listener.onError();
                Timber.d(error.name());
                switch (error){
                    case STORAGE_NOT_FOUND:
                        break;
                    case PARSE_ERROR:
                        break;
                    case ATTEMPT_FAILED:
                        break;
                    case VK_ERROR:
                        break;
                }
            }
        });
    }


    private Map<String, Note> getNotes(String s){
        Type type = new TypeToken<Map<String, Note>>(){}.getType();
        Map<String, Note> notes = mGson.fromJson(s, type);
        return  notes;
    }


}
