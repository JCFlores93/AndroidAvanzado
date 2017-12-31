package com.apurata.prestamos.creditos.BackendNetwork.HandleResponse.ErrorResponse;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jeancarlo on 6/7/17.
 */

public class ErrorResponse {

    @SerializedName("message")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @SerializedName("code")
    private int code;
}