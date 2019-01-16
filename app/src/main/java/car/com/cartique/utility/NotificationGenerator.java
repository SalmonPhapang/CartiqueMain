package car.com.cartique.utility;

import android.util.Log;

import com.google.gson.JsonObject;
import java.io.IOException;
import java.util.Date;

import car.com.cartique.app.AppController;
import car.com.cartique.model.Order;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NotificationGenerator {
    public static final String TAG = NotificationGenerator.class.getSimpleName();
    private static String FCM_ENDPOINT =
            "https://fcm.googleapis.com/fcm/send";
    public void sendMessageToFcm(String jsonMessage,String key) {
        final MediaType mediaType = MediaType.parse("application/json");

        OkHttpClient httpClient = new OkHttpClient();
        try {
            Request request = new Request.Builder().url(FCM_ENDPOINT)
                    .addHeader("Content-Type", "application/json; UTF-8")
                    .addHeader("Authorization", "key=" + key)
                    .post(MultipartBody.create(mediaType, jsonMessage)).build();

            System.out.println(".........Notification MSG "+jsonMessage);
            httpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e(TAG, "error sending firebase app instance token to app server");
                    System.out.println("............ Notification failuer "+call.toString());
                    e.printStackTrace();
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    System.out.println("............ Notification success "+response.message() + " " +response.toString());
                    if (response.isSuccessful()) {
                        Log.i(TAG,"Message has been sent to FCM server "
                        + response.body().toString());
                        System.out.println("............ Notification success "+call.toString());
                    }
                }
            });

        } catch (Exception e) {
            Log.e(TAG,"Error in sending message to FCM server " + e);
            e.printStackTrace();
        }

    }
    public String getFCMDataMessage(Order order) {

        JsonObject jsonObj = new JsonObject();
        jsonObj.addProperty("to", order.getClientNotificationToken());

        JsonObject itemInfo = new JsonObject();
        itemInfo.addProperty("Name", order.getUserName());
        itemInfo.addProperty("Date", new Date().toString());
        jsonObj.add("data", itemInfo);

       /* JsonObject msgObj = new JsonObject();
        msgObj.add("message", jsonObj);*/

       // log.info("data  message " + msgObj.toString());

        return jsonObj.toString();
    }
    public String getFCMNotificationMessage(Order order, String title, String msg) {
        JsonObject jsonObj = new JsonObject();
        // client registration key is sent as token in the message to FCM server
        jsonObj.addProperty("to", order.getClientNotificationToken());
        JsonObject notification = new JsonObject();
        notification.addProperty("body", msg);
        notification.addProperty("title", title);
        notification.addProperty("priority", "high");
        notification.addProperty("sound", "default");
        jsonObj.add("notification", notification);
        return jsonObj.toString();
    }
    public String getFCMNotificationMessage(Order order, String title, String msg,int index) {
        JsonObject jsonObj = new JsonObject();
        // client registration key is sent as token in the message to FCM server
        jsonObj.addProperty("to", order.getQuotes().get(index).getClientNotifictionToken());

        JsonObject notification = new JsonObject();
        notification.addProperty("body", msg);
        notification.addProperty("title", title);
        notification.addProperty("priority", "high");
        notification.addProperty("sound", "default");
        jsonObj.add("notification", notification);

       /* JsonObject message = new JsonObject();
        message.add("message", jsonObj);*/

        //log.info("notification message " + message.toString());

        return jsonObj.toString();
    }
    public String getFCMTopicNotificationMessage(String title, String msg) {
        JsonObject jsonObj = new JsonObject();
        // client registration key is sent as token in the message to FCM server
        jsonObj.addProperty("to", "/topics/Paint");

        JsonObject notification = new JsonObject();
        notification.addProperty("body", msg);
        notification.addProperty("title", title);
        notification.addProperty("priority", "high");
        notification.addProperty("sound", "default");
        notification.addProperty("type","paint");
        jsonObj.add("notification", notification);

        return jsonObj.toString();
    }


}
