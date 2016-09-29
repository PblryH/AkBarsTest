package pro.rgun.akbarstest.domain.repository;

import java.util.List;

import pro.rgun.akbarstest.domain.model.Note;

/**
 * Created by rgun on 25.09.16.
 */

public interface NotesRepository {

    Note getNote(String id);

    void saveNote(Note note);

    void deleteNote(String id);

    List<Note> getAllNotes();
}
