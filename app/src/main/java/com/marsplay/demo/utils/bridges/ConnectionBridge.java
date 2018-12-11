package com.marsplay.demo.utils.bridges;

public interface ConnectionBridge {

    boolean checkNetworkAvailableWithError();

    boolean isNetworkAvailable();

    void registerConnectionHelper(NetworkStateReceiverListener helper);
}