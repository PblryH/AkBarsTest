package pro.rgun.akbarstest.repository.vk_wall;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    public static final String QUERY = "AkBars Storage";

    public static final String METHOD_WALL_SEARCH = "wall.search";

    private Gson mGson = new GsonBuilder().create();

    @Override
    public void getNote(String id, ResponseListener<Note> listener) {
    }

    @Override
    public void saveNote(Note note, ResponseListener<Void> listener) {

    }

    @Override
    public void deleteNote(String id, ResponseListener<Void> listener) {

    }

    @Override
    public void getAllNotes(ResponseListener<List<Note>> listener) {
        readFromVkWall(new VkCallback() {
            @Override
            public void onSuccess(String string) {
                Map<String, Note> notes = getNotes(string);
                List<Note> list = new ArrayList<>();
                if (notes != null) {
                    list = new ArrayList(notes.values());
                }
                listener.onGetResponse(list);
            }

            @Override
            public void onError() {
                listener.onGetResponse(null);
            }
        });
    }


    private Map<String, Note> getNotes(String s){
        Type type = new TypeToken<Map<String, Note>>(){}.getType();
        Map<String, Note> notes = mGson.fromJson(s, type);
        return  notes;
    }

    private void readFromVkWall(VkCallback callback){

        VKRequest request = new VKRequest(
                METHOD_WALL_SEARCH,
                VKParameters.from("query", QUERY));

        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                try {
                    JSONArray items = response.json.getJSONObject("response").getJSONArray("items");
                    if(items.length() > 0){
                        JSONObject object = items.getJSONObject(0);
                        String text = object.getString("text");
                        String[] strings = text.split("\\n");
                        if(strings.length > 1){
                            String string = strings[1];
                            callback.onSuccess(string);
                            return;
                        }
                    }
                    callback.onError();
                } catch (JSONException e) {
                    Timber.d("Parse error");
                    callback.onError();
                }
            }

            @Override
            public void onError(VKError error) {
//                listener.onGetResponse(null);
                callback.onError();
            }

            @Override
            public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
                Timber.d("attemptFailed %d", attemptNumber);
                callback.onError();
            }
        });
    }

    interface VkCallback {

        void onSuccess(String string);

        void onError();
    }
}
