package com.dingmouren.easymvp.ui.home;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
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
import com.dingmouren.easymvp.ui.videos.VideosFragment;
import com.dingmouren.easymvp.ui.webdetail.WebDetailActivity;
import com.dingmouren.easymvp.util.MyGlideImageLoader;
import com.dingmouren.easymvp.util.SPUtil;
import com.dingmouren.easymvp.util.SnackbarUtils;
import com.dingmouren.easymvp.util.StatusBarUtil;
import com.dingmouren.easymvp.util.ViewUtils;
import com.yancy.gallerypick.config.GalleryConfig;
import com.yancy.gallerypick.config.GalleryPick;
import com.yancy.gallerypick.inter.IHandlerCallBack;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class HomeActivity extends BaseActivity  {
    private static final String TAG = HomeActivity.class.getName();

    @BindView(R.id.drawer_main)  DrawerLayout mDrawer;
    @BindView(R.id.coordinator)  CoordinatorLayout mCoordinator;
    @BindView(R.id.toolbar)  Toolbar mToolbar;
    @BindView(R.id.nav_view_main)  NavigationView mNavView;
//    @BindView(R.id.fab_main)  FloatingActionButton mFab; 不显示这个小图标，以后留作他用
    @BindView(R.id.tab_bottom)  BottomNavigationView mTabBottom;

    private ActionBarDrawerToggle mDrawerToggle;
    private FragmentManager mFragmentManager;
    private BaseFragment mCurrentFragment;
    private ImageView mNavHeaderImg;
    private GalleryConfig mGalleryConfig;//图片选择器的配置
    private List<String> mNavHeaderImgPaths = new ArrayList<>();//记录已选的图片

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
        initHeader();//初始化用户头像
    }

    @Override
    protected void setUpData() {

    }

    /**
     * 初始化toolbar
     */
    public void initToolbar() {
        mToolbar.setTitle(getResources().getString(R.string.main_home));
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

    /**
     * 设置nav_view
     */
    private void setupNavView(){
        mNavView.setNavigationItemSelectedListener(mNavgationViewItemSelectedListener);//nav 条目点击的监听
        mNavView.getHeaderView(0).findViewById(R.id.img_btn_night).setOnClickListener((view)-> changeNightMode());//切换夜间模式的按钮监听
        mNavHeaderImg = (ImageView) mNavView.getHeaderView(0).findViewById(R.id.img_nav_header);
        mNavHeaderImg.setOnClickListener((view)-> changeHeader());//更换头像
        //博客点击跳转
        mNavView.getHeaderView(0).findViewById(R.id.linear_csdn).setOnClickListener((view)-> WebDetailActivity.newInstance(this,getResources().getString(R.string.csdn_url),"钉某人的CSDN"));
        mNavView.getHeaderView(0).findViewById(R.id.linear_github).setOnClickListener((view)->WebDetailActivity.newInstance(this,getResources().getString(R.string.github_url),"钉某人的github"));
    }

    /**
     * 初始化用户头像
     */
    private void initHeader(){
        if (!TextUtils.isEmpty((String)SPUtil.get(this,Constant.HEADER_IMG_PATH,"")))
            Glide.with(this).load(SPUtil.get(this, Constant.HEADER_IMG_PATH, "")).into(mNavHeaderImg);
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
            mTabBottom.setItemIconTintList(getResources().getColorStateList(R.color.bottom_nav_view_tinit_night));
            mTabBottom.setItemTextColor(getResources().getColorStateList(R.color.bottom_nav_view_tinit_night));
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
            mTabBottom.setItemIconTintList(getResources().getColorStateList(R.color.bottom_nav_view_tinit));
            mTabBottom.setItemTextColor(getResources().getColorStateList(R.color.bottom_nav_view_tinit));
            mNavView.getHeaderView(0).findViewById(R.id.nav_header_layout).setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            mNavView.getHeaderView(0).findViewById(R.id.img_btn_night).setBackground(getResources().getDrawable(R.mipmap.daily));
            SPUtil.put(HomeActivity.this,Constant.NIGHT_MODE,true);
        }
    }

    //更换头像
    private void changeHeader(){
        initGalleryConfig();//初始化图片选择器的配置参数
        initPermissions();//授权管理
    }

    private void initGalleryConfig() {
        mGalleryConfig = new GalleryConfig.Builder()
                .imageLoader(new MyGlideImageLoader())
                .iHandlerCallBack(imgTakeListener)
                .pathList(mNavHeaderImgPaths)
                .multiSelect(false)//是否多选
                .crop(true)//开启快捷裁剪功能
                .isShowCamera(true)//显示相机按钮，默认是false
                .filePath("/EasyMvp")//图片存放路径
                .build();
    }

    //更换头像时的授权管理
    private void initPermissions() {
        if (ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            //需要授权
            if (ActivityCompat.shouldShowRequestPermissionRationale(HomeActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                //拒绝过了
                SnackbarUtils.showSimpleSnackbar(mCoordinator,"请在 设置-应用管理 中开启此应用的存储权限");
            }else {
                //进行授权
                ActivityCompat.requestPermissions(HomeActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},8);//这个8做什么的不知道，以后再研究
            }
        } else{
            //不需要授权
            GalleryPick.getInstance().setGalleryConfig(mGalleryConfig).open(HomeActivity.this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 8){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //同意授权
                GalleryPick.getInstance().setGalleryConfig(mGalleryConfig).open(HomeActivity.this);
            }else {
                //拒绝授权
            }
        }
    }

    //切换Fragment
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
                    AboutActivity.newInstance(HomeActivity.this);
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
            mTabBottom.setItemIconTintList(getResources().getColorStateList(R.color.bottom_nav_view_tinit));
            mTabBottom.setItemTextColor(getResources().getColorStateList(R.color.bottom_nav_view_tinit));
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
            mTabBottom.setItemIconTintList(getResources().getColorStateList(R.color.bottom_nav_view_tinit_night));
            mTabBottom.setItemTextColor(getResources().getColorStateList(R.color.bottom_nav_view_tinit_night));
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

  //获取一个 View的缓存视图，用于夜间模式切换时的过渡动画
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

    //动态修改BottomNavigationView的图标和文字的着色方案
    private static final int[] CHECKED_STATE_SET = {android.R.attr.state_checked};
    private static final int[] DISABLED_STATE_SET = {-android.R.attr.state_enabled};
    private ColorStateList createDefaultColorStateList(int baseColorThemeAttr) {
        final TypedValue value = new TypedValue();
        if (!this.getTheme().resolveAttribute(baseColorThemeAttr, value, true)) {
            return null;
        }
        ColorStateList baseColor = AppCompatResources.getColorStateList(
                this, value.resourceId);
        if (!this.getTheme().resolveAttribute(
                android.support.v7.appcompat.R.attr.colorPrimary, value, true)) {
            return null;
        }
        int colorPrimary = value.data;
        int defaultColor = baseColor.getDefaultColor();
        return new ColorStateList(new int[][]{
                DISABLED_STATE_SET,
                CHECKED_STATE_SET,
                null
        }, new int[]{
                baseColor.getColorForState(DISABLED_STATE_SET, defaultColor),
                colorPrimary,
                defaultColor
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    //BottomNavigationView的监听接口
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
                   case R.id.item_videos:
                    mToolbar.setTitle(getResources().getString(R.string.main_videos));
                    clazz = VideosFragment.class;
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
    //图片选择器的监听接口
    IHandlerCallBack imgTakeListener = new IHandlerCallBack() {
        @Override
        public void onStart() {

        }
        @Override
        public void onSuccess(List<String> photoList) {
            SPUtil.put(HomeActivity.this,Constant.HEADER_IMG_PATH,photoList.get(0));//记录选择的头像图片的路径
            Glide.with(HomeActivity.this).load(photoList.get(0)).into(mNavHeaderImg);
        }
        @Override
        public void onCancel() {
        }

        @Override
        public void onFinish() {
        }
        @Override
        public void onError() {
        }
    };
}
