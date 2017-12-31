package pe.cibertec.trabajofinalcibertec.presentation.view.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.List;

import pe.cibertec.trabajofinalcibertec.presentation.TasksView;
import pe.cibertec.trabajofinalcibertec.presentation.model.TaskModel;
import pe.cibertec.trabajofinalcibertec.presentation.presenter.TasksPresenter;
import pe.cibertec.trabajofinalcibertec.presentation.util.Constants;
import pe.cibertec.trabajofinalcibertec.presentation.view.fragment.DatosTareaFragment;

public class DatosTareaActivity extends AppCompatActivity
        implements DatosTareaFragment.OnActionButtonTarea,
        TasksView {
    private TaskModel tarea;
    private Integer action;
    private static final String TAG = "DatosTareaActivity";
    private String user_troken="";
    private TasksPresenter tasksPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tarea = getIntent().getExtras().getParcelable("tarea");
        action = getIntent().getExtras().getInt("action");
        user_troken = getIntent().getExtras().getString(Constants.NAME_USER_TOKEN_PARAM);
        tasksPresenter = new TasksPresenter(this);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(android.R.id.content, DatosTareaFragment.newInstance(action, tarea));
        ft.commit();
    }

    @Override
    public void onButtonClick(int action, TaskModel tarea) {
        Log.e(TAG,"ACTION: "+action);
        switch(action){
            case 1: insertTarea(tarea); break;
            case 2: updateTarea(tarea); break;
            case 3: deleteTarea(tarea);break;
            case 4: cancelEdit(); break;
        }
    }

    private void insertTarea(TaskModel tarea){

/*
        boolean exito = db.insertTarea(values);
        if(exito){
            Intent returnIntent = new Intent();
            returnIntent.putExtra("action", Constants.ACTION_NEW);
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        }*/
    }

    private void updateTarea(TaskModel tarea){

        /*boolean exito = db.updateTarea(tarea, values);
        if(exito){
            Intent returnIntent = new Intent();
            returnIntent.putExtra("action", Constante.ACTION_MODIFY);
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        }*/
    }

    private void cancelEdit(){
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

    private void deleteTarea(TaskModel tarea){
        /*Integer result = db.deleteTarea(tarea.getId());
        if(result>0){
            Intent returnIntent = new Intent();
            returnIntent.putExtra("action", Constante.ACTION_DELETE);
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        }*/
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String errorMessage) {

    }

    @Override
    public Context context() {
        return getBaseContext();
    }

    @Override
    public void getTasks() {

    }

    @Override
    public void renderTasks(List<TaskModel> taskModelList) {

    }
}
