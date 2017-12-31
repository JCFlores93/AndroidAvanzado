package pe.cibertec.trabajofinalcibertec.presentation.model;

import android.os.Parcel;
import android.os.Parcelable;

public class TaskModel implements Parcelable {
    private String id;
    private String title;
    private String date_time;
    private Integer remember;

    public TaskModel() {
    }

    public TaskModel(String id, String title, String date_time, Integer remember) {
        this.id = id;
        this.title = title;
        this.date_time = date_time;
        this.remember = remember;
    }

    protected TaskModel(Parcel in) {
        id = in.readString();
        title = in.readString();
        date_time = in.readString();
        remember = in.readInt();
    }

    public static final Creator<TaskModel> CREATOR = new Creator<TaskModel>() {
        @Override
        public TaskModel createFromParcel(Parcel in) {
            return new TaskModel(in);
        }

        @Override
        public TaskModel[] newArray(int size) {
            return new TaskModel[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public Integer getRemember() {
        return remember;
    }

    public void setRemember(Integer remember) {
        this.remember = remember;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(date_time);
        dest.writeInt(remember);
    }

    @Override
    public String toString() {
        return "TaskModel{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", date_time='" + date_time + '\'' +
                ", remember=" + remember +
                '}';
    }
}
