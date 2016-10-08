package pro.rgun.akbarstest.ui.screen.note_detail.model;

import pro.rgun.akbarstest.domain.model.Note;
import pro.rgun.akbarstest.domain.repository.ResponseListener;

/**
 * Created by rgun on 10.09.16.
 */
public interface NoteDetailModel {

    /**
     * Инициализация модели
     * @param id - идентификатор заметки
     */
    void initNote(String id);

    /**
     * Получение заметки
     * @param listener - колбек с результатом
     */
    void getNote(ResponseListener<Note> listener);

    /**
     * Сохранение заметки
     * @param note - заметка
     * @param listener - колбек с результатом
     */
    void saveNote(Note note, ResponseListener<Void> listener);

    /**
     * Удаление заметки
     * @param listener -колбэк с результатом
     */
    void deleteNote(ResponseListener<Void> listener);
}
