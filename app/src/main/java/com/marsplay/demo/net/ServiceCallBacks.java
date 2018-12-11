package com.marsplay.demo.net;

public interface ServiceCallBacks {

    int UPLOAD_IMAGE = 1;
    int IMAGES = 2;

    /**
     * Call back method to pass the data to caller.
     *
     * @param data   : response gathered from server.
     * @param caller to identify service call for what
     */
    void onRequestComplete(Object data, int caller);

    /**
     * Call back method in case the there is some error while fetching data from server
     *
     * @param errorString : Error string to let caller know.
     * @param caller      to identify service call for what
     */
    void onError(String errorString, int caller);

    /**
     * callback method in case service request got canceled
     *
     * @param errorString show error message to user:
     * @param caller      to identify service call for what
     */
    void onRequestCancel(String errorString, int caller);
}
