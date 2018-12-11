package com.marsplay.demo.ui.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.marsplay.demo.R;
import com.marsplay.demo.net.ServiceCallBacks;
import com.marsplay.demo.net.ServiceManager;
import com.marsplay.demo.response.BaseResponse;
import com.marsplay.demo.ui.base.BaseActivity;
import com.marsplay.demo.utils.ToastUtils;

import java.io.File;

public class UploadImageActivity extends BaseActivity implements ServiceCallBacks {

    ImageView imgPreview;
    Button btnUploadImage;

    String imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);

        imagePath = getIntent().getStringExtra("path");

        if (imagePath == null) {
            ToastUtils.shortToast("Missing Image");
            finish();
            return;
        }

        bindViews();//1

        previewImage();//2
    }

    private void bindViews() {

        setUpActionBarWithUpButton();
        setActionBarTitle("Upload Image");

        imgPreview = findViewById(R.id.img_Preview);

        btnUploadImage = findViewById(R.id.btn_upload_image);
        btnUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callUploadImageAPI();
            }
        });
    }

    /**
     * Displaying captured image/video on the screen
     */
    private void previewImage() {

        BitmapFactory.Options options = new BitmapFactory.Options();

        // down sizing image as it throws OutOfMemory Exception for larger
        options.inSampleSize = 8;

        final Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);

        imgPreview.setImageBitmap(bitmap);
    }

    public void callUploadImageAPI() {

        File file = new File(imagePath);

        ServiceManager manager = new ServiceManager(this, this);
        manager.uploadImage(file, new Object());
    }

    @Override
    public void onRequestComplete(Object data, int caller) {

        if (caller == ServiceCallBacks.IMAGES) {

            BaseResponse response = new Gson().fromJson((String) data, BaseResponse.class);

            if (response.getResponse().isSuccess()) {

                ToastUtils.shortToast(response.getResponse().getMessage());
                finish();

            } else {
                ToastUtils.shortToast(response.getResponse().getMessage());
            }
        }
    }

    @Override
    public void onError(String errorString, int caller) {
        ToastUtils.shortToast(errorString);
    }

    @Override
    public void onRequestCancel(String errorString, int caller) {
        ToastUtils.shortToast(errorString);
    }

}