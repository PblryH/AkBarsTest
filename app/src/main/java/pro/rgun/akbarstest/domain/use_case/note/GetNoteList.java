package pro.rgun.akbarstest.domain.use_case.note;

import java.util.Collections;
import java.util.List;

import pro.rgun.akbarstest.domain.model.Note;
import pro.rgun.akbarstest.domain.repository.NotesRepository;

/**
 * Created by rgun on 24.09.16.
 */

public class GetNoteList extends NoteUseCase{


    public GetNoteList(NotesRepository repository) {
        super(repository);
    }

    public List<Note> getNoteList() {
        List<Note> noteList = mRepository.getAllNotes();
        Collections.sort(noteList, (note, note2) -> {
            if(note.getDateTimeTS() < note2.getDateTimeTS()) return 1;
            if(note.getDateTimeTS() > note2.getDateTimeTS()) return -1;
            else return 0;
        });
        return noteList;
    }
}
