package com.otherlokscreen.bestlockscreenforandroid32.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.otherlokscreen.bestlockscreenforandroid32.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.nullview, container, false);
    }
}
