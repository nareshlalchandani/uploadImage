package com.marsplay.demo.net;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.marsplay.demo.application.AppController;
import com.marsplay.demo.utils.AppConstant;
import com.marsplay.demo.utils.LogUtility;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by naresh on 10/12/18.
 */

public class VolleyTask {

    private Context mContext;
    private ServiceCallBacks mCallBacks;
    private int mCallerType;
    private ProgressDialog mProgressDialog;
    private String TAG = VolleyTask.class.getSimpleName();

    VolleyTask(Context mContext, ServiceCallBacks mCallBacks, int mCallerType) {
        super();
        this.mContext = mContext;
        this.mCallBacks = mCallBacks;
        this.mCallerType = mCallerType;
    }

    /**
     * @param isShowProgress the isShowProgress to set
     */
    void setShowProgress(boolean isShowProgress) {

        if (isShowProgress && mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(mContext);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setInverseBackgroundForced(true);
            mProgressDialog.setMessage("Please wait...");
            mProgressDialog.setCancelable(false);
        }
    }

    /**
     * Show progress dialog
     */
    private void showProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.show();
        }
    }

    /**
     * Cancel progress dialog
     */
    private void cancelProgressDialog() {

        if (mProgressDialog != null) {
            if (mProgressDialog.isShowing()) {
                mProgressDialog.cancel();
            }
        }
    }

    /**
     * Call a service without post parameters
     */
    public void postData(String url) {

        showProgressDialog();

        LogUtility.printDebugMsg(TAG, "Request url" + url);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        LogUtility.printDebugMsg(TAG, "Response data : " + response);

                        mCallBacks.onRequestComplete(response.toString(), mCallerType);

                        cancelProgressDialog();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                printVolleyError(error);

                mCallBacks.onError(error.getMessage(), mCallerType);

                cancelProgressDialog();

            }
        }) {

            //add the extra header here
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, AppConstant.APP_NAME);
    }

    /**
     * Call a service without body parameter
     */
    void getData(String url) {

        showProgressDialog();

        LogUtility.printDebugMsg(TAG, "Request url : " + url);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        LogUtility.printDebugMsg(TAG, "Response data : " + response);

                        mCallBacks.onRequestComplete(response.toString(), mCallerType);
                        cancelProgressDialog();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                printVolleyError(error);

                mCallBacks.onError(error.getMessage(), mCallerType);

                cancelProgressDialog();
            }
        }) {

            //add the extra header here
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json; charset=utf-8");
                return params;
            }
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, AppConstant.APP_NAME);
    }

    void postMultipartImage(String url, final File file, final String stringData) {

        showProgressDialog();

        LogUtility.printErrorMsg(TAG, "Request Url : " + url);
        LogUtility.printErrorMsg(TAG, "Request Body : " + stringData);

        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, url, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                String resultResponse = new String(response.data);

                LogUtility.printErrorMsg("Response : ", resultResponse);
                mCallBacks.onRequestComplete(resultResponse, mCallerType);
                cancelProgressDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                printVolleyError(error);

                mCallBacks.onError(error.getMessage(), mCallerType);

                cancelProgressDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("data", stringData);
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                // file name could found file base or direct access from real path
                // for now just get bitmap data from ImageView

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HH_mm_ss", Locale.US);
                String strImageName = dateFormat.format(new Date());

                params.put("file", new DataPart(strImageName + ".jpg", getCompressedByteFromFile(file), "image/jpeg"));

                return params;
            }
        };

        multipartRequest.setRetryPolicy(new DefaultRetryPolicy(0, -1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppController.getInstance().addToRequestQueue(multipartRequest);
    }

    private byte[] getCompressedByteFromFile(File imageFile) {

        int DESIRED_HEIGHT = 1024;
        int DESIRED_WIDTH = 720;

        FileInputStream fis = null;

        try {

            fis = new FileInputStream(imageFile);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Bitmap originalBitmap = BitmapFactory.decodeStream(fis);

        int imageWidth = originalBitmap.getWidth();
        int imageHeight = originalBitmap.getHeight();

        LogUtility.printDebugMsg(TAG, "Original :" + "Width : " + imageWidth + ",  Height : " + imageHeight);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        //Check is image resolution is greater then given resolution
        if (imageWidth > DESIRED_WIDTH || imageHeight > DESIRED_HEIGHT) {

            Bitmap scaled = Bitmap.createScaledBitmap(originalBitmap, DESIRED_WIDTH, DESIRED_HEIGHT, true);
            scaled.compress(Bitmap.CompressFormat.JPEG, 100, bos);

            LogUtility.printDebugMsg(TAG, "Compress :" + "Width : " + scaled.getWidth() + ",  Height : " + scaled.getHeight());
            return bos.toByteArray();

        } else {

            originalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            return bos.toByteArray();
        }
    }

    private void printVolleyError(VolleyError error) {

        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
            LogUtility.printErrorMsg(TAG, "VOLLEY EXCEPTION : Time out error");

        } else if (error instanceof AuthFailureError) {
            LogUtility.printErrorMsg(TAG, "VOLLEY EXCEPTION : Server error");

        } else if (error instanceof ServerError) {
            LogUtility.printErrorMsg(TAG, "VOLLEY EXCEPTION : Server error");

        } else if (error instanceof NetworkError) {
            LogUtility.printErrorMsg(TAG, "VOLLEY EXCEPTION : Network error");

        } else if (error instanceof ParseError) {
            LogUtility.printErrorMsg(TAG, "VOLLEY EXCEPTION : Parse error");

        } else {
            LogUtility.printErrorMsg(TAG, "VOLLEY EXCEPTION : GENERIC error");
        }
    }
}

