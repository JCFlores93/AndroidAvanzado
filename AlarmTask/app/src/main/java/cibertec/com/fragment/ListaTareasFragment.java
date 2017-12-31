package cibertec.com.fragment;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cibertec.com.adapters.TareaAdapter;
import cibertec.com.apptareaszumaran.R;
import cibertec.com.db.DataBaseTareas;
import cibertec.com.db.TareasContract;
import cibertec.com.model.Tarea;
import cibertec.com.util.Constante;

public class ListaTareasFragment extends Fragment implements TareaAdapter.OnItemClickListener {
    private OnTareaClickListener mListener;
    private RecyclerView list_tareas;
    private TareaAdapter tareaAdapter;
    private DataBaseTareas db;

    public ListaTareasFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        db = new DataBaseTareas(getContext());
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lista_tareas, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        list_tareas = (RecyclerView)view.findViewById(R.id.list_tareas);
        tareaAdapter = new TareaAdapter(this, getListaTareas());
        list_tareas.setLayoutManager(new LinearLayoutManager(getContext()));
        list_tareas.setHasFixedSize(true);
        list_tareas.setAdapter(tareaAdapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnTareaClickListener) {
            mListener = (OnTareaClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        db = null;
    }

    @Override
    public void onItemClick(Tarea tarea) {
        mListener.onTareaClick(Constante.ACTION_MODIFY, tarea);
    }

    public interface OnTareaClickListener {
        void onTareaClick(Integer action, Tarea tarea);
    }

    private List<Tarea> getListaTareas(){
        List<Tarea> tareas = new ArrayList<>();
        String[] projection = new String[]{
                TareasContract.Tarea._ID,
                TareasContract.Tarea.COLUMN_NAME_TITLE,
                TareasContract.Tarea.COLUMN_NAME_DATE_TIME,
                TareasContract.Tarea.COLUMN_NAME_REMEMBER
        };

        Cursor cursor = db.listarTareas(projection);
        if(cursor!=null){
            while (cursor.moveToNext()){
                Tarea tarea = cursorToTarea(cursor);
                tareas.add(tarea);
            }
            cursor.close();
        }
        return tareas;
    }

    private Tarea cursorToTarea(Cursor cursor){
        Tarea tarea = new Tarea();
        tarea.setId(cursor.getLong(0));
        tarea.setTitle(cursor.getString(1));
        tarea.setDate_time(cursor.getString(2));
        tarea.setRemember(cursor.getInt(3));
        return tarea;
    }

    public void refreshListado(){
        tareaAdapter.setListado(getListaTareas());
        tareaAdapter.notifyDataSetChanged();
    }

    public void lanzarSelectedTarea(Integer position){
        Tarea tarea = tareaAdapter.getTareaByPosition(position);
        onItemClick(tarea);
    }
}
