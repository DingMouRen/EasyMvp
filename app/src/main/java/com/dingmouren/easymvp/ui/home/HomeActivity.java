package com.dingmouren.easymvp.ui.home;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.MenuItem;

import com.dingmouren.easymvp.R;
import com.dingmouren.easymvp.base.BaseActivity;
import com.dingmouren.easymvp.ui.gallery.GalleryActivity;
import com.dingmouren.easymvp.util.SnackbarUtils;

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
    PagerBottomTabLayout mTabBottom;

    private Controller mController;
    private ActionBarDrawerToggle mDrawerToggle;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private HomeFragment mHomeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        //初始化toolbar
        initToolbar();
        //初始化底部导航栏
        setupTabBottom();
        //初始化首页视图
        initHomeFragment();
        mNavView.setNavigationItemSelectedListener(mNavgationViewItemSelectedListener);
    }

    private void initHomeFragment() {
        mFragmentManager = getFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mHomeFragment = new HomeFragment();
        mFragmentTransaction.replace(R.id.frame_home_activity,mHomeFragment).commit();
    }

    public void initToolbar() {
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawerToggle.syncState();
    }


    private void setupTabBottom() {
        mController = mTabBottom.builder()
                .addTabItem(R.mipmap.home, "首页")
                .addTabItem(R.mipmap.music_icon, "Music")
                .addTabItem(R.mipmap.weather, "天气")
                .addTabItem(R.mipmap.setting, "设置")
                .build();
        mController.addTabItemClickListener(mTabItemListener);
    }


    //底部导航栏item的监听
    OnTabItemSelectListener mTabItemListener = new OnTabItemSelectListener() {
        @Override
        public void onSelected(int index, Object tag) {
            switch (index) {
                case 0:

                    break;
                case 1:
                    SnackbarUtils.showSimpleSnackbar(mDrawer, "开启音乐");
                    break;
                case 2:
                    SnackbarUtils.showSimpleSnackbar(mDrawer, "开启天气");
                    break;
                case 3:
                    SnackbarUtils.showSimpleSnackbar(mDrawer, "开启设置");
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onRepeatClick(int index, Object tag) {
            switch (index) {
                case 0:
                    mHomeFragment = new HomeFragment();
                    mFragmentTransaction.replace(R.id.frame_home_activity,mHomeFragment).commit();
                    break;
                case 1:
                    SnackbarUtils.showSimpleSnackbar(mDrawer, "位置被点击了");
                    break;
                case 2:
                    SnackbarUtils.showSimpleSnackbar(mDrawer, "搜索被点击了");
                    break;
                case 3:
                    SnackbarUtils.showSimpleSnackbar(mDrawer, "帮助被点击了");
                    break;
                default:
                    break;
            }
        }
    };

    //侧滑栏选项的监听
    NavigationView.OnNavigationItemSelectedListener mNavgationViewItemSelectedListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.item1:
                    startActivity(new Intent(HomeActivity.this, GalleryActivity.class));
                    break;

            }
            item.setChecked(true);
            mDrawer.closeDrawers();
            return true;
        }
    };
}
