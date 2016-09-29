package pro.rgun.akbarstest.ui.screen.notes_list.model;

import android.content.Context;

import java.util.List;

import pro.rgun.akbarstest.domain.model.Note;
import pro.rgun.akbarstest.domain.model.StorageType;
import pro.rgun.akbarstest.domain.use_case.NotesCurrentRepository;
import pro.rgun.akbarstest.domain.use_case.NotesCurrentRepositoryImpl;

/**
 * Created by rgun on 10.09.16.
 */
public class NotesListModelImpl implements NotesListModel {

    private final NotesCurrentRepository mNotesCurrentRepository;

    public NotesListModelImpl(Context context) {
        mNotesCurrentRepository = new NotesCurrentRepositoryImpl(context);
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
    public void getNotes(ResponseListener<List<Note>> listener) {
        mNotesCurrentRepository.getNotes(listener::onGetResponse);
    }

    @Override
    public void deleteNote(String id, ResponseListener<Void> listener) {
        mNotesCurrentRepository.deleteNote(id,response -> listener.onGetResponse(null));
    }

}
