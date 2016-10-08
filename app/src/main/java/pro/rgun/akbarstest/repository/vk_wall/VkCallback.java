package pro.rgun.akbarstest.repository.vk_wall;

/**
 * Created by railkamalov on 07.10.16.
 * <P>Колбек ответа при работе с вк</P>
 */
interface VkCallback<T> {

    /**
     * При успешном ответе
     * @param result - результат
     */
    void onSuccess(T result);

    /**
     * При ошибке
     * @param error - тип ошибки
     */
    void onError(MyVkError error);
}
