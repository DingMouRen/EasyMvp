package com.dingmouren.easymvp.behavior;

import android.animation.Animator;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.PathInterpolator;

import okhttp3.Interceptor;


/**
 * Created by dingmouren on 2016/12/2.
 */

public class HomeFabBehavior extends CoordinatorLayout.Behavior<View> {

//    private static final Interpolator INTERCEPTOR = new FastOutLinearInInterpolator();
//    private static final Interpolator INTERCEPTOR = new FastOutSlowInInterpolator();
//    private static final Interpolator INTERCEPTOR = new LinearOutSlowInInterpolator();
//    private static final Interpolator INTERCEPTOR = new PathInterpolator(); 这个需要传入一个path路径
//    private static final Interpolator INTERCEPTOR = new BounceInterpolator();
    private static final Interpolator INTERCEPTOR = new OvershootInterpolator();
    private float viewY; //控件离coordinatorLayout底部的间距
    private boolean isAnimate;//动画是否在进行

    public HomeFabBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 判断滑动的方向，我需要的是垂直方向的滑动
     * @param coordinatorLayout
     * @param child
     * @param directTargetChild
     * @param target
     * @param nestedScrollAxes
     * @return
     */
    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        if (child.getVisibility() == View.VISIBLE && viewY == 0){
            //获取控件距离父控件（CoordinatorLayout）底部的距离
            viewY = coordinatorLayout.getHeight() - child.getY();
        }
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0; //判断是否是垂直滚动
    }

    /**
     * 根据滑动距离显示或者隐藏footerview
     * @param coordinatorLayout
     * @param child
     * @param target
     * @param dx
     * @param dy
     * @param consumed
     */
    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed) {
        //大于0是向上滚动，小于0是向下滚动
        if (dy >= 0 && !isAnimate && child.getVisibility() == View.VISIBLE){
            hide(child);
        }else if (dy < 0 && !isAnimate && child.getVisibility() == View.GONE){
            show(child);
        }
    }

    /**
     * 隐藏时的动画
     * @param view
     */
    private void hide(final View view){

        ViewPropertyAnimator animator = view.animate().translationY(viewY).alpha(0).setInterpolator(INTERCEPTOR).setDuration(400);

        animator.setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                isAnimate = true;
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                view.setVisibility(View.GONE);
                isAnimate = false;
            }

            @Override
            public void onAnimationCancel(Animator animator) {
                    show(view);
            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animator.start();
    }

    /**
     * 显示时的动画
     * @param view
     */
    private void show(final View view){

        ViewPropertyAnimator animator = view.animate().translationY(0).alpha(1).setInterpolator(INTERCEPTOR).setDuration(400);

        animator.setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                view.setVisibility(View.VISIBLE);
                isAnimate = true;
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                isAnimate = false;
            }

            @Override
            public void onAnimationCancel(Animator animator) {
                hide(view);
            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animator.start();
    }
}
