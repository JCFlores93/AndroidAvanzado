package cibertec.com.apptareaszumaran;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.EventLogTags;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import cibertec.com.db.DataBaseTareas;
import cibertec.com.db.TareasContract;
import cibertec.com.fragment.DatosTareaFragment;
import cibertec.com.fragment.ListaTareasFragment;
import cibertec.com.model.Tarea;
import cibertec.com.service.ReadTareasService;
import cibertec.com.util.Constante;

public class MainActivity extends AppCompatActivity
        implements ListaTareasFragment.OnTareaClickListener,
                    DatosTareaFragment.OnActionButtonTarea{
    private boolean es_tablet = false;
    private ListaTareasFragment listaTareasFragment;
    private FloatingActionButton btn_new;
    private static final String TAG = "MainActivity";
    private DataBaseTareas db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DataBaseTareas(this);
        btn_new = (FloatingActionButton)findViewById(R.id.btn_new);
        es_tablet = getResources().getBoolean(R.bool.es_tablet);
        btn_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(es_tablet){
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    Tarea tarea = new Tarea();
                    tarea.setRemember(Constante.RECORDAR_FALSE);
                    ft.replace(R.id.contenedor, DatosTareaFragment.newInstance(Constante.ACTION_NEW, tarea));
                    ft.commit();
                }else{
                    Tarea tarea = new Tarea();
                    tarea.setRemember(Constante.RECORDAR_FALSE);
                    Intent intent = new Intent(MainActivity.this, DatosTareaActivity.class);
                    intent.putExtra("tarea", tarea);
                    intent.putExtra("action", Constante.ACTION_NEW);
                    startActivityForResult(intent, Constante.REQUEST_CODE);
                }
            }
        });
        listaTareasFragment = (ListaTareasFragment)getSupportFragmentManager().findFragmentById(R.id.tareas);
        if(es_tablet){
            listaTareasFragment.lanzarSelectedTarea(0);
        }
        Intent intent = new Intent(this, ReadTareasService.class);
        startService(intent);
    }

    @Override
    public void onTareaClick(Integer action, Tarea tarea) {
        if(es_tablet){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.contenedor, DatosTareaFragment.newInstance(Constante.ACTION_MODIFY, tarea));
            ft.commit();
        }else{
            Intent intent = new Intent(this, DatosTareaActivity.class);
            intent.putExtra("tarea", tarea);
            intent.putExtra("action", Constante.ACTION_MODIFY);
            startActivityForResult(intent, Constante.REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Constante.REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Log.e(TAG, "OK");
                Integer result = data.getExtras().getInt("action");
                if(result == Constante.ACTION_NEW){
                    Toast.makeText(this,"CORRECTO! Se insertó exitosamente", Toast.LENGTH_SHORT).show();
                    loadListado();
                }
                if(result == Constante.ACTION_MODIFY){
                    Toast.makeText(this,"CORRECTO! Se modificó exitosamente", Toast.LENGTH_SHORT).show();
                    loadListado();
                }
                if(result == Constante.ACTION_DELETE){
                    Toast.makeText(this,"CORRECTO! Se eliminó exitosamente", Toast.LENGTH_SHORT).show();
                    loadListado();
                }
            }
        }
    }

    @Override
    public void onButtonClick(int action, Tarea tarea) {
        Log.e(TAG,""+action);
        Log.e(TAG, tarea.toString());
        switch(action){
            case 1: insertTarea(tarea); break;
            case 2: updateTarea(tarea); break;
            case 3: deleteTarea(tarea); break;
        }
    }
    private void insertTarea(Tarea tarea){
        ContentValues values = new ContentValues();
        values.put(TareasContract.Tarea.COLUMN_NAME_TITLE, tarea.getTitle());
        values.put(TareasContract.Tarea.COLUMN_NAME_DATE_TIME, tarea.getDate_time());
        values.put(TareasContract.Tarea.COLUMN_NAME_REMEMBER, tarea.getRemember());

        boolean exito = db.insertTarea(values);
        if(exito){
            loadListado();
            Toast.makeText(this,"CORRECTO! Se insertó exitosamente", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"ERROR! No se pudo realizar la operaciòn", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateTarea(Tarea tarea){
        ContentValues values = new ContentValues();
        values.put(TareasContract.Tarea.COLUMN_NAME_TITLE, tarea.getTitle());
        values.put(TareasContract.Tarea.COLUMN_NAME_DATE_TIME, tarea.getDate_time());
        values.put(TareasContract.Tarea.COLUMN_NAME_REMEMBER, tarea.getRemember());

        boolean exito = db.updateTarea(tarea, values);
        if(exito){
            Toast.makeText(this,"CORRECTO! Se actualizó exitosamente", Toast.LENGTH_SHORT).show();
            loadListado();
        }
    }

    private void deleteTarea(Tarea tarea){
        Integer result = db.deleteTarea(tarea.getId());
        if(result>0){
            Toast.makeText(this,"CORRECTO! Se eliminó exitosamente", Toast.LENGTH_SHORT).show();
            loadListado();
        }
    }

    private void loadListado(){
        listaTareasFragment.refreshListado();
        if(es_tablet) {
            listaTareasFragment.lanzarSelectedTarea(0);
        }
    }
}
