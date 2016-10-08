package pro.rgun.akbarstest.domain.repository;

/**
 * Created by rgun on 29.09.16.
 * <p>Колбек для методов интерфейса {@link NotesRepository}</p>
 */
public interface ResponseListener<T> {

    void onGetResponse(T response);

    void onError();
}
