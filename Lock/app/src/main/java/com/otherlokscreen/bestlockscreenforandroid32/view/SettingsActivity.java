package com.otherlokscreen.bestlockscreenforandroid32.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.view.View;

import com.otherlokscreen.bestlockscreenforandroid32.R;
import com.otherlokscreen.bestlockscreenforandroid32.controller.MyService;
import com.otherlokscreen.bestlockscreenforandroid32.util.PreferenceUtil;

import java.util.HashMap;

public class SettingsActivity extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_layout);
        addPreferencesFromResource(R.xml.lock_settings);

        Preference previewPreference = (Preference) findPreference("key_preview");
        previewPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                startActivity(new Intent(SettingsActivity.this, LockScreenActivity.class));
                return true;
            }
        });

        Preference setWallpaperPreference = (Preference) findPreference("key_select_photo");
        setWallpaperPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                showSelectWallpaperDialog();
                return true;
            }
        });

        Preference enableLock = (Preference) findPreference("key_enable");
        enableLock.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                if (preference.getSharedPreferences().getBoolean("key_enable", true)) {
                    startService(new Intent(SettingsActivity.this, MyService.class));
                }
                return true;
            }
        });

        Preference share = (Preference) findPreference("key_share");
        share.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                Intent share = new Intent(android.content.Intent.ACTION_SEND);
                share.setType("text/plain");
                share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

                //To be replaced
                share.putExtra(Intent.EXTRA_TEXT, "Ios Lock screen");

                startActivity(Intent.createChooser(share, "Share App!"));
                return true;
            }
        });

    }

    private void showSelectWallpaperDialog() {
        final String[] backgrounds = getResources().getStringArray(R.array.select_backgrounds);
        String selectedBackground = PreferenceUtil.getSelectedWallpaper(getApplicationContext());
        int selectedBackgroundIndex = 1;
        for (int i = 0; i < backgrounds.length; i++) {
            if (backgrounds[i].equals(selectedBackground)) {
                selectedBackgroundIndex = i;
            }
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.select_photo));
        builder.setNegativeButton("Cancel", null);
        builder.setCancelable(true);
        builder.setSingleChoiceItems(backgrounds, selectedBackgroundIndex, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == Dialog.BUTTON_NEGATIVE) {
                    dialog.dismiss();
                    return;
                }
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString(getString(R.string.wallpaper), backgrounds[which]).commit();
                dialog.dismiss();
            }
        });
        builder.show();
    }
}
