package com.marsplay.demo.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.text.TextUtils;
import android.webkit.URLUtil;
import android.widget.ImageView;

import com.marsplay.demo.R;
import com.marsplay.demo.net.ServiceManager;
import com.squareup.picasso.Picasso;

/**
 * Created by naresh on 10/12/18.
 */

public class Utility {

    /**
     * Open the application detail activity to grant permissions manually
     *
     * @param context ,context
     */
    public static void openApplicationDetailActivity(Context context) {

        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
        intent.setData(uri);
        context.startActivity(intent);
    }

    public static void loadImageWithoutBase(String imageUrl, ImageView imageView) {

        if (imageUrl != null && !TextUtils.isEmpty(imageUrl)) {

            String path = ServiceManager.BASE_URL + imageUrl;
            //LogUtility.printDebugMsg( "path : " + path);

            Picasso.get()
                    .load(path)
                    .placeholder(R.drawable.default_image)
                    .error(R.drawable.default_image)
                    .into(imageView);
        } else {

            if (imageView != null) {
                imageView.setImageResource(R.drawable.default_image);
            }
        }
    }

    public static void loadImage(String imageUrl, ImageView imageView) {

        Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.default_image)
                .error(R.drawable.default_image)
                .into(imageView);
    }
}
