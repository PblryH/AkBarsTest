package pro.rgun.akbarstest.ui.screen.note_detail.model;

import pro.rgun.akbarstest.domain.model.Note;
import pro.rgun.akbarstest.ui.screen.notes_list.model.ResponseListener;

/**
 * Created by rgun on 10.09.16.
 */
public interface NoteDetailModel {

    void initNote(String id);

    void getNote(ResponseListener<Note> listener);

    void saveNote(Note note, ResponseListener<Void> listener);

    void deleteNote(ResponseListener<Void> listener);
}
