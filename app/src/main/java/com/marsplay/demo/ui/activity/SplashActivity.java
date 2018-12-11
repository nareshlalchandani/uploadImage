package com.marsplay.demo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.marsplay.demo.R;
import com.marsplay.demo.ui.base.BaseActivity;

public class SplashActivity extends BaseActivity {

    private static final int INTERVAL_TIME = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);

        launchScreen();
    }

    private void launchScreen() {

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(i);
                finish();

            }
        }, INTERVAL_TIME);
    }
}
