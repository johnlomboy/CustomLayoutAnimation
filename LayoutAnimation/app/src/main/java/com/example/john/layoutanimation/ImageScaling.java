package com.example.john.layoutanimation;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.widget.ImageView;

/**
 * Created by Philip on 7/8/2015.
 */
public class ImageScaling {

    private static final String TAG = "ImageScaling";
    private Matrix mMatrix;
    private ImageView mView;
    private Bitmap mBitmap;
    private float mViewWidth;
    private float mViewHeight;
    private float mViewWidthHalf;
    private float mViewHeightHalf;
    private float mBitmapWidth;
    private float mBitmapHeight;
    private float mInitialScale;
    private float mScaleFactorX;
    private float mScaleFactorY;
    private float mScaleFactor;

    public ImageScaling(ImageView view, Bitmap bitmap) {
        this.mMatrix = new Matrix();
        this.mView = view;
        this.mBitmap = bitmap;
        this.mViewWidth = view.getWidth();
        this.mViewHeight = view.getHeight();
    }

    public void startScaling() {
        calculateScaling();
    }

    private void calculateScaling() {
        Matrix matrix = mMatrix;
        matrix.reset();

        initializeScalingValues();

        mInitialScale = 1.0f;

        calculateScaleValue();
        matrix.postScale(mScaleFactor, mScaleFactor);

        /**
         * Translate the image up and left half the height
         * and width so rotation (below) is around the center.
         */
        matrix.postTranslate(-mViewWidthHalf, -mViewHeightHalf);

        /**
         * If the bitmap is to be scaled, do so.
         * Also figure out the x and y offset values, which start
         * with the values assigned to the view
         * and are adjusted based on the scale.
         */
        if (mInitialScale != 1.0f) {
            matrix.postScale(mInitialScale, mInitialScale);
        }

        invalidate(matrix);
    }

    public void scaleAndRotateImage(int degree, boolean isLayoutAnimated) {
        Matrix matrix = mMatrix;
        matrix.reset();

        initializeScalingValues();

        if (isLayoutAnimated) {
            mInitialScale = Math.abs((float) degree / 30) + (float) 1;
        } else {
            mInitialScale = 1.0f;
        }

        calculateScaleValue();
        matrix.postScale(mScaleFactor, mScaleFactor);

        matrix.postTranslate(-mViewWidthHalf, -mViewHeightHalf);

        matrix.postRotate(degree);

        if (mInitialScale != 1.0f) {
            matrix.postScale(mInitialScale, mInitialScale);
        }

        invalidate(matrix);
    }

    private void initializeScalingValues() {
        mViewWidthHalf = mViewWidth / 2;
        mViewHeightHalf = mViewHeight / 2;
        mBitmapWidth = (float) mBitmap.getWidth();
        mBitmapHeight = (float) mBitmap.getHeight();
    }

    /**
     * First scale the bitmap to fit into the view.
     * Use either scale factor for width and height,
     * whichever is the smallest.
     */
    private void calculateScaleValue() {
        mScaleFactorX = mViewWidth / mBitmapWidth;
        mScaleFactorY = mViewHeight / mBitmapHeight;
        mScaleFactor = (mScaleFactorX < mScaleFactorY) ? mScaleFactorX : mScaleFactorY;
    }

    /**
     * The last translation moves the bitmap to where it has to be to have its top left point be
     * where it should be following the rotation and scaling.
     */
    private void invalidate(Matrix matrix) {
        matrix.postTranslate(mViewWidthHalf, mViewHeightHalf);
        mView.setImageMatrix(matrix);
        mView.invalidate();
    }
}
