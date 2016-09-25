package pro.rgun.akbarstest.domain.use_case.note;

import pro.rgun.akbarstest.domain.model.Note;
import pro.rgun.akbarstest.domain.repository.NotesRepository;

/**
 * Created by rgun on 24.09.16.
 */

public class GetNote extends NoteUseCase {

    public GetNote(NotesRepository repository) {
        super(repository);
    }

    public Note getNote(String id) {
        return mRepository.getNote(id);
    }
}
