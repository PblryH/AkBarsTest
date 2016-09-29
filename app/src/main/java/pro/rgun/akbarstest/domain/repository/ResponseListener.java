package pro.rgun.akbarstest.domain.repository;

/**
 * Created by rgun on 29.09.16.
 */
public interface ResponseListener<T> {
    void onGetResponse(T response);
}
