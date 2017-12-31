package cibertec.com.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cibertec.com.apptareaszumaran.R;
import cibertec.com.model.Tarea;
import cibertec.com.util.Constante;

public class TareaAdapter extends RecyclerView.Adapter<TareaAdapter.ViewHolder> {

    private OnItemClickListener listener;
    private List<Tarea> tareas;
    protected static Integer index_item_selected = -1;
    protected static View view_holder_selected;

    public TareaAdapter(OnItemClickListener listener,@NonNull List<Tarea> tareas) {
        this.listener = listener;
        this.tareas = tareas;
    }

    public Tarea getTareaByPosition(Integer position){
        return tareas.get(position);
    }

    public void setListado(List<Tarea> tareas){
        this.tareas = new ArrayList<>();
        this.tareas = tareas;
        if(view_holder_selected!=null) {
            if(view_holder_selected.getContext().getResources().getBoolean(R.bool.es_tablet)) {
                index_item_selected = -1;
                view_holder_selected.setSelected(false);
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tarea, parent, false);
        return new ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(tareas.get(position), position);
        if(holder.itemView.getContext().getResources().getBoolean(R.bool.es_tablet)) {
            if (index_item_selected == position) {
                view_holder_selected = holder.itemView;
                holder.itemView.setSelected(true);
            }
            if (index_item_selected == -1) {
                index_item_selected = 0;
                view_holder_selected = holder.itemView;
                holder.itemView.setSelected(true);
            }
        }
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

        public void bind(final Tarea tarea, final Integer position) {
            label_titulo.setText(tarea.getTitle());
            label_fecha_hora.setText(tarea.getDate_time());
            if(tarea.getRemember()==Constante.RECORDAR_FALSE) {
                label_recordar.setText(Constante.TEXT_NO);
            }
            if(tarea.getRemember()==Constante.RECORDAR_TRUE) {
                label_recordar.setText(Constante.TEXT_SI);
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        if(v.getContext().getResources().getBoolean(R.bool.es_tablet)) {
                            view_holder_selected.setSelected(false);
                            index_item_selected = position;
                            view_holder_selected = v;
                            v.setSelected(true);
                        }
                        listener.onItemClick(tarea);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Tarea tarea);
    }
}
