package com.marsplay.demo.response;

import com.google.gson.annotations.SerializedName;
import com.marsplay.demo.response.model.ImagesResponseModel;

import java.util.List;

/**
 * Created by naresh on 4/4/17.
 */

public class ImagesResponse extends BaseResponse {

    @SerializedName("data")
    private List<ImagesResponseModel> data;

    public List<ImagesResponseModel> getData() {
        return data;
    }

    public void setData(List<ImagesResponseModel> data) {
        this.data = data;
    }

}
