package pro.rgun.akbarstest.domain.model;

/**
 * Created by rgun on 24.09.16.
 */

public class Note {

    /**
     * Идентификатор
     */
    private String id;
    /**
     * Заголовок
     */
    private String title;
    /**
     * Текст
     */
    private String text;
    /**
     * Таймстамп даты и времени создания
     */
    private long dateTimeTS;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getDateTimeTS() {
        return dateTimeTS;
    }

    public void setDateTimeTS(long dateTimeTS) {
        this.dateTimeTS = dateTimeTS;
    }
}
