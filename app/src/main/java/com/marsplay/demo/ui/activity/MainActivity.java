package com.marsplay.demo.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.text.Html;
import android.view.View;
import android.widget.Button;

import com.marsplay.demo.R;
import com.marsplay.demo.ui.base.BaseActivity;
import com.marsplay.demo.utils.RuntimePermissions;
import com.marsplay.demo.utils.ToastUtils;
import com.marsplay.demo.utils.Utility;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.util.List;

import pl.aprilapps.easyphotopicker.Constants;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

public class MainActivity extends BaseActivity {

    private static final int REQUEST_OPEN_CAMERA = 2;
    private static final int REQUEST_OPEN_GALLERY = 3;

    Button btnCapturePicture;
    Button btnImagesList;

    private Boolean doubleBackToExitPressedOnce = false;
    private static final int BACK_INTERVAL = 2500;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindViews();
    }

    /**
     * Bind view
     */
    private void bindViews() {

        btnCapturePicture = findViewById(R.id.btn_capture_picture);
        btnImagesList = findViewById(R.id.btn_images_list);

        btnCapturePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialog();
            }
        });

        btnImagesList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ImagesListActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {

        if (doubleBackToExitPressedOnce) {
            //finish the main activity and close the app
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        ToastUtils.shortToast("Please click back again to exit");

        //Handle delay after first and second time button click
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, BACK_INTERVAL);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.RequestCodes.TAKE_PICTURE || requestCode == Constants.RequestCodes.PICK_PICTURE_FROM_GALLERY) {

            EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
                @Override
                public void onImagesPicked(@NonNull List<File> imageFiles, EasyImage.ImageSource source, int type) {

                    startCropActivity(imageFiles.get(0));
                }

                @Override
                public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                    super.onImagePickerError(e, source, type);
                    e.printStackTrace();
                }

                @Override
                public void onCanceled(EasyImage.ImageSource source, int type) {
                    super.onCanceled(source, type);
                }
            });
        } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {

                Uri resultUri = result.getUri();

                File imageFile = new File(resultUri.getPath());

                launchUploadActivity(imageFile);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                error.printStackTrace();
            }
        }
    }

    private void startCropActivity(File file) {

        CropImage.activity(Uri.fromFile(file))
                .start(this);
    }

    private void showDialog() {

        final CharSequence[] items = {"Take Image", "Choose from Gallery", "Cancel"};
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle(Html.fromHtml("<u>Upload Image!</u>"));
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Image")) {

                    dialog.dismiss();
                    takePicture();

                } else if (items[item].equals("Choose from Gallery")) {

                    dialog.dismiss();
                    chooseImage();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });

        builder.show();
    }

    private void takePicture() {

        if (!RuntimePermissions.isPermissionGranted(this, RuntimePermissions.CAMERA_PERMISSION_ID)) {
            RuntimePermissions.requestPermission(this, REQUEST_OPEN_CAMERA, RuntimePermissions.CAMERA_PERMISSION_ID);
            return;
        }

        if (!RuntimePermissions.isPermissionGranted(this, RuntimePermissions.READ_EXTERNAL_STORAGE_PERMISSION_ID)) {
            RuntimePermissions.requestPermission(this, REQUEST_OPEN_GALLERY, RuntimePermissions.READ_EXTERNAL_STORAGE_PERMISSION_ID);
            return;
        }

        EasyImage.openCamera(this, REQUEST_OPEN_CAMERA);
    }

    private void chooseImage() {

        if (!RuntimePermissions.isPermissionGranted(this, RuntimePermissions.READ_EXTERNAL_STORAGE_PERMISSION_ID)) {
            RuntimePermissions.requestPermission(this, REQUEST_OPEN_GALLERY, RuntimePermissions.READ_EXTERNAL_STORAGE_PERMISSION_ID);
            return;
        }

        EasyImage.openGallery(this, REQUEST_OPEN_GALLERY);
    }

    private void launchUploadActivity(File file) {
        Intent i = new Intent(MainActivity.this, UploadImageActivity.class);
        i.putExtra("path", file.getPath());
        startActivity(i);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        //Camera
        if (requestCode == REQUEST_OPEN_CAMERA) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                //Permission Allow
                takePicture();

            } else {

                //Permission Deny
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, RuntimePermissions.CAMERA_PERMISSION_ID) || ActivityCompat.shouldShowRequestPermissionRationale(this, RuntimePermissions.READ_EXTERNAL_STORAGE_PERMISSION_ID)) {

                    ToastUtils.shortToast("Camera permission required to upload image");

                    //Permission Deny--never ask again selected
                } else {

                    Snackbar snackbar = Snackbar.make(btnCapturePicture, getResources().getString(R.string.permission_camera), Snackbar.LENGTH_LONG);

                    snackbar.setAction(getResources().getString(R.string.settings), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Utility.openApplicationDetailActivity(MainActivity.this);
                        }
                    });

                    snackbar.show();
                }
            }
        }

        //Gallery
        if (requestCode == REQUEST_OPEN_GALLERY) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                //Permission Allow
                chooseImage();

            } else {

                //Permission Deny
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, RuntimePermissions.READ_EXTERNAL_STORAGE_PERMISSION_ID)) {

                    ToastUtils.shortToast("Permission required to upload image");

                    //Permission Deny--never ask again selected
                } else {

                    Snackbar snackbar = Snackbar.make(btnCapturePicture, getResources().getString(R.string.permission_read_storage), Snackbar.LENGTH_LONG);

                    snackbar.setAction(getResources().getString(R.string.settings), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Utility.openApplicationDetailActivity(MainActivity.this);
                        }
                    });

                    snackbar.show();
                }
            }
        }
    }
}
