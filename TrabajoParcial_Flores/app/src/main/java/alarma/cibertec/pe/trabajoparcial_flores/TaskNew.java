package alarma.cibertec.pe.trabajoparcial_flores;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import alarma.cibertec.pe.trabajoparcial_flores.db.TaskContract;
import alarma.cibertec.pe.trabajoparcial_flores.model.Task;

import static alarma.cibertec.pe.trabajoparcial_flores.R.id.contenedor;
import static alarma.cibertec.pe.trabajoparcial_flores.R.id.swtCmpat;

/**
 * Created by user on 06/05/2017.
 */



public class TaskNew extends Fragment
        implements View.OnClickListener{

    public static final String ARG_TASK = "task";
    private static final String TAG = "TaskNew";
    static String BROADCAST_ELEMENTS = "alarma.cibertec.pe.trabajoparcial_flores.NewData";

    private Task task;
    EditText edtTitulo, edtFecha;
    SwitchCompat swtCompat;
    int remember ;
    static Context contexto ;
    private boolean isDualPane = false;


    public static TaskNew newInstance(Task task,Context context) {
        contexto = context;
        TaskNew tf = new TaskNew();
        Bundle args = new Bundle();
        args.putParcelable(ARG_TASK, task);
        tf.setArguments(args);
        return tf;
    }

    public TaskNew() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            task = getArguments().getParcelable(ARG_TASK);

        }
        isDualPane = getResources().getBoolean(R.bool.dual_pane);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.newfragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edtTitulo = (EditText) view.findViewById(R.id.edtTitulo);
        edtFecha = (EditText) view.findViewById(R.id.edtFecha);
        swtCompat = (SwitchCompat) view.findViewById(R.id.swtCmpat);
        swtCompat.setChecked(true);
        if(swtCompat.isChecked()){
            remember = 1;
        }else{
            remember = 0;
        }

        Button btnSave = (Button) view.findViewById(R.id.btnSave);
        Button btnCancel = (Button) view.findViewById(R.id.btnCancel);

        btnCancel.setOnClickListener(this);
        btnSave.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSave:
                if (isDualPane){

                    swtCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (swtCompat.isChecked()){
                                remember = 1;
                            }else{
                                remember = 0;
                            }
                        }
                    });
                    insert(getContentValues());
                    boolean newElement=true;
                    Intent broadcastIntent = new Intent();
                    broadcastIntent.setAction(BROADCAST_ELEMENTS);
                    broadcastIntent.putExtra("NuevosElementos", newElement);
                    contexto.sendBroadcast(broadcastIntent);
                    FragmentTransaction fragmentManager = getFragmentManager().beginTransaction();
                    fragmentManager.replace(contenedor, TaskFragment.newInstance(getTaskList().get(0)));
                    fragmentManager.commit();

                }else{
                    swtCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (swtCompat.isChecked()){
                                remember = 1;
                            }else{
                                remember = 0;
                            }
                        }
                    });
                    insert(getContentValues());
                    boolean newElement=true;
                    Intent broadcastIntent = new Intent();
                    broadcastIntent.setAction(BROADCAST_ELEMENTS);
                    broadcastIntent.putExtra("NuevosElementos", newElement);
                    contexto.sendBroadcast(broadcastIntent);
                    getActivity().finish();
                }


                break;

            case R.id.btnCancel:
                if (isDualPane){
                    FragmentTransaction fragmentManager = getFragmentManager().beginTransaction();
                    fragmentManager.replace(contenedor, TaskFragment.newInstance(getTaskList().get(0)));
                    fragmentManager.commit();
                }else{
                    getActivity().finish();

                }

                break;

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

    private Task cursorToTask(Cursor cursor) {
        Task task = new Task();
        task.setId(cursor.getInt(0));
        task.setTaskTitle(cursor.getString(1));
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
        ContentResolver cr = getContext().getContentResolver();
        String[] projection = new String[]{
                //Columnas de la tabla a recuperar
                TaskContract.Task._ID,
                TaskContract.Task.COLUMN_NAME_TITLE,
                TaskContract.Task.COLUMN_NAME_DATE
        };
        return cr.query(TaskContract.Task.CONTENT_URI,
                projection, null, null, null);
    }

    private void insert(ContentValues values) {
        ContentResolver cr = getActivity().getContentResolver();
        Uri uri = cr.insert(TaskContract.Task.CONTENT_URI, values);
        if (uri != null) {
            Log.i(TAG, "Inserted row id: " + uri.getLastPathSegment());
        } else {
            Log.i(TAG, "Uri is null");
        }
    }


    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(TaskContract.Task.COLUMN_NAME_TITLE, edtTitulo.getText().toString().trim());
        values.put(TaskContract.Task.COLUMN_NAME_DATE, edtFecha.getText().toString().trim());
        values.put(TaskContract.Task.COLUMN_NAME_REMEMBER , remember);
        return values;
    }





}
