package com.dingmouren.easymvp.util;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

/**
 * Created by dingmouren on 2016/12/31.
 * 设置手机状态栏的颜色
 * 适用于4.4.2以上的系统
 * 思路：1.将手机状态栏设置成透明 2.在状态栏上添加一个与状态栏等高的View  通过修改这个View的背景颜色来修改状态栏的颜色
 */

public class StatusBarUtil {
    public static void setStatusBarColor(Activity activity,int color){
        Window window = activity.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        ViewGroup decorViewGroup = (ViewGroup) window.getDecorView();
        View statusBarView = new View(window.getContext());
        int statusBarHeight = getStatusBarHeight(activity);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,statusBarHeight);
          params.gravity = Gravity.TOP;
        statusBarView.setLayoutParams(params);
        statusBarView.setBackgroundColor(color);
        decorViewGroup.addView(statusBarView);
    }

    //获取状态栏的高度
    private static int getStatusBarHeight(Activity activity) {
        int height = 0;
        int resourceId = activity.getResources().getIdentifier("status_bar_height","dimen","android");
        if (resourceId > 0){
            height = activity.getResources().getDimensionPixelSize(resourceId);
        }
        return height;
    }
}
