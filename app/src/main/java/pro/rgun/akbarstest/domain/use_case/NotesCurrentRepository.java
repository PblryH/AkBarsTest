package pro.rgun.akbarstest.domain.use_case;

import java.util.List;
import java.util.Observer;

import pro.rgun.akbarstest.domain.model.Note;
import pro.rgun.akbarstest.domain.model.StorageType;
import pro.rgun.akbarstest.domain.repository.ResponseListener;

/**
 * Created by rgun on 29.09.16.
 * <p>Интерфейс для работы с текущей репозиторией</p>
 */
public interface NotesCurrentRepository {

    /**
     * Получение типа текущего хранилища
     * @return - тип хранилища
     */
    StorageType getCurrentStorageType();

    /**
     * Установка типа текущего хранилища
     * @param storageType тип хранилища
     */
    void setCurrentStorageType(StorageType storageType);

    /**
     * Получить все заметки списком для текущего хранилища
     * @param listener - колбэк в который приходит ответ
     */
    void getNotes(ResponseListener<List<Note>> listener);

    /**
     * Получить заметку для текущего хранилища
     * @param id - идентификатор
     * @param listener - колбэк в который приходит ответ
     */
    void getNote(String id, ResponseListener<Note> listener);

    /**
     * Сохранить заметку для текущего хранилища
     * @param note - заметка
     * @param listener - колбэк в который приходит ответ
     */
    void saveNote(Note note, ResponseListener<Void> listener);

    /**
     * Удалить заметку для текущего хранилища
     * @param id - идентификатор
     * @param listener - колбэк в который приходит ответ
     */
    void deleteNote(String id, ResponseListener<Void> listener, boolean isNeedUpdate);

    /**
     * Подписаться на изменение данных в хранилище
     * @param observer - наблюдатель
     */
    void subscribeToNotesUpdate(Observer observer);
}
