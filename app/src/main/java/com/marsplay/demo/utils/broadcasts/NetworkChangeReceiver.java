package com.marsplay.demo.utils.broadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.LocalBroadcastManager;

import com.marsplay.demo.application.AppController;
import com.marsplay.demo.utils.LogUtility;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class NetworkChangeReceiver extends BroadcastReceiver {

    public static final String NETWORK_CHANGE_FILTER_NAME = "action_local_connectivity";
    public static final String FIELD_IS_ACTIVE_CONNECTION = "extra_is_active_connection";

    private String TAG = NetworkChangeReceiver.class.getSimpleName();

    @Override
    public void onReceive(final Context context, final Intent intent) {

        boolean activeConnection = isConnectedToInternet(context);

        LogUtility.printDebugMsg(TAG, "onReceive : " + activeConnection);

        notifyAboutChangeConnection(activeConnection);
    }

    private boolean isConnectedToInternet(Context context) {
        try {
            if (context != null) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                return networkInfo != null && networkInfo.isConnected();
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void notifyAboutChangeConnection(boolean activeConnection) {
        Intent intent = new Intent(NETWORK_CHANGE_FILTER_NAME);
        intent.putExtra(FIELD_IS_ACTIVE_CONNECTION, activeConnection);
        LocalBroadcastManager.getInstance(AppController.getInstance()).sendBroadcast(intent);
    }
}