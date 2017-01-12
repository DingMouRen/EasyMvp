package com.dingmouren.easymvp.ui.videos;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.dingmouren.easymvp.R;
import com.shuyu.gsyvideoplayer.GSYImageCover;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import moe.codeest.enviews.ENDownloadView;
import moe.codeest.enviews.ENPlayView;

/**
 * Created by dingmouren on 2017/1/11.
 */

public class MyVideoPlayer extends StandardGSYVideoPlayer {
    private RelativeLayout surface_container;
    private RelativeLayout thumb;
    public GSYImageCover cover;
    private LinearLayout layout_bottom;
    private TextView current;
    private SeekBar progress;
    private TextView total;
    private ImageView fullscreen;
    private ProgressBar bottom_progressbar;
    private ImageView back_tiny;
    private LinearLayout layout_top;
    private ImageView back;
    private TextView title;
    private ENDownloadView loading;
    private ENPlayView start;
    private ImageView small_close;
    private ImageView lock_screen;

    public MyVideoPlayer(Context context, Boolean fullFlag) {
        super(context, fullFlag);
    }

    public MyVideoPlayer(Context context) {
        super(context);
    }

    public MyVideoPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        surface_container = (RelativeLayout) findViewById(R.id.surface_container);
        thumb = (RelativeLayout) findViewById(R.id.thumb);
        cover = (GSYImageCover) findViewById(R.id.cover);
        layout_bottom = (LinearLayout) findViewById(R.id.layout_bottom);
        current = (TextView) findViewById(R.id.current);
        progress = (SeekBar) findViewById(R.id.progress);
        total = (TextView) findViewById(R.id.total);
        fullscreen = (ImageView) findViewById(R.id.fullscreen);
        bottom_progressbar = (ProgressBar) findViewById(R.id.bottom_progressbar);
        back_tiny = (ImageView) findViewById(R.id.back_tiny);
        layout_top = (LinearLayout) findViewById(R.id.layout_top);
        back = (ImageView) findViewById(R.id.back);
        title = (TextView) findViewById(R.id.title);
        loading = (ENDownloadView) findViewById(R.id.loading);
        start = (ENPlayView) findViewById(R.id.start);
        small_close = (ImageView) findViewById(R.id.small_close);
        lock_screen = (ImageView) findViewById(R.id.lock_screen);
    }

    //重写布局样式
    @Override
    public int getLayoutId() {
        return R.layout.video_mylayout;
    }

    //在视频列表页需要隐藏的部分
    public void hidden(){
        back.setVisibility(View.INVISIBLE);
        title.setVisibility(View.INVISIBLE);
        current.setVisibility(View.INVISIBLE);
        progress.setVisibility(View.INVISIBLE);
        total.setVisibility(View.INVISIBLE);
    }
}
