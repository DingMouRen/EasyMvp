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
import com.dingmouren.easymvp.util.MyGlideImageLoader;
import com.dingzi.greendao.VideoCoverBeanDao;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

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
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(VideoBean bean, int position) {
            mVideoPlayer.hidden();
            mVideoPlayer.setUp(bean.getVideo_uri(), false, "");

            if (null != bean) {
                MyGlideImageLoader.displayImage(bean.getProfile_image(), imgUser);
                userName.setText(bean.getName());
                videoTitle.setText(bean.getText().trim());
                likesCount.setText(bean.getLove());
                hatesCount.setText(bean.getHate());
                time.setText(bean.getCreate_time());
            }
            showCover(bean.getVideo_uri());//显示封面图
        }

        private void showCover(String video_uri) {
            List<VideoCoverBean> listCovers = MyApplication.getDaoSession().getVideoCoverBeanDao().queryBuilder().where(VideoCoverBeanDao.Properties.Url.eq(video_uri)).list();
            if (null != listCovers && 0 < listCovers.size()) {
                byte[] bytes = listCovers.get(0).getBytes();
                if (null != bytes && video_uri.equals(listCovers.get(0).getUrl())) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    ImageView img = new ImageView(itemView.getContext());
                    img.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    img.setImageBitmap(bitmap);
                    mVideoPlayer.setThumbImageView(img);
                }
            }

        }
    }
}
