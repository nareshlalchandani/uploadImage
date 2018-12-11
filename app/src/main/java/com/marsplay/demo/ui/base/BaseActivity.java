package com.marsplay.demo.ui.base;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.marsplay.demo.application.AppController;
import com.marsplay.demo.utils.bridges.ActionBarBridge;
import com.marsplay.demo.utils.bridges.PreferenceBridge;
import com.marsplay.demo.prefrences.PreferenceManager;

/**
 * Created by naresh chandani on 09/09/2017
 */

public class BaseActivity extends AppCompatActivity implements ActionBarBridge, PreferenceBridge {

    private static final String TAG = BaseActivity.class.getSimpleName();

    protected AppController app;
    protected String title;
    private ActionBar actionBar;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initFields();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                navigateToParent();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void navigateToParent() {
        Intent intent = NavUtils.getParentActivityIntent(this);
        if (intent == null) {
            finish();
        } else {
            NavUtils.navigateUpFromSameTask(this);
        }
    }

    private void initFields() {
        Log.d("BaseActivity", "initFields()");

        app = AppController.getInstance();
        preferenceManager = new PreferenceManager(AppController.getInstance());
    }

    protected void setUpActionBarWithUpButton() {
        initActionBar();
        setActionBarUpButtonEnabled(true);
        setActionBarTitle(title);
    }

    @Override
    public PreferenceManager getPreferenceManager() {
        return preferenceManager;
    }

    @Override
    public void initActionBar() {
        actionBar = getSupportActionBar();
    }

    @Override
    public void setActionBarTitle(String title) {
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }

    @Override
    public void setActionBarTitle(@StringRes int title) {
        setActionBarTitle(getString(title));
    }

    @Override
    public void setActionBarSubtitle(String subtitle) {
        if (actionBar != null) {
            actionBar.setSubtitle(subtitle);
        }
    }

    @Override
    public void setActionBarSubtitle(@StringRes int subtitle) {
        setActionBarSubtitle(getString(subtitle));
    }

    @Override
    public void setActionBarIcon(Drawable icon) {
        if (actionBar != null) {
            // In appcompat v21 there will be no icon if we don't add this display option
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setIcon(icon);
        }
    }

    @Override
    public void setActionBarIcon(@DrawableRes int icon) {
        Drawable drawable;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            drawable = getDrawable(icon);
        } else {
            drawable = getResources().getDrawable(icon);
        }

        setActionBarIcon(drawable);
    }

    @Override
    public void setActionBarUpButtonEnabled(boolean enabled) {
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(enabled);
            actionBar.setDisplayHomeAsUpEnabled(enabled);
        }
    }
}