package com.marsplay.demo.ui.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.marsplay.demo.R;
import com.marsplay.demo.application.AppController;
import com.marsplay.demo.utils.ConnectivityUtils;
import com.marsplay.demo.utils.LogUtility;
import com.marsplay.demo.utils.ToastUtils;
import com.marsplay.demo.utils.bridges.ActionBarBridge;
import com.marsplay.demo.utils.bridges.ConnectionBridge;
import com.marsplay.demo.utils.bridges.NetworkStateReceiverListener;
import com.marsplay.demo.utils.bridges.PreferenceBridge;
import com.marsplay.demo.prefrences.PreferenceManager;

/**
 * Created by naresh chandani on 09/09/2017
 */

public class BaseActivity extends AppCompatActivity implements ConnectionBridge, ActionBarBridge, PreferenceBridge {

    private static final String TAG = BaseActivity.class.getSimpleName();

    protected AppController app;
    protected String title;
    private ActionBar actionBar;
    private PreferenceManager preferenceManager;
    private NetworkBroadcastReceiver networkBroadcastReceiver;
    private NetworkStateReceiverListener connectionHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initFields();
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtility.printDebugMsg(TAG, "onPause");
        unregisterBroadcastReceivers();
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtility.printDebugMsg(TAG, "onResume");
        registerBroadcastReceivers();
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

        networkBroadcastReceiver = new NetworkBroadcastReceiver();
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
    public boolean checkNetworkAvailableWithError() {

        if (!isNetworkAvailable()) {
            ToastUtils.longToast(R.string.dlg_fail_connection);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean isNetworkAvailable() {
        return ConnectivityUtils.isNetworkAvailable(this);
    }

    @Override
    public void registerConnectionHelper(NetworkStateReceiverListener connectionHelper) {
        this.connectionHelper = connectionHelper;
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

    protected void onConnectionAvailable() {

        if (connectionHelper != null) {
            connectionHelper.networkAvailable();
        }

        //ToastUtils.shortToast("Connection Available");
    }

    protected void onConnectionError() {

        if (connectionHelper != null) {
            connectionHelper.networkUnavailable();
        }

        //ToastUtils.shortToast("Connection Error");
    }

    private void registerBroadcastReceivers() {

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);

        networkBroadcastReceiver = new NetworkBroadcastReceiver();
        registerReceiver(networkBroadcastReceiver, intentFilter);
    }

    private void unregisterBroadcastReceivers() {
        unregisterReceiver(networkBroadcastReceiver);
    }

    protected boolean isAppInstalled(String uri) {

        PackageManager pm = getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_META_DATA);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Replace fragment
     *
     * @param fragment    ,fragment that we have to load
     * @param isBackAllow ,get allow back for fragment
     * @param actionTitle ,action bar title
     */
    public void replaceFragment(Fragment fragment, boolean isBackAllow, String actionTitle) {

        if (fragment == null) {
            return;
        }

        //Get instant of fragment transaction
        FragmentManager fm = getSupportFragmentManager();

        //Get fragment class name
        String backStateName = fragment.getClass().getName();
        //Check fragment present in back stack or not
        boolean isAlreadyLoaded = fm.findFragmentByTag(backStateName) != null;
        LogUtility.printDebugMsg(TAG, "Is Already Loaded : " + isAlreadyLoaded);

        //Stop loading same fragment again n again
        /*if (isAlreadyLoaded) {
            return;
        }*/

       /* if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack();
        }*/

        //Get fragment transaction instance
        FragmentTransaction ft = fm.beginTransaction();

        //Set left to right animation on fragment launch
        //ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);

        //Replace fragment
        // ft.replace(R.id.container_main, fragment, backStateName);

        //Add to back  stack
        if (isBackAllow && !isAlreadyLoaded) {
            ft.addToBackStack(actionTitle);
        }

        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
    }

    private class NetworkBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            LogUtility.printDebugMsg(TAG, "NetworkBroadcastReceiver");

            if (!isNetworkAvailable()) {

                onConnectionError();

            } else {

                onConnectionAvailable();
            }
        }
    }
}