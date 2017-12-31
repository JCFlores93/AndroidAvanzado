package pe.cibertec.trabajofinalcibertec.presentation.view.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;

import pe.cibertec.trabajofinalcibertec.R;
import pe.cibertec.trabajofinalcibertec.presentation.model.TaskModel;
import pe.cibertec.trabajofinalcibertec.presentation.util.Constants;

public class DatosTareaFragment extends Fragment implements View.OnClickListener {
    private TaskModel tarea;
    private EditText edit_titulo, edit_date_time;
    private SwitchCompat sw_recordar;
    private Button btn_aceptar, btn_cancelar, btn_eliminar;
    private OnActionButtonTarea listenerButtons;
    private Integer action;
    private boolean recordar=false;

    public DatosTareaFragment() {
        // Required empty public constructor
    }

    public static DatosTareaFragment newInstance(Integer action, TaskModel tarea) {
        DatosTareaFragment fragment = new DatosTareaFragment();
        Bundle args = new Bundle();
        args.putParcelable("tarea", tarea);
        args.putInt("action", action);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tarea = getArguments().getParcelable("tarea");
            action = getArguments().getInt("action");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_datos_tarea, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edit_titulo = (EditText)view.findViewById(R.id.edit_titulo);
        edit_date_time = (EditText)view.findViewById(R.id.edit_date_time);
        sw_recordar = (SwitchCompat)view.findViewById(R.id.sw_recordar);
        btn_aceptar = (Button)view.findViewById(R.id.btn_aceptar);
        btn_cancelar = (Button)view.findViewById(R.id.btn_cancelar);
        btn_eliminar = (Button)view.findViewById(R.id.btn_eliminar);

        edit_titulo.setText(tarea.getTitle());
        edit_date_time.setText(tarea.getDate_time());
        btn_aceptar.setOnClickListener(this);
        btn_cancelar.setOnClickListener(this);
        btn_eliminar.setOnClickListener(this);

        if(tarea.getRemember() == Constants.RECORDAR_TRUE){
            sw_recordar.setChecked(true);
            recordar=true;
        }else{
            sw_recordar.setChecked(false);
            recordar=false;
        }
        btn_cancelar.setVisibility(View.VISIBLE);
        if(action == Constants.ACTION_NEW){
            btn_eliminar.setVisibility(View.GONE);
        }

        sw_recordar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                recordar=isChecked;
            }
        });

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnActionButtonTarea) {
            listenerButtons = (OnActionButtonTarea) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listenerButtons = null;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_aceptar){
            if(action == Constants.ACTION_NEW) {
                tarea = new TaskModel();
                tarea.setId("");
                tarea.setTitle(edit_titulo.getText().toString());
                tarea.setDate_time(edit_date_time.getText().toString());
                if(recordar){
                    tarea.setRemember(1);
                }else{
                    tarea.setRemember(0);
                }
                listenerButtons.onButtonClick(action, tarea);
            }
            if(action == Constants.ACTION_MODIFY) {
                tarea.setTitle(edit_titulo.getText().toString());
                tarea.setDate_time(edit_date_time.getText().toString());
                if(recordar){
                    tarea.setRemember(1);
                }else{
                    tarea.setRemember(0);
                }
                listenerButtons.onButtonClick(action, tarea);
            }
        }
        if(v.getId() == R.id.btn_cancelar){
            listenerButtons.onButtonClick(Constants.ACTION_CANCELAR, tarea);
        }
        if(v.getId() == R.id.btn_eliminar){
            listenerButtons.onButtonClick(Constants.ACTION_DELETE, tarea);
        }
    }

    public interface OnActionButtonTarea {
        void onButtonClick(int action, TaskModel tarea);
    }
}
