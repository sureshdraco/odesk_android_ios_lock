package com.otherlokscreen.bestlockscreenforandroid32.view;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.otherlokscreen.bestlockscreenforandroid32.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class LockScreenFragment extends Fragment {

    private TextView time, date;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.lock_screen_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        setupReceiver();
        setupDate();
        setupTime();
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
        time = (TextView) getActivity().findViewById(R.id.timeDisplay);
        date = (TextView) getActivity().findViewById(R.id.date);
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
