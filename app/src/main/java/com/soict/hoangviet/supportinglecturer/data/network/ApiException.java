package com.soict.hoangviet.supportinglecturer.data.network;

public class ApiException extends Exception {
    private int statusCode;

    public ApiException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public ApiException(String message) {
        super(message);
        this.statusCode = ApiConstant.HttpStatusCode.UNKNOWN;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
