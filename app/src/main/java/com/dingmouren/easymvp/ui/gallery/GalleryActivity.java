package com.dingmouren.easymvp.ui.gallery;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.dingmouren.easymvp.R;
import com.dingmouren.easymvp.base.BaseActivity;

/**
 * Created by dingmouren on 2016/12/3.
 */

public class GalleryActivity extends BaseActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
    }
}
