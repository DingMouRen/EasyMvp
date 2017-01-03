package com.dingmouren.easymvp.ui.home;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.dingmouren.easymvp.Constant;
import com.dingmouren.easymvp.R;
import com.dingmouren.easymvp.base.BaseActivity;
import com.dingmouren.easymvp.base.BaseFragment;
import com.dingmouren.easymvp.event.NightModeChangeEvent;
import com.dingmouren.easymvp.ui.about.AboutActivity;
import com.dingmouren.easymvp.ui.category.CategoryFragment;
import com.dingmouren.easymvp.ui.gallery.GalleryActivity;
import com.dingmouren.easymvp.ui.picture.WelfareFragment;
import com.dingmouren.easymvp.ui.reading.ReadingFragment;
import com.dingmouren.easymvp.ui.video.VideoActivity;
import com.dingmouren.easymvp.util.FragmentHelpr;
import com.dingmouren.easymvp.util.SPUtil;
import com.dingmouren.easymvp.util.SnackbarUtils;
import com.dingmouren.easymvp.util.StatusBarUtil;
import com.dingmouren.easymvp.util.ViewUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.majiajie.pagerbottomtabstrip.Controller;
import me.majiajie.pagerbottomtabstrip.PagerBottomTabLayout;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectListener;

public class HomeActivity extends BaseActivity {

    @BindView(R.id.drawer_main)  DrawerLayout mDrawer;
    @BindView(R.id.coordinator)  CoordinatorLayout mCoordinator;
    @BindView(R.id.toolbar)  Toolbar mToolbar;
    @BindView(R.id.nav_view_main)  NavigationView mNavView;
//    @BindView(R.id.fab_main)  FloatingActionButton mFab; 不显示这个小图标，以后留作他用
    @BindView(R.id.tab_bottom)  BottomNavigationView mTabBottom;

    private ActionBarDrawerToggle mDrawerToggle;
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
        initToolbar(); //初始化toolbar
        setupTabBottom();//初始化底部导航栏
        setupNavView();//初始化nav_view
        initNightMode();//初始化夜间模式

    }

    @Override
    protected void setUpData() {

    }

    /**
     * 初始化toolbar
     */
    public void initToolbar() {
        mToolbar.setTitle(getResources().getString(R.string.main_home));
        mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawerToggle.syncState();
    }

    /**
     * 初始化底部导航栏
     */
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
                    clazz = CategoryFragment.class;
                    break;
                case R.id.item_welfare:
                    mToolbar.setTitle(getResources().getString(R.string.main_welfare));
                    clazz = WelfareFragment.class;
                    break;
                case R.id.item_reading:
                    mToolbar.setTitle(getResources().getString(R.string.main_reading));
                    clazz = ReadingFragment.class;
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

    /**
     * 设置nav_view
     */
    private void setupNavView(){
        mNavView.setNavigationItemSelectedListener(mNavgationViewItemSelectedListener);//nav 条目点击的监听
        mNavView.getHeaderView(0).findViewById(R.id.img_btn_night).setOnClickListener((view)-> changeNightMode());//切换夜间模式的按钮监听
    }

    //切换夜间模式，并发送事件消息通知其他视图
    private void changeNightMode(){
        showAnimation();//仿知乎切换夜间模式时候的过渡动画，但是状态栏没有渐变的效果
        EventBus.getDefault().postSticky(new NightModeChangeEvent());
        if ((Boolean) SPUtil.get(HomeActivity.this, Constant.NIGHT_MODE,true)){
            mCoordinator.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
            mToolbar.setBackgroundColor(getResources().getColor(android.R.color.black));
            mToolbar.setTitleTextColor(getResources().getColor(android.R.color.darker_gray));
            mDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(android.R.color.darker_gray));
            mToolbar.getOverflowIcon().setTint(getResources().getColor(android.R.color.darker_gray));
            StatusBarUtil.setStatusBarColor(HomeActivity.this,getResources().getColor(android.R.color.black));
            mTabBottom.setBackgroundColor(getResources().getColor(android.R.color.black));
            mNavView.getHeaderView(0).findViewById(R.id.nav_header_layout).setBackgroundColor(getResources().getColor(android.R.color.black));
            mNavView.getHeaderView(0).findViewById(R.id.img_btn_night).setBackground(getResources().getDrawable(R.mipmap.night));
            SPUtil.put(HomeActivity.this, Constant.NIGHT_MODE,false);
        }else {
            mCoordinator.setBackgroundColor(getResources().getColor(R.color.gray));
            mToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
            mDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(android.R.color.white));
            mToolbar.getOverflowIcon().setTint(getResources().getColor(android.R.color.white));
            StatusBarUtil.setStatusBarColor(HomeActivity.this,getResources().getColor(R.color.colorPrimaryDark));
            mTabBottom.setBackgroundColor(getResources().getColor(android.R.color.white));
            mNavView.getHeaderView(0).findViewById(R.id.nav_header_layout).setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            mNavView.getHeaderView(0).findViewById(R.id.img_btn_night).setBackground(getResources().getDrawable(R.mipmap.daily));
            SPUtil.put(HomeActivity.this,Constant.NIGHT_MODE,true);
        }
    }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_about:
                AboutActivity.newInstance(this);
                break;
        }
        return super.onOptionsItemSelected(item);
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
                case R.id.item3:
                    break;

            }
            mDrawer.closeDrawers();
            return true;
        }
    };

    //初始化夜间模式
    private void initNightMode(){
        if ((Boolean) SPUtil.get(HomeActivity.this, Constant.NIGHT_MODE,true)){
            mCoordinator.setBackgroundColor(getResources().getColor(R.color.gray));
            mToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
            mDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(android.R.color.white));
            mToolbar.getOverflowIcon().setTint(getResources().getColor(android.R.color.white));
            StatusBarUtil.setStatusBarColor(HomeActivity.this,getResources().getColor(R.color.colorPrimaryDark));
            mTabBottom.setBackgroundColor(getResources().getColor(android.R.color.white));
            mNavView.getHeaderView(0).findViewById(R.id.nav_header_layout).setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            mNavView.getHeaderView(0).findViewById(R.id.img_btn_night).setBackground(getResources().getDrawable(R.mipmap.daily));
        }else {
            mCoordinator.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
            mToolbar.setBackgroundColor(getResources().getColor(android.R.color.black));
            mToolbar.setTitleTextColor(getResources().getColor(android.R.color.darker_gray));
            mDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(android.R.color.darker_gray));
            mToolbar.getOverflowIcon().setTint(getResources().getColor(android.R.color.darker_gray));
            StatusBarUtil.setStatusBarColor(HomeActivity.this,getResources().getColor(android.R.color.black));
            mTabBottom.setBackgroundColor(getResources().getColor(android.R.color.black));
            mNavView.getHeaderView(0).findViewById(R.id.nav_header_layout).setBackgroundColor(getResources().getColor(android.R.color.black));
            mNavView.getHeaderView(0).findViewById(R.id.img_btn_night).setBackground(getResources().getDrawable(R.mipmap.night));
        }
    }

    //切换夜间模式的过渡动画
    private void showAnimation() {
        final View decorView = getWindow().getDecorView();
        Bitmap cacheBitmap = getCacheBitmapFromView(decorView);
        if (decorView instanceof ViewGroup && cacheBitmap != null) {
            final View view = new View(this);
            view.setBackgroundDrawable(new BitmapDrawable(getResources(), cacheBitmap));
            ViewGroup.LayoutParams layoutParam = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            ((ViewGroup) decorView).addView(view, layoutParam);
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f);
            objectAnimator.setDuration(300);
            objectAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    ((ViewGroup) decorView).removeView(view);
                }
            });
            objectAnimator.start();
        }
    }

  //获取一个 View的缓存视图
    private Bitmap getCacheBitmapFromView(View view) {
        final boolean drawingCacheEnabled = true;
        view.setDrawingCacheEnabled(drawingCacheEnabled);
        view.buildDrawingCache(drawingCacheEnabled);
        final Bitmap drawingCache = view.getDrawingCache();
        Bitmap bitmap;
        if (drawingCache != null) {
            bitmap = Bitmap.createBitmap(drawingCache);
            view.setDrawingCacheEnabled(false);
        } else {
            bitmap = null;
        }
        return bitmap;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
