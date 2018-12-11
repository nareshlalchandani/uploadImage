package com.marsplay.demo.prefrences;

import android.content.Context;
import android.content.SharedPreferences;

import com.marsplay.demo.utils.AppConstant;

/**
 * Created by ubuntu11 on 10/3/16.
 */

public class PreferenceManager {

    private final static String SHARE = AppConstant.APP_NAME;
    private final static String SHARE_GCM = AppConstant.APP_NAME + "_gcm";
    private SharedPreferences preferences;

    private SharedPreferences preferencesGCMID;
    private SharedPreferences.Editor editor;

    private static final String GCM_ID = "g1";
    private static final String GCM_STATUS = "g2";

    private static final String PREFERENCE_SAVED = "a1";
    private static final String PROPERTY_APP_VERSION = "a2";

    private static final String FIELD_EMPLOYEE_ID = "u1";
    private static final String FIELD_MOBILE_NUMBER = "u2";
    private static final String FIELD_FULL_NAME = "u3";
    private static final String FIELD_EMAIL = "u4";

    private static final String FIELD_NOTIFICATION = "n1";
    private static final String FIELD_NOTIFICATION_SOUND = "n2";
    private static final String FIELD_NOTIFICATION_VIBRATION = "n3";
    private static final String FIELD_NOTIFICATION_COUNT = "n4";

    public PreferenceManager(Context context) {
        preferences = context.getSharedPreferences(SHARE, Context.MODE_PRIVATE);
        preferencesGCMID = context.getSharedPreferences(SHARE_GCM, Context.MODE_PRIVATE);
    }

    public void setPreference(boolean isSaved) {
        editor = preferences.edit();
        editor.putBoolean(PREFERENCE_SAVED, isSaved);
        editor.apply();
    }

    public boolean isPreferenceSaved() {
        return preferences.getBoolean(PREFERENCE_SAVED, false);
    }

    public void setGcmPostStatus(boolean isSent) {
        editor = preferences.edit();
        editor.putBoolean(GCM_STATUS, isSent);
        editor.apply();
    }

    public boolean getGcmPostStatus() {
        return preferences.getBoolean(GCM_STATUS, false);
    }

    public String getEmployeeId() {
        return preferences.getString(FIELD_EMPLOYEE_ID, null);
    }

    public void setEmployeeId(String employeeId) {
        editor = preferences.edit();
        editor.putString(FIELD_EMPLOYEE_ID, employeeId);
        editor.apply();
    }

    public String getEmail() {
        return preferences.getString(FIELD_EMAIL, null);
    }

    public void setEmail(String employeeId) {
        editor = preferences.edit();
        editor.putString(FIELD_EMAIL, employeeId);
        editor.apply();
    }

    public String getMobileNumber() {
        return preferences.getString(FIELD_MOBILE_NUMBER, null);
    }

    public void setMobileNumber(String serviceName) {
        editor = preferences.edit();
        editor.putString(FIELD_MOBILE_NUMBER, serviceName);
        editor.apply();
    }

    public String getFullName() {
        return preferences.getString(FIELD_FULL_NAME, null);
    }

    public void setFullName(String name) {
        editor = preferences.edit();
        editor.putString(FIELD_FULL_NAME, name);
        editor.apply();
    }

    public void setGcmId(String gcmId) {

        editor = preferencesGCMID.edit();
        editor.putString(GCM_ID, gcmId);
        editor.apply();
    }

    public String getGcmId() {

        return preferencesGCMID.getString(GCM_ID, null);
    }

    public void setAppVersion(String appVersion) {

        editor = preferences.edit();
        editor.putString(PROPERTY_APP_VERSION, appVersion);
        editor.apply();
    }

    public String getAppVersion() {

        return preferences.getString(PROPERTY_APP_VERSION, null);
    }

    public boolean isNotificationEnable() {
        return preferences.getBoolean(FIELD_NOTIFICATION, true);
    }

    public void setNotificationEnableStatus(boolean isNotificationOn) {
        editor = preferences.edit();
        editor.putBoolean(FIELD_NOTIFICATION, isNotificationOn);
        editor.apply();
    }


    public boolean isNotificationSoundEnable() {
        return preferences.getBoolean(FIELD_NOTIFICATION_SOUND, true);
    }

    public void setNotificationSoundStatus(boolean isNotificationSoundOn) {
        editor = preferences.edit();
        editor.putBoolean(FIELD_NOTIFICATION_SOUND, isNotificationSoundOn);
        editor.apply();
    }


    public boolean isNotificationVibrationEnable() {
        return preferences.getBoolean(FIELD_NOTIFICATION_VIBRATION, true);
    }

    public void setNotificationVibrationStatus(boolean isVibrationOn) {
        editor = preferences.edit();
        editor.putBoolean(FIELD_NOTIFICATION_VIBRATION, isVibrationOn);
        editor.apply();
    }


    public int getNotificationCount() {
        return preferences.getInt(FIELD_NOTIFICATION_COUNT, 0);
    }

    public void setNotificationCount(int count) {
        editor = preferences.edit();
        editor.putInt(FIELD_NOTIFICATION_COUNT, count);
        editor.apply();
    }

    public void logout() {
        editor = preferences.edit();
        editor.clear();
        editor.apply();
    }

}
