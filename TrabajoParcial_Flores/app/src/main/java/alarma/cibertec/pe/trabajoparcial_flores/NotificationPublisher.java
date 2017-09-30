package alarma.cibertec.pe.trabajoparcial_flores;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import alarma.cibertec.pe.trabajoparcial_flores.db.TaskContract;
import alarma.cibertec.pe.trabajoparcial_flores.model.Task;

/**
 * Created by USUARIO on 26/05/2017.
 */

public class NotificationPublisher extends BroadcastReceiver {

    Task task;
    int i;
    Context contexto;

    @Override
    public void onReceive(Context context, Intent intent) {

        task  = intent.getParcelableExtra(TaskUtil.TASK);
        contexto = intent.getParcelableExtra("contexto");

        Log.d("task id",""+task.getId());
        Log.d("task title",""+task.getTaskTitle());
        Log.i("My id ::::",""+i);
        if (task == null){
            ArrayList<Task> tareas = (ArrayList<Task>) getTaskList();
            for (Task tarea: tareas) {
                if (tarea.getRemember() == 1){
                    NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

                    Intent repeating_intent = new Intent(context, MainActivity.class);
                    repeating_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    PendingIntent pendingIntent = PendingIntent.getActivity(context,
                            tarea.getId(),
                            repeating_intent,
                            PendingIntent.FLAG_UPDATE_CURRENT);
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                            .setContentIntent(pendingIntent)
                            .setSmallIcon(android.R.drawable.arrow_up_float)
                            .setContentTitle("" + tarea.getTaskTitle())
                            .setContentText("" + tarea.getDay() + tarea.getDay())
                            .setAutoCancel(true);


                    notificationManager.notify(task.getId(), builder.build());
                }
        }

        }else{
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            Intent repeating_intent = new Intent(context, MainActivity.class);
            repeating_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            PendingIntent pendingIntent = PendingIntent.getActivity(context,
                    task.getId(),
                    repeating_intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(android.R.drawable.arrow_up_float)
                    .setContentTitle("" + task.getTaskTitle())
                    .setContentText("" + task.getDay() + task.getDay())
                    .setAutoCancel(true);


            notificationManager.notify(task.getId(), builder.build());
        }








    }


        private List<Task> getTaskList() {
            Cursor cursor = query();
            List<Task> tasks = new ArrayList<>();
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    Task task = cursorToTask(cursor);
                    tasks.add(task);
                }
                cursor.close();
            }
            return tasks;
        }

    private Cursor query() {
        ContentResolver cr = contexto.getContentResolver();
        String[] projection = new String[]{
                //Columnas de la tabla a recuperar
                TaskContract.Task._ID,
                TaskContract.Task.COLUMN_NAME_TITLE,
                TaskContract.Task.COLUMN_NAME_DATE,
                TaskContract.Task.COLUMN_NAME_REMEMBER,
        };
        return cr.query(TaskContract.Task.CONTENT_URI,
                projection, null, null, null);
    }
    private Task cursorToTask(Cursor cursor)  {
        Task task = new Task();
        task.setId(cursor.getInt(0));
        task.setTaskTitle(cursor.getString(1));
        String fecha = cursor.getString(2);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = null;
        try {
            date = formatter.parse(fecha);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        task.setDay(date);
        task.setRemember(cursor.getInt(3));
        return task;
    }
}
