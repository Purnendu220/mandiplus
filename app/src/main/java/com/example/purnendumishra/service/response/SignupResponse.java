package com.example.purnendumishra.service.response;

/**
 * Created by Mobikasa on 7/25/2016.
 */
public class SignupResponse {
    private boolean status;
    private String message;
    private SignupData data;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public SignupData getData() {
        return data;
    }

    public void setData(SignupData data) {
        this.data = data;
    }

    public class SignupData{



    }
}
