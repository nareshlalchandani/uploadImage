package com.marsplay.demo.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.webkit.URLUtil;
import android.widget.ImageView;

import com.marsplay.demo.R;
import com.squareup.picasso.Picasso;

/**
 * Created by naresh chandani on 09/09/2017
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

    public static void loadBannerImage(String imageUrl, ImageView imageView) {

        if (URLUtil.isValidUrl(imageUrl)) {

            Picasso.get()
                    .load(imageUrl)
                    .placeholder(R.drawable.default_image)
                    .error(R.drawable.default_image)
                    .into(imageView);
        } else {

            if (imageView != null) {
                imageView.setImageResource(R.drawable.default_image);
            }
        }
    }
}
