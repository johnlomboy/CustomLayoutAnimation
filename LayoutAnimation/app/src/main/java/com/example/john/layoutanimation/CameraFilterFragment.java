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
public class CameraFilterFragment extends Fragment {

    public static CameraFilterFragment newInstance() {
        CameraFilterFragment fragments = new CameraFilterFragment();
        Bundle args = new Bundle();
        fragments.setArguments(args);
        return fragments;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camera_filter, container, false);

        return view;
    }
}
