package com.dingmouren.easymvp.ui.category;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;

import com.dingmouren.easymvp.R;
import com.dingmouren.easymvp.base.BaseFragment;
import com.dingmouren.easymvp.bean.GankContent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by dingmouren on 2016/12/13.
 */

public class CategoryDetailFragment extends BaseFragment implements CategoryContract.View{

    @BindView(R.id.swipe_refresh)  SwipeRefreshLayout mSwipeRefresh;
    @BindView(R.id.recycler) RecyclerView mRecycler;

    private MultiTypeAdapter mMultiTypeAdapter;
    private List mItems;
    private String mType;
    private LinearLayoutManager mLinearLayoutManager;
    private CategoryPresenter mPresenter;
    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_category_detail;
    }

    @Override
    protected void init() {
        mType = getArguments().getString("type");
        mItems = new ArrayList<>();
        mMultiTypeAdapter = new MultiTypeAdapter(mItems);
        mMultiTypeAdapter.applyGlobalMultiTypePool();
    }

    @Override
    protected void setUpView() {
        setupSwipeRefresh();  //初始化SwipeRefresh的颜色
        initView();
    }

    @Override
    protected void setUpData() {

    }

    private void initView() {
        mRecycler.setLayoutManager(new LinearLayoutManager(mRecycler.getContext()));
        mRecycler.setHasFixedSize(true);
    }

    /**
     * 初始化SwipeRefresh的颜色
     */
    private void setupSwipeRefresh() {
        if (mSwipeRefresh != null){
            mSwipeRefresh.setColorSchemeResources(R.color.main_color);//设置进度动画的颜色
//            mSwipeRefresh.setProgressBackgroundColorSchemeResource(android.R.color.holo_blue_bright);//设置进度圈背景颜色
            //这里进行单位换算  第一个参数是单位，第二个参数是单位数值，这里最终返回的是24dp对相应的px值
            mSwipeRefresh.setProgressViewOffset(true,0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,24,getResources().getDisplayMetrics()));
            mSwipeRefresh.setOnRefreshListener(()-> mPresenter.requestData());
        }
    }

    @Override
    public void setData(List<GankContent> list) {
        mItems.addAll(list);
        mRecycler.setAdapter(mMultiTypeAdapter);
    }
}
