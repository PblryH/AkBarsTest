package pro.rgun.akbarstest.repository.vk_wall;

import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;

import java.util.List;

import pro.rgun.akbarstest.domain.model.Note;
import pro.rgun.akbarstest.domain.repository.NotesRepository;
import pro.rgun.akbarstest.domain.repository.ResponseListener;

/**
 * Created by railkamalov on 06.10.16.
 */

public class VkWallNotesRepository implements NotesRepository {


    public static final String METHOD_PHOTOS_GET = "friends.get";

    @Override
    public void getNote(String id, ResponseListener<Note> listener) {
        VKRequest request = new VKRequest(
                METHOD_PHOTOS_GET,
                VKParameters.from(VKApiConst.FIELDS));
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
}
