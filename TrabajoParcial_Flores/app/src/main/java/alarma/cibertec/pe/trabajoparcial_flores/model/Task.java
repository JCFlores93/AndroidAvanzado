package alarma.cibertec.pe.trabajoparcial_flores.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by USUARIO on 23/05/2017.
 */

public class Task implements Parcelable{
    private int id;
    private String taskTitle;
    private Date day ;
    private int remember;


    public Task(){

    }

    protected Task(Parcel in){
        id=in.readInt();
        taskTitle=in.readString();
        day=(Date)in.readSerializable();
        remember = in.readInt();
    }

    public static final Parcelable.Creator<Task> CREATOR = new Parcelable.Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    public String getTaskTitle() {return taskTitle;}
    public void setTaskTitle(String taskTitle) { this.taskTitle = taskTitle;}
    public Date getDay() {
        return day;
    }
    public void setDay(Date day) {
        this.day = day;
    }
    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public int getRemember(){return remember;}
    public void setRemember(int remember){this.remember = remember;}


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(taskTitle);
        parcel.writeSerializable(day);
        parcel.writeInt(remember);
    }


   /*
    public String toString (){
        //TODO : ver lo de conversión de datos
    }*/

}
