package com.dingmouren.easymvp.ui.category;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.dingmouren.easymvp.util.ViewUtils;

import java.util.List;

/**
 * Created by dingmouren on 2016/12/13.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private String[] mArr;
    public ViewPagerAdapter(FragmentManager fm, String[] list) {
        super(fm);
        this.mArr = list;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = ViewUtils.createFragment(CategoryDetailFragment.class,false);
        Bundle bundle = new Bundle();
        bundle.putString("type",mArr[position]);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return mArr ==  null ? 0 : mArr.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mArr[position];
    }
}
