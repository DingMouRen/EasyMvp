package com.dingmouren.easymvp.ui.home.layouts;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dingmouren.easymvp.R;

import java.util.List;

/**
 * Created by dingmouren on 2016/12/24.
 */

public class ViewPageHomeAdapter extends PagerAdapter {

    private List<String>  mListImgUrls;

    public ViewPageHomeAdapter(List<String> mListImgUrls) {
        this.mListImgUrls = mListImgUrls;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imgView = new ImageView(container.getContext());
        Glide.with(container.getContext()).load(mListImgUrls.get(position)).placeholder(R.mipmap.loading).into(imgView);
        imgView.setOnClickListener((view)-> Glide.with(container.getContext()).load(mListImgUrls.get(position)).placeholder(R.mipmap.loading).into(imgView));//点击图片重新加载图片
        container.addView(imgView);
        return imgView;
    }

    @Override
    public int getCount() {
        return mListImgUrls.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
