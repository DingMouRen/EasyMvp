package com.dingmouren.easymvp.ui.category.layouts;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.dingmouren.easymvp.R;
import com.dingmouren.easymvp.bean.GankContent;
import com.dingmouren.easymvp.ui.home.layouts.ViewPageHomeAdapter;
import com.dingmouren.easymvp.ui.webdetail.WebDetailActivity;
import com.dingmouren.easymvp.util.DateUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewProvider;

/**
 * Created by dingmouren on 2016/12/24.
 */
public class CategoryItemViewProvider
        extends ItemViewProvider<GankContent, CategoryItemViewProvider.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_category_item, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(
            @NonNull ViewHolder holder, @NonNull GankContent gank) {
        holder.bindData(gank);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cardView)  CardView carView;
        @BindView(R.id.tv_title)  TextView tvTitle;
        @BindView(R.id.tv_anthor) TextView tvAnthor;
        @BindView(R.id.tv_time) TextView tvTime;
        @BindView(R.id.frame)  FrameLayout frameLayout;
        @BindView(R.id.viewpager) ViewPager viewPager;
        @BindView(R.id.tv_page) TextView tvPage;
        private String anthor;
        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }

        public void bindData(GankContent gank){
            tvTitle.setText(gank.getDesc());
            anthor = gank.getWho() ==  null ? "无名教主" : gank.getWho();
            tvAnthor.setText("  作者：" + anthor );
            String date = gank.getPublishedAt().replace('T', ' ').replace('Z', ' ');
            tvTime.setText(DateUtils.friendlyTime(date));
            //显示图片
            //显示轮播图片
            if (gank.getImages() == null ){
                frameLayout.setVisibility(View.GONE);
            } else if (gank.getImages() != null && 0 < gank.getImages().size()) {
                frameLayout.setVisibility(View.VISIBLE);
                viewPager.setAdapter(new ViewPageHomeAdapter(gank.getImages()));
                //显示页码，
                tvPage.setText( 1 + "/"+gank.getImages().size());
                viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        tvPage.setText(position+ 1+"/"+gank.getImages().size());
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });

            }

            //跳转详情页
            carView.setOnClickListener((view )-> WebDetailActivity.newInstance(carView.getContext(),gank.getUrl(),gank.getDesc()));
        }
    }
}