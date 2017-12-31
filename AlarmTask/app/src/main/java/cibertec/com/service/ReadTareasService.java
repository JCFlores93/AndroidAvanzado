package cibertec.com.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cibertec.com.apptareaszumaran.R;
import cibertec.com.db.DataBaseTareas;
import cibertec.com.db.TareasContract;
import cibertec.com.model.Tarea;
import cibertec.com.util.Constante;

public class ReadTareasService extends IntentService {
    private DataBaseTareas db;
    Handler mHandler = new Handler();
    Runnable mHandlerTask;
    private int ID=0;
    public ReadTareasService() {
        super("ReadTareasService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        db = new DataBaseTareas(this);
        Intent notify = new Intent();
        mHandlerTask = new Runnable(){
            @Override
            public void run() {
                Calendar cal = Calendar.getInstance();
                List<Tarea> tareas = getListaTareas();
                if(tareas!=null){
                    if(tareas.size()>0){
                        Log.e("TAG","ENTRAMOS!");
                        for(Tarea t: tareas) {
                            if(t.getRemember()== Constante.RECORDAR_TRUE) {
                                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                                try {
                                    Date time = formatter.parse(t.getDate_time());
                                    Calendar auxTime = Calendar.getInstance();
                                    auxTime.setTime(time);
                                    Log.e("TAG","time tarea:"+time.toString()+" now:"+cal.getTime().toString());

                                    if (cal.get(Calendar.DAY_OF_MONTH) == auxTime.get(Calendar.DAY_OF_MONTH) &&
                                            cal.get(Calendar.MONTH) == auxTime.get(Calendar.MONTH) &&
                                            cal.get(Calendar.YEAR) == auxTime.get(Calendar.YEAR) &&
                                            cal.get(Calendar.HOUR_OF_DAY) == auxTime.get(Calendar.HOUR_OF_DAY) &&
                                            cal.get(Calendar.MINUTE) == auxTime.get(Calendar.MINUTE)) {
                                        Log.e("TAG","showNotification");
                                        ID++;
                                        showNotification(t.getTitle(),ID);
                                    }
                                } catch (ParseException ex) {}
                            }
                        }
                    }
                }
                mHandler.postDelayed(mHandlerTask, 1000*60);
            }
        };
        mHandlerTask.run();
    }

    private List<Tarea> getListaTareas(){
        List<Tarea> tareas = new ArrayList<>();
        String[] projection = new String[]{
                TareasContract.Tarea._ID,
                TareasContract.Tarea.COLUMN_NAME_TITLE,
                TareasContract.Tarea.COLUMN_NAME_DATE_TIME,
                TareasContract.Tarea.COLUMN_NAME_REMEMBER
        };

        Cursor cursor = db.listarTareas(projection);
        if(cursor!=null){
            while (cursor.moveToNext()){
                Tarea tarea = cursorToTarea(cursor);
                tareas.add(tarea);
            }
            cursor.close();
        }
        return tareas;
    }

    private Tarea cursorToTarea(Cursor cursor){
        Tarea tarea = new Tarea();
        tarea.setId(cursor.getLong(0));
        tarea.setTitle(cursor.getString(1));
        tarea.setDate_time(cursor.getString(2));
        tarea.setRemember(cursor.getInt(3));
        return tarea;
    }

    private void showNotification(String mensaje, int id){
        //TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(this);
        //taskStackBuilder.addParentStack(ResultActivity.class);
        //taskStackBuilder.addNextIntent(resultIntent);
        //PendingIntent resultPendingIntent = taskStackBuilder.getPendingIntent(
        //        0, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setDefaults(Notification.DEFAULT_ALL)
                .setSmallIcon(R.drawable.ic_schedule_black_24dp)
                .setColor(Color.parseColor("#0000FF"))
                .setContentTitle("Notification de Tarea")
                .setContentText(mensaje)
                .setAutoCancel(true);
        Notification notification = builder.build();

        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(this);
        notificationManager.notify(id, notification);
    }
}
