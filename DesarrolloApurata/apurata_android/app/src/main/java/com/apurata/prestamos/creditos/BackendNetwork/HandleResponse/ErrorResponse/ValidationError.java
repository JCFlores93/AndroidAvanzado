package com.apurata.prestamos.creditos.BackendNetwork.HandleResponse.ErrorResponse;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jeancarlo on 6/7/17.
 */

public class ValidationError {


    @SerializedName("phone_pin")
    private String phonePin;

    @SerializedName("dni_photo")
    private String dniPhoto;

    @SerializedName("signed_paper_photo")
    private String signedPaperPhoto;

    @SerializedName("bank_account")
    private String bankAccount;

    public String getPhonePin() {
        return phonePin;
    }
    public void setPhonePin(String phonePin) {
        this.phonePin = phonePin;
    }
    public String getDniPhoto() {
        return dniPhoto;
    }
    public void setDniPhoto(String dniPhoto) {
        this.dniPhoto = dniPhoto;
    }
    public String getSignedPaperPhoto() {
        return signedPaperPhoto;
    }
    public void setSignedPaperPhoto(String signedPaperPhoto) {this.signedPaperPhoto = signedPaperPhoto;}
    public String getBankAccount() {
        return bankAccount;
    }
    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }
}
