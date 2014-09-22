package com.otherlokscreen.bestlockscreenforandroid32.view;


import android.animation.ObjectAnimator;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.otherlokscreen.bestlockscreenforandroid32.R;
import com.otherlokscreen.bestlockscreenforandroid32.util.OnSwipeTouchListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class LockScreenFragment extends Fragment {

    private TextView time, date, slideToUnlock;
    private Handler handler;
    private ArrayList<View> textViews;
    private int slideAnimeIndex = 0;

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
        handler.sendEmptyMessageDelayed(0, 200);
        setupReceiver();
        setupDate();
        setupTime();
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
        slideToUnlock = (TextView) getActivity().findViewById(R.id.slide_unlock);
        View camera = getActivity().findViewById(R.id.camera_btn);
        camera.setOnTouchListener(new OnSwipeTouchListener(getActivity()) {
            public void onSwipeTop() {
                getActivity().finish();
                getActivity().overridePendingTransition(0, R.anim.slide_up);
                safeCameraOpen();
            }
        });
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
