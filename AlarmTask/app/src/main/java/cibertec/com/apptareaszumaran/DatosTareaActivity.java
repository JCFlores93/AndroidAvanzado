package cibertec.com.apptareaszumaran;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import cibertec.com.db.DataBaseTareas;
import cibertec.com.db.TareasContract;
import cibertec.com.fragment.DatosTareaFragment;
import cibertec.com.model.Tarea;
import cibertec.com.util.Constante;

public class DatosTareaActivity extends AppCompatActivity implements DatosTareaFragment.OnActionButtonTarea {
    private Tarea tarea;
    private Integer action;
    private static final String TAG = "DatosTareaActivity";
    private DataBaseTareas db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DataBaseTareas(this);
        tarea = getIntent().getExtras().getParcelable("tarea");
        action = getIntent().getExtras().getInt("action");
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(android.R.id.content, DatosTareaFragment.newInstance(action, tarea));
        ft.commit();
    }

    @Override
    public void onButtonClick(int action, Tarea tarea) {
        Log.e(TAG,"ACTION: "+action);
        switch(action){
            case 1: insertTarea(tarea); break;
            case 2: updateTarea(tarea); break;
            case 3: deleteTarea(tarea);break;
            case 4: cancelEdit(); break;
        }
    }

    private void insertTarea(Tarea tarea){
        ContentValues values = new ContentValues();
        values.put(TareasContract.Tarea.COLUMN_NAME_TITLE, tarea.getTitle());
        values.put(TareasContract.Tarea.COLUMN_NAME_DATE_TIME, tarea.getDate_time());
        values.put(TareasContract.Tarea.COLUMN_NAME_REMEMBER, tarea.getRemember());

        boolean exito = db.insertTarea(values);
        if(exito){
            Intent returnIntent = new Intent();
            returnIntent.putExtra("action", Constante.ACTION_NEW);
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        }
    }

    private void updateTarea(Tarea tarea){
        ContentValues values = new ContentValues();
        values.put(TareasContract.Tarea.COLUMN_NAME_TITLE, tarea.getTitle());
        values.put(TareasContract.Tarea.COLUMN_NAME_DATE_TIME, tarea.getDate_time());
        values.put(TareasContract.Tarea.COLUMN_NAME_REMEMBER, tarea.getRemember());

        boolean exito = db.updateTarea(tarea, values);
        if(exito){
            Intent returnIntent = new Intent();
            returnIntent.putExtra("action", Constante.ACTION_MODIFY);
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        }
    }

    private void cancelEdit(){
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

    private void deleteTarea(Tarea tarea){
        Integer result = db.deleteTarea(tarea.getId());
        if(result>0){
            Intent returnIntent = new Intent();
            returnIntent.putExtra("action", Constante.ACTION_DELETE);
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        }
    }
}
