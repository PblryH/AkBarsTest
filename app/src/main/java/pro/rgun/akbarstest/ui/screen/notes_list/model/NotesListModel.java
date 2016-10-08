package pro.rgun.akbarstest.ui.screen.notes_list.model;

import java.util.List;
import java.util.Observer;

import pro.rgun.akbarstest.domain.model.Note;
import pro.rgun.akbarstest.domain.model.StorageType;
import pro.rgun.akbarstest.domain.repository.ResponseListener;

/**
 * Created by rgun on 10.09.16.
 */
public interface NotesListModel {

    /**
     * Получение типа текущей репозитории
     * @return - тип репозитории
     */
    StorageType getCurrentStorageType();

    /**
     * Установить тип текущей репозитории
     * @param storageType - тип репозитории
     */
    void setCurrentStorageType(StorageType storageType);

    /**
     * Получить заметки списком
     * @param listener - колбэк получения результатов
     */
    void getNotes(ResponseListener<List<Note>> listener);

    /**
     * Удалить заметку
     * @param id - иденитфикатор заметки
     * @param listener - колбек с результатом
     */
    void deleteNote(String id, ResponseListener<Void> listener);

    /**
     * Подписаться на обновление информации о заметках
     * @param observer - наблюдатель
     */
    void subscribeToNotesUpdate(Observer observer);

}
