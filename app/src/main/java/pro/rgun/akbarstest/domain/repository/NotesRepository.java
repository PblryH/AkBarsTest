package pro.rgun.akbarstest.domain.repository;

import java.util.List;

import pro.rgun.akbarstest.domain.model.Note;

/**
 * Created by rgun on 25.09.16.
 */

public interface NotesRepository {

    /**
     * Получить заметку
     * @param id - идентификатор
     * @param listener - колбэк в который приходит ответ
     */
    void getNote(String id, ResponseListener<Note> listener);

    /**
     * Сохранить заметку
     * @param note - заметка
     * @param listener - колбэк в который приходит ответ
     */
    void saveNote(Note note, ResponseListener<Void> listener);

    /**
     * Удалить заметку
     * @param id - идентификатор
     * @param listener - колбэк в который приходит ответ
     */
    void deleteNote(String id, ResponseListener<Void> listener);

    /**
     * Получить все заметки списком
     * @param listener - колбэк в который приходит ответ
     */
    void getAllNotes(ResponseListener<List<Note>> listener);

}
