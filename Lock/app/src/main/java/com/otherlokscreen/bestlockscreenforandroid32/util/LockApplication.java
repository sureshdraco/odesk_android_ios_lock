package com.otherlokscreen.bestlockscreenforandroid32.util;

import android.app.Application;

public class LockApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        TypefaceCache.init(this);
    }
}
