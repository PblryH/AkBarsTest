package pro.rgun.akbarstest.domain.use_case.note;

import pro.rgun.akbarstest.domain.repository.NotesRepository;

/**
 * Created by rgun on 24.09.16.
 */

public class DeleteNote extends NoteUseCase {

    public DeleteNote(NotesRepository repository) {
        super(repository);
    }

    public void deleteNote(String id) {
        mRepository.deleteNote(id);
    }
}
