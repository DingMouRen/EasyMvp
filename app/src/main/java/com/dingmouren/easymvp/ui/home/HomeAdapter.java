package com.dingmouren.easymvp.ui.home;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
 * Created by dingmouren on 2016/12/9.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {

    private Context mContext;
    private List<GankContent> mList = new ArrayList<>();
    private String mImgUrl;

    public HomeAdapter(Context mContext) {
        this.mContext = mContext;
    }



    protected void setData(List<GankContent> list, String girlUrl){
        this.mList = list;
        this.mImgUrl = girlUrl;
    }

    @Override
    public HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_home,parent,false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HomeViewHolder holder, int position) {
        if ( position == 0){
            holder.imgGirl.setVisibility(View.VISIBLE);
            holder.cardView.setVisibility(View.GONE);
            Glide.with(mContext).load(mImgUrl).centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.mipmap.place_holder).into(holder.imgGirl);
        }else {
            holder.imgGirl.setVisibility(View.GONE);
            holder.bindItemData(mList.get(position));
        }

    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

     class HomeViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.card_itemHome) CardView cardView;
        @BindView(R.id.img_itemHome_girl)  ImageView imgGirl;
        @BindView(R.id.img_itemHome_content) ImageView imgContent;
        @BindView(R.id.img_itemHome_first) ImageView imgFirst;
        @BindView(R.id.tv_itemHome_first)  TextView tv_first;
        @BindView(R.id.tv_itemHome_anthor) TextView tv_Author;
        @BindView(R.id.tv_itemHome_title) TextView tv_title;
         private String mAuthor;
        public HomeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        /**
         * 为item绑定数据
         * @param gank
         */
        public void bindItemData(GankContent gank){

            switch (gank.getType()){
                case "Android":
                    imgFirst.setImageResource(R.mipmap.android);
                    break;
                case "iOS":
                    imgFirst.setImageResource(R.mipmap.ios);
                    break;
                case "休息视频":
                    imgFirst.setImageResource(R.mipmap.video);
                    break;
                case "前端":
                    imgFirst.setImageResource(R.mipmap.web);
                    break;
                case "拓展资源":
                    imgFirst.setImageResource(R.mipmap.tuozhan);
                    break;
            }

            tv_first.setText("来自话题:" + gank.getType());
            mAuthor =  gank.getWho() ==  null ? "无名教主" : gank.getWho().toString();
            tv_Author.setText("  by " +mAuthor);
            tv_title.setText(gank.getDesc());
            String imgUrl;
           /* if (gank.getImages() != null  ) { //加载图片Bug  不能显示以及错位
                 imgUrl = gank.getImages().get(0);
                Glide.with(mContext).load(imgUrl).diskCacheStrategy(DiskCacheStrategy.ALL).into(imgContent);
            }*/
            cardView.setOnClickListener((view) -> WebDetailActivity.newInstance(mContext,gank.getUrl(),gank.getDesc()));
        }
    }
}
