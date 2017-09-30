package pe.cibertec.intentservicedemo;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.util.concurrent.TimeUnit;

/**
 * Created by Android on 13/05/2017.
 */

public class MyIntentService extends IntentService {

    private static final String TAG = "MyIntentService";

    public static final String ACTION_TASK_STARTED = "action_task_started";
    public static final String ACTION_TASK_FINISHED = "action_task_finished";

    public MyIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Intent startedIntent = new Intent(ACTION_TASK_STARTED);
        LocalBroadcastManager.getInstance(this).sendBroadcast(startedIntent);
        Log.d(TAG, "onHandleIntent: start task");
        
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Intent finishedIntent = new Intent(ACTION_TASK_FINISHED);
        LocalBroadcastManager.getInstance(this).sendBroadcast(finishedIntent);
        Log.d(TAG, "onHandleIntent: finish task");
    }
}
