package pe.cibertec.trabajofinalcibertec.presentation.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pe.cibertec.trabajofinalcibertec.R;
import pe.cibertec.trabajofinalcibertec.presentation.model.TaskModel;
import pe.cibertec.trabajofinalcibertec.presentation.util.Constants;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    private OnItemClickListener listener;
    private List<TaskModel> tareas;

    public TaskAdapter(OnItemClickListener listener,@NonNull List<TaskModel> tareas) {
        this.listener = listener;
        this.tareas = tareas;
    }

    public void setListado(List<TaskModel> tareas){
        clearAll();
        this.tareas = tareas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tarea, parent, false);
        return new ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(tareas.get(position));
    }

    public void clearAll(){
        tareas.clear();
        tareas = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return tareas.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView label_titulo, label_fecha_hora, label_recordar;
        OnItemClickListener listener;

        public ViewHolder(View itemView, OnItemClickListener listener) {
            super(itemView);
            label_titulo = (TextView) itemView.findViewById(R.id.label_titulo);
            label_fecha_hora = (TextView) itemView.findViewById(R.id.label_fecha_hora);
            label_recordar = (TextView) itemView.findViewById(R.id.label_recordar);
            this.listener = listener;
        }

        public void bind(final TaskModel tarea) {
            label_titulo.setText(tarea.getTitle());
            label_fecha_hora.setText(tarea.getDate_time());
            if(tarea.getRemember()== Constants.RECORDAR_FALSE) {
                label_recordar.setText(Constants.TEXT_NO);
            }
            if(tarea.getRemember()==Constants.RECORDAR_TRUE) {
                label_recordar.setText(Constants.TEXT_SI);
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        listener.onItemClick(tarea);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(TaskModel tarea);
    }
}
