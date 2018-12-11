package com.marsplay.demo.response;

import com.google.gson.annotations.SerializedName;
import com.marsplay.demo.response.model.BaseModel;
/**
 * Created by naresh on 10/12/18.
 */

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
