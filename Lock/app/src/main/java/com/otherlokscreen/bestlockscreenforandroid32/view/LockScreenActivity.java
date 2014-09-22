package com.otherlokscreen.bestlockscreenforandroid32.view;

import com.otherlokscreen.bestlockscreenforandroid32.R;
import com.otherlokscreen.bestlockscreenforandroid32.controller.MyService;

import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.BatteryManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.ArrayList;


public class LockScreenActivity extends FragmentActivity {

    private TextView operatorName, battery;
    private ViewPager viewPager;
    private View backgroundWallpaper;
    private boolean isUnlock = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG | WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lock_screen_activity);
        setupReceiver();
        initView();
    }

    private void setupReceiver() {
        // initialize receiver
        startService(new Intent(this, MyService.class));
        StateListener phoneStateListener = new StateListener();
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
        this.registerReceiver(batInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    private void initView() {
        backgroundWallpaper = findViewById(R.id.wallpaper);
        backgroundWallpaper.setBackgroundDrawable(getSelectedWallpaper());
        operatorName = (TextView) findViewById(R.id.operator);
        battery = (TextView) findViewById(R.id.battery_value);
        viewPager = (ViewPager) findViewById(R.id.pager);
        final ArrayList<Fragment> fragmentArrayList = new ArrayList<Fragment>();
        fragmentArrayList.add(new BlankFragment());
        fragmentArrayList.add(new LockScreenFragment());
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return fragmentArrayList.get(i);
            }

            @Override
            public int getCount() {
                return fragmentArrayList.size();
            }
        });
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {
            }

            @Override
            public void onPageSelected(int i) {
                if (i == 0 && isUnlock) {
                    finish();
                } else {
                    isUnlock = true;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
        viewPager.setCurrentItem(2);
    }

    private Drawable getSelectedWallpaper() {
        String selectedWallpaper = PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
                .getString(getString(R.string.wallpaper), "Wallpaper1");
        if (selectedWallpaper.equals("System Wallpaper")) {
            return WallpaperManager.getInstance(getApplicationContext()).getDrawable();
        } else if (selectedWallpaper.equals("Wallpaper1")) {
            return getResources().getDrawable(R.drawable.wallpaper01);
        } else if (selectedWallpaper.equals("Wallpaper2")) {
            return getResources().getDrawable(R.drawable.wallpaper02);
        } else {
            return getResources().getDrawable(R.drawable.wallpaper03);
        }
    }

    private BroadcastReceiver batInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context ctxt, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            battery.setText(String.valueOf(level) + "%");
        }
    };

    private void setupView() {
        TelephonyManager manager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        operatorName.setText(manager.getSimOperatorName());
    }

    class StateListener extends PhoneStateListener {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);
            switch (state) {
                case TelephonyManager.CALL_STATE_RINGING:
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    finish();
                    break;
                case TelephonyManager.CALL_STATE_IDLE:
                    break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        //ignore
    }

    //only used in lockdown mode
    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupView();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) || (keyCode == KeyEvent.KEYCODE_POWER) || (keyCode == KeyEvent.KEYCODE_VOLUME_UP) || (keyCode == KeyEvent.KEYCODE_CAMERA)) {
            //this is where I can do my stuff
            return true; //because I handled the event
        }
        if ((keyCode == KeyEvent.KEYCODE_HOME)) {
            return true;
        }
        return false;

    }

    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_POWER || (event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_DOWN) || (event.getKeyCode() == KeyEvent.KEYCODE_POWER)) {
            return false;
        }
        if ((event.getKeyCode() == KeyEvent.KEYCODE_HOME)) {
            return true;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(batInfoReceiver);
    }
}
