package alarma.cibertec.pe.trabajoparcial_flores;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import alarma.cibertec.pe.trabajoparcial_flores.db.TaskContract;
import alarma.cibertec.pe.trabajoparcial_flores.model.Task;

/**
 * Created by USUARIO on 23/05/2017.
 */

public class ListFragment extends Fragment implements TaskAdapter.OnItemClickListener {

    private OnTaskClickListener mListener;
    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter;
    private List<Task> listaTareas ;
    public static final String TAG ="ListFragment";
    private IntentFilter filterPosicion ;
    private IntentFilter filterUpdate;
    private IntentFilter filterDelete;
    static String BROADCAST_ELEMENTS = "alarma.cibertec.pe.trabajoparcial_flores.NewData";
    static String BROADCAST_UPDATE_ELEMENTS = "alarma.cibertec.pe.trabajoparcial_flores.UpdateData";
    static String BROADCAST_DELETE_ELEMENTS = "alarma.cibertec.pe.trabajoparcial_flores.DeleteData";
    private myReceiver  myReceiver = new myReceiver();
    private myReceiverUpdate  myReceiverUpdate = new myReceiverUpdate();
    private myReceiverDelete  myReceiverDelete = new myReceiverDelete();


    public ListFragment(){
        //It's required by Android in its creation
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_fragment,container,false);
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        filterPosicion = new IntentFilter(BROADCAST_ELEMENTS);
        filterUpdate = new IntentFilter(BROADCAST_UPDATE_ELEMENTS);
        filterDelete = new IntentFilter(BROADCAST_DELETE_ELEMENTS);

         listaTareas = getTaskList();

        taskAdapter = new TaskAdapter(listaTareas, this,getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(taskAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        listaTareas = getTaskList();
        Log.i("lista.size() onStart:",""+listaTareas.size());
        taskAdapter = new TaskAdapter(listaTareas, this,getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(taskAdapter);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnTaskClickListener){
            mListener = (OnTaskClickListener) context;
        }else{
            throw new RuntimeException(context.toString()
                    +"must implement OnItemClickListener");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener=null;
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

        ContentResolver cr = getContext().getContentResolver();
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

    public interface OnTaskClickListener{void onTaskClick(Task tarea);}
    @Override
    public void onItemClick(Task tarea) {mListener.onTaskClick(tarea);}

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(myReceiver, filterPosicion);
        getActivity().registerReceiver(myReceiverUpdate,filterUpdate);
        getActivity().registerReceiver(myReceiverDelete,filterDelete);

    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(myReceiver);
        getActivity().unregisterReceiver(myReceiverUpdate);
        getActivity().unregisterReceiver(myReceiverDelete);
    }


    class myReceiver extends BroadcastReceiver {
        @Override public void onReceive(Context context, Intent intent) {
           boolean newElement = false;
            newElement = intent.getBooleanExtra("NuevosElementos",false);

            boolean updateElement = false;
            updateElement = intent.getBooleanExtra("ElementosModificados",false);

            boolean deleteElement = false;
            deleteElement = intent.getBooleanExtra("ElementoEliminado",false);



            if (newElement){
                Log.e(TAG, "Nuevo elementos para la lista ::::: " + newElement );
                taskAdapter = new TaskAdapter(getTaskList(),ListFragment.this,getContext());
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(taskAdapter);
            }
            if (updateElement){
                Log.e(TAG, "Nuevo elementos modificado para la lista ::::: " + updateElement );
                taskAdapter = new TaskAdapter(getTaskList(),ListFragment.this,getContext());
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(taskAdapter);
            }
            if (deleteElement){
                Log.e(TAG, "Elemento Eliminado de la lista ::::: " + deleteElement );
                taskAdapter = new TaskAdapter(getTaskList(),ListFragment.this,getContext());
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(taskAdapter);
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



            if (newElement){
                Log.e(TAG, "Nuevo elementos para la lista ::::: " + newElement );
                taskAdapter = new TaskAdapter(getTaskList(),ListFragment.this,getContext());
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(taskAdapter);
            }
            if (updateElement){
                Log.e(TAG, "Nuevo elementos modificado para la lista ::::: " + updateElement );
                taskAdapter = new TaskAdapter(getTaskList(),ListFragment.this,getContext());
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(taskAdapter);
            }
            if (deleteElement){
                Log.e(TAG, "Elemento Eliminado de la lista ::::: " + deleteElement );
                taskAdapter = new TaskAdapter(getTaskList(),ListFragment.this,getContext());
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(taskAdapter);
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



            if (newElement){
                Log.e(TAG, "Nuevo elementos para la lista ::::: " + newElement );
                taskAdapter = new TaskAdapter(getTaskList(),ListFragment.this,getContext());
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(taskAdapter);
            }
            if (updateElement){
                Log.e(TAG, "Nuevo elementos modificado para la lista ::::: " + updateElement );
                taskAdapter = new TaskAdapter(getTaskList(),ListFragment.this,getContext());
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(taskAdapter);
            }
            if (deleteElement){
                Log.e(TAG, "Elemento Eliminado de la lista ::::: " + deleteElement );
                taskAdapter = new TaskAdapter(getTaskList(),ListFragment.this,getContext());
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(taskAdapter);
            }
        }
    }

}
