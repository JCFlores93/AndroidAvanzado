package pe.cibertec.asynctask;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String TASK= "MainActivity";
    MyAsynTask myTask;

    public Button btnStart;
    public ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStart = (Button) findViewById(R.id.btnStart);
        progressBar = (ProgressBar) findViewById(R.id.progress);

        btnStart.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        myTask = new MyAsynTask(this);
        myTask.execute();
        //myTask.executeOnExecutor(AsynTask.THREAD_POOL_EXECUTOR);
        //Execute Task
     }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myTask.cancel(true);
    }

    private static class MyAsynTask extends AsyncTask<Void,Void,Void>{

        private final WeakReference<MainActivity> activityRef ;

        public MyAsynTask(MainActivity mainActivity){

            activityRef = new WeakReference<MainActivity>(mainActivity);

        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           // Log.d();
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
            //Si no ha sido cancelado
            if (!isCancelled()){
                activityRef.get().progressBar.setVisibility(View.GONE);
                activityRef.get().btnStart.setEnabled(true);
            }
        }
    }



}
