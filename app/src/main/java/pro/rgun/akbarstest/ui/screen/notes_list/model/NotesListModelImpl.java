package pro.rgun.akbarstest.ui.screen.notes_list.model;

import android.content.Context;

import java.util.List;

import pro.rgun.akbarstest.domain.model.Note;
import pro.rgun.akbarstest.domain.model.StorageType;
import pro.rgun.akbarstest.domain.use_case.note.NotesCurrentRepository;

/**
 * Created by rgun on 10.09.16.
 */
public class NotesListModelImpl implements NotesListModel {

    private final NotesCurrentRepository mNotesCurrentRepository;

    public NotesListModelImpl(Context context) {
        mNotesCurrentRepository = new NotesCurrentRepository(context);
    }

    @Override
    public StorageType getCurrentStorageType() {
        return mNotesCurrentRepository.getCurrentStorageType();
    }

    @Override
    public void setCurrentStorageType(StorageType storageType) {
        mNotesCurrentRepository.setCurrentStorageType(storageType);
    }

    @Override
    public void requestNotes(ResponseListener<List<Note>> listener) {
        List<Note> noteList = mNotesCurrentRepository.getNoteList();
        listener.onGetResponse(noteList);
    }

    @Override
    public void deleteNote(String id, ResponseListener<Void> listener) {
        mNotesCurrentRepository.deleteNote(id);
        listener.onGetResponse(null);
    }



}
