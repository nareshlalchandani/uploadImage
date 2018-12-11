package com.marsplay.demo.prefrences;

import android.content.Context;
import android.content.SharedPreferences;

import com.marsplay.demo.utils.AppConstant;

/**
 * Created by naresh on 10/12/18.
 */

public class PreferenceManager {

    private final static String SHARE = AppConstant.APP_NAME;
    private SharedPreferences preferences;

    private SharedPreferences.Editor editor;

    private static final String GCM_ID = "g1";

    private static final String PREFERENCE_SAVED = "a1";
    private static final String PROPERTY_APP_VERSION = "a2";

    private static final String FIELD_EMPLOYEE_ID = "u1";


    public PreferenceManager(Context context) {
        preferences = context.getSharedPreferences(SHARE, Context.MODE_PRIVATE);
    }

    public void setPreference(boolean isSaved) {
        editor = preferences.edit();
        editor.putBoolean(PREFERENCE_SAVED, isSaved);
        editor.apply();
    }

    public boolean isPreferenceSaved() {
        return preferences.getBoolean(PREFERENCE_SAVED, false);
    }


    public String getEmployeeId() {
        return preferences.getString(FIELD_EMPLOYEE_ID, null);
    }

    public void setEmployeeId(String employeeId) {
        editor = preferences.edit();
        editor.putString(FIELD_EMPLOYEE_ID, employeeId);
        editor.apply();
    }

    public void setGcmId(String gcmId) {

        editor = preferences.edit();
        editor.putString(GCM_ID, gcmId);
        editor.apply();
    }

    public String getGcmId() {

        return preferences.getString(GCM_ID, null);
    }

    public void setAppVersion(String appVersion) {

        editor = preferences.edit();
        editor.putString(PROPERTY_APP_VERSION, appVersion);
        editor.apply();
    }

    public String getAppVersion() {

        return preferences.getString(PROPERTY_APP_VERSION, null);
    }

    public void logout() {
        editor = preferences.edit();
        editor.clear();
        editor.apply();
    }
}
