package com.marsplay.demo.net;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by naresh on 10/12/18.
 */

public class ConnectionChecker {

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager
                .getActiveNetworkInfo().isConnectedOrConnecting();
    }
}

