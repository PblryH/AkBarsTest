package pro.rgun.akbarstest.domain.repository;

import java.util.List;

import pro.rgun.akbarstest.domain.model.Note;

/**
 * Created by rgun on 25.09.16.
 */

public interface NotesRepository {

    void getNote(String id, ResponseListener<Note> listener);

    void saveNote(Note note, ResponseListener<Void> listener);

    void deleteNote(String id, ResponseListener<Void> listener);

    void getAllNotes(ResponseListener<List<Note>> listener);

}
