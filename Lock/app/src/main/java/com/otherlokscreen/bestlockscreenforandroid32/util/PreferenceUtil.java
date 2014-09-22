package com.otherlokscreen.bestlockscreenforandroid32.util;

import android.content.Context;
import android.preference.PreferenceManager;

import com.otherlokscreen.bestlockscreenforandroid32.R;

/**
 * Created by suresh.kumar on 2014-09-22.
 */
public class PreferenceUtil {
    public static boolean isLockScreenEnabled(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean("key_enable", true);
    }

    public static String getSelectedWallpaper(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(context.getString(R.string.wallpaper), "wallpaper01");
    }
}
