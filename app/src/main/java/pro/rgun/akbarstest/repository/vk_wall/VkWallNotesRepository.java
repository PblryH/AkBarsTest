package pro.rgun.akbarstest.repository.vk_wall;

import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import org.json.JSONException;

import java.util.List;

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

    }

    private void readFromVkWall(){

        VKRequest request = new VKRequest(
                METHOD_WALL_SEARCH,
                VKParameters.from("query", QUERY));

        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                String items = null;
                try {
                    items = response.json.getJSONObject("response").getJSONArray("items").toString();
                    items.trim();
                } catch (JSONException e) {
                    Timber.d("Parse error");
                }
            }

            @Override
            public void onError(VKError error) {
//                listener.onGetResponse(null);
            }

            @Override
            public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
                Timber.d("attemptFailed %d", attemptNumber);
            }
        });
    }
}
