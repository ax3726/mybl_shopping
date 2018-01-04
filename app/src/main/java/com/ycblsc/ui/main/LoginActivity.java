package com.ycblsc.ui.main;

import android.view.View;

import com.ycblsc.R;
import com.ycblsc.base.BaseActivity;
import com.ycblsc.base.BasePresenter;
import com.ycblsc.databinding.ActivityLoginBinding;

public class LoginActivity extends BaseActivity<BasePresenter, ActivityLoginBinding> implements View.OnClickListener {
    
    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected boolean isTitleBar() {
        return false;
    }

    @Override
    protected boolean isPrestener() {
        return false;
    }

    @Override

    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initData() {
        super.initData();

    }


    @Override
    protected void initEvent() {
        super.initEvent();

        mBinding.btnLoginIn.setOnClickListener(this);
        mBinding.btnLoginYouke.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login_in:
                startActivity(MainActivity.class);
                finish();
                break;
            case R.id.btn_login_youke:
                startActivity(MainActivity.class);
                finish();
                break;
        }
    }
}
