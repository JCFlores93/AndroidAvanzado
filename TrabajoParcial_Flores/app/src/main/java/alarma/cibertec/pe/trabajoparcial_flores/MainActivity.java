package alarma.cibertec.pe.trabajoparcial_flores;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import alarma.cibertec.pe.trabajoparcial_flores.db.TaskContract;
import alarma.cibertec.pe.trabajoparcial_flores.model.Task;



public class MainActivity extends AppCompatActivity
        implements ListFragment.OnTaskClickListener{


    private boolean isDualPane = false;
    Task task,modifiedTask,taskToModified;
    private Button btnNew,btnUpdate,btnCancel;
    private String option="new";
    private static final String TAG = "MainActivity";
    private NotificationPublisher notificationPublisher ;
    ArrayList<Task> tareas;

    private IntentFilter filterPosicion ;
    private IntentFilter filterUpdate;
    private IntentFilter filterDelete;
    static String BROADCAST_ELEMENTS = "alarma.cibertec.pe.trabajoparcial_flores.NewData";
    static String BROADCAST_UPDATE_ELEMENTS = "alarma.cibertec.pe.trabajoparcial_flores.UpdateData";
    static String BROADCAST_DELETE_ELEMENTS = "alarma.cibertec.pe.trabajoparcial_flores.DeleteData";
    private myReceiver myReceiver = new myReceiver();
    private myReceiverUpdate myReceiverUpdate = new myReceiverUpdate();
    private myReceiverDelete myReceiverDelete = new myReceiverDelete();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isDualPane = getResources().getBoolean(R.bool.dual_pane);

        btnNew = (Button) findViewById(R.id.btnNew);

        btnUpdate = (Button) findViewById(R.id.btnUpdate);


       notificationPublisher = new NotificationPublisher();
        tareas = (ArrayList<Task>) getTaskList();
        if (tareas.size() == 0){
            btnUpdate.setVisibility(View.GONE);
            btnUpdate.setEnabled(false);
            btnUpdate.setClickable(false);

        }else{
            btnUpdate.setVisibility(View.VISIBLE);
            btnUpdate.setEnabled(true);
            btnUpdate.setClickable(true);


        }



        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDualPane){
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.contenedor,TaskNew.newInstance(null,getBaseContext()));
                    ft.commit();
                }else{
                    //todo ver porque se cae en celular y no en tablet
                    option = "new";
                    Intent intentTaskActivity = new Intent(getBaseContext(),TaskActivity.class);
                    if (task == null){
                        intentTaskActivity.putExtra(TaskActivity.EXTRA_TASK, task);
                        intentTaskActivity.putExtra(TaskActivity.EXTRA_OPTION,option);
                        startActivity(intentTaskActivity);
                    }else{
                        intentTaskActivity.putExtra(TaskActivity.EXTRA_TASK, getTaskList().get(0));
                        intentTaskActivity.putExtra(TaskActivity.EXTRA_OPTION,option);
                        startActivity(intentTaskActivity);
                    }

                }

            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDualPane){
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    if (tareas.size() != 0) {
                        taskToModified = tareas.get(0);

                        ft.replace(R.id.contenedor,UpdateTask.newInstance(taskToModified,getBaseContext()));
                        ft.commit();
                    }else{
                        if (task == null){
                            ft.replace(R.id.contenedor,UpdateTask.newInstance(getTaskList().get(0),getBaseContext()));
                            ft.commit();
                        }else{
                            ft.replace(R.id.contenedor,UpdateTask.newInstance(task,getBaseContext()));
                            ft.commit();
                        }


                    }

                }else{
                    option = "update";
                    if (tareas.size() != 0) {
                        taskToModified = tareas.get(0);
                    }
                    Intent intent = new Intent(getBaseContext(),TaskActivity.class);
                    intent.putExtra(TaskActivity.EXTRA_TASK,taskToModified);
                    intent.putExtra(TaskActivity.EXTRA_OPTION,option);
                    startActivity(intent);
                }
            }
        });





    }

    @Override
    public void onTaskClick(Task task) {
        if (isDualPane){
            this.task= task;
            this.modifiedTask = task;
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.contenedor, TaskFragment.newInstance(task));
            ft.commit();
        }else{
            Intent intent = new Intent(this, TaskActivity.class);
            intent.putExtra(TaskActivity.EXTRA_TASK, task);
            intent.putExtra(TaskActivity.EXTRA_OPTION,"mostrar");
            startActivity(intent);
        }
    }


    @Override
    protected void onStart() {

        super.onStart();
        if (isDualPane)
        {
            filterPosicion = new IntentFilter(BROADCAST_ELEMENTS);
            filterUpdate = new IntentFilter(BROADCAST_UPDATE_ELEMENTS);
            filterDelete = new IntentFilter(BROADCAST_DELETE_ELEMENTS);


            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            Task task = new Task();
            task.setTaskTitle("Contact "+0);
            Calendar calendar = Calendar.getInstance();
            task.setDay(calendar.getTime());
            TaskFragment fragmentDemo = TaskFragment.newInstance(task);
            ft.replace(R.id.contenedor,fragmentDemo);
            ft.commit();
        }else{
            filterPosicion = new IntentFilter(BROADCAST_ELEMENTS);
            filterUpdate = new IntentFilter(BROADCAST_UPDATE_ELEMENTS);
            filterDelete = new IntentFilter(BROADCAST_DELETE_ELEMENTS);

            registerReceiver(myReceiver, filterPosicion);
            registerReceiver(myReceiverUpdate,filterUpdate);
            registerReceiver(myReceiverDelete,filterDelete);
           /* Intent intent = new Intent(this, TaskActivity.class);
            intent.putExtra(TaskActivity.EXTRA_TASK, task);
            startActivity(intent);*/
        }

    }

    @Override
    protected void onResume() {

        super.onResume();
        if (isDualPane){
            registerReceiver(myReceiver, filterPosicion);
            registerReceiver(myReceiverUpdate,filterUpdate);
            registerReceiver(myReceiverDelete,filterDelete);

        }else{
            registerReceiver(myReceiver, filterPosicion);
            registerReceiver(myReceiverUpdate,filterUpdate);
            registerReceiver(myReceiverDelete,filterDelete);

            tareas = (ArrayList<Task>) getTaskList();
            if (tareas.size() == 0){
                btnUpdate.setVisibility(View.GONE);
                btnUpdate.setEnabled(false);
                btnUpdate.setClickable(false);

            }else{
                btnUpdate.setVisibility(View.VISIBLE);
                btnUpdate.setEnabled(true);
                btnUpdate.setClickable(true);


            }

        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(myReceiver);
        unregisterReceiver(myReceiverUpdate);
        unregisterReceiver(myReceiverDelete);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    List<Task> getTaskList() {
        Cursor cursor = query();
        List<Task> taskList = new ArrayList<>();
        if (cursor != null){
            while(cursor.moveToNext()){
                Task task = cursorToTask(cursor);
                taskList.add(task);
            }
            cursor.close();
        }
        return  taskList;
    }

    private Task cursorToTask(Cursor cursor) {
        Task task = new Task();
        task.setId(cursor.getInt(0)) ;
        task.setTaskTitle(cursor.getString(1));
        task.setRemember(cursor.getInt(3));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = null;
        try {
            date = dateFormat.parse(cursor.getString(2));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        task.setDay(date);

        return task;
    }


    private Cursor query() {

        ContentResolver cr = this.getContentResolver();
        String[] projection = new String[]{
                //Columnas de la tabla a recuperar
                TaskContract.Task._ID,
                TaskContract.Task.COLUMN_NAME_TITLE,
                TaskContract.Task.COLUMN_NAME_DATE,
                TaskContract.Task.COLUMN_NAME_REMEMBER
        };
        return cr.query(TaskContract.Task.CONTENT_URI,
                projection, null, null, null);
    }


    class myReceiver extends BroadcastReceiver {
        @Override public void onReceive(Context context, Intent intent) {
            boolean newElement = false;
            newElement = intent.getBooleanExtra("NuevosElementos",false);

            boolean updateElement = false;
            updateElement = intent.getBooleanExtra("ElementosModificados",false);

            boolean deleteElement = false;
            deleteElement = intent.getBooleanExtra("ElementoEliminado",false);



            if (newElement &&  !getTaskList().isEmpty()){
                Log.e(TAG, "Nuevo elementos para la lista ::::: " + newElement );
                btnUpdate.setVisibility(View.VISIBLE);
                btnUpdate.setEnabled(true);
                btnUpdate.setClickable(true);
            }
            if (updateElement &&  !getTaskList().isEmpty()){
                Log.e(TAG, "Nuevo elementos modificado para la lista ::::: " + updateElement );
                btnUpdate.setVisibility(View.VISIBLE);
                btnUpdate.setEnabled(true);
                btnUpdate.setClickable(true);
            }
            if (deleteElement &&  !getTaskList().isEmpty()){
                Log.e(TAG, "Elemento Eliminado de la lista ::::: " + deleteElement );
                btnUpdate.setVisibility(View.VISIBLE);
                btnUpdate.setEnabled(true);
                btnUpdate.setClickable(true);
            }
        }
    }


    class myReceiverUpdate extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean newElement = false;
            newElement = intent.getBooleanExtra("NuevosElementos",false);

            boolean updateElement = false;
            updateElement = intent.getBooleanExtra("ElementosModificados",false);

            boolean deleteElement = false;
            deleteElement = intent.getBooleanExtra("ElementoEliminado",false);



            if (newElement &&  !getTaskList().isEmpty()){
                Log.e(TAG, "Nuevo elementos para la lista ::::: " + newElement );
                btnUpdate.setVisibility(View.VISIBLE);
                btnUpdate.setEnabled(true);
                btnUpdate.setClickable(true);
            }
            if (updateElement &&  !getTaskList().isEmpty()){
                Log.e(TAG, "Nuevo elementos modificado para la lista ::::: " + updateElement );
                btnUpdate.setVisibility(View.VISIBLE);
                btnUpdate.setEnabled(true);
                btnUpdate.setClickable(true);
            }
            if (deleteElement &&  !getTaskList().isEmpty()){
                Log.e(TAG, "Elemento Eliminado de la lista ::::: " + deleteElement );
                btnUpdate.setVisibility(View.VISIBLE);
                btnUpdate.setEnabled(true);
                btnUpdate.setClickable(true);
            }
        }
    }

    class myReceiverDelete extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean newElement = false;
            newElement = intent.getBooleanExtra("NuevosElementos",false);

            boolean updateElement = false;
            updateElement = intent.getBooleanExtra("ElementosModificados",false);

            boolean deleteElement = false;
            deleteElement = intent.getBooleanExtra("ElementoEliminado",false);



            if (newElement &&  !getTaskList().isEmpty()){
                Log.e(TAG, "Nuevo elementos para la lista ::::: " + newElement );
                btnUpdate.setVisibility(View.VISIBLE);
                btnUpdate.setEnabled(true);
                btnUpdate.setClickable(true);
            }
            if (updateElement &&  !getTaskList().isEmpty()){
                Log.e(TAG, "Nuevo elementos modificado para la lista ::::: " + updateElement );
                btnUpdate.setVisibility(View.VISIBLE);
                btnUpdate.setEnabled(true);
                btnUpdate.setClickable(true);
            }
            if (deleteElement &&  !getTaskList().isEmpty()){
                Log.e(TAG, "Elemento Eliminado de la lista ::::: " + deleteElement );
                btnUpdate.setVisibility(View.VISIBLE);
                btnUpdate.setEnabled(true);
                btnUpdate.setClickable(true);
            }
        }
    }
    }











