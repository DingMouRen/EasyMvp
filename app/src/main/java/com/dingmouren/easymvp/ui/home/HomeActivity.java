package com.dingmouren.easymvp.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;

import com.dingmouren.easymvp.R;
import com.dingmouren.easymvp.base.BaseActivity;
import com.dingmouren.easymvp.base.BasePresenter;
import com.dingmouren.easymvp.ui.gallery.GalleryActivity;
import com.dingmouren.easymvp.util.SnackbarUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.majiajie.pagerbottomtabstrip.Controller;
import me.majiajie.pagerbottomtabstrip.PagerBottomTabLayout;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectListener;

public class HomeActivity extends BaseActivity implements HomeContract.View{
    
    @BindView(R.id.drawer_main)  DrawerLayout mDrawer;
    @BindView(R.id.coordinator)  CoordinatorLayout mCoordinator;
    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.swipe_refresh)   SwipeRefreshLayout mSwipeRefresh;
    @BindView(R.id.recycler) RecyclerView mRecycler;
    @BindView(R.id.nav_view_main)  NavigationView mNavView;
    @BindView(R.id.fab_main)  FloatingActionButton mFab;
    @BindView(R.id.tab_bottom)  PagerBottomTabLayout mTabBottom;

    private GridLayoutManager mGridLayoutManager;
    public HomeAdapter mHomeAdapter;
    public HomePresenter mHomePresenter;
    private Controller mController;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        //初始化SwipeRefresh的颜色
        setupSwipeRefresh();
        //初始化底部导航栏
        setupTabBottom();
        //初始化布局
        initView();
        //初始化数据
        initData();
    }

    private void setupSwipeRefresh() {
        if (mSwipeRefresh != null) {
            mSwipeRefresh.setColorSchemeResources(R.color.blue,
                    R.color.main_color, R.color.blue);
            mSwipeRefresh.setProgressViewOffset(true, 0, (int) TypedValue
                    .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
            mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    mHomePresenter.requestData();
                }
            });
        }
    }

    private void setupTabBottom(){
        mController = mTabBottom.builder()
                .addTabItem(R.mipmap.home,"首页")
                .addTabItem(R.mipmap.music_icon, "Music")
                .addTabItem(R.mipmap.weather, "天气")
                .addTabItem(R.mipmap.setting, "设置")
                .build();
        mController.addTabItemClickListener(mTabItemListener);
    }
    private void initView() {
        mGridLayoutManager = new GridLayoutManager(this,2);
        mHomeAdapter = new HomeAdapter(this);
        mRecycler.setHasFixedSize(true);
        mRecycler.setLayoutManager(mGridLayoutManager);
        mRecycler.setAdapter(mHomeAdapter);

    }

    private void initData(){
        mHomePresenter = createPresenter(this);
        setDataRefresh(true);
        mHomePresenter.requestData();
        mHomePresenter.addScrollListener();
    }


    public HomePresenter createPresenter(Context context){
        return new HomePresenter((HomeContract.View) this);
    }


    @Override
    public void setDataRefresh(boolean refresh) {
        if (refresh){
            mSwipeRefresh.setRefreshing(refresh);
        }else {
            new Handler().postDelayed(()->mSwipeRefresh.setRefreshing(refresh),1000);//延时消失加载的loading
        }
    }

    @Override
    public GridLayoutManager getLayoutManager() {
        return  mGridLayoutManager == null ? new GridLayoutManager( this,2 ) : mGridLayoutManager;
    }

    @Override
    public RecyclerView getRecyclerView() {
        return mRecycler;
    }

    @Override
    public HomeAdapter getHomeAdapter() {
        return mHomeAdapter == null ? new HomeAdapter(this) : mHomeAdapter;
    }

    //底部导航栏item的监听
    OnTabItemSelectListener mTabItemListener = new OnTabItemSelectListener() {
        @Override
        public void onSelected(int index, Object tag) {
            switch (index){
                case 0:
                    break;
                case 1:
                    SnackbarUtils.showSimpleSnackbar(mDrawer,"开启音乐");
                    break;
                case 2:
                    SnackbarUtils.showSimpleSnackbar(mDrawer,"开启天气");
                    break;
                case 3:
                    SnackbarUtils.showSimpleSnackbar(mDrawer,"开启设置");
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onRepeatClick(int index, Object tag) {
            switch (index){
                case 0:
                    SnackbarUtils.showSimpleSnackbar(mDrawer,"相机被点击了");
                    break;
                case 1:
                    SnackbarUtils.showSimpleSnackbar(mDrawer,"位置被点击了");
                    break;
                case 2:
                    SnackbarUtils.showSimpleSnackbar(mDrawer,"搜索被点击了");
                    break;
                case 3:
                    SnackbarUtils.showSimpleSnackbar(mDrawer,"帮助被点击了");
                    break;
                default:
                    break;
            }
        }
    };
}
