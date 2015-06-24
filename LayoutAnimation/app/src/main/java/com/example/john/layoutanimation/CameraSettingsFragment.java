package com.example.john.layoutanimation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Philip on 6/23/2015.
 */
public class CameraSettingsFragment extends Fragment {

    public static CameraSettingsFragment newInstance() {
        CameraSettingsFragment fragments = new CameraSettingsFragment();
        Bundle args = new Bundle();
        fragments.setArguments(args);
        return fragments;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camera_settings, container, false);

        return view;
    }
}
