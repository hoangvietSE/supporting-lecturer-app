package com.soict.hoangviet.supportinglecturer.data.network;

import com.google.gson.annotations.SerializedName;

public class ApiError {
    @SerializedName("status")
    private int statusCode;
    @SerializedName("msg")
    private String message;

    public ApiError() {
        this.statusCode = ApiConstant.HttpStatusCode.UNKNOWN;
        this.message = ApiConstant.HttpMessage.ERROR_TRY_AGAIN;
    }

    public ApiError(String message) {
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ApiException getApiException() {
        return new ApiException(message, statusCode);
    }


}
