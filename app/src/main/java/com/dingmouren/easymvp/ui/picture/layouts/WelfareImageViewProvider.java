package com.dingmouren.easymvp.ui.picture.layouts;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dingmouren.easymvp.R;
import com.dingmouren.easymvp.bean.gank.GankResultWelfare;
import com.dingmouren.easymvp.ui.picture.PictureActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewProvider;

/**
 * Created by dingmouren on 2016/12/24.
 */
public class WelfareImageViewProvider
        extends ItemViewProvider<GankResultWelfare, WelfareImageViewProvider.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_welfare_image, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(
            @NonNull ViewHolder holder, @NonNull GankResultWelfare welfareImage) {
        Glide.with(holder.img.getContext()).load(welfareImage.getUrl()).centerCrop().placeholder(R.mipmap.loading).into(holder.img);
        holder.img.setOnClickListener((view -> PictureActivity.newInstance(holder.img.getContext(),welfareImage.getUrl(),welfareImage.get_id())));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img) ImageView img;
        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}