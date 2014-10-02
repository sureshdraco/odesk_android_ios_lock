package com.otherlokscreen.bestlockscreenforandroid32.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.otherlokscreen.bestlockscreenforandroid32.R;
import com.otherlokscreen.bestlockscreenforandroid32.view.LockDialog;

public class ConfirmDialogUtil {

    private static final String CONFIRM_DIALOG_SHOWN = "confirmDialogShown";
    public static final int DIALOG_CONFIRM = 1;

    public static LockDialog getConfirmDialog(final Context mContext) {
        final LockDialog dialog = new LockDialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.confirm_dialog);
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final Button bNow = (Button) dialog.findViewById(R.id.confirm);
        bNow.setEnabled(false);
        bNow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                PreferenceManager.getDefaultSharedPreferences(mContext).edit().putBoolean(CONFIRM_DIALOG_SHOWN, true).commit();
                dialog.dismiss();
            }
        });

        Button bLater = (Button) dialog.findViewById(R.id.confirmLater);
        bLater.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        final CheckBox checkBox = (CheckBox) dialog.findViewById(R.id.confirmCheckBox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    bNow.setEnabled(true);
                } else {
                    bNow.setEnabled(false);
                }
            }
        });
        dialog.findViewById(R.id.confirmText).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBox.toggle();
            }
        });

        return dialog;
    }

    public static boolean isDialogShown(Context applicationContext) {
        return PreferenceManager.getDefaultSharedPreferences(applicationContext).getBoolean(CONFIRM_DIALOG_SHOWN, false);
    }
}
