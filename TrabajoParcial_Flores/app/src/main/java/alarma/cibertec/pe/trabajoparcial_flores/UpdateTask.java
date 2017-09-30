package alarma.cibertec.pe.trabajoparcial_flores;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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
import java.util.Date;
import java.util.List;

import alarma.cibertec.pe.trabajoparcial_flores.db.TaskContract;
import alarma.cibertec.pe.trabajoparcial_flores.model.Task;

import static alarma.cibertec.pe.trabajoparcial_flores.R.id.contenedor;
import static alarma.cibertec.pe.trabajoparcial_flores.R.id.swtCmpat;

/**
 * Created by JeanCarlo on 08/05/2017.
 */

public class UpdateTask extends Fragment implements View.OnClickListener{

    private boolean isDualPane = false;
    public static final String ARG_TASK= "task";
    private static final String TAG = "UpdateTask";

    private Task task;
    EditText edtTitulo,edtFecha;
    SwitchCompat swtCmpat;
    private List<Task> AddedTasks;
    private String taskToModify;
    ListFragment fragment;
    TaskAdapter taskAdapter ;
    static String BROADCAST_UPDATE_ELEMENTS = "alarma.cibertec.pe.trabajoparcial_flores.UpdateData";
    static String BROADCAST_DELETE_ELEMENTS = "alarma.cibertec.pe.trabajoparcial_flores.DeleteData";

    int remember ;
    static Context contexto;

    public static UpdateTask newInstance(Task task, Context context){
       contexto = context;
        UpdateTask tf = new UpdateTask();
        Bundle args = new Bundle();
        args.putParcelable(ARG_TASK,task);
        tf.setArguments(args);
        return tf;
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
        return inflater.inflate(R.layout.updatefragment,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edtTitulo = (EditText) view.findViewById(R.id.edtTitulo);
        edtFecha = (EditText) view.findViewById(R.id.edtFecha);
        swtCmpat = (SwitchCompat)view.findViewById(R.id.swtCmpat);

        Button btnSave = (Button) view.findViewById(R.id.btnSave);
        Button btnCancel = (Button) view.findViewById(R.id.btnCancel);
        Button btnDelete = (Button) view.findViewById(R.id.btnDelete);


        edtTitulo.setText(task.getTaskTitle().toString());
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String day = sdf.format(task.getDay());
        edtFecha.setText(day);
        if (task.getRemember() == 1){
            swtCmpat.setChecked(true);
        }else{
            swtCmpat.setChecked(false);
            remember = 0;
        }


        btnCancel.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btnDelete.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btnSave :
                if (isDualPane){
                    boolean updateElement=true;
                    Intent broadcastIntent = new Intent();
                    broadcastIntent.setAction(BROADCAST_UPDATE_ELEMENTS);
                    broadcastIntent.putExtra("ElementosModificados", updateElement);
                    contexto.sendBroadcast(broadcastIntent);
                    swtCmpat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (swtCmpat.isChecked()){
                                remember = 1;
                            }else{
                                remember = 0;
                            }
                        }
                    });
                    update(getContentValues(),task.getTaskTitle());

                }else{
                    boolean updateElement=true;
                    Intent broadcastIntent = new Intent();
                    broadcastIntent.setAction(BROADCAST_UPDATE_ELEMENTS);
                    broadcastIntent.putExtra("ElementosModificados", updateElement);
                    contexto.sendBroadcast(broadcastIntent);
                    swtCmpat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (swtCmpat.isChecked()){
                                remember = 1;
                            }else{
                                remember = 0;
                            }
                        }
                    });
                    update(getContentValues(),task.getTaskTitle().trim());
                    getActivity().finish();
                    //// TODO: 27/05/2017 revisar como se obtiene el listener 
                }

                break;
            case R.id.btnDelete :
                if (isDualPane){
                    boolean deleteElement=true;
                    Intent broadcastIntentDelete = new Intent();
                    broadcastIntentDelete.setAction(BROADCAST_DELETE_ELEMENTS);
                    broadcastIntentDelete.putExtra("ElementoEliminado", deleteElement);
                    contexto.sendBroadcast(broadcastIntentDelete);
                    delete(getContentValues(),edtTitulo.getText().toString());
                }else{
                    boolean deleteElement=true;
                    Intent broadcastIntentDelete = new Intent();
                    broadcastIntentDelete.setAction(BROADCAST_DELETE_ELEMENTS);
                    broadcastIntentDelete.putExtra("ElementoEliminado", deleteElement);
                    contexto.sendBroadcast(broadcastIntentDelete);
                    delete(getContentValues(),edtTitulo.getText().toString());
                    getActivity().finish();
                }

                break; 
            case R.id.btnCancel :
                if (isDualPane){
                    FragmentTransaction fragmentManager = getFragmentManager().beginTransaction();
                    if (getTaskList().isEmpty()){
                        Task task = new Task();
                        task.setId(0);
                        task.setTaskTitle("No hay elementos");
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        Date dia = new Date();
                        try {
                            dia = format.parse("0000-00-00 00:00:00");
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        task.setDay(dia);
                        task.setRemember(0);
                        fragmentManager.replace(contenedor,TaskFragment.newInstance(task));
                        fragmentManager.commit();
                    }else{
                        fragmentManager.replace(contenedor,TaskFragment.newInstance(getTaskList().get(0)));
                        fragmentManager.commit();
                    }

                }else{
                    getActivity().finish();
                }


                break;
        }
    }

    private void update(ContentValues contentValues,String titulo) {
        ContentResolver cr = getActivity().getContentResolver();
        long id = busqueda(getTaskList(),titulo);
        int uri = cr.update(TaskContract.Task.CONTENT_URI,contentValues,TaskContract.Task._ID + "=" + id +"",null);
        if (uri != 0) {
            Log.i(TAG, "Updated row id: " + id);
        } else {
            Log.i(TAG, "Uri is null");
        }
    }

    private void delete(ContentValues values,String nombre){
        ContentResolver cr = getActivity().getContentResolver();
        long id =  busqueda(getTaskList(),nombre);
        int uri = cr.delete(TaskContract.Task.CONTENT_URI,TaskContract.Task._ID + " = "+ id +" ",null);
        if (uri != 0) {
            Log.i(TAG, "Deleted row id: " + id);
        } else {
            Log.i(TAG, "Name is null");
        }
    }

    private long busqueda(List<Task> lista,String nombre) {
        long idEncontrado=0;
        for (Task task: lista) {
            if(task.getTaskTitle().trim().equals(nombre)) {
                idEncontrado=task.getId();
                break;
            }else{
                Log.i(TAG, "ERROR NO SE ENCUENTRA EL TITULO");
            }
        }
        return idEncontrado;
    }

    private List<Task> getTaskList() {
        Cursor cursor = query();
        List<Task> contactList = new ArrayList<>();
        if (cursor != null) {
            while(cursor.moveToNext()) {
                Task contact = cursorToTask(cursor);
                contactList.add(contact);
            }
            cursor.close();
        }
        return contactList;
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

    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(TaskContract.Task.COLUMN_NAME_TITLE, edtTitulo.getText().toString().trim());
        values.put(TaskContract.Task.COLUMN_NAME_DATE, edtFecha.getText().toString().trim());
        values.put(TaskContract.Task.COLUMN_NAME_REMEMBER,remember);
        return values;
    }


}
