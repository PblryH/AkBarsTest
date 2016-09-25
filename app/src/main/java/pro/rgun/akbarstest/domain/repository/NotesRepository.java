package pro.rgun.akbarstest.domain.repository;

import java.util.List;

import pro.rgun.akbarstest.domain.model.Note;

/**
 * Created by rgun on 25.09.16.
 */

public interface NotesRepository {

    void addNote(Note note);

    Note getNote(String id);

    void saveNote(String id, Note note);

    void deleteNote(String id);

    List<Note> getAllNotes();
}
