package com.dingmouren.easymvp.view;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.widget.VideoView;

/**
 * 支持充满屏幕的VideoView
 * Created by dingmouren on 2016/12/3.
 */

public class CustomVideoView extends VideoView {
    public CustomVideoView(Context context) {
        super(context);
    }

    public CustomVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(View.MeasureSpec.getSize(widthMeasureSpec),View.MeasureSpec.getSize(heightMeasureSpec));
    }

    /**
     * 播放视频
     * @param uri 播放地址
     */
    public void playVideo(Uri uri){
        if (uri == null){
            throw new IllegalArgumentException("播放地址不能为空");
        }
        //设置播放路径
        setVideoURI(uri);

        //开始播放
        start();

        setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                //设置循环播放
                mediaPlayer.setLooping(true);
            }
        });

        setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                return true;
            }
        });
    }
}
