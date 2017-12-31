package com.apurata.prestamos.creditos.RequestModels;

import android.location.Location;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by apurata on 9/26/17.
 */

public class CoordBGService extends Coord {

    public CoordBGService(){}

    public CoordBGService(Location location){
        super(location);
        setDateTime(location.getTime());
    }

    private void setDateTime(long dateTime) {
        Log.i("GPS_TRACKER", "this is the date time: " + dateTime);

        Date creationTime = new Date(dateTime);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        String timeToEvent = dateFormat.format(creationTime);

        Log.i("GPS_TRACKER","creation time coordDated: " + timeToEvent);
        this.created_at = timeToEvent;

        Date recordedTime = new Date();
        String recordedTimeToEvent = dateFormat.format(recordedTime);
        this.recorded_at = recordedTimeToEvent;
    }

    @SerializedName("counter")
    private int counter;

    @SerializedName("created_at")
    private String created_at;

    @SerializedName("recorded_at")
    private String recorded_at;

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public int getCounter() {
        return counter;
    }

    public String getRecorded_at() {
        return recorded_at;
    }

    public void setRecorded_at(String recorded_at) {
        this.recorded_at = recorded_at;
    }
}
