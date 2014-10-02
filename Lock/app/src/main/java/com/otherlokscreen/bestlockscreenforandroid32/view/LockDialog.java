package com.otherlokscreen.bestlockscreenforandroid32.view;

import android.app.Dialog;
import android.content.Context;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

import com.otherlokscreen.bestlockscreenforandroid32.R;

public class LockDialog extends Dialog {
    private DialogCancelledListener mDialogCancelledListener = null;

    public LockDialog(Context context) {
        super(context);
        Window window = this.getWindow();
        window.setBackgroundDrawableResource(R.drawable.background_dialog_window);
        setCancelable(false);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_SEARCH) {
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void setCancelable(boolean flag) {
        // If dialog cannot be cancelled, DialogCancelledListener should not be
        // called
        // when back key is pressed
        if (flag == false) {
            mDialogCancelledListener = null;
        }
        super.setCancelable(flag);
    }

    public void setDialogCancelledListener(DialogCancelledListener dlgClosedListener) {
        super.setCancelable(true);
        mDialogCancelledListener = dlgClosedListener;
    }

    @Override
    public void onBackPressed() {
    }
}
