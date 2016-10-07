package pro.rgun.akbarstest.repository.vk_wall;

/**
 * Created by railkamalov on 07.10.16.
 */
interface VkCallback<T> {

    void onSuccess(T result);

    void onError(MyVkError error);
}
