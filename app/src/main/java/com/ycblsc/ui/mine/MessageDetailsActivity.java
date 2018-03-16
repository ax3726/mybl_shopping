package com.ycblsc.ui.mine;

import com.ycblsc.R;
import com.ycblsc.base.BaseActivity;
import com.ycblsc.base.BasePresenter;
import com.ycblsc.databinding.ActivityMessgeDetailsBinding;
import com.ycblsc.databinding.ActivityModifyAddressBinding;

/**
 * Created by Administrator on 2018/3/16.
 * 信息详情
 */

public class MessageDetailsActivity extends BaseActivity<BasePresenter, ActivityMessgeDetailsBinding> {
    @Override
    protected BasePresenter createPresenter() {
        return new BasePresenter();
    }

    private String time;
    private String content;

    @Override
    public int getLayoutId() {
        return R.layout.activity_messge_details;
    }

    @Override
    protected void initTitleBar() {
        super.initTitleBar();
        mTitleBarLayout.setTitle("信息详情");
        time = getIntent().getStringExtra("time");
        content = getIntent().getStringExtra("content");
        mBinding.tvTime.setText(time);
        mBinding.tvContent.setText(content);
    }
}
