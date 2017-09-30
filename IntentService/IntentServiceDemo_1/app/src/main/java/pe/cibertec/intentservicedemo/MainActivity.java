package pe.cibertec.intentservicedemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    private Button btnStart;
    private ProgressBar progressBar;

    private MyReceiver myReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStart = (Button) findViewById(R.id.btn_start);
        progressBar = (ProgressBar) findViewById(R.id.progress);

        btnStart.setOnClickListener(this);

        myReceiver = new MyReceiver();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Create intent filter
        IntentFilter filter = new IntentFilter();
        filter.addAction(MyIntentService.ACTION_TASK_STARTED);
        filter.addAction(MyIntentService.ACTION_TASK_FINISHED);

        // Register receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(myReceiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Unregister receiver
        LocalBroadcastManager.getInstance(this).unregisterReceiver(myReceiver);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, MyIntentService.class);
        startService(intent);
    }

    private class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (TextUtils.equals(action, MyIntentService.ACTION_TASK_STARTED)) {
                // Task started
                Log.d(TAG, "onReceive: task stared");
                progressBar.setVisibility(View.VISIBLE);
                btnStart.setEnabled(false);
            } else if (TextUtils.equals(action, MyIntentService.ACTION_TASK_FINISHED)) {
                // Task finished
                Log.d(TAG, "onReceive: task finished");
                progressBar.setVisibility(View.GONE);
                btnStart.setEnabled(true);
            }
        }
    }
}
