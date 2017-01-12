package com.dingmouren.easymvp.ui.gallery;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.dingmouren.easymvp.R;
import com.dingmouren.easymvp.bean.gank.GankResultWelfare;
import com.dingmouren.easymvp.helper.CardAdapterHelper;
import com.dingmouren.easymvp.ui.picture.PictureActivity;

import java.util.List;

/**
 * Created by dingmouren on 2016/12/3.
 */

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder> {

    private Context mContext;
    private List<GankResultWelfare> mList;
    private CardAdapterHelper mCardAdpaterHelper = new CardAdapterHelper();
    public GalleryAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public GalleryAdapter(Context mContext, List<GankResultWelfare> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    public  void setList(List<GankResultWelfare> list){
        this.mList = list;
    }

    @Override
    public GalleryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_gallery,parent,false);
        mCardAdpaterHelper.onCreateViewHolder(parent,view);
        return new GalleryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GalleryViewHolder holder, int position) {
        mCardAdpaterHelper.onBindViewHolder(holder.itemView,position,getItemCount());
        Glide.with(mContext).load(mList.get(position).getUrl()).centerCrop().placeholder(R.mipmap.place_holder).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.img);
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PictureActivity.newInstance(mContext,mList.get(position).getUrl(),mList.get(position).get_id());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList  == null ? 0 : mList.size() ;
    }

    static class GalleryViewHolder extends RecyclerView.ViewHolder{

       ImageView img;

        public GalleryViewHolder(View itemView) {
            super(itemView);
           img = (ImageView) itemView.findViewById(R.id.img_item_gallery);
        }
    }
}
