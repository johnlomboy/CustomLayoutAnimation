package com.example.john.layoutanimation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

/**
 * Created by Philip on 6/23/2015.
 */
public class CameraCropFragment extends Fragment {

    private static final String TAG = "CameraCropFragment";
    private static EditingTools mEditingTools;
    private SeekBar mCrop;

    public static CameraCropFragment newInstance(EditingTools editingTools) {
        CameraCropFragment.mEditingTools = editingTools;
        CameraCropFragment fragments = new CameraCropFragment();
        Bundle args = new Bundle();
        fragments.setArguments(args);
        return fragments;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_camera_crop, container, false);
        initializeVariables(v);
        return v;
    }

    private void initializeVariables(View v) {
        mCrop = (SeekBar) v.findViewById(R.id.sbCrop);
        mCrop.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mEditingTools.onCropChanged(i - 30);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
