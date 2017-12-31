package com.apurata.prestamos.creditos.RequestModels;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by apurata on 10/19/17.
 */

public class SessionEventMessage {

    public SessionEventMessage() {
       this.setCurrentDate();
    }

    @SerializedName("type")
    private String type;

    @SerializedName("sessionId")
    private String sessionId;

    private Boolean isComingFromApp;

    @SerializedName("created")
    private String createdAt;

    private String message;

    private void setCurrentDate() {
        Date creationTime = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        String timeToEvent = dateFormat.format(creationTime);
        Log.i("GPS_TRACKER", timeToEvent);
        this.createdAt = timeToEvent;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getComingFromApp() {
        return isComingFromApp;
    }

    public void setComingFromApp(Boolean comingFromApp) {
        isComingFromApp = comingFromApp;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
