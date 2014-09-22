package com.otherlokscreen.bestlockscreenforandroid32.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.otherlokscreen.bestlockscreenforandroid32.R;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_enter);
    }

    public void onClickPreview(View view) {
        Intent intent = new Intent(this, LockScreenActivity.class);
        startActivity(intent);
    }
}
