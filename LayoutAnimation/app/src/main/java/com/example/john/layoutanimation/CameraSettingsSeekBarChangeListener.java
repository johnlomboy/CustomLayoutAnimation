package com.example.john.layoutanimation;

import android.util.Log;
import android.widget.SeekBar;

/**
 * Created by Philip on 6/29/2015.
 */
public class CameraSettingsSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {

    private static final String TAG = "CameraSeekBarChange..";
    private int settingType;
    private CameraSeekBarCallback c;

    public CameraSettingsSeekBarChangeListener(int settingType, CameraSeekBarCallback c) {
        this.settingType = settingType;
        this.c = c;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        calculateValue(i);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    private void calculateValue(float i) {
        switch (settingType) {
            case CameraSettingsFragment.BRIGHTNESS:
            case CameraSettingsFragment.EXPOSURE:
                c.onSeekBarChanged((i - 100) / 100);
                break;
            case CameraSettingsFragment.CONTRAST:
            case CameraSettingsFragment.SATURATION:
            case CameraSettingsFragment.TEMPERATURE:
                c.onSeekBarChanged(i / 100);
                break;
            default:
                Log.e(TAG, "Type not found");
                break;
        }
    }

    public interface CameraSeekBarCallback {
        void onSeekBarChanged(float value);
    }
}
