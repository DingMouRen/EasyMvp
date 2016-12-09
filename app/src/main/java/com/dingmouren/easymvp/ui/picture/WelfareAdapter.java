package com.dingmouren.easymvp.ui.picture;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.dingmouren.easymvp.R;
import com.dingmouren.easymvp.bean.GankResultWelfare;

import java.util.List;

/**
 * Created by dingmouren on 2016/12/1.
 */

public class WelfareAdapter extends RecyclerView.Adapter<WelfareAdapter.HomeViewHolder> {

    private Context mContext;
    private List<GankResultWelfare> mList;

    public WelfareAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public WelfareAdapter(Context mContext, List<GankResultWelfare> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    public void setList(List<GankResultWelfare> list){
        this.mList = list;
    }

    @Override
    public HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_welfare,parent,false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HomeViewHolder holder, int position) {
        Glide.with(mContext).load(mList.get(position).getUrl()).centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.mipmap.place_holder).into(holder.img_girl);//显示美女图片
        holder.img_girl.setOnClickListener((view -> PictureActivity.newInstance(mContext,mList.get(position).getUrl(),mList.get(position).get_id())));
    }

    @Override
    public int getItemCount() {
        return mList==  null ? 0 : mList.size();
    }

    static class HomeViewHolder extends RecyclerView.ViewHolder{
        ImageView img_girl;
        public HomeViewHolder(View itemView) {
            super(itemView);
            img_girl = (ImageView) itemView.findViewById(R.id.img_item_home);
        }
    }
}
