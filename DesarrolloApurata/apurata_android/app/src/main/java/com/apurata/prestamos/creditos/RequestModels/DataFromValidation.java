package com.apurata.prestamos.creditos.RequestModels;

import android.os.Parcel;
import android.os.Parcelable;

import com.apurata.prestamos.creditos.BackendNetwork.HandleResponse.ErrorResponse.ErrorResponse;
import com.apurata.prestamos.creditos.BackendNetwork.HandleResponse.ErrorResponse.ValidationError;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USUARIO on 2/06/2017.
 */

public class DataFromValidation implements Parcelable{

    public DataFromValidation(){

    }
    @SerializedName("_created")
    private String created;

    @SerializedName("_id")
    private String id;

    @SerializedName("userID")
    private String userID;

    @SerializedName("signed_paper_photo")
    private String signedPaperPhoto;

    @SerializedName("dni_photo")
    private String dniPhoto;

    @SerializedName("gps_list")
    private List<CoordActivity> coordActivity = new ArrayList<CoordActivity>();

    @SerializedName("_status")
    private String status;

    @SerializedName("front_camera_width")
    private int front_camera_width;

    @SerializedName("front_camera_height")
    private int front_camera_height;

    @SerializedName("back_camera_width")
    private int back_camera_width;

    @SerializedName("back_camera_height")
    private int back_camera_height;


    @SerializedName("validation_declared_ubication")
    private String validationDeclaredUbication;

    @SerializedName("bank_account")
    private String bankAccount;

    @SerializedName("phone_pin")
    private String phonePin;

    @SerializedName("_issues")
    private ValidationError validationError;

    @SerializedName("_error")
    private ErrorResponse error;

    @SerializedName("window_size_height")
    private int window_size_height;

    @SerializedName("window_size_width")
    private int window_size_width;

    @SerializedName("paper_camera")
    private String paper_camera;

    @SerializedName("dni_camera")
    private String dni_camera;

    @SerializedName("android_version")
    private String android_version;

    @SerializedName("cel_model")
    private String cel_model;

    @SerializedName("cel_brand")
    private String cel_brand;

    @SerializedName("is_gps_working")
    private boolean gpsWorking;

    public DataFromValidation(Parcel in) {
        created = in.readString();
        id = in.readString();
        userID = in.readString();
        signedPaperPhoto = in.readString();
        dniPhoto = in.readString();
        status = in.readString();
        front_camera_width = in.readInt();
        front_camera_height = in.readInt();
        back_camera_width = in.readInt();
        back_camera_height = in.readInt();
        validationDeclaredUbication = in.readString();
        bankAccount = in.readString();
        phonePin = in.readString();
        window_size_height = in.readInt();
        window_size_width = in.readInt();
        paper_camera = in.readString();
        dni_camera = in.readString();
        android_version = in.readString();
        cel_model = in.readString();
        cel_brand = in.readString();
        gpsWorking = in.readByte() != 0;
    }

    public static final Creator<DataFromValidation> CREATOR = new Creator<DataFromValidation>() {
        @Override
        public DataFromValidation createFromParcel(Parcel in) {
            return new DataFromValidation(in);
        }

        @Override
        public DataFromValidation[] newArray(int size) {
            return new DataFromValidation[size];
        }
    };

    public String getPaper_camera() {
        return paper_camera;
    }
    public void setPaper_camera(String paper_camera) {
        this.paper_camera = paper_camera;
    }
    public String getDni_camera() {
        return dni_camera;
    }
    public void setDni_camera(String dni_camera) {
        this.dni_camera = dni_camera;
    }
    public String getAndroid_version() {return android_version;}
    public void setAndroid_version(String android_version) {this.android_version = android_version;}
    public boolean isGpsWorking() {
        return gpsWorking;
    }
    public void setGpsWorking(boolean gpsWorking) {
        this.gpsWorking = gpsWorking;
    }
    public int getWindow_size_height() {
        return window_size_height;
    }
    public String getCel_model() {
        return cel_model;
    }
    public void setCel_model(String cel_model) {
        this.cel_model = cel_model;
    }
    public String getCel_brand() {
        return cel_brand;
    }
    public void setCel_brand(String cel_brand) {
        this.cel_brand = cel_brand;
    }
    public void setWindow_size_height(int window_size_height) { this.window_size_height = window_size_height;}
    public int getWindow_size_width() {
        return window_size_width;
    }
    public void setWindow_size_width(int window_size_width) {
        this.window_size_width = window_size_width;
    }
    public List<CoordActivity> getCoordActivity() {
        return coordActivity;
    }
    public void setCoordActivity(List<CoordActivity> coordActivity) {
        this.coordActivity = coordActivity;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public ValidationError getValidationError() {
        return validationError;
    }
    public void setValidationError(ValidationError validationError) {
        this.validationError = validationError;
    }
    public ErrorResponse getError() {return error;}
    public void setError(ErrorResponse error) {
        this.error = error;
    }
    public String getCreated() {
        return created;
    }
    public void setCreated(String created) {
        this.created = created;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getUserID() {
        return userID;
    }
    public void setUserID(String userID) {
        this.userID = userID;
    }
    public String getSignedPaperPhoto() {
        return signedPaperPhoto;
    }
    public void setSignedPaperPhoto(String signedPaperPhoto) {this.signedPaperPhoto = signedPaperPhoto;}
    public String getDniPhoto() {
        return dniPhoto;
    }
    public void setDniPhoto(String dniPhoto) {
        this.dniPhoto = dniPhoto;
    }
    public String getValidationDeclaredUbication() {
        return validationDeclaredUbication;
    }
    public void setValidationDeclaredUbication(String validationDeclaredUbication) {this.validationDeclaredUbication = validationDeclaredUbication;}
    public String getBankAccount() {
        return bankAccount;
    }
    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }
    public String getPhonePin() {
        return phonePin;
    }
    public void setPhonePin(String phonePin) {
        this.phonePin = phonePin;
    }
    public int getFront_camera_width() {
        return front_camera_width;
    }
    public void setFront_camera_width(int front_camera_width) { this.front_camera_width = front_camera_width;}
    public int getFront_camera_height() {
        return front_camera_height;
    }
    public void setFront_camera_height(int front_camera_height) {this.front_camera_height = front_camera_height;}
    public int getBack_camera_width() {
        return back_camera_width;
    }
    public void setBack_camera_width(int back_camera_width) {   this.back_camera_width = back_camera_width;}
    public int getBack_camera_height() {    return back_camera_height;  }
    public void setBack_camera_height(int back_camera_height) { this.back_camera_height = back_camera_height;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(created);
        dest.writeString(id);
        dest.writeString(userID);
        dest.writeString(signedPaperPhoto);
        dest.writeString(dniPhoto);
        dest.writeString(status);
        dest.writeInt(front_camera_width);
        dest.writeInt(front_camera_height);
        dest.writeInt(back_camera_width);
        dest.writeInt(back_camera_height);
        dest.writeString(validationDeclaredUbication);
        dest.writeString(bankAccount);
        dest.writeString(phonePin);
        dest.writeInt(window_size_height);
        dest.writeInt(window_size_width);
        dest.writeString(paper_camera);
        dest.writeString(dni_camera);
        dest.writeString(android_version);
        dest.writeString(cel_model);
        dest.writeString(cel_brand);
        dest.writeByte((byte) (gpsWorking ? 1 : 0));
    }
}

