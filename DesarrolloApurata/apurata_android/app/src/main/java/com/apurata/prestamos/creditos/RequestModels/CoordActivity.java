package com.apurata.prestamos.creditos.RequestModels;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by USUARIO on 5/06/2017.
 */

public class CoordActivity implements Parcelable{

    @SerializedName("coords")
    private Coord coord;

    @SerializedName("timestamp")
    private long timesTamp;

    @SerializedName("owner")
    private String owner;

    public CoordActivity(Parcel in) {
        coord = in.readParcelable(Coord.class.getClassLoader());
        timesTamp = in.readLong();
        owner = in.readString();
    }

    public static final Creator<CoordActivity> CREATOR = new Creator<CoordActivity>() {
        @Override
        public CoordActivity createFromParcel(Parcel in) {
            return new CoordActivity(in);
        }

        @Override
        public CoordActivity[] newArray(int size) {
            return new CoordActivity[size];
        }
    };

    public CoordActivity() {

    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public long getTimesTamp() {
        return timesTamp;
    }

    public void setTimesTamp(long timesTamp) {
        this.timesTamp = timesTamp;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(coord, flags);
        dest.writeLong(timesTamp);
        dest.writeString(owner);
    }
}
