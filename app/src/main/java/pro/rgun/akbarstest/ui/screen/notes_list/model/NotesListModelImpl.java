package pro.rgun.akbarstest.ui.screen.notes_list.model;

import android.content.Context;

import java.util.List;
import java.util.Observer;

import pro.rgun.akbarstest.Application;
import pro.rgun.akbarstest.domain.model.Note;
import pro.rgun.akbarstest.domain.model.StorageType;
import pro.rgun.akbarstest.domain.repository.ResponseListener;
import pro.rgun.akbarstest.domain.use_case.NotesCurrentRepository;

/**
 * Created by rgun on 10.09.16.
 */
public class NotesListModelImpl implements NotesListModel {

    private final NotesCurrentRepository mNotesCurrentRepository;

    public NotesListModelImpl(Context context) {
        mNotesCurrentRepository = ((Application) context.getApplicationContext()).getNotesCurrentRepository();
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
        mNotesCurrentRepository.getNotes(listener);
    }

    @Override
    public void deleteNote(String id, ResponseListener<Void> listener) {
        mNotesCurrentRepository.deleteNote(id,listener,false);
    }

    @Override
    public void subscribeToNotesUpdate(Observer observer) {
        mNotesCurrentRepository.subscribeToNotesUpdate(observer);
    }

}
