package pro.rgun.akbarstest.repository.vk_wall;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import org.json.JSONArray;
import org.json.JSONException;

import timber.log.Timber;

/**
 * Created by railkamalov on 07.10.16.
 */

public class VkWallStorageHelper {


    public static final String VK_WALL_ID = "VkWallId";
    public static final String VK_USER_ID = "VkUserId";

    public static final String DEFAULT_STORAGE = "{}";

    public static final String METHOD_USERS_GET = "users.get";
    public static final String METHOD_WALL_GET = "wall.getById";
    public static final String METHOD_WALL_POST = "wall.post";
    public static final String METHOD_WALL_EDIT = "wall.edit";
    private final SharedPreferences pref;

    public VkWallStorageHelper(Context context) {
        pref = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void read(VkCallback<String> callback) {
        int id = pref.getInt(VK_WALL_ID, 0);
        int user = pref.getInt(VK_USER_ID, 0);
        if (id == 0 || user == 0) {
            currentVkUser(new VkCallback<Integer>() {
                @Override
                public void onSuccess(Integer result) {

                    SharedPreferences.Editor edit = pref.edit();
                    edit.putInt(VK_USER_ID, result);
                    edit.apply();

                    createStorage(new VkCallback<Integer>() {
                        @Override
                        public void onSuccess(Integer integer) {
                            SharedPreferences.Editor edit = pref.edit();
                            edit.putInt(VK_WALL_ID, integer);
                            edit.apply();
                            int userId = pref.getInt(VK_USER_ID, 0);
                            readFromVkWall(integer, userId, new VkCallback<String>() {
                                @Override
                                public void onSuccess(String result) {
                                    callback.onSuccess(result);
                                }

                                @Override
                                public void onError(MyVkError error) {
                                    callback.onError(error);
                                }
                            });
                        }

                        @Override
                        public void onError(MyVkError error) {
                            callback.onError(error);
                        }
                    });
                }

                @Override
                public void onError(MyVkError error) {
                    callback.onError(error);
                }
            });
        } else {
            readFromVkWall(id, user, new VkCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    callback.onSuccess(result);
                }

                @Override
                public void onError(MyVkError error) {
                    callback.onError(error);
                }
            });
        }
    }

    public static void readFromVkWall(int id, int userId, VkCallback<String> callback){
        VKRequest request = new VKRequest(
                METHOD_WALL_GET,
                VKParameters.from("posts", userId + "_" + id));

        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                try {
                    JSONArray responses = response.json.getJSONArray("response");
                    if(responses.length() == 0){
                        callback.onError(MyVkError.STORAGE_NOT_FOUND);
                    }
                    else {
                        String text = responses.getJSONObject(0).getString("text");
                        callback.onSuccess(text);
                    }
                } catch (JSONException e) {
                    Timber.d("Parse error");
                    callback.onError(MyVkError.PARSE_ERROR);
                }
            }

            @Override
            public void onError(VKError error) {
                callback.onError(MyVkError.VK_ERROR);
            }

            @Override
            public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
                Timber.d("attemptFailed %d", attemptNumber);
                callback.onError(MyVkError.ATTEMPT_FAILED);
            }
        });
    }

    public static void currentVkUser(VkCallback<Integer> callback){
        VKRequest request = new VKRequest(METHOD_USERS_GET);

        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                try {
                    int id = response.json.getJSONArray("response").getJSONObject(0).getInt("id");
                    callback.onSuccess(id);
                } catch (JSONException e) {
                    Timber.d("Parse error");
                    callback.onError(MyVkError.PARSE_ERROR);
                }
            }

            @Override
            public void onError(VKError error) {
                callback.onError(MyVkError.VK_ERROR);
            }

            @Override
            public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
                Timber.d("attemptFailed %d", attemptNumber);
                callback.onError(MyVkError.ATTEMPT_FAILED);
            }
        });
    }


    public void write(String jsonNote, VkCallback<Integer> callback) {
        int id = pref.getInt(VK_WALL_ID, 0);
        if (id == 0) {
            createStorage(new VkCallback<Integer>() {
                @Override
                public void onSuccess(Integer integer) {
                    SharedPreferences.Editor edit = pref.edit();
                    edit.putInt(VK_WALL_ID, integer);
                    edit.apply();
                    writeToVkWall(id, jsonNote, new VkCallback<Integer>() {
                        @Override
                        public void onSuccess(Integer result) {
                            callback.onSuccess(result);
                        }

                        @Override
                        public void onError(MyVkError error) {
                            callback.onError(error);
                        }
                    });
                }

                @Override
                public void onError(MyVkError error) {
                    callback.onError(error);
                }
            });
        } else {
            writeToVkWall(id, jsonNote, new VkCallback<Integer>() {
                @Override
                public void onSuccess(Integer result) {
                    callback.onSuccess(result);
                }

                @Override
                public void onError(MyVkError error) {
                    callback.onError(error);
                }
            });
        }
    }

    private static void writeToVkWall(Integer id, String jsonNote, VkCallback<Integer> callback){

        VKRequest request = new VKRequest(
                METHOD_WALL_EDIT,
                VKParameters.from("post_id", id, "message", jsonNote));

        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                try {
                    Integer result = response.json.getInt("response");
                    callback.onSuccess(result);
                } catch (JSONException e) {
                    Timber.d("Parse error");
                    callback.onError(MyVkError.PARSE_ERROR);
                }
            }

            @Override
            public void onError(VKError error) {
                Timber.d(error.toString());
                callback.onError(MyVkError.VK_ERROR);
            }

            @Override
            public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
                Timber.d("attemptFailed %d", attemptNumber);
                callback.onError(MyVkError.ATTEMPT_FAILED);
            }
        });
    }

    private static void createStorage(VkCallback<Integer> callback){

        VKRequest request = new VKRequest(
                METHOD_WALL_POST,
                VKParameters.from("message", DEFAULT_STORAGE));

        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                try {
                    Integer id = response.json.getJSONObject("response").getInt("post_id");
                    callback.onSuccess(id);
                } catch (JSONException e) {
                    Timber.d("Parse error");
                    callback.onError(MyVkError.PARSE_ERROR);
                }
            }

            @Override
            public void onError(VKError error) {
                callback.onError(MyVkError.VK_ERROR);
            }

            @Override
            public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
                Timber.d("attemptFailed %d", attemptNumber);
                callback.onError(MyVkError.ATTEMPT_FAILED);
            }
        });
    }

}
