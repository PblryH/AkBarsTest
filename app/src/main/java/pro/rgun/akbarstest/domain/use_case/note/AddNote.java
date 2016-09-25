package pro.rgun.akbarstest.domain.use_case.note;

import pro.rgun.akbarstest.domain.model.Note;
import pro.rgun.akbarstest.domain.repository.NotesRepository;

/**
 * Created by rgun on 24.09.16.
 */

public class AddNote extends NoteUseCase {


    public AddNote(NotesRepository repository) {
        super(repository);
    }


    public void addNote(Note note) {
        mRepository.addNote(note);
    }

}
