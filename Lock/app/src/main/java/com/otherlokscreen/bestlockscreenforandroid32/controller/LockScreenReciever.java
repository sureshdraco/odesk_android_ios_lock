package com.otherlokscreen.bestlockscreenforandroid32.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.otherlokscreen.bestlockscreenforandroid32.R;
import com.otherlokscreen.bestlockscreenforandroid32.util.PreferenceUtil;
import com.otherlokscreen.bestlockscreenforandroid32.view.LockScreenActivity;

public class LockScreenReciever extends BroadcastReceiver {
    public static boolean wasScreenOn = true;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (!PreferenceUtil.isLockScreenEnabled(context))
            return;
        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            wasScreenOn = false;
            Intent intent11 = new Intent(context, LockScreenActivity.class);
            intent11.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent11);
            // do whatever you need to do here
            //wasScreenOn = false;
        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            wasScreenOn = true;
            Intent intent11 = new Intent(context, LockScreenActivity.class);
            intent11.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } else if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            Intent intent11 = new Intent(context, LockScreenActivity.class);
            intent11.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent11);
        }
    }
}
