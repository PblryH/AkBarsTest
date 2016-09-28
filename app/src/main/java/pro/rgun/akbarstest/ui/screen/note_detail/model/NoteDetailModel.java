package pro.rgun.akbarstest.ui.screen.note_detail.model;

import pro.rgun.akbarstest.domain.model.Note;

/**
 * Created by rgun on 10.09.16.
 */
public interface NoteDetailModel {

    void initNote(String id);

    Note getNote();

    void saveNote(Note note);

    void deleteNote();
}
