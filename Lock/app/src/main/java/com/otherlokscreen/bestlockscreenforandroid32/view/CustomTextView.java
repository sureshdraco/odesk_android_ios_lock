package com.otherlokscreen.bestlockscreenforandroid32.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.otherlokscreen.bestlockscreenforandroid32.R;
import com.otherlokscreen.bestlockscreenforandroid32.util.TypefaceCache;

public class CustomTextView extends TextView {
	private static final String TAG = TextView.class.getSimpleName();

	public CustomTextView(Context context) {
		super(context);
	}

	public CustomTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setCustomFont(context, attrs);
	}

	public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setCustomFont(context, attrs);
	}

	private void setCustomFont(Context ctx, AttributeSet attrs) {
		TypedArray a = ctx.obtainStyledAttributes(attrs, R.styleable.CustomTextView);
		String customFont = a.getString(R.styleable.CustomTextView_font);
		setCustomFont(customFont);
		a.recycle();
	}

	public boolean setCustomFont(String asset) {
		if(!isInEditMode()) {
			Typeface tf = TypefaceCache.getTypeface(asset);
			if(tf != null) {
				setTypeface(tf);
			}
		}
		return true;
	}

}
