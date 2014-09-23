package com.otherlokscreen.bestlockscreenforandroid32.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.otherlokscreen.bestlockscreenforandroid32.R;

import junit.framework.Test;

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

    public void onClickSettings(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void onClickGreatApps(View view) {
//        Toast.makeText(this, "Not Implemented", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, TestActivity.class);
        startActivity(intent);
    }


    public void onClickSydApps(View view) {
        Toast.makeText(this, "Not Implemented", Toast.LENGTH_SHORT).show();
    }

    public void onClickRemoteControl(View view) {
        Toast.makeText(this, "Not Implemented", Toast.LENGTH_SHORT).show();
    }

    public void onClickLast(View view) {
        Toast.makeText(this, "Not Implemented", Toast.LENGTH_SHORT).show();
    }
}
