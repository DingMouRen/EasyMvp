package com.dingmouren.easymvp.ui.videos;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dingmouren.easymvp.R;
import com.dingmouren.easymvp.bean.video.VideoBean;
import com.dingmouren.easymvp.util.MyGlideImageLoader;
import com.jiongbull.jlog.JLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by dingmouren on 2017/1/12.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {
    public static final String TAG = VideoAdapter.class.getName();
    private List<VideoBean> mList = new ArrayList<>();
    private Context mContext;
    public VideoAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_video_bean,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindData(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return null == mList ? 0 : mList.size();
    }

    //获取List集合
    public List<VideoBean> getmList() {
        return mList;
    }


    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.card_view)CardView mCardView;
        @BindView(R.id.videoplayer) MyVideoPlayer mVideoPlayer;
        @BindView(R.id.relative_item_video)RelativeLayout mRelative;
        @BindView(R.id.img_video_user)CircleImageView imgUser;
        @BindView(R.id.tv_video_user_name)TextView userName;
        @BindView(R.id.tv_video_title) TextView videoTitle;
        @BindView(R.id.tv_likes_count) TextView likesCount;
        @BindView(R.id.tv_hates_count) TextView hatesCount;
        @BindView(R.id.tv_video_time) TextView time;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void bindData(VideoBean bean){
            mVideoPlayer.hidden();
            mVideoPlayer.setUp(bean.getVideo_uri(),false,"");

            if (null != bean){
                MyGlideImageLoader.displayImage(bean.getProfile_image(),imgUser);
                userName.setText(bean.getName());
                videoTitle.setText(bean.getText().trim());
                likesCount.setText(bean.getLove());
                hatesCount.setText(bean.getHate());
                time.setText(bean.getCreate_time());

            }

            Observable.just(bean.getVideo_uri())
                    .flatMap(new Func1<String, Observable<ImageView>>() {
                        @Override
                        public Observable<ImageView> call(String s) {
                            return Observable.just(createBitmap(s));
                        }
                    }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<ImageView>() {
                        @Override
                        public void onCompleted() {
                            JLog.e(TAG,"completed");
                        }

                        @Override
                        public void onError(Throwable e) {
                            JLog.e(TAG,"onError:" + e.getMessage());
                        }

                        @Override
                        public void onNext(ImageView imageView) {
                            mVideoPlayer.setThumbImageView(imageView);
                        }
                    });
        }

        private ImageView createBitmap(String url){
            Bitmap bitmap = null;
            ImageView img = null;
            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            int kind = MediaStore.Video.Thumbnails.MINI_KIND;
            try {
                if (Build.VERSION.SDK_INT>=14){
                    if (url.startsWith("http://") || url.startsWith("https://") || url.startsWith("widevine://")){
                        retriever.setDataSource(url,new HashMap<String, String>());
                    }else {
                        retriever.setDataSource(url);
                    }
                }
                bitmap = retriever.getFrameAtTime(500,MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
                img = new ImageView(itemView.getContext());
                img.setImageBitmap(bitmap);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
            return img;
        }

    }
}
