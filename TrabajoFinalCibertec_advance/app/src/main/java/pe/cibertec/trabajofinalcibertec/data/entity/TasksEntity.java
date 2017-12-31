package pe.cibertec.trabajofinalcibertec.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.Realm;
import io.realm.RealmModel;

/**
 * Created by USUARIO on 3/06/2017.
 */

public class TasksEntity implements RealmModel {

    @SerializedName("objectId")
    @Expose
    private String objectId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("remember")
    @Expose
    private boolean remember;
    @SerializedName("date_time")
    @Expose
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
