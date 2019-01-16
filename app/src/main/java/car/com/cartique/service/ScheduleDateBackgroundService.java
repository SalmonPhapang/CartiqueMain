package car.com.cartique.service;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

public class ScheduleDateBackgroundService extends IntentService {
    public static final String ACTION="car.com.cartique.service.Receivers.ResponseBroadcastReceiver";

    // Must create a default constructor
    public ScheduleDateBackgroundService() {
        // Used to name the worker thread, important only for debugging.
        super("ScheduleDateBackgroundService");
    }
        @Override
        protected void onHandleIntent(@Nullable Intent intent) {
            // This describes what will happen when service is triggered
            Log.i("backgroundService","Service running");

            //create a broadcast to send the toast message
            Intent toastIntent= new Intent(ACTION);
            toastIntent.putExtra("resultCode", Activity.RESULT_OK);
            toastIntent.putExtra("toastMessage","I'M running after ever 15 minutes");
            sendBroadcast(toastIntent);

        }

    }