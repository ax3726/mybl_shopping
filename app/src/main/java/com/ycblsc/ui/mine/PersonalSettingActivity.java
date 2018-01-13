package com.ycblsc.ui.mine;

import android.view.View;

import com.ycblsc.R;
import com.ycblsc.base.BaseActivity;
import com.ycblsc.base.BasePresenter;
import com.ycblsc.databinding.ActivitySettingLayoutBinding;

/**
 * Created by Administrator on 2018/1/12.
 */

public class PersonalSettingActivity extends BaseActivity<BasePresenter, ActivitySettingLayoutBinding> implements View.OnClickListener {
    @Override
    protected void initTitleBar() {
        super.initTitleBar();
        mTitleBarLayout.setTitle("个人设置");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting_layout;
    }

    @Override
    protected BasePresenter createPresenter() {
        return new BasePresenter();
    }

    @Override
    protected void initEvent() {
        super.initEvent();
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    public void onClick(View v) {

    }

}
