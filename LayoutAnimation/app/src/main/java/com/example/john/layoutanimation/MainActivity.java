package com.example.john.layoutanimation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private RelativeLayout mEditingToolsLayout;
    private LinearLayout mMainToolsLayout;
    private LinearLayout mToolDisplay;
    private LinearLayout mNavigationLayout;

    private ImageView mCrop;
    private ImageView mFilter;
    private ImageView mSettings;
    private ImageView mFrame;
    private Button mBackReset;
    private Button mNext;

    private int mMainToolsHeight = 0;
    private int mActualScreenHeight = 0;
    private int mToolbarHeight = 0;
    private float mEditingToolsY = 0;
    private boolean isValuesChanged = false;
    private boolean isLayoutAnimated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeVariables();
    }

    private void initializeVariables() {
        getScreenHeight();
        mToolbarHeight = getTabHeight(this);

        mToolDisplay = (LinearLayout) findViewById(R.id.llToolDisplay);

        mEditingToolsLayout = (RelativeLayout) findViewById(R.id.rlCameraEditingTools);
        mEditingToolsLayout.post(new Runnable() {
            @Override
            public void run() {
                mEditingToolsY = mEditingToolsLayout.getY();
            }
        });
        mMainToolsLayout = (LinearLayout) findViewById(R.id.rlCameraMainTools);
        mMainToolsLayout.post(new Runnable() {
            @Override
            public void run() {
                mMainToolsHeight = mMainToolsLayout.getHeight();
            }
        });
        mNavigationLayout = (LinearLayout) findViewById(R.id.llCameraNavigation);

        mCrop = (ImageView) findViewById(R.id.ivCrop);
        mFilter = (ImageView) findViewById(R.id.ivFilter);
        mSettings = (ImageView) findViewById(R.id.ivSettings);
        mFrame = (ImageView) findViewById(R.id.ivFrame);
        mBackReset = (Button) findViewById(R.id.bCameraNavigationBackReset);
        mNext = (Button) findViewById(R.id.bCameraNavigationNext);

        mMainToolsLayout.setOnClickListener(this);
        mCrop.setOnClickListener(this);
        mFilter.setOnClickListener(this);
        mSettings.setOnClickListener(this);
        mFrame.setOnClickListener(this);
        mBackReset.setOnClickListener(this);
        mNext.setOnClickListener(this);
    }

    private void getScreenHeight() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        mActualScreenHeight = displaymetrics.heightPixels;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rlCameraMainTools:
                changeLayout(R.id.rlCameraMainTools);
                changeLayoutVisibility(isLayoutAnimated);
                animateLayout();
                mMainToolsLayout.setEnabled(false);
                break;
            case R.id.ivCrop:
                changeSelectStateToTrue(mCrop);
                changeSelectStateToFalse(mFilter, mSettings, mFrame);
                changeLayout(R.id.ivCrop);
                break;
            case R.id.ivFilter:
                changeSelectStateToTrue(mFilter);
                changeSelectStateToFalse(mCrop, mSettings, mFrame);
                changeLayout(R.id.ivFilter);
                break;
            case R.id.ivSettings:
                changeSelectStateToTrue(mSettings);
                changeSelectStateToFalse(mCrop, mFilter, mFrame);
                changeLayout(R.id.ivSettings);
                break;
            case R.id.ivFrame:
                changeSelectStateToTrue(mFrame);
                changeSelectStateToFalse(mCrop, mFilter, mSettings);
                changeLayout(R.id.ivFrame);
                break;
            case R.id.bCameraNavigationBackReset:
                if (isValuesChanged) {
                    /**
                     * TODO : reset editing tools values then change button text to "Back"
                     */
                } else {
                    setViewsClickListener(!isLayoutAnimated);
                    animateLayout();
                    isValuesChanged = false;
                }
                break;
            case R.id.bCameraNavigationNext:
                /**
                 * TODO : proceed to post activity
                 */
                break;
        }
    }

    private void animateLayout() {
        final long animDuration = 250;
        final int yPosition = (int) mEditingToolsY - ((int) ((double) mActualScreenHeight / 2));
        mNavigationLayout.getLayoutParams().height = mToolbarHeight;
        mToolDisplay.getLayoutParams().height = mMainToolsHeight + yPosition - mToolbarHeight;


        AnimatorSet setAnim = new AnimatorSet();

        if (isLayoutAnimated) {
            ObjectAnimator editingToolsAnim = ObjectAnimator.ofFloat(mEditingToolsLayout, View.TRANSLATION_Y, mEditingToolsLayout.getTranslationY(), 0).setDuration(animDuration);
            ObjectAnimator toolDisplayAnim = ObjectAnimator.ofFloat(mToolDisplay, View.TRANSLATION_Y, 0, mToolDisplay.getLayoutParams().height).setDuration(animDuration);
            ObjectAnimator navigationAnim = ObjectAnimator.ofFloat(mNavigationLayout, View.TRANSLATION_Y, 0, mToolbarHeight).setDuration(animDuration);
            setAnim.playTogether(editingToolsAnim, toolDisplayAnim, navigationAnim);
        } else {
            ObjectAnimator editingToolsAnim = ObjectAnimator.ofFloat(mEditingToolsLayout, View.TRANSLATION_Y, 0, -yPosition).setDuration(animDuration);
            ObjectAnimator toolDisplayAnim = ObjectAnimator.ofFloat(mToolDisplay, View.TRANSLATION_Y, mToolDisplay.getLayoutParams().height, -mToolbarHeight).setDuration(animDuration);
            ObjectAnimator navigationAnim = ObjectAnimator.ofFloat(mNavigationLayout, View.TRANSLATION_Y, mToolbarHeight, 0).setDuration(animDuration);
            setAnim.playTogether(editingToolsAnim, toolDisplayAnim, navigationAnim);
        }
        setAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                if (!isLayoutAnimated) {
                    changeLayoutVisibility(isLayoutAnimated);
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (isLayoutAnimated) {
                    changeLayoutVisibility(isLayoutAnimated);
                    mMainToolsLayout.setEnabled(isLayoutAnimated);
                    isLayoutAnimated = false;
                    changeSelectStateToFalse(mCrop, mFilter, mSettings, mFrame);
                } else {
                    isLayoutAnimated = true;
                    setViewsClickListener(isLayoutAnimated);
                }
            }
        });
        setAnim.start();

    }

    private void changeLayoutVisibility(boolean isDisplayed) {
        if (isDisplayed) {
            mToolDisplay.setVisibility(View.GONE);
            mNavigationLayout.setVisibility(View.GONE);
        } else {
            mToolDisplay.setVisibility(View.VISIBLE);
            mNavigationLayout.setVisibility(View.VISIBLE);
        }
    }

    private void changeLayout(int viewId) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        switch (viewId) {
            case R.id.rlCameraMainTools:
                changeSelectStateToTrue(mCrop);
                ft.replace(mToolDisplay.getId(), CameraCropFragment.newInstance());
                break;
            case R.id.ivCrop:
                ft.replace(mToolDisplay.getId(), CameraCropFragment.newInstance());
                break;
            case R.id.ivFilter:
                ft.replace(mToolDisplay.getId(), CameraFilterFragment.newInstance());
                break;
            case R.id.ivSettings:
                ft.replace(mToolDisplay.getId(), CameraSettingsFragment.newInstance());
                break;
            case R.id.ivFrame:
                ft.replace(mToolDisplay.getId(), CameraFrameFragment.newInstance());
                break;
        }
        ft.commit();
    }

    private void changeSelectStateToTrue(ImageView image) {
        image.setSelected(true);
    }

    private void changeSelectStateToFalse(ImageView... items) {
        for (ImageView image : items) {
            image.setSelected(false);
        }
    }

    private void setViewsClickListener(boolean flag) {
        mCrop.setEnabled(flag);
        mFilter.setEnabled(flag);
        mSettings.setEnabled(flag);
        mFrame.setEnabled(flag);
        mBackReset.setEnabled(flag);
        mNext.setEnabled(flag);
    }

    public static int getTabHeight(Context mContext) {
        final TypedArray styledAttributes = mContext.getTheme().obtainStyledAttributes(
                new int[]{android.R.attr.actionBarSize});
        int mActionBarSize = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();
        return mActionBarSize;
    }
}
