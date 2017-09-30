package pe.cibertec.asynctaskdemo;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    
    private Button btnStart;
    private ProgressBar progressBar;

    private MyAsyncTask myTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStart = (Button) findViewById(R.id.btn_start);
        progressBar = (ProgressBar) findViewById(R.id.progress);

        btnStart.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // Execute task
        myTask = new MyAsyncTask(this);
        myTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myTask.cancel(true);
    }

    private static class MyAsyncTask extends AsyncTask<Void, Void, Void> {

        private final WeakReference<MainActivity> activityRef;

        public MyAsyncTask(MainActivity mainActivity) {
            activityRef = new WeakReference<MainActivity>(mainActivity);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d(TAG, "onPreExecute: ");
            activityRef.get().progressBar.setVisibility(View.VISIBLE);
            activityRef.get().btnStart.setEnabled(false);
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (!isCancelled()) {
                Log.d(TAG, "onPostExecute: ");
                activityRef.get().progressBar.setVisibility(View.GONE);
                activityRef.get().btnStart.setEnabled(true);
            }
        }
    }
}
