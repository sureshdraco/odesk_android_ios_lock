package com.otherlokscreen.bestlockscreenforandroid32.view;


import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.otherlokscreen.bestlockscreenforandroid32.R;
import com.otherlokscreen.bestlockscreenforandroid32.util.PreferenceUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class LockScreenFragment extends Fragment implements View.OnTouchListener {

    private TextView time, date, slideToUnlock;
    private Handler handler;
    private ArrayList<View> textViews;
    private int slideAnimeIndex = 0;
    private RelativeLayout rootLayout;
    private int height;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.lock_screen_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                handleAnimateText();
            }
        };
        initView();
        setupBackground();
        handler.sendEmptyMessageDelayed(0, 200);
        setupReceiver();
        setupDate();
        setupTime();
    }

    private void setupBackground() {
        if (PreferenceUtil.getSelectedWallpaper(getActivity()).equals("Wallpaper1")) {
            rootLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.wallpaper01));
        } else if (PreferenceUtil.getSelectedWallpaper(getActivity()).equals("Wallpaper2")) {
            rootLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.wallpaper02));
        } else if (PreferenceUtil.getSelectedWallpaper(getActivity()).equals("Wallpaper3")) {
            rootLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.wallpaper03));
        } else {
            //system wallpaper
            rootLayout.setBackgroundDrawable(WallpaperManager.getInstance(getActivity()).getDrawable());
        }
    }

    private void handleAnimateText() {
        if (slideAnimeIndex == 0) {
            textViews.get(5).setVisibility(View.INVISIBLE);
            textViews.get(slideAnimeIndex).setVisibility(View.VISIBLE);
        } else {
            textViews.get(slideAnimeIndex).setVisibility(View.VISIBLE);
            textViews.get(slideAnimeIndex - 1).setVisibility(View.INVISIBLE);
        }
        slideAnimeIndex++;
        if (slideAnimeIndex == 6) {
            slideAnimeIndex = 0;
        }
        handler.sendEmptyMessageDelayed(0, 210);
    }

    private void setupReceiver() {
        getActivity().registerReceiver(timeUpdateReceiver, new IntentFilter(Intent.ACTION_TIME_TICK));
        getActivity().registerReceiver(dateUpdateReceiver, new IntentFilter(Intent.ACTION_DATE_CHANGED));
    }

    private BroadcastReceiver timeUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context ctxt, Intent intent) {
            setupTime();
        }
    };

    private void setupTime() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        time.setText(timeFormat.format(new Date()));
    }

    private BroadcastReceiver dateUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context ctxt, Intent intent) {
            setupDate();
        }
    };

    private void setupDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM d, yyyy");
        date.setText(dateFormat.format(new Date()));
    }

    private void initView() {
        initTextViewFragments();
        time = (TextView) getActivity().findViewById(R.id.timeDisplay);
        date = (TextView) getActivity().findViewById(R.id.date);
        rootLayout = (RelativeLayout) getActivity().findViewById(R.id.wallpaper);
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        rootLayout.setMinimumWidth(display.getWidth());
        height = display.getHeight();
        rootLayout.setMinimumHeight(display.getHeight());
        slideToUnlock = (TextView) getActivity().findViewById(R.id.slide_unlock);
        View camera = getActivity().findViewById(R.id.camera_btn);
        camera.setOnTouchListener(this);
    }

    public boolean onTouch(View view, MotionEvent event) {
        final int Y = (int) event.getRawY();
        final RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) rootLayout.getLayoutParams();
        TranslateAnimation translateAnimation;
        int action = event.getAction() & MotionEvent.ACTION_MASK;
        Log.d("drag", "Event: " + action);
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                translateAnimation = new TranslateAnimation(layoutParams.leftMargin, layoutParams.leftMargin, Y - height, layoutParams.topMargin);
                translateAnimation.setFillAfter(true);
                translateAnimation.setFillEnabled(true);
                rootLayout.startAnimation(translateAnimation);
                break;
            case MotionEvent.ACTION_UP:
                Log.d("drag", "ACTION_UP");
                if (Y < height / 2) {
                    getActivity().finish();
                    safeCameraOpen();
                    return true;
                }
                translateAnimation = new TranslateAnimation(layoutParams.leftMargin, layoutParams.leftMargin, Y - height, 0);
                translateAnimation.setDuration(1000);
                translateAnimation.setFillAfter(true);
                translateAnimation.setFillEnabled(true);
                translateAnimation.setInterpolator(new BounceInterpolator());
                rootLayout.startAnimation(translateAnimation);
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                Log.d("drag", "ACTION_POINTER_DOWN");
                break;
            case MotionEvent.ACTION_POINTER_UP:
                Log.d("drag", "ACTION_POINTER_UP");
                break;
            case MotionEvent.ACTION_MOVE:
                translateAnimation = new TranslateAnimation(layoutParams.leftMargin, layoutParams.leftMargin, Y - height, layoutParams.topMargin);
                translateAnimation.setDuration(10000);
                translateAnimation.setFillAfter(true);
                translateAnimation.setFillEnabled(true);
                rootLayout.startAnimation(translateAnimation);
                break;
        }
        rootLayout.invalidate();
        return true;
    }

    private void initTextViewFragments() {
        textViews = new ArrayList<View>();
        textViews.add(getActivity().findViewById(R.id.slide_unlock1));
        textViews.add(getActivity().findViewById(R.id.slide_unlock2));
        textViews.add(getActivity().findViewById(R.id.slide_unlock3));
        textViews.add(getActivity().findViewById(R.id.slide_unlock4));
        textViews.add(getActivity().findViewById(R.id.slide_unlock5));
        textViews.add(getActivity().findViewById(R.id.slide_unlock6));
    }

    private void safeCameraOpen() {
        Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            PackageManager pm = getActivity().getPackageManager();

            final ResolveInfo mInfo = pm.resolveActivity(i, 0);

            Intent intent = new Intent();
            intent.setComponent(new ComponentName(mInfo.activityInfo.packageName, mInfo.activityInfo.name));
            intent.setAction(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);

            startActivity(intent);
        } catch (Exception e) {
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        try {
            getActivity().unregisterReceiver(timeUpdateReceiver);
            getActivity().unregisterReceiver(dateUpdateReceiver);
        } catch (Exception ignored) {
        }
    }
}
