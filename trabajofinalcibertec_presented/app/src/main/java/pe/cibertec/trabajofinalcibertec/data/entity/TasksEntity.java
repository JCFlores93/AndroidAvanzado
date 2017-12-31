package pe.cibertec.trabajofinalcibertec.data.entity;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by USUARIO on 3/06/2017.
 */

public class TasksEntity extends RealmObject {

    @SerializedName("objectId")
    @PrimaryKey
    private String objectId;

    private String title;

    private boolean remember;

    @SerializedName("date_time")
    private String dateTime;

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isRemember() {
        return remember;
    }

    public void setRemember(boolean remember) {
        this.remember = remember;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
    }
