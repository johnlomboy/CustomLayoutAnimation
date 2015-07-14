package com.example.john.layoutanimation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by Philip on 7/7/2015.
 */
public class EditingToolsGestureListener extends GestureDetector.SimpleOnGestureListener {
    private static final String TAG = "EditingToolsGesture";
    private final int initialContainerHeight;
    private final int reducedContainerHeight;
    private final int halvedYPosition;

    private RelativeLayout.LayoutParams params;
    private RelativeLayout mToolsContainer;
    private RelativeLayout mEditingToolsLayout;

    private int animatedYPosition;
    private long animDuration = 250;
    private boolean isLayoutAtBottom = false;


    public EditingToolsGestureListener(RelativeLayout toolContainer, RelativeLayout editingToolsLayout, int animatedYPosition) {
        this.mToolsContainer = toolContainer;
        this.mEditingToolsLayout = editingToolsLayout;
        this.animatedYPosition = animatedYPosition;
        params = (RelativeLayout.LayoutParams) mToolsContainer.getLayoutParams();
        initialContainerHeight = params.height;
        halvedYPosition = initialContainerHeight - animatedYPosition / 2;
        reducedContainerHeight = initialContainerHeight - animatedYPosition;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        mEditingToolsLayout.setY(mEditingToolsLayout.getY());
        return false;
    }


    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        final float totalY = mEditingToolsLayout.getY() + e2.getY();
        final float touchY = e2.getY(e2.getPointerId(0));
        final float translationY = mEditingToolsLayout.getTranslationY();

        /**
         * if true = subtract container height
         * else = add container height
         */
        if (touchY > 0) {
            if (translationY < -10) {
                mEditingToolsLayout.setY((int) totalY);
                params.height -= touchY;
            }
        } else {
            if (translationY > -animatedYPosition) {
                mEditingToolsLayout.setY((int) totalY);
                params.height -= touchY;
            }
        }
        mToolsContainer.setLayoutParams(params);
        mEditingToolsLayout.invalidate();
        return false;
    }

    /**
     * @param isTopToBottom animate layout from top to bottom
     */
    private void animateLayout(boolean isTopToBottom) {
        AnimatorSet setAnimator = new AnimatorSet();
        if (isTopToBottom) {
            ObjectAnimator animateTopToBottom = ObjectAnimator.ofFloat(mEditingToolsLayout, View.TRANSLATION_Y, mEditingToolsLayout.getTranslationY(), 0).setDuration(animDuration);
            setAnimator.playTogether(animateTopToBottom);
            setAnimator.addListener(new AnimatorListenerAdapter() {

                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    ResizeAnimation resizeAnimation = new ResizeAnimation(mToolsContainer, reducedContainerHeight);
                    resizeAnimation.setDuration(animDuration - 50);
                    mToolsContainer.startAnimation(resizeAnimation);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    isLayoutAtBottom = true;
                }
            });
        } else {
            ObjectAnimator animateBottomToTop = ObjectAnimator.ofFloat(mEditingToolsLayout, View.TRANSLATION_Y, mEditingToolsLayout.getTranslationY(), -animatedYPosition).setDuration(animDuration);
            setAnimator.playTogether(animateBottomToTop);
            setAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    ResizeAnimation resizeAnimation = new ResizeAnimation(mToolsContainer, initialContainerHeight);
                    resizeAnimation.setDuration(animDuration - 50);
                    mToolsContainer.startAnimation(resizeAnimation);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    isLayoutAtBottom = false;
                }
            });
        }
        setAnimator.start();
    }

    public void onActionUp() {
        if (isLayoutAtBottom) {
            if (params.height < halvedYPosition) {
                animateLayout(true);
            } else {
                animateLayout(false);
            }
        } else {
            if (params.height < halvedYPosition) {
                animateLayout(true);
            } else {
                animateLayout(false);
            }
        }
    }
}
