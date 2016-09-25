package pro.rgun.akbarstest.domain.use_case.note;

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
        return mRepository.getAllNotes();
    }
}
