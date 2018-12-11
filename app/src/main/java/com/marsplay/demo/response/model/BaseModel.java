package com.marsplay.demo.response.model;

import com.google.gson.annotations.SerializedName;

public class BaseModel {

    @SerializedName("message")
    private String message;

    @SerializedName("status")
    private String status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isSuccess() {
        return status != null && status.equalsIgnoreCase("success");
    }
}
