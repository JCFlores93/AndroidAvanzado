package pe.cibertec.notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationManagerCompat;

/**
 * Created by Android on 13/05/2017.
 */

public class DismissReciever extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        //Cerramos la notificacion
        final int notification = intent.getIntExtra("notification_id", 0);

        NotificationManagerCompat notificationManagerCompat =
                NotificationManagerCompat.from(context);
        notificationManagerCompat.cancel(notification);

        //Con esto podemos quitar la notificacion de la barra de notificaciones
    }
}
