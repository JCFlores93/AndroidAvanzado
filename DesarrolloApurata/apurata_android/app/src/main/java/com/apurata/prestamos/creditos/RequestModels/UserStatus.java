package com.apurata.prestamos.creditos.RequestModels;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jeancarlo on 6/22/17.
 */

public class UserStatus implements Parcelable{

    @SerializedName("aproval_status")
    private String aproval_status;

    @SerializedName("funding_status")
    private String funding_status;

    public UserStatus(Parcel in) {
        aproval_status = in.readString();
        funding_status = in.readString();
    }

    public static final Creator<UserStatus> CREATOR = new Creator<UserStatus>() {
        @Override
        public UserStatus createFromParcel(Parcel in) {
            return new UserStatus(in);
        }

        @Override
        public UserStatus[] newArray(int size) {
            return new UserStatus[size];
        }
    };

    public UserStatus() {

    }

    public String getAproval_status() {
        return aproval_status;
    }

    public void setAproval_status(String aproval_status) {
        this.aproval_status = aproval_status;
    }

    public String getFunding_status() {
        return funding_status;
    }

    public void setFunding_status(String funding_status) {
        this.funding_status = funding_status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(aproval_status);
        dest.writeString(funding_status);
    }


}
