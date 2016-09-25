package pro.rgun.akbarstest.domain.use_case.note;

import pro.rgun.akbarstest.domain.repository.NotesRepository;

/**
 * Created by rgun on 24.09.16.
 */

public class SubscribeToNoteListUpdate extends NoteUseCase {

    public SubscribeToNoteListUpdate(NotesRepository repository) {
        super(repository);
    }



}
