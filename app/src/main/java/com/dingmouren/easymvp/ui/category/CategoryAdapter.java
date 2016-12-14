package com.dingmouren.easymvp.ui.category;

import android.content.Context;
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
import com.dingmouren.easymvp.bean.GankContent;
import com.dingmouren.easymvp.ui.webdetail.WebDetailActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dingmouren on 2016/12/14.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder>{

    private Context mContext;
    private List<GankContent> mList = new ArrayList<>();

    public CategoryAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public CategoryAdapter(Context mContext, List<GankContent> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    public void setList(List<GankContent> list){
        this.mList = list;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_gank_data_image,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tv_title.setText(mList.get(position).getDesc());
        holder.tv_anthor.setText(mList.get(position).getWho());
        holder.tv_time.setText(mList.get(position).getPublishedAt());
        holder.cardView.setOnClickListener((view) -> WebDetailActivity.newInstance(holder.cardView.getContext(),mList.get(position).getUrl(),mList.get(position).getDesc()));
        if (null != mList.get(position).getImages() && 0 < mList.get(position).getImages().size()) {
            Glide.with(holder.image.getContext()).load(mList.get(position).getImages().get(0)).placeholder(R.mipmap.loading).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.image);
        }
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_title)  TextView tv_title;
        @BindView(R.id.tv_anthor) TextView tv_anthor;
        @BindView(R.id.tv_time) TextView tv_time;
        @BindView(R.id.cardView)  CardView cardView;
        @BindView(R.id.image)  ImageView image;
        @BindView(R.id.rl_bottom)   RelativeLayout rl_bottom;
        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
