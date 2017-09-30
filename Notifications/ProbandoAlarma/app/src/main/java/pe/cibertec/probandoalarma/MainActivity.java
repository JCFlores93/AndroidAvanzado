package pe.cibertec.probandoalarma;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnFire;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnFire = (Button) findViewById(R.id.btnFire);
        btnFire.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
       switch (v.getId()){
            case (R.id.btnFire) :
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY,15);
                calendar.set(Calendar.MINUTE,36);
               // calendar.set(Calendar.SECOND,00);
                Intent intent = new Intent(getApplicationContext(),
                        Notification_Receiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        getApplicationContext(),
                        100,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager)
                        getSystemService(ALARM_SERVICE);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                        calendar.getTimeInMillis(),
                        AlarmManager.INTERVAL_DAY,
                        pendingIntent);

                break;
        }
    }
}
