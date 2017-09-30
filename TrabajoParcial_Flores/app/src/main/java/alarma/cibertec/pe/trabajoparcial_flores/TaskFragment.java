package alarma.cibertec.pe.trabajoparcial_flores;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import alarma.cibertec.pe.trabajoparcial_flores.db.TaskContract;
import alarma.cibertec.pe.trabajoparcial_flores.model.Task;

/**
 * Created by JeanCarlo on 05/05/2017.
 */

public class TaskFragment extends Fragment implements View.OnClickListener{

    private boolean isDualPane = false;
    Button btnCancel;
    public static final String ARG_TASK= "task";

    private Task task;

    public static TaskFragment newInstance(Task task){
        TaskFragment tf = new TaskFragment();
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
        return inflater.inflate(R.layout.fragament_task, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText edtTitulo = (EditText) view.findViewById(R.id.edtTitulo);
        EditText edtFecha = (EditText) view.findViewById(R.id.edtFecha);
        SwitchCompat swtCompat = (SwitchCompat) view.findViewById(R.id.swtCmpat);
        btnCancel = (Button) view.findViewById(R.id.btnCancel);
        if (isDualPane){
            btnCancel.setVisibility(View.GONE);

        }else{
            btnCancel.setVisibility(View.VISIBLE);

            btnCancel.setOnClickListener(this);
        }




        if (task != null){

            edtTitulo.setText(task.getTaskTitle());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String probandoDia = task.getDay().toString();
            String day = dateFormat.format(task.getDay());
            edtFecha.setText(probandoDia);
            if(task.getRemember() == 1){
            swtCompat.setChecked(true);
            }/*else  if(task.getRemember() == 0){
               // swtCompat.setChecked(false);
            */

        }else{
            edtTitulo.setText("Tarea 1");
            edtFecha.setText("2017-05-04 17:10:55");
            swtCompat.setChecked(true);
        }


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnCancel :
                getActivity().finish();
                break;
        }

    }
}
