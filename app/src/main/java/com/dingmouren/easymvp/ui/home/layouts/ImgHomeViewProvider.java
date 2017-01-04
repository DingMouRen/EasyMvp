package com.dingmouren.easymvp.ui.home.layouts;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.dingmouren.easymvp.R;
import com.dingmouren.easymvp.util.MyGlideImageLoader;

import me.drakeet.multitype.ItemViewProvider;

/**
 * Created by dingmouren on 2016/12/24.
 */
public class ImgHomeViewProvider
        extends ItemViewProvider<ImgHome, ImgHomeViewProvider.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_img_home, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(
            @NonNull ViewHolder holder, @NonNull ImgHome imgHome) {
        Glide.with(holder.img.getContext()).load(imgHome.getImgUrl()).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(holder.img);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        ViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img);
        }
    }
}