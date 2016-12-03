package com.dingmouren.easymvp.behavior;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by dingmouren on 2016/12/2.
 */

public class HomeTabBottomBehavior extends CoordinatorLayout.Behavior<View> {

    public HomeTabBottomBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        //这个子控件依赖AppBarLayout
        return dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        //获取跟随布局的顶部位置
        float transitionY = Math.abs(dependency.getTop());
        child.setTranslationY(transitionY);
        return true;
    }
}
