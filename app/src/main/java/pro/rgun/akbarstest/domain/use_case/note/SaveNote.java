package pro.rgun.akbarstest.domain.use_case.note;

import pro.rgun.akbarstest.domain.model.Note;
import pro.rgun.akbarstest.domain.repository.NotesRepository;

/**
 * Created by rgun on 24.09.16.
 */

public class SaveNote extends NoteUseCase {

    public SaveNote(NotesRepository repository) {
        super(repository);
    }

    public void saveNote(Note note) {
        mRepository.saveNote(note);
    }
}
