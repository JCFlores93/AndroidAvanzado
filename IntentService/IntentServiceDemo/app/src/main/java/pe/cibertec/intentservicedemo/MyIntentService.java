package pe.cibertec.intentservicedemo;

import android.app.IntentService;
import android.content.Intent;
import java.util.concurrent.TimeUnit;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

/**
 * Created by Android on 13/05/2017.
 */

public class MyIntentService extends IntentService {

    //DEFINIR ACCIONES
    public static final String ACTION_TASK_STARTED = "action_task_started";
    public static final String ACTION_TASK_FINISHED = "action_task_finished";
    public static final String TAG ="MyIntentService" ;

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        Intent startedIntent = new Intent(ACTION_TASK_STARTED);
        LocalBroadcastManager.getInstance(this).sendBroadcast(startedIntent);
        Log.d(TAG,"onhandleItent : start task");

        try{
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Intent finishedIntent = new Intent(ACTION_TASK_FINISHED);
        LocalBroadcastManager.getInstance(this).sendBroadcast(finishedIntent);
        Log.d(TAG,"onhandleItent : finish task");

    }
}
