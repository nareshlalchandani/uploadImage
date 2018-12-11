package com.marsplay.demo.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

/**
 * Created by ubuntu12 on 14/10/16.
 */

public class RuntimePermissions {

    public static final String CAMERA_PERMISSION_ID = Manifest.permission.CAMERA;
    public static final String READ_EXTERNAL_STORAGE_PERMISSION_ID = Manifest.permission.READ_EXTERNAL_STORAGE;

    public static boolean isPermissionGranted(Context context, String... permissions) {

        if (permissions == null || permissions.length == 0) {
            return false;
        }

        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(context, permission) != 0) {
                return false;
            }
        }
        return true;
    }


    public static void requestPermission(Activity activity, int reqCode, String... permissions) {


        if (permissions == null || permissions.length == 0) {
            return;
        }

        for (String permission : permissions) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {

                ActivityCompat.requestPermissions(activity, new String[]{permission}, reqCode);

            } else {
                ActivityCompat.requestPermissions(activity, new String[]{permission}, reqCode);
            }
        }
    }

    public static void requestPermissionFromFragment(Fragment fragment, int reqCode, String... permissions) {

        if (permissions == null || permissions.length == 0) {
            return;
        }

        for (String permission : permissions) {

            if (fragment.shouldShowRequestPermissionRationale(permission)) {

                fragment.requestPermissions(new String[]{permission}, reqCode);

            } else {
                fragment.requestPermissions(new String[]{permission}, reqCode);
            }
        }
    }

}
