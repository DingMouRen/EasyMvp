package com.dingmouren.easymvp.ui.video;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.dingmouren.easymvp.R;
import com.dingmouren.easymvp.bean.VideoBean;
import com.shuyu.gsyvideoplayer.utils.ListVideoUtil;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dingmouren on 2016/12/7.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder>{

    public  final String URL = "http://baobab.wdjcdn.com/14564977406580.mp4";
    private Context mContext;
    private List<VideoBean> mList = new ArrayList<>();
    private ListVideoUtil mListVideoUtil;
    private ImageView mImg;
    public final static String TAG = "VideoAdapter";

    public VideoAdapter(Context mContext, ListVideoUtil listVideoUtil) {
        this.mContext = mContext;
        this.mListVideoUtil = listVideoUtil;
        //用于测试，创建一个集合
        for (int i = 0; i < 66; i++) {
            mList.add(new VideoBean());
        }
    }

    public VideoAdapter(Context mContext, List<VideoBean> mList, ListVideoUtil listVideoUtil) {
        this.mContext = mContext;
        this.mList = mList;
        this.mListVideoUtil = listVideoUtil;
    }

    public void setList(List<VideoBean> list){
        this.mList = list;
    }
    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_video,parent,false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position) {
        //设置封面
        mImg = new ImageView(mContext);
        mImg.setScaleType(ImageView.ScaleType.CENTER_CROP);
        mImg.setImageResource(R.mipmap.video_cover);

        mListVideoUtil.addVideoPlayer(position,mImg,TAG,holder.videoContainer,holder.img_item_video);

        holder.img_item_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notifyDataSetChanged();
                mListVideoUtil.setLoop(true);
                mListVideoUtil.setPlayPositionAndTag(position,TAG);
                mListVideoUtil.startPlay(URL);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    static class VideoViewHolder extends RecyclerView.ViewHolder{
        TextView tv_item_video;
        ImageView img_item_video;
        FrameLayout videoContainer;
        public VideoViewHolder(View itemView) {
            super(itemView);
            tv_item_video = (TextView) itemView.findViewById(R.id.tv_item_video);
            img_item_video = (ImageView) itemView.findViewById(R.id.img_item_video);
            videoContainer = (FrameLayout) itemView.findViewById(R.id.frame_item_video);
        }
    }
}
