package com.example.john.layoutanimation;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

/**
 * Created by Philip on 6/23/2015.
 */
public class CameraSettingsFragment extends Fragment {

    private static final String TAG = "CameraSettingsFragment";

    public static final int BRIGHTNESS = 0;
    public static final int CONTRAST = 1;
    public static final int SATURATION = 2;
    public static final int TEMPERATURE = 3;
    public static final int EXPOSURE = 4;

    private SeekBar mBrightnessBar;
    private SeekBar mSaturation;
    private Paint mPaint;
    private SeekBar mTemperature;
    private SeekBar mContrast;
    private SeekBar mExposure;

    public static CameraSettingsFragment newInstance() {
        CameraSettingsFragment fragments = new CameraSettingsFragment();
        Bundle args = new Bundle();
        fragments.setArguments(args);
        return fragments;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_camera_settings, container, false);
        initializeVariables(v);
        return v;
    }

    private void initializeVariables(View v) {
        mBrightnessBar = (SeekBar) v.findViewById(R.id.sbBrightness);
        mBrightnessBar.setOnSeekBarChangeListener(new CameraSettingsSeekBarChangeListener(BRIGHTNESS, new CameraSettingsSeekBarChangeListener.CameraSeekBarCallback() {
            @Override
            public void onSeekBarChanged(float value) {
                Log.e(TAG, "Brightness : " + value);
            }
        }));
        mContrast = (SeekBar) v.findViewById(R.id.sbContrast);
        mContrast.setOnSeekBarChangeListener(new CameraSettingsSeekBarChangeListener(CONTRAST, new CameraSettingsSeekBarChangeListener.CameraSeekBarCallback() {
            @Override
            public void onSeekBarChanged(float value) {
                Log.e(TAG, "Contrast : " + value);
            }
        }));
        mSaturation = (SeekBar) v.findViewById(R.id.sbSaturation);
        mSaturation.setOnSeekBarChangeListener(new CameraSettingsSeekBarChangeListener(SATURATION, new CameraSettingsSeekBarChangeListener.CameraSeekBarCallback() {
            @Override
            public void onSeekBarChanged(float value) {
                Log.e(TAG, "Saturation : " + value);
            }
        }));
        mTemperature = (SeekBar) v.findViewById(R.id.sbTemperature);
        mTemperature.setOnSeekBarChangeListener(new CameraSettingsSeekBarChangeListener(TEMPERATURE, new CameraSettingsSeekBarChangeListener.CameraSeekBarCallback() {
            @Override
            public void onSeekBarChanged(float value) {
                Log.e(TAG, "Temperature : " + value);
            }
        }));
        mExposure = (SeekBar) v.findViewById(R.id.sbExposure);
        mExposure.setOnSeekBarChangeListener(new CameraSettingsSeekBarChangeListener(EXPOSURE, new CameraSettingsSeekBarChangeListener.CameraSeekBarCallback() {
            @Override
            public void onSeekBarChanged(float value) {
                Log.e(TAG, "Exposure : " + value);
            }
        }));
    }


}
