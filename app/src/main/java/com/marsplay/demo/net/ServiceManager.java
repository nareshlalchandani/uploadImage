package com.marsplay.demo.net;

import android.content.Context;

import com.google.gson.Gson;
import com.marsplay.demo.R;

import java.io.File;

public class ServiceManager {

    //Development
    private static String BASE_URL = "https://bsapp-new-api.app6.in/Api/";

    private Context mContext;
    private ServiceCallBacks mServiceCallBacks;

    public ServiceManager(Context mContext, ServiceCallBacks callBacks) {
        super();
        this.mContext = mContext;
        this.mServiceCallBacks = callBacks;
    }

    public void uploadImage(File file, Object model) {

        if (!ConnectionChecker.isNetworkAvailable(mContext)) {
            mServiceCallBacks.onRequestCancel(mContext.getString(R.string.internet_not_available), ServiceCallBacks.UPLOAD_IMAGE);
            return;
        }

        VolleyTask submitTask = new VolleyTask(mContext, mServiceCallBacks, ServiceCallBacks.UPLOAD_IMAGE);
        submitTask.setShowProgress(true);

        String reqBodyString = new Gson().toJson(model);

        String urlString = BASE_URL + "/API/Hazard/Hazard_Process_Submit/";

        submitTask.postMultipartImage(urlString, file, reqBodyString);
    }

    public void getImagesAPI() {

        if (!ConnectionChecker.isNetworkAvailable(mContext)) {
            mServiceCallBacks.onRequestCancel(mContext.getString(R.string.internet_not_available), ServiceCallBacks.IMAGES);
            return;
        }

        VolleyTask submitTask = new VolleyTask(mContext, mServiceCallBacks, ServiceCallBacks.IMAGES);
        submitTask.setShowProgress(false);

        String serviceUrl = BASE_URL + "Hazard/Get_Category_List";

        submitTask.getData(serviceUrl);
    }
}