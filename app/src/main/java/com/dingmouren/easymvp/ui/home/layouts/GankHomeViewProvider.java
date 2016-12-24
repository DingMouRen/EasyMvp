package com.dingmouren.easymvp.ui.home.layouts;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dingmouren.easymvp.R;
import com.dingmouren.easymvp.bean.GankContent;
import com.dingmouren.easymvp.ui.webdetail.WebDetailActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewProvider;

/**
 * Created by dingmouren on 2016/12/24.
 */
public class GankHomeViewProvider
        extends ItemViewProvider<GankContent, GankHomeViewProvider.ViewHolder> {
    private static final String TAG = GankHomeViewProvider.class.getName();

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_gank_home, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(
            @NonNull ViewHolder holder, @NonNull GankContent gankHome) {
        holder.bindData(gankHome);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.card_itemHome)  CardView cardView;
        @BindView(R.id.relative_itemHome_first) RelativeLayout relativeLayout;
        @BindView(R.id.img_itemHome_topic) ImageView imgTopic;
        @BindView(R.id.tv_itemHome_topic) TextView tvTopic;
        @BindView(R.id.tv_itemHome_anthor) TextView tvAnthor;
        @BindView(R.id.tv_itemHome_title) TextView tvTitle;
        @BindView(R.id.viewpager) ViewPager viewpager;
        @BindView(R.id.frame)  FrameLayout frameLayout;
        @BindView(R.id.tv_page) TextView tv_page;
        private String anthor;
        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        //绑定数据
        public void bindData(GankContent gankHome){

            //根据类型显示不同类型的图片
            switch (gankHome.getType()){
                case "Android":
                    imgTopic.setImageResource(R.mipmap.android);
                    break;
                case "iOS":
                    imgTopic.setImageResource(R.mipmap.ios);
                    break;
                case "休息视频":
                    imgTopic.setImageResource(R.mipmap.video);
                    break;
                case "前端":
                    imgTopic.setImageResource(R.mipmap.web);
                    break;
                case "拓展资源":
                    imgTopic.setImageResource(R.mipmap.tuozhan);
                    break;
            }
            tvTopic.setText("来自话题:" + gankHome.getType());
            anthor = gankHome.getWho() ==  null ? "无名教主" : gankHome.getWho();
            tvAnthor.setText("  作者：" + anthor );
            tvTitle.setText(gankHome.getDesc());
            //显示轮播图片
            if (gankHome.getImages() == null ){
                frameLayout.setVisibility(View.GONE);
            } else if (gankHome.getImages() != null && 0 < gankHome.getImages().size()) {
                frameLayout.setVisibility(View.VISIBLE);
                viewpager.setAdapter(new ViewPageHomeAdapter(gankHome.getImages()));
                //显示页码，
                tv_page.setText( 1 + "/"+gankHome.getImages().size());
                viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        tv_page.setText(position+ 1+"/"+gankHome.getImages().size());
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });

            }

            //跳转详情页
            relativeLayout.setOnClickListener((view )-> WebDetailActivity.newInstance(relativeLayout.getContext(),gankHome.getUrl(),gankHome.getDesc()));
        }
    }
}