package alarma.cibertec.pe.trabajoparcial_flores;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;

import alarma.cibertec.pe.trabajoparcial_flores.model.Task;

/**
 * Created by USUARIO on 26/05/2017.
 */

public class NotificationReceiver extends BroadcastReceiver{

    public static final String TAG = "DESDE TASK ADAPTER";
    @Override
    public void onReceive(Context context, Intent intent) {

       /* boolean launch = intent.getBooleanExtra(TaskUtil.LAUNCHED,false);
        //Task task = intent.getTaskExtras(TaskUtil.TASK);
        Task task = intent.getParcelableExtra(TaskUtil.TASK);

        NotificationManager notificationManager = (NotificationManager)context.
                getSystemService(Context.NOTIFICATION_SERVICE);

        Log.e(TAG, "La notificación se lanzará : " + launch );

        if (launch) {
            // Task started
            Intent repeating_intent = new Intent(context,MainActivity.class);
            repeating_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            PendingIntent pendingIntent = PendingIntent.getActivity(context,
                    (int) task.getId(),
                    repeating_intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(android.R.drawable.arrow_up_float)
                    .setContentTitle("Notification Title")
                    .setContentText("Notification text")
                    .setAutoCancel(true);

        Notification notification = intent.getParcelableExtra(TaskUtil.NOTIFICATION);
        int id = intent.getIntExtra(TaskUtil.NOTIFICATION,0);

            notificationManager.notify((int) task.getId(),builder.build());


            Log.d(TAG, "onReceive: task stared");

        } else if (!TextUtils.equals("Finalizado", "Finalizado")) {
            // Task finished
            Log.d(TAG, "onReceive: task finished");

        }*/

    }
}
