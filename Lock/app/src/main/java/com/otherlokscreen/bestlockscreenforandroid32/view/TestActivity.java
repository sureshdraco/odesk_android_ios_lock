package com.otherlokscreen.bestlockscreenforandroid32.view;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.otherlokscreen.bestlockscreenforandroid32.R;

public class TestActivity extends Activity implements View.OnTouchListener {

    private ImageView mImageView;
    private FrameLayout mRrootLayout;
    private int height;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout);
        mRrootLayout = (FrameLayout) findViewById(android.R.id.content);
        mImageView = (ImageView) mRrootLayout.findViewById(R.id.imageView);
        Display display = getWindowManager().getDefaultDisplay();
        mRrootLayout.setMinimumWidth(display.getWidth());
        height = display.getHeight();
        mRrootLayout.setMinimumHeight(display.getHeight());
//        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(150, 150);
//        mImageView.setLayoutParams(layoutParams);
        mImageView.setOnTouchListener(this);
    }

    public boolean onTouch(View view, MotionEvent event) {
        final int Y = (int) event.getRawY();
        final LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mRrootLayout.getLayoutParams();
        TranslateAnimation translateAnimation;
        int action = event.getAction() & MotionEvent.ACTION_MASK;
        Log.d("drag", "Event: " + action);
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                translateAnimation = new TranslateAnimation(layoutParams.leftMargin, layoutParams.leftMargin, Y - height, layoutParams.topMargin);
                translateAnimation.setFillAfter(true);
                translateAnimation.setFillEnabled(true);
                mRrootLayout.startAnimation(translateAnimation);
                break;
            case MotionEvent.ACTION_UP:
                Log.d("drag", "ACTION_UP");
                translateAnimation = new TranslateAnimation(layoutParams.leftMargin, layoutParams.leftMargin, Y - height, 0);
                translateAnimation.setDuration(1000);
                translateAnimation.setFillAfter(true);
                translateAnimation.setFillEnabled(true);
                translateAnimation.setInterpolator(new BounceInterpolator());
                mRrootLayout.startAnimation(translateAnimation);
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
                mRrootLayout.startAnimation(translateAnimation);
                break;
        }
        mRrootLayout.invalidate();
        return true;
    }
}