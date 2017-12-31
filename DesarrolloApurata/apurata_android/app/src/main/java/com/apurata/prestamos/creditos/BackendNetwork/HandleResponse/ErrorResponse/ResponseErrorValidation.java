package com.apurata.prestamos.creditos.BackendNetwork.HandleResponse.ErrorResponse;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jeancarlo on 6/7/17.
 */

public class ResponseErrorValidation {

    public ResponseErrorValidation(){}

    @SerializedName("_status")
    private String status;

    @SerializedName("_issues")
    private ValidationError validationError;

    @SerializedName("_error")
    private ErrorResponse error;

    public String getStatus() {return status;}
    public void setStatus(String status) {
        this.status = status;
    }
    public ValidationError getValidationError() {
        return validationError;
    }
    public void setValidationError(ValidationError validationError) {
        this.validationError = validationError;
    }
    public ErrorResponse getError() {
        return error;
    }
    public void setError(ErrorResponse error) {
        this.error = error;
    }

}
