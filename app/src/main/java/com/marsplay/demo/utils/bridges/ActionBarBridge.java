package com.marsplay.demo.utils.bridges;

import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

/**
 * Created by naresh on 10/12/18.
 */

public interface ActionBarBridge {

    void initActionBar();

    void setActionBarTitle(String title);

    void setActionBarTitle(@StringRes int title);

    void setActionBarSubtitle(String subtitle);

    void setActionBarSubtitle(@StringRes int subtitle);

    void setActionBarIcon(Drawable icon);

    void setActionBarIcon(@DrawableRes int icon);

    void setActionBarUpButtonEnabled(boolean enabled);
}