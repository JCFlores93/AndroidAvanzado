package pe.cibertec.notifications;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity
        implements AdapterView.OnItemClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0:
                showBasicNotification();
                break;
            case 1:
                showBasicNotificationWithbackStack();
                break;
            case 2:
                showBasicNotificationWithActions();
                break;
            case 3:
                showBasicNotificationWithBigTextStyle();
                break;
        }
    }

    private void showBasicNotification() {
       Intent resultIntent = new Intent(this,ResultActivity.class);

        //Espera hasta ser lanzado.
        PendingIntent resultPendingIntent = PendingIntent.getActivity(
                this,0, resultIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        /* NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_notification)
                .setColor(Color.parseColor("#FF00FF00"));*/

        NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_notification)
                .setColor(Color.parseColor("#0000FF"))
                .setContentTitle("My Notification")
                .setContentText("Basic Notification")
                //Sirve para setear to-do sonido y vibración
                .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(Notification.PRIORITY_HIGH)
                .setContentIntent(resultPendingIntent)
                //Nos permite cerrar la notificacion al acceder a ella
                .setAutoCancel(true);


        Notification notification = builder.build() ;
        NotificationManagerCompat notificationManafer =
                NotificationManagerCompat.from(this);

        notificationManafer.notify(1,notification);
    }

    private void showBasicNotificationWithbackStack() {
        //Nos permite regresar al activity anterior

        Intent resultIntent = new Intent(this,ResultActivity.class);

        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(this);
        taskStackBuilder.addParentStack(ResultActivity.class);
        taskStackBuilder.addNextIntent(resultIntent);

        //Espera hasta ser lanzado.
        PendingIntent resultPendingIntent = taskStackBuilder.getPendingIntent(
                0,PendingIntent.FLAG_UPDATE_CURRENT);

        /* NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_notification)
                .setColor(Color.parseColor("#FF00FF00"));*/

        NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_notification)
                .setColor(Color.parseColor("#0000FF"))
                .setContentTitle("My Notification")
                .setContentText("Basic Notification")
                //Sirve para setear to-do sonido y vibración
                .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(Notification.PRIORITY_HIGH)
                .setContentIntent(resultPendingIntent)
                //Nos permite cerrar la notificacion al acceder a ella
                .setAutoCancel(true);



        Notification notification = builder.build() ;
        NotificationManagerCompat notificationManafer =
                NotificationManagerCompat.from(this);

        notificationManafer.notify(2,notification);
    }

    private void showBasicNotificationWithActions() {
         final int notificationId = 3;
        // Para darle acciones
        Intent resultIntent = new Intent(this,ResultActivity.class);
        resultIntent.putExtra("notification_id",notificationId);

        Intent dismissIntent = new Intent(this,DismissReciever.class);
        dismissIntent.putExtra("notification_id",notificationId);

        //Espera hasta ser lanzado.
        PendingIntent resultPendingIntent = PendingIntent.getActivity(
                this,0, resultIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        PendingIntent dismissPendingIntent = PendingIntent.getBroadcast(
                this,0, dismissIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        /* NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_notification)
                .setColor(Color.parseColor("#FF00FF00"));*/

        NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_notification)
                .setColor(Color.parseColor("#0000FF"))
                .setContentTitle("My Notification")
                .setContentText("Basic Notification")
                //Sirve para setear to-do sonido y vibración
                .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(Notification.PRIORITY_HIGH)
                .setContentIntent(resultPendingIntent)
                //Nos permite cerrar la notificacion al acceder a ella
                //Auto cancel funciona siempre cuando se toca la notificacion pero no los botones
                .setAutoCancel(true)
                .setContentIntent(resultPendingIntent)
                .addAction(R.drawable.ic_notification,"Open",resultPendingIntent)
                .addAction(R.drawable.ic_notification,"Dismiss",dismissPendingIntent);


        Notification notification = builder.build() ;
        NotificationManagerCompat notificationManafer =
                NotificationManagerCompat.from(this);
        //Lo necesitamos para manejar la notificacion
        notificationManafer.notify(3,notification);
    }

    private void showBasicNotificationWithBigTextStyle() {
        Intent resultIntent = new Intent(this,ResultActivity.class);

        //Espera hasta ser lanzado.
        PendingIntent resultPendingIntent = PendingIntent.getActivity(
                this,0, resultIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        /* NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_notification)
                .setColor(Color.parseColor("#FF00FF00"));*/

        NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_notification)
                .setColor(Color.parseColor("#0000FF"))
                .setContentTitle("My Notification")
                .setContentText("Big Text Notification")
                //Sirve para setear to-do sonido y vibración
                .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(Notification.PRIORITY_HIGH)
                .setContentIntent(resultPendingIntent)
                //Nos permite cerrar la notificacion al acceder a ella
                .setAutoCancel(true)
                .setStyle(new NotificationCompat.BigTextStyle()
                .bigText("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam vel venenatis purus." +
                        " Nunc in vestibulum urna. Morbi laoreet, augue eget pellentesque sodales, turpis diam " +
                        "tincidunt diam, ac gravida risus nunc a metus. Integer quis purus ut nibh malesuada pharetra." +
                        " Nunc luctus diam non purus hendrerit tempus. Nullam in quam nec augue tincidunt volutpat eget" +
                        " quis nisl. Cras venenatis ornare sodales. Suspendisse faucibus turpis at rhoncus convallis." +
                        " Duis gravida molestie ex, in dignissim dolor. Nam tempus tellus quis magna dignissim," +
                        " non blandit tellus lacinia. In hac habitasse platea dictumst. Integer auctor quam quis " +
                        "ultrices mattis. Proin feugiat purus libero, et posuere augue maximus et. Integer lobortis " +
                        "consequat volutpat. Proin ante ex, ultricies vehicula urna sed, lobortis vestibulum nunc.")
                .setBigContentTitle("Big text title")
                .setSummaryText("Big Text Summary"));


        Notification notification = builder.build() ;
        NotificationManagerCompat notificationManafer =
                NotificationManagerCompat.from(this);

        notificationManafer.notify(4,notification);
    }

}
