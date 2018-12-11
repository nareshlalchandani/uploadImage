package com.marsplay.demo.ui.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;

import com.marsplay.demo.utils.LogUtility;
import com.marsplay.demo.utils.bridges.ConnectionBridge;
import com.marsplay.demo.utils.bridges.NetworkStateReceiverListener;
import com.marsplay.demo.utils.bridges.PreferenceBridge;
import com.marsplay.demo.prefrences.PreferenceManager;


/**
 * Created by Sarvang1 on 12/14/2016.
 */

public class BaseFragment extends Fragment implements PreferenceBridge, ConnectionBridge {

    private static final String TAG = BaseFragment.class.getSimpleName();

    private NetworkStateReceiverListener connectionHelper;

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

        //Get fragment manager instance
        FragmentManager fm = getActivity().getSupportFragmentManager();

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
        //ft.replace(R.id.ic_container, fragment, backStateName);

        //Add to back  stack
        if (isBackAllow && !isAlreadyLoaded) {
            ft.addToBackStack(actionTitle);
        }

        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
    }

    public ActionBar getSupportedActionBar() {
        return ((BaseActivity) getActivity()).getSupportActionBar();
    }

    @Override
    public PreferenceManager getPreferenceManager() {
        try {
            return ((BaseActivity) getActivity()).getPreferenceManager();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean checkNetworkAvailableWithError() {
        return ((BaseActivity) getActivity()).checkNetworkAvailableWithError();
    }

    @Override
    public boolean isNetworkAvailable() {
        return ((BaseActivity) getActivity()).isNetworkAvailable();
    }

    @Override
    public void registerConnectionHelper(NetworkStateReceiverListener helper) {

        this.connectionHelper = helper;
    }
}

