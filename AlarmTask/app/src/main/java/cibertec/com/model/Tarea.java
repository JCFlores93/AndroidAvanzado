package cibertec.com.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Tarea implements Parcelable {
    private long id;
    private String title;
    private String date_time;
    private Integer remember;

    public Tarea() {
    }

    public Tarea(long id, String title, String date_time, Integer remember) {
        this.id = id;
        this.title = title;
        this.date_time = date_time;
        this.remember = remember;
    }

    protected Tarea(Parcel in) {
        id = in.readLong();
        title = in.readString();
        date_time = in.readString();
        remember = in.readInt();
    }

    public static final Creator<Tarea> CREATOR = new Creator<Tarea>() {
        @Override
        public Tarea createFromParcel(Parcel in) {
            return new Tarea(in);
        }

        @Override
        public Tarea[] newArray(int size) {
            return new Tarea[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeString(date_time);
        dest.writeInt(remember);
    }

    @Override
    public String toString() {
        return "Tarea{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", date_time='" + date_time + '\'' +
                ", remember=" + remember +
                '}';
    }
}
