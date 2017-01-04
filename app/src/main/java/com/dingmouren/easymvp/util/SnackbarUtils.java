package com.dingmouren.easymvp.util;

import android.annotation.TargetApi;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import com.dingmouren.easymvp.R;

import java.lang.ref.WeakReference;


/**
 * Created by dingmouren on 2016/11/9.
 */

public class SnackbarUtils {
    //设置Snackbar背景颜色
    private static final int color_info = 0XFF2094F3;
    private static final int color_confirm = 0XFF4CB04E;
    private static final int color_warning = 0XFFFEC005;
    private static final int color_danger = 0XFFF44336;

    private static final int color_simple = 0XFF979696;
    //工具类当前持有的Snackbar实例
    private static WeakReference<Snackbar> snackbarWeakReference;
    private SnackbarUtils(){
        throw new RuntimeException("禁止使用无参创建实例对象");
    }

    public SnackbarUtils(WeakReference<Snackbar> snackbarWeakReference){
        this.snackbarWeakReference = snackbarWeakReference;
    }

    public Snackbar getSnackbar(){
        if(this.snackbarWeakReference != null && this.snackbarWeakReference.get()!=null){
            return this.snackbarWeakReference.get();
        }else {
            return null;
        }
    }

    public static void showSimpleSnackbar(View view,String message){
        Short(view,message).backColor(color_simple)
                .alpha(0.6f)
                .margins(20)
                .radius(20)
                .messageCenter()
                .show();
    }

    /**
     * 显示时间short
     * @param view
     * @param message
     * @return
     */
    public static SnackbarUtils Short(View view,String message){
        return new SnackbarUtils(new WeakReference<Snackbar>(Snackbar.make(view,message, Snackbar.LENGTH_SHORT))).backColor(0XFF323232);
    }

    /**
     * 显示时间long
     * @param view
     * @param message
     * @return
     */
    public static SnackbarUtils Long(View view,String message){
        return new SnackbarUtils(new WeakReference<Snackbar>(Snackbar.make(view,message, Snackbar.LENGTH_LONG))).backColor(0XFF323232);
    }

    /**
     * 显示时间 ：无限时长，indefinite
     * @param view
     * @param message
     * @return
     */
    public static SnackbarUtils Indefinite(View view,String message){
        return new SnackbarUtils(new WeakReference<Snackbar>(Snackbar.make(view,message, Snackbar.LENGTH_INDEFINITE))).backColor(0XFF323232);
    }

    /**
     * 自定义显示时间
     * @param view
     * @param message
     * @param duration
     * @return
     */
    public static SnackbarUtils Custom(View view,String message,int duration){
        return new SnackbarUtils(new WeakReference<Snackbar>(Snackbar.make(view,message, Snackbar.LENGTH_SHORT).setDuration(duration))).backColor(0XFF323232);
    }

    /**
     * 设置Snackbar的背景颜色是 color_info
     * @return
     */
    public SnackbarUtils info(){
        Snackbar snackbar = getSnackbar();
        if (snackbar != null){
            snackbar.getView().setBackgroundColor(color_info);
        }
        return new SnackbarUtils(snackbarWeakReference);
    }

    /**
     * 设置Snackbar的背景颜色是color_confirm
     * @return
     */
    public SnackbarUtils confirm(){
        Snackbar snackbar = getSnackbar();
        if (snackbar != null){
            snackbar.getView().setBackgroundColor(color_confirm);
        }
        return new SnackbarUtils(snackbarWeakReference);
    }

    /**
     * 设置Snackbar的背景颜色是color_warning
     * @return
     */
    public SnackbarUtils warning(){
        Snackbar snackbar = getSnackbar();
        if (snackbar != null){
            snackbar.getView().setBackgroundColor(color_warning);
        }
        return new SnackbarUtils(snackbarWeakReference);
    }

    /**
     * 设置Snackbar的背景颜色是color_danger
     * @return
     */
    public SnackbarUtils danger(){
        Snackbar snackbar = getSnackbar();
        if (snackbar != null){
            snackbar.getView().setBackgroundColor(color_danger);
        }
        return new SnackbarUtils(snackbarWeakReference);
    }

    /**
     * 设置Snackbar背景颜色
     * @param backgroundColor
     * @return
     */
    public SnackbarUtils backColor(@ColorInt int backgroundColor){
        Snackbar snackbar = getSnackbar();
        if (snackbar != null){
            snackbar.getView().setBackgroundColor(backgroundColor);
        }
        return new SnackbarUtils(snackbarWeakReference);
    }

    /**
     * 设置Snackbar中TextView的文字颜色
     * @param messageColor
     * @return
     */
    public SnackbarUtils messageColor(@ColorInt int messageColor){
        Snackbar snackbar = getSnackbar();
        if (snackbar != null){
            ((TextView)snackbar.getView().findViewById(R.id.snackbar_text)).setTextColor(messageColor);
        }
        return new SnackbarUtils(snackbarWeakReference);
    }

    /**
     * 设置Snackbar中Button的文字颜色
     * @param actionTextColor
     * @return
     */
    public SnackbarUtils actionColor(@ColorInt int actionTextColor){
        Snackbar snackbar = getSnackbar();
        if (snackbar != null){
            ((Button)snackbar.getView().findViewById(R.id.snackbar_action)).setTextColor(actionTextColor);
        }
        return new SnackbarUtils(snackbarWeakReference);
    }

    /**
     * 设置Snackbar中背景、TextView文字、Button文字的颜色
     * @param backgroundColor
     * @param messageColor
     * @param actionColor
     * @return
     */
    public SnackbarUtils colors(@ColorInt int backgroundColor, @ColorInt int messageColor, @ColorInt int actionColor){
        Snackbar snackbar = getSnackbar();
        if (snackbar != null){
            snackbar.getView().setBackgroundColor(backgroundColor);
            ((TextView)snackbar.getView().findViewById(R.id.snackbar_text)).setTextColor(messageColor);
            ((Button)snackbar.getView().findViewById(R.id.snackbar_action)).setTextColor(actionColor);
        }
        return new SnackbarUtils(snackbarWeakReference);
    }

    /**
     * 设置Snackbar的透明度
     * @param alpha
     * @return
     */
    public SnackbarUtils alpha(float alpha){
        Snackbar snackbar = getSnackbar();
        if (snackbar != null){
            alpha = alpha > 1.0f ? 1.0f : (alpha < 0.0f ? 0.0f : alpha);
            snackbar.getView().setAlpha(alpha);
        }
        return new SnackbarUtils(snackbarWeakReference);
    }

    /**
     * 设置按钮文字内容以及点击监听(会调用下面的setAction)
     * @param resId
     * @param listener
     * @return
     */
    public SnackbarUtils setAction(@StringRes int resId, View.OnClickListener listener){
        Snackbar snackbar = getSnackbar();
        if (snackbar != null){
            return setAction(snackbar.getView().getResources().getText(resId),listener);
        }else {
            return new SnackbarUtils(snackbarWeakReference);
        }
    }

    /**
     * 设置按钮文字内容以及点击监听
     * @param text
     * @param listener
     * @return
     */
    public SnackbarUtils setAction(CharSequence text, View.OnClickListener listener){
        Snackbar snackbar = getSnackbar();
        if (snackbar != null){
            snackbar.setAction(text,listener);
        }
        return new SnackbarUtils(snackbarWeakReference);
    }

    /**
     * 设置Snackbar的展示完成以及隐藏完成的监听
     * @param setCallback
     * @return
     */
    public SnackbarUtils setCallback(Snackbar.Callback setCallback){
        Snackbar snackbar = getSnackbar();
        if (snackbar != null){
            snackbar.setCallback(setCallback);
        }
        return new SnackbarUtils(snackbarWeakReference);
    }

    /**
     * 设置TextView中文字的对齐方式，居中
     * @return
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public SnackbarUtils messageCenter(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
            Snackbar snackbar = getSnackbar();
            if (snackbar != null){
                TextView message = (TextView)snackbar.getView().findViewById(R.id.snackbar_text);
                //View.setTextAlignment需要SDK大于或者等于17
                message.setTextAlignment(View.TEXT_ALIGNMENT_GRAVITY);//设置文本的显示方式
                message.setGravity(Gravity.CENTER);
            }
        }
        return new SnackbarUtils(snackbarWeakReference);
    }

    /**
     * 设置TextView中文字的对齐方式，居右
     * @return
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public SnackbarUtils messageRight(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
            Snackbar snackbar = getSnackbar();
            if (snackbar != null){
                TextView message = (TextView)snackbar.getView().findViewById(R.id.snackbar_text);
                message.setTextAlignment(View.TEXT_ALIGNMENT_GRAVITY);
                message.setGravity(Gravity.CENTER_VERTICAL|Gravity.RIGHT);
            }
        }
        return new SnackbarUtils(snackbarWeakReference);
    }

    /**
     * 设置TextView左右两侧的图片，这个方法会调用下面的方法
     * @param leftDrawable
     * @param rightDrawable
     * @return
     */
    public SnackbarUtils leftAndRightDrawable(@Nullable @DrawableRes Integer leftDrawable, @Nullable @DrawableRes Integer rightDrawable){
        Snackbar snackbar = getSnackbar();
        if (snackbar != null){
            Drawable drawableLeft = null;
            Drawable drawableRight = null;
            if (leftDrawable != null){
                try {
                    drawableLeft = snackbar.getView().getResources().getDrawable(leftDrawable.intValue());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            if (rightDrawable != null){
                try{
                    drawableRight = snackbar.getView().getResources().getDrawable(rightDrawable.intValue());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            return leftAndRightDrawable(drawableLeft,drawableRight);
        }else {
            return new SnackbarUtils(snackbarWeakReference);
        }
    }

    /**
     * 设置TextView左右两侧的图片
     * @param leftDrawable
     * @param rightDrawable
     * @return
     */
    public SnackbarUtils leftAndRightDrawable(@Nullable Drawable leftDrawable, @Nullable Drawable rightDrawable){
        Snackbar snackbar = getSnackbar();
        if (snackbar != null){
            TextView message = (TextView)snackbar.getView().findViewById(R.id.snackbar_text);
            LinearLayout.LayoutParams paramsMessage = (LinearLayout.LayoutParams)message.getLayoutParams();
            paramsMessage = new LinearLayout.LayoutParams(paramsMessage.width,paramsMessage.height,0.0f);
            message.setLayoutParams(paramsMessage);
            message.setCompoundDrawablePadding(message.getPaddingLeft());
            int textSize = (int) message.getTextSize();
            Log.e("log","设置TextView两边图片中 textSize : " + textSize);
            if (leftDrawable != null){
                leftDrawable.setBounds(0,0,textSize,textSize);
            }
            if (rightDrawable != null){
                rightDrawable.setBounds(0,0,textSize,textSize);
            }
            message.setCompoundDrawables(leftDrawable,null,rightDrawable,null);
            LinearLayout.LayoutParams paramsSpace = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT,1.0f);
            ((Snackbar.SnackbarLayout)snackbar.getView()).addView(new Space(snackbar.getView().getContext()),1,paramsSpace);
        }
        return new SnackbarUtils(snackbarWeakReference);
    }

    /**
     * 向Snackbar布局中添加View  (谷歌不建议，复杂的布局应该使用DialogFragment进行展示)
     * @param layoutId 要添加的布局的 id
     * @param index
     * @return
     */
    public SnackbarUtils addView(int layoutId,int index){
        Snackbar snackbar = getSnackbar();
        if (snackbar != null){
            //加载布局文件 新建view
            View addView = LayoutInflater.from(snackbar.getView().getContext()).inflate(layoutId,null);
            return addView(addView,index);
        }else {
            return new SnackbarUtils(snackbarWeakReference);
        }
    }

    /**
     * 向Snackbar布局中添加view
     * @param addView
     * @param index
     * @return
     */
    public SnackbarUtils addView(View addView,int index){
        Snackbar snackbar = getSnackbar();
        if (snackbar != null){
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            //设置新建view在Snackbar中垂直居中
            params.gravity = Gravity.CENTER_VERTICAL;
            addView.setLayoutParams(params);
            ((Snackbar.SnackbarLayout)snackbar.getView()).addView(addView,index);
        }
        return new SnackbarUtils(snackbarWeakReference);
    }

    /**
     * 设置Snackbar布局的圆角半径值
     * @param radius    圆角半径
     * @return
     */
    public SnackbarUtils radius(float radius){
        Snackbar snackbar = getSnackbar();
        if (snackbar != null){
            //获取将要设置给Snackbar的背景
            GradientDrawable background = getRadiusDrawable(snackbar.getView().getBackground());
            if (background != null){
                radius = radius < 0 ? 12 : radius;
                background.setCornerRadius(radius);
                snackbar.getView().setBackgroundDrawable(background);
            }
        }
        return new SnackbarUtils(snackbarWeakReference);
    }

    /**
     * 设置Snackbar布局的圆角半径值、边框颜色、边框宽度
     * @param radius
     * @param strokeWidth
     * @param strokeColor
     * @return
     */
    public SnackbarUtils radius(int radius,int strokeWidth,@ColorInt int strokeColor){
        Snackbar snackbar = getSnackbar();
        if (snackbar != null){
            //将要设置给Snackbar的背景
            GradientDrawable background = getRadiusDrawable(snackbar.getView().getBackground());
            if (background != null){
                radius = radius < 0 ? 12 : radius;
                strokeWidth = strokeWidth <= 0 ? 1 : (strokeWidth >= snackbar.getView().findViewById(R.id.snackbar_text).getPaddingTop() ? 2 : strokeWidth);
                background.setCornerRadius(radius);
                background.setStroke(strokeWidth,strokeColor);
                snackbar.getView().setBackgroundDrawable(background);
            }
        }
        return new SnackbarUtils(snackbarWeakReference);
    }

    /**
     * 设置Snackbar显示的位置
     * @param gravity
     * @return
     */
    public SnackbarUtils gravityFrameLayout(int gravity){
        Snackbar snackbar = getSnackbar();
        if (snackbar != null){
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(snackbarWeakReference.get().getView().getLayoutParams().width,
                    snackbarWeakReference.get().getView().getLayoutParams().height);
            params.gravity = gravity;
            snackbar.getView().setLayoutParams(params);
        }
        return new SnackbarUtils(snackbarWeakReference);
    }

    /**
     * 设置Snackbar显示位置，当Snackbar和CoordinatorLayout组合使用的时候
     * @param gravity
     * @return
     */
    public SnackbarUtils gravityCoordinatorLayout(int gravity){
        Snackbar snackbar = getSnackbar();
        if (snackbar != null){
            CoordinatorLayout.LayoutParams params = new CoordinatorLayout.LayoutParams(snackbarWeakReference.get().getView().getLayoutParams().width,
                    snackbarWeakReference.get().getView().getLayoutParams().height);
            params.gravity = gravity;
            snackbar.getView().setLayoutParams(params);
        }
        return new SnackbarUtils(snackbarWeakReference);
    }

    /**
     * 设置Snackbar布局的外边距
     *  调用madgins后，再调用gravityFrameLayout,margins无效，所以调用顺序gravityFrameLayout() -> margins() -> show()
     * @param margin
     * @return
     */
    public  SnackbarUtils margins(int margin){
        Snackbar snackbar = getSnackbar();
        if (snackbar != null){
            return margins(margin,margin,margin,margin);
        }else{
            return new SnackbarUtils(snackbarWeakReference);
        }
    }

    /**
     * 设置Snackbar布局的外边距
     * @param left
     * @param top
     * @param right
     * @param bottom
     * @return
     */
    public SnackbarUtils margins(int left, int top, int right,int bottom){
        Snackbar snackbar = getSnackbar();
        if (snackbar != null){
            ViewGroup.LayoutParams params = snackbar.getView().getLayoutParams();
            ((ViewGroup.MarginLayoutParams)params).setMargins(left,top,right,bottom);
            snackbar.getView().setLayoutParams(params);
        }
        return new SnackbarUtils(snackbarWeakReference);

    }

    /**
     * 显示Snackbar
     */
    public void show(){
        if (getSnackbar() != null){
            snackbarWeakReference.get().show();
        }
    }

    /**
     * 通过Snackbar现在的背景，获取其设置圆角值时候所需的GradientDrawable实例
     * @param backgroundOri
     * @return
     */
    private GradientDrawable getRadiusDrawable(Drawable backgroundOri){
        GradientDrawable background = null;
        if (backgroundOri instanceof GradientDrawable){
            background = (GradientDrawable) backgroundOri;
        }else if (backgroundOri instanceof ColorDrawable){
            int backgroundColor = ((ColorDrawable) backgroundOri).getColor();
            background = new GradientDrawable();
            background.setColor(backgroundColor);
        }
        return background;
    }
}
