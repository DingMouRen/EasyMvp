package com.dingmouren.easymvp.ui.category;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.dingmouren.easymvp.Constant;
import com.dingmouren.easymvp.R;
import com.dingmouren.easymvp.base.BaseFragment;
import com.dingmouren.easymvp.event.NightModeChangeEvent;
import com.dingmouren.easymvp.util.SPUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
        EventBus.getDefault().register(this);//注册事件总线
        for (int i = 0; i <titles.length ; i++) {
            mTabLayout.addTab(mTabLayout.newTab().setText(titles[i]));
        }
        initNightMode();//初始化夜间模式
    }

    @Override
    protected void setUpData() {
        mAdapter = new ViewPagerAdapter(getChildFragmentManager(),titles);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(0);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    //接收到夜间模式变化的通知时，刷新视图
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void changeNightMode(NightModeChangeEvent event){
        if ((Boolean) SPUtil.get(getContext(), Constant.NIGHT_MODE,true)){
            mTabLayout.setBackgroundColor(getResources().getColor(R.color.gray_dark));
            mTabLayout.setTabTextColors(getResources().getColor(R.color.gray),getResources().getColor(android.R.color.black));
            mTabLayout.setSelectedTabIndicatorColor(getResources().getColor(android.R.color.black));
        }else {
            mTabLayout.setBackgroundColor(getResources().getColor(android.R.color.white));
            mTabLayout.setTabTextColors(getResources().getColor(R.color.gray_dark),getResources().getColor(R.color.colorPrimary));
            mTabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorPrimary));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);//解绑事件总线
    }

    //根据所处的夜间模式，设置颜色
    private void initNightMode(){
        if ((Boolean) SPUtil.get(getContext(), Constant.NIGHT_MODE,true)){
            mTabLayout.setBackgroundColor(getResources().getColor(android.R.color.white));
            mTabLayout.setTabTextColors(getResources().getColor(R.color.gray_dark),getResources().getColor(R.color.colorPrimary));
            mTabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorPrimary));
        }else {
            mTabLayout.setBackgroundColor(getResources().getColor(R.color.gray_dark));
            mTabLayout.setTabTextColors(getResources().getColor(R.color.gray),getResources().getColor(android.R.color.black));
            mTabLayout.setSelectedTabIndicatorColor(getResources().getColor(android.R.color.black));
        }
    }
}
