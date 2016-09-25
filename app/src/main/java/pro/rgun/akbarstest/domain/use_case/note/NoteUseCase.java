package pro.rgun.akbarstest.domain.use_case.note;

import pro.rgun.akbarstest.domain.repository.NotesRepository;

/**
 * Created by rgun on 25.09.16.
 */

public abstract class NoteUseCase  {

    protected NotesRepository mRepository;

    public NoteUseCase(NotesRepository repository) {
        mRepository = repository;
    }

    public void setRepository(NotesRepository repository) {
        mRepository = repository;
    }
}
