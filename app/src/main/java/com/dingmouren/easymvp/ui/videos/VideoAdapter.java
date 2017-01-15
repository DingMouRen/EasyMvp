package com.dingmouren.easymvp.ui.videos;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dingmouren.easymvp.MyApplication;
import com.dingmouren.easymvp.R;
import com.dingmouren.easymvp.bean.video.VideoBean;
import com.dingmouren.easymvp.bean.video.VideoCoverBean;
import com.dingmouren.easymvp.util.DateUtils;
import com.dingmouren.easymvp.util.MyGlideImageLoader;
import com.dingzi.greendao.VideoCoverBeanDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import rx.Observable;
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
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_video_bean, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindData(mList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return null == mList ? 0 : mList.size();
    }

    //获取List集合
    public List<VideoBean> getmList() {
        return mList;
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.card_view)
        CardView mCardView;
        @BindView(R.id.videoplayer)
        MyVideoPlayer mVideoPlayer;
        @BindView(R.id.relative_item_video)
        RelativeLayout mRelative;
        @BindView(R.id.img_video_user)
        CircleImageView imgUser;
        @BindView(R.id.tv_video_user_name)
        TextView userName;
        @BindView(R.id.tv_video_title)
        TextView videoTitle;
        @BindView(R.id.tv_likes_count)
        TextView likesCount;
        @BindView(R.id.tv_hates_count)
        TextView hatesCount;
        @BindView(R.id.tv_video_time)
        TextView time;
        private String video_uri;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(VideoBean bean, int position) {
            mVideoPlayer.hidden();
            mVideoPlayer.setUp(bean.getVideo_uri(), false, "");
            video_uri = bean.getVideo_uri();
            if (null != bean) {
                MyGlideImageLoader.displayImage(bean.getProfile_image(), imgUser);
                userName.setText(bean.getName());
                videoTitle.setText(bean.getText().trim());
                likesCount.setText(bean.getLove());
                hatesCount.setText(bean.getHate());
                time.setText(DateUtils.friendlyTime(bean.getCreate_time()));
            }
            showCover();//显示封面图
        }
        private void showCover() {

            Observable.just(MyApplication.getDaoSession().getVideoCoverBeanDao().queryBuilder())
                     .flatMap(new Func1<QueryBuilder<VideoCoverBean>, Observable<ImageView>>() {
                         @Override
                         public Observable<ImageView> call(QueryBuilder<VideoCoverBean> videoCoverBeanQueryBuilder) {
                             return Observable.just(createImg(videoCoverBeanQueryBuilder));
                         }
                     }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(imageView -> {
                        if (null != imageView){
                            mVideoPlayer.getThumbImageViewLayout().removeAllViews();
                           mVideoPlayer.setThumbImageView(imageView);
                        }
                    });
        }

        private ImageView createImg(QueryBuilder<VideoCoverBean> queryBuilder) {
            ImageView img = new ImageView(itemView.getContext());
            List<VideoCoverBean> list = queryBuilder.where(VideoCoverBeanDao.Properties.Url.eq(video_uri)).list();
            if (null != list && 0 < list.size()){
                VideoCoverBean videoCoverBean= list.get(0);
                if (null != videoCoverBean && null != videoCoverBean.getBytes()){
                    Bitmap bitmap = BitmapFactory.decodeByteArray(videoCoverBean.getBytes(),0,videoCoverBean.getBytes().length);
                    img.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    img.setImageBitmap(bitmap);
                    return img;
                }
            }
            return null;
        }
    }
}
