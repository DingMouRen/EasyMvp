package com.dingmouren.easymvp.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.dingmouren.easymvp.R;
import com.dingmouren.easymvp.base.BaseActivity;
import com.dingmouren.easymvp.base.BaseFragment;
import com.dingmouren.easymvp.ui.gallery.GalleryActivity;
import com.dingmouren.easymvp.ui.picture.WelfareFragment;
import com.dingmouren.easymvp.ui.video.VideoActivity;
import com.dingmouren.easymvp.util.FragmentHelpr;
import com.dingmouren.easymvp.util.SnackbarUtils;
import com.dingmouren.easymvp.util.ViewUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.majiajie.pagerbottomtabstrip.Controller;
import me.majiajie.pagerbottomtabstrip.PagerBottomTabLayout;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectListener;

public class HomeActivity extends BaseActivity {

    @BindView(R.id.drawer_main)
    DrawerLayout mDrawer;
    @BindView(R.id.coordinator)
    CoordinatorLayout mCoordinator;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    RecyclerView mRecycler;
    @BindView(R.id.nav_view_main)
    NavigationView mNavView;
    @BindView(R.id.fab_main)
    FloatingActionButton mFab;
    @BindView(R.id.tab_bottom)
    BottomNavigationView mTabBottom;

    private Controller mController;
    private ActionBarDrawerToggle mDrawerToggle;
    private WelfareFragment mWelfareFragment;
    private HomeFragment mHomeFragment;
    private FragmentManager mFragmentManager;
    private BaseFragment mCurrentFragment;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_home;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mFragmentManager = getSupportFragmentManager();
        mCurrentFragment = (BaseFragment) mFragmentManager.findFragmentById(R.id.frame_home_activity);
        if (mCurrentFragment == null){
            mCurrentFragment = ViewUtils.createFragment(HomeFragment.class);
            mFragmentManager.beginTransaction().add(R.id.frame_home_activity,mCurrentFragment).commit();
        }
        FragmentTransaction trans = mFragmentManager.beginTransaction();
        if (savedInstanceState != null){
            List<Fragment> fragments = mFragmentManager.getFragments();
            for (int i = 0; i <fragments.size(); i++) {
                trans.hide(fragments.get(i));
            }
        }
        trans.show(mCurrentFragment).commitAllowingStateLoss();//在Activity状态保存了之后执行commit(),避免异常的发生
    }

    @Override
    protected void setUpView() {
        //初始化toolbar
        initToolbar();
        //初始化底部导航栏
        setupTabBottom();
        //初始化首页视图
        initHomeFragment();
        mNavView.setNavigationItemSelectedListener(mNavgationViewItemSelectedListener);
    }

    @Override
    protected void setUpData() {

    }

    private void initHomeFragment() {

    }

    public void initToolbar() {
        mToolbar.setTitle(getResources().getString(R.string.main_home));
        mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawerToggle.syncState();
    }


    private void setupTabBottom() {
        mTabBottom.setOnNavigationItemSelectedListener(mTabItemListener);
    }
    BottomNavigationView.OnNavigationItemSelectedListener mTabItemListener =  new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected( MenuItem item) {
            Class<?> clazz = null;
            switch (item.getItemId()){
                case R.id.item_home:
                    mToolbar.setTitle(getResources().getString(R.string.main_home));
                    clazz = HomeFragment.class;
                    break;
                case R.id.item_category:
                    mToolbar.setTitle(getResources().getString(R.string.main_category));
                    break;
                case R.id.item_welfare:
                    mToolbar.setTitle(getResources().getString(R.string.main_welfare));
                    clazz = WelfareFragment.class;
                    break;
                case R.id.item_reading:
                    mToolbar.setTitle(getResources().getString(R.string.main_reading));
                    break;
                default:
                    break;
            }
            if (clazz != null){
                switchFragment(clazz);
            }
            return true;
        }
    };

    private void switchFragment(Class<?> clazz) {
        if (clazz == null) return;
        BaseFragment fragment = ViewUtils.createFragment(clazz);
        if (fragment.isAdded()){
            mFragmentManager.beginTransaction().hide(mCurrentFragment).show(fragment).commitAllowingStateLoss();
        }else {
            mFragmentManager.beginTransaction().hide(mCurrentFragment).add(R.id.frame_home_activity,fragment).commitAllowingStateLoss();
        }
        mCurrentFragment = fragment;
    }


    //侧滑栏选项的监听
    NavigationView.OnNavigationItemSelectedListener mNavgationViewItemSelectedListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.item1:
                    startActivity(new Intent(HomeActivity.this, GalleryActivity.class));
                    break;
                case R.id.item2:
                    startActivity(new Intent(HomeActivity.this, VideoActivity.class));
                    break;

            }
            mDrawer.closeDrawers();
            item.setChecked(false);
            return true;
        }
    };
}
