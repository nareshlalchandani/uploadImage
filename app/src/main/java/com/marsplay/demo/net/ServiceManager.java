package com.marsplay.demo.net;

import android.content.Context;

import com.google.gson.Gson;
import com.marsplay.demo.R;

import java.io.File;

/**
 * Created by naresh on 10/12/18.
 */

public class ServiceManager {

    //Development URL
    private static String BASE_URL = "http://nareshchandani-001-site10.itempurl.com";

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

        String serviceUrl = BASE_URL + "/API/Hazard/Get_Category_List";

        submitTask.getData(serviceUrl);
    }
}