package com.example.john.layoutanimation;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by Philip on 7/10/2015.
 */
public class ResizeAnimation extends Animation {
    final int mStartHeight;
    final int mTargetHeight;
    private View view;

    public ResizeAnimation(View view, int targetHeight) {
        this.view = view;
        this.mTargetHeight = targetHeight;
        mStartHeight = view.getHeight();
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        int newHeight = (int) (mStartHeight + (mTargetHeight - mStartHeight) * interpolatedTime);
        view.getLayoutParams().height = newHeight;
        view.requestLayout();
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
    }

    @Override
    public boolean willChangeBounds() {
        return true;
    }
}
