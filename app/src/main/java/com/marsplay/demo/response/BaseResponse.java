package com.marsplay.demo.response;

import com.google.gson.annotations.SerializedName;
import com.marsplay.demo.response.model.BaseModel;

public class BaseResponse {

    @SerializedName("response")
    private BaseModel response;

    public BaseModel getResponse() {
        return response;
    }

    public void setResponse(BaseModel response) {
        this.response = response;
    }
}
