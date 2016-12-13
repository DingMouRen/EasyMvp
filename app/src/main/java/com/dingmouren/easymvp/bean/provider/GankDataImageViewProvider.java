package com.dingmouren.easymvp.bean.provider;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.dingmouren.easymvp.R;
import com.dingmouren.easymvp.bean.multitype.GankDataImage;
import com.dingmouren.easymvp.ui.picture.PictureActivity;
import com.dingmouren.easymvp.ui.webdetail.WebDetailActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewProvider;

/**
 * Created by dingmouren on 2016/12/13.
 */
public class GankDataImageViewProvider
        extends ItemViewProvider<GankDataImage, GankDataImageViewProvider.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_gank_data_image, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(
            @NonNull ViewHolder holder, @NonNull GankDataImage gankDataImage) {
        holder.tv_title.setText(gankDataImage.getDesc());
        holder.tv_anthor.setText(gankDataImage.getWho().toString());
        holder.tv_time.setText(gankDataImage.getPublishedAt());
        holder.rl_bottom.setOnClickListener((view) -> WebDetailActivity.newInstance(holder.rl_bottom.getContext(),gankDataImage.getUrl(),gankDataImage.getDesc()));
        holder.viewPager.setAdapter(new VPAdapter(holder.viewPager.getContext(),gankDataImage.getImages()));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)  TextView tv_title;
        @BindView(R.id.tv_anthor) TextView tv_anthor;
        @BindView(R.id.tv_time) TextView tv_time;
        @BindView(R.id.cardView)  CardView cardView;
        @BindView(R.id.viewpager)  ViewPager viewPager;
        @BindView(R.id.rl_bottom)  RelativeLayout rl_bottom;
        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    /**
     * ViewPager
     */
    class VPAdapter extends PagerAdapter{
        List<String> images;
        Context mContext;
        public VPAdapter( Context context,List<String> images ) {
            this.images = images;
            this.mContext = context;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            final ImageView imageView = new ImageView(mContext);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            Glide.with(mContext).load(images.get(position)).asGif().diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
            imageView.setOnClickListener((view) -> PictureActivity.newInstance(mContext,images.get(position),images.get(position).hashCode()+""));
            return imageView;
        }

        @Override
        public int getCount() {
            return images == null ? 0 : images.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view ==  object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}