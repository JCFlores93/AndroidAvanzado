package com.apurata.prestamos.creditos.RequestModels;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by USUARIO on 5/06/2017.
 */

public class Coord implements Parcelable {
    public Coord() {
    }

    @SerializedName("speed")
    private float speed;

    @SerializedName("heading")
    private String heading;

    @SerializedName("altitudeAccuracy")
    private float altitudeAccuracy;

    @SerializedName("accuracy")
    private float accuracy;

    @SerializedName("altitude")
    private float altitude;

    @SerializedName("longitude")
    private float longitude;

    @SerializedName("latitude")
    private float latitude;

    protected Coord(Parcel in) {
        speed = in.readFloat();
        heading = in.readString();
        altitudeAccuracy = in.readFloat();
        accuracy = in.readFloat();
        altitude = in.readFloat();
        longitude = in.readFloat();
        latitude = in.readFloat();
    }

    public static final Creator<Coord> CREATOR = new Creator<Coord>() {
        @Override
        public Coord createFromParcel(Parcel in) {
            return new Coord(in);
        }

        @Override
        public Coord[] newArray(int size) {
            return new Coord[size];
        }
    };

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public float getAltitudeAccuracy() {
        return altitudeAccuracy;
    }

    public void setAltitudeAccuracy(float altitudeAccuracy) {
        this.altitudeAccuracy = altitudeAccuracy;
    }

    public float getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(float accuracy) {
        this.accuracy = accuracy;
    }

    public float getAltitude() {
        return altitude;
    }

    public void setAltitude(float altitude) {
        this.altitude = altitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public String getStringPosition() {
        return String.valueOf(this.longitude) + " " + this.latitude;
    }

    public Coord(Location location){
        this.setSpeed(location.getSpeed());
        this.setAccuracy(location.getAccuracy());
        this.setAltitude((float) location.getAltitude());
        this.setLongitude((float) location.getLongitude());
        this.setLatitude((float) location.getLatitude());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(speed);
        dest.writeString(heading);
        dest.writeFloat(altitudeAccuracy);
        dest.writeFloat(accuracy);
        dest.writeFloat(altitude);
        dest.writeFloat(longitude);
        dest.writeFloat(latitude);
    }
}
