package com.dingmouren.easymvp.ui.category;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.dingmouren.easymvp.R;
import com.dingmouren.easymvp.base.BaseFragment;

import butterknife.BindView;

/**
 * Created by dingmouren on 2016/12/13.
 */

public class CategoryFragment extends BaseFragment {

    public static String[] titles = new String[]{"Android","iOS","前端","拓展资源","瞎推荐","休息视频"};
    private ViewPagerAdapter mAdapter;

    @BindView(R.id.tab_layout) TabLayout mTabLayout;
    @BindView(R.id.viewpager)  ViewPager mViewPager;
    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_category;
    }

    @Override
    protected void setUpView() {
        for (int i = 0; i <titles.length ; i++) {
            mTabLayout.addTab(mTabLayout.newTab().setText(titles[i]));
        }

    }

    @Override
    protected void setUpData() {
        mAdapter = new ViewPagerAdapter(getChildFragmentManager(),titles);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(0);
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
