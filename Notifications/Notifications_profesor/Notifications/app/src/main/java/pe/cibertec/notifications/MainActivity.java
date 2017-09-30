package pe.cibertec.notifications;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity
        implements AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                showBasicNotification();
                break;
            case 1:
                showBasicNotificationWithBackStack();
                break;
            case 2:
                showNotificationWithActions();
                break;
            case 3:
                showNotificationWithBigTextStyle();
                break;
        }
    }

    private void showBasicNotification() {
        Intent resultIntent = new Intent(this, ResultActivity.class);

        PendingIntent resultPendingIntent = PendingIntent.getActivity(
                this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(Notification.PRIORITY_HIGH)
                .setSmallIcon(R.drawable.ic_notification)
                .setColor(Color.parseColor("#0000FF"))
                .setContentTitle("My Notification")
                .setContentText("Basic Notification")
                .setAutoCancel(true)
                .setContentIntent(resultPendingIntent);

        Notification notification = builder.build();

        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(this);
        notificationManager.notify(1, notification);
    }

    private void showBasicNotificationWithBackStack() {
        Intent resultIntent = new Intent(this, ResultActivity.class);

        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(this);
        taskStackBuilder.addParentStack(ResultActivity.class);
        taskStackBuilder.addNextIntent(resultIntent);

        PendingIntent resultPendingIntent = taskStackBuilder.getPendingIntent(
                0, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(Notification.PRIORITY_HIGH)
                .setSmallIcon(R.drawable.ic_notification)
                .setColor(Color.parseColor("#0000FF"))
                .setContentTitle("My Notification")
                .setContentText("Basic Notification with back stack")
                .setAutoCancel(true)
                .setContentIntent(resultPendingIntent);

        Notification notification = builder.build();

        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(this);
        notificationManager.notify(2, notification);
    }

    private void showNotificationWithActions() {
        final int notificationId = 3;
        Intent resultIntent = new Intent(this, ResultActivity.class);
        resultIntent.putExtra("notification_id", notificationId);

        Intent dismissIntent = new Intent(this, DismissReceiver.class);
        dismissIntent.putExtra("notification_id", notificationId);

        PendingIntent resultPendingIntent = PendingIntent.getActivity(
                this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        PendingIntent dismissPendingIntent = PendingIntent.getBroadcast(
                this, 0, dismissIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(Notification.PRIORITY_HIGH)
                .setSmallIcon(R.drawable.ic_notification)
                .setColor(Color.parseColor("#0000FF"))
                .setContentTitle("My Notification")
                .setContentText("Notification with actions")
                .setAutoCancel(true)
                .setContentIntent(resultPendingIntent)
                .addAction(R.drawable.ic_notification, "Open", resultPendingIntent)
                .addAction(R.drawable.ic_notification, "Dismiss", dismissPendingIntent);

        Notification notification = builder.build();

        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(this);
        notificationManager.notify(notificationId, notification);
    }

    private void showNotificationWithBigTextStyle() {
        Intent resultIntent = new Intent(this, ResultActivity.class);

        PendingIntent resultPendingIntent = PendingIntent.getActivity(
                this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(Notification.PRIORITY_HIGH)
                .setSmallIcon(R.drawable.ic_notification)
                .setColor(Color.parseColor("#0000FF"))
                .setContentTitle("My Notification")
                .setContentText("Big Text Notification")
                .setStyle(new NotificationCompat.BigTextStyle()
                    .bigText("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc fermentum, purus ac interdum posuere, augue sapien consequat felis, et interdum est ante in nisl. Fusce gravida porta venenatis. Nam orci nisl, dignissim vel sodales et, porta quis augue. Donec in risus non sem laoreet mollis id vel nisi. In euismod, leo vel blandit vestibulum, eros ante varius urna, in tincidunt eros nunc a risus. Vivamus pretium gravida mauris, non egestas urna placerat quis. In placerat arcu risus, sit amet cursus orci suscipit nec.")
                    .setBigContentTitle("Big text title")
                    .setSummaryText("Big text summary"))
                .setAutoCancel(true)
                .setContentIntent(resultPendingIntent);

        Notification notification = builder.build();

        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(this);
        notificationManager.notify(4, notification);
    }
}
