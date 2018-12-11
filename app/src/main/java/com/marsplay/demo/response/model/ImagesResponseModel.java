package com.marsplay.demo.response.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by naresh on 10/12/18.
 */
public class ImagesResponseModel {

    @SerializedName("ID")
    private String ID;

    @SerializedName("Image_Url")
    private String Image_Url;

    @SerializedName("Name")
    private String Name;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getImage_Url() {
        return Image_Url;
    }

    public void setImage_Url(String image_Url) {
        Image_Url = image_Url;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
