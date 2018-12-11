package com.marsplay.demo.response.model;

import com.google.gson.annotations.SerializedName;

public class ImagesResponseModel {

    @SerializedName("id")
    private String id;

    @SerializedName("imageUrl")
    private String imageUrl;

    @SerializedName("createDate")
    private String createDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
