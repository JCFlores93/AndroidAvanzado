package pe.cibertec.trabajofinalcibertec.presentation.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import butterknife.ButterKnife;
import io.realm.Realm;
import pe.cibertec.trabajofinalcibertec.R;
import pe.cibertec.trabajofinalcibertec.presentation.model.TaskModel;
import pe.cibertec.trabajofinalcibertec.presentation.presenter.TasksPresenter;
import pe.cibertec.trabajofinalcibertec.presentation.util.Constants;
import pe.cibertec.trabajofinalcibertec.presentation.view.fragment.TaskDeleteFragment;
import pe.cibertec.trabajofinalcibertec.presentation.view.fragment.TaskListFragment;
import butterknife.BindView;

public class TasksMainActivity extends AppCompatActivity
        implements TaskListFragment.OnTaskEventListener{
    private String user_troken="";


    @BindView(R.id.btn_new)
    FloatingActionButton btn_new;
    //private TaskListFragment taskListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks_main);
        ButterKnife.bind(this);
        Realm.init(this);

        if (savedInstanceState == null){
            //taskListFragment = ;
            user_troken = getIntent().getStringExtra(Constants.NAME_USER_TOKEN_PARAM);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(android.R.id.content, TaskListFragment.newInstance(user_troken));
            ft.commit();
        }
        btn_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskModel tarea = new TaskModel();
                tarea.setRemember(Constants.RECORDAR_FALSE);
                Intent intent = new Intent(TasksMainActivity.this, DatosTareaActivity.class);
                intent.putExtra("tarea", tarea);
                intent.putExtra("action", Constants.ACTION_NEW);
                intent.putExtra(Constants.NAME_USER_TOKEN_PARAM, user_troken);
                startActivityForResult(intent, Constants.REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Constants.REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {

                Integer result = data.getExtras().getInt("action");
                if(result == Constants.ACTION_NEW){
                    Toast.makeText(this,"CORRECTO! Se insertó exitosamente", Toast.LENGTH_SHORT).show();
                    loadListado();
                }
                if(result == Constants.ACTION_MODIFY){
                    Toast.makeText(this,"CORRECTO! Se modificó exitosamente", Toast.LENGTH_SHORT).show();
                    loadListado();
                }
                if(result == Constants.ACTION_DELETE){
                    Toast.makeText(this,"CORRECTO! Se eliminó exitosamente", Toast.LENGTH_SHORT).show();
                    loadListado();
                }
            }
        }
    }

    private void loadListado(){
        /*listaTareasFragment.refreshListado();
        if(es_tablet) {
            listaTareasFragment.lanzarSelectedTarea(0);
        }*/
    }


    @Override
    public void onTaskEvent(Integer action, TaskModel taskModel) {
        Intent intent = new Intent(this, DatosTareaActivity.class);
        intent.putExtra("tarea", taskModel);
        intent.putExtra("action", Constants.ACTION_MODIFY);
        intent.putExtra(Constants.NAME_USER_TOKEN_PARAM, user_troken);
        startActivityForResult(intent, Constants.REQUEST_CODE);
    }
}
