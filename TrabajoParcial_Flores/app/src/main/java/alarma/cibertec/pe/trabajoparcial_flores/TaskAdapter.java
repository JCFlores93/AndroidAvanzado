package alarma.cibertec.pe.trabajoparcial_flores;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import alarma.cibertec.pe.trabajoparcial_flores.db.TaskContract;
import alarma.cibertec.pe.trabajoparcial_flores.model.Task;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by USUARIO on 23/05/2017.
 */

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private List<Task> tareas ;
    private OnItemClickListener listener;
    private int selectedItem=0;
    private Context context;

    public TaskAdapter(@NonNull List<Task> tareas ,
                       OnItemClickListener listener, Context context) {
        this.tareas=tareas;
        this.listener=listener;
        this.context = context ;
    }




    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.task_item,parent,false);
        return new ViewHolder(view,listener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(tareas.get(position),position);
    }

    @Override
    public int getItemCount() {
        return tareas.size();
    }




    class ViewHolder extends RecyclerView.ViewHolder  {

        TextView txtTitulo,txtFecha;
        SwitchCompat swtCmpat;
        LinearLayout contenedorLinearLayout;
        OnItemClickListener listener;
        Task tareaCambiar;



        public ViewHolder(View itemView,OnItemClickListener listener) {
            super(itemView);
            this.listener=listener;
            txtTitulo = (TextView)itemView.findViewById(R.id.txtTitulo);
            txtFecha = (TextView)itemView.findViewById(R.id.txtFecha);
            swtCmpat = (SwitchCompat)itemView.findViewById(R.id.swtCmpat);
            contenedorLinearLayout = (LinearLayout)itemView.findViewById(R.id.contenedorLayout);
        }

        public void bind(final Task task,final int position) {
            txtTitulo.setText(String.valueOf(task.getTaskTitle()));
            SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String newDay = date.format(task.getDay());
            Log.i("Fecha :",""+newDay);
            tareaCambiar = task;
            txtFecha.setText(newDay);
            if (tareaCambiar.getRemember() == 1){
                swtCmpat.setChecked(true);
                //update(getContentValues(tareaCambiar),tareaCambiar.getTaskTitle());

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String fechaIndicada = format.format(tareaCambiar.getDay());

                Calendar objCalendar = Calendar.getInstance();
                objCalendar.set(Calendar.HOUR_OF_DAY,Integer.parseInt(fechaIndicada.substring(11,13)));
                objCalendar.set(Calendar.MINUTE,Integer.parseInt(fechaIndicada.substring(14,16)));
                objCalendar.set(Calendar.SECOND,Integer.parseInt(fechaIndicada.substring(17,19)));

                Intent intentPublisher = new Intent(context,
                        NotificationPublisher.class);
                intentPublisher.putExtra(TaskUtil.TASK,tareaCambiar);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        context,
                        tareaCambiar.getId(),
                        intentPublisher,
                        PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager)
                        context.getSystemService(ALARM_SERVICE);

                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                        objCalendar.getTimeInMillis()
                        ,AlarmManager.INTERVAL_DAY,
                        pendingIntent);

            }else {
                swtCmpat.setChecked(false);
                //update(getContentValues(tareaCambiar),tareaCambiar.getTaskTitle());
            }
            swtCmpat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked) {
                            tareaCambiar.setRemember(1);
                            update(getContentValues(tareaCambiar),tareaCambiar.getTaskTitle());
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        String fechaIndicada = format.format(task.getDay());

                        Calendar objCalendar = Calendar.getInstance();
                        objCalendar.set(Calendar.HOUR_OF_DAY,Integer.parseInt(fechaIndicada.substring(11,13)));
                        objCalendar.set(Calendar.MINUTE,Integer.parseInt(fechaIndicada.substring(14,16)));
                        objCalendar.set(Calendar.SECOND,Integer.parseInt(fechaIndicada.substring(17,19)));

                        Intent intent = new Intent(context,
                                NotificationPublisher.class);
                        intent.putExtra("contexto", (Parcelable) context);
                        intent.putExtra(TaskUtil.TASK,tareaCambiar);

                        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                                context,
                                task.getId(),
                                intent,
                                PendingIntent.FLAG_UPDATE_CURRENT);
                        AlarmManager alarmManager = (AlarmManager)
                                context.getSystemService(ALARM_SERVICE);

                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                                objCalendar.getTimeInMillis()
                                ,AlarmManager.INTERVAL_DAY,
                                pendingIntent);


                    } else {
                            tareaCambiar.setRemember(0);
                            update(getContentValues(tareaCambiar),tareaCambiar.getTaskTitle());
                    }
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener!=null){
                        listener.onItemClick(task);
                        selectedItem = position;
                        notifyDataSetChanged();
                    }
                }
            });

            if (selectedItem == position){
                contenedorLinearLayout.setBackgroundColor(Color.parseColor("#567845"));
            }
            else{
                contenedorLinearLayout.setBackgroundColor(Color.parseColor("#ffffff"));
            }

        }

    }


    public interface OnItemClickListener{
        void onItemClick(Task tarea);
    }


    private void update (ContentValues values,String nombre){
        ContentResolver cr = context.getContentResolver();
        long id = busqueda((List<Task>) getTaskList(),nombre);
        int uri = cr.update(TaskContract.Task.CONTENT_URI,values,TaskContract.Task._ID + " = "+ id +" ",null);
        if (uri != 0) {
            Log.i("TASK ADAPTER", "Updated row id: " + id);
        } else {
            Log.i("TASK ADAPTER", "Name is null");
        }

    }

    private long busqueda(List<Task> lista,String nombre) {
        long idEncontrado=0;
        for (Task task : lista) {
            if(task.getTaskTitle().equals(nombre)) {
                idEncontrado=task.getId();
                break;
            }else{
                Log.i("TaskAdapter", "ERROR NO SE ENCUENTRA EL CONTACTO");
            }
        }
        return idEncontrado;}

    public Object getTaskList() {
        Cursor cursor = query();
        List<Task> contactList = new ArrayList<>();
        if (cursor != null) {
            while(cursor.moveToNext()) {
                Task contact = cursorToTask(cursor);
                contactList.add(contact);
            }
            cursor.close();
        }
        return contactList;
    }

    private Task cursorToTask(Cursor cursor)  {
        Task task = new Task();
        task.setId(cursor.getInt(0));
        task.setTaskTitle(cursor.getString(1));
        String fecha = cursor.getString(2);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = null;
        try {
            date = formatter.parse(fecha);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        task.setDay(date);
        task.setRemember(cursor.getInt(3));
        return task;
    }

    private Cursor query() {
        ContentResolver cr = context.getContentResolver();
        String[] projection = new String[]{
                //Columnas de la tabla a recuperar
                TaskContract.Task._ID,
                TaskContract.Task.COLUMN_NAME_TITLE,
                TaskContract.Task.COLUMN_NAME_DATE,
                TaskContract.Task.COLUMN_NAME_REMEMBER,
        };
        return cr.query(TaskContract.Task.CONTENT_URI,
                projection, null, null, null);
    }

    private ContentValues getContentValues(Task task) {
        ContentValues values = new ContentValues();
        values.put(TaskContract.Task.COLUMN_NAME_TITLE, task.getTaskTitle());
        values.put(TaskContract.Task.COLUMN_NAME_DATE, task.getDay().toString());
        values.put(TaskContract.Task.COLUMN_NAME_REMEMBER, task.getRemember());
        return values;
    }



}

