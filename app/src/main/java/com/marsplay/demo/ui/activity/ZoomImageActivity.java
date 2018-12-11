package com.marsplay.demo.ui.activity;

import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.URLUtil;

import com.marsplay.demo.R;
import com.marsplay.demo.ui.base.BaseActivity;
import com.marsplay.demo.utils.LogUtility;
import com.marsplay.demo.utils.ToastUtils;
import com.squareup.picasso.Picasso;

import it.sephiroth.android.library.imagezoom.ImageViewTouch;
import it.sephiroth.android.library.imagezoom.ImageViewTouchBase;

public class ZoomImageActivity extends BaseActivity {

    private static final String TAG = ZoomImageActivity.class.getSimpleName();

    public static final String FIELD_IMAGE_URL = "imageUrl";
    private ImageViewTouch imgFeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.black));
        }

        setContentView(R.layout.activity_zoom_image);

        loadImage();
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();

        imgFeed = findViewById(R.id.imgFeed);

        if (imgFeed == null) {
            return;
        }

        // set the default image display type
        imgFeed.setDisplayType(ImageViewTouchBase.DisplayType.FIT_TO_SCREEN);
        //imgFeed.setScrollEnabled(true);

        imgFeed.setSingleTapListener(
                new ImageViewTouch.OnImageViewTouchSingleTapListener() {

                    @Override
                    public void onSingleTapConfirmed() {
                        LogUtility.printDebugMsg(TAG, "onSingleTapConfirmed");
                    }
                }
        );

        imgFeed.setDoubleTapListener(
                new ImageViewTouch.OnImageViewTouchDoubleTapListener() {

                    @Override
                    public void onDoubleTap() {
                        LogUtility.printDebugMsg(TAG, "onDoubleTap");
                    }
                }
        );

        imgFeed.setOnDrawableChangedListener(
                new ImageViewTouchBase.OnDrawableChangeListener() {

                    @Override
                    public void onDrawableChanged(Drawable drawable) {
                        LogUtility.printDebugMsg(TAG, "onBitmapChanged: " + drawable);
                    }
                }
        );
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    /* @Override
     public boolean onOptionsItemSelected(MenuItem item) {
         switch (item.getItemId()) {
             case android.R.id.home:
                 onBackPressed();
                 return true;

             default:
                 return super.onOptionsItemSelected(item);
         }
     }
 */
    private void loadImage() {

        setUpActionBarWithUpButton();
        setActionBarTitle(R.string.app_name);

        imgFeed = findViewById(R.id.imgFeed);

        String url = getIntent().getStringExtra(FIELD_IMAGE_URL);
        LogUtility.printDebugMsg(TAG, "Url : " + url);

        if (url != null && URLUtil.isValidUrl(url)) {

            Picasso.get()
                    .load(url)
                    .into(imgFeed);

            imgFeed.setVisibility(View.VISIBLE);

        } else {

            ToastUtils.shortToast("Invalid image url");
            finish();
        }
    }
}
