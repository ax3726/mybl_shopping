package com.ycblsc.ui.main;

import android.text.TextUtils;
import android.view.View;

import com.ycblsc.R;
import com.ycblsc.base.BaseActivity;
import com.ycblsc.base.BasePresenter;
import com.ycblsc.common.Api;
import com.ycblsc.common.CacheService;
import com.ycblsc.databinding.ActivityLoginBinding;
import com.ycblsc.model.BaseBean;
import com.ycblsc.model.UserModel;
import com.ycblsc.ui.mine.RetrievePwdActivity;

import cn.jpush.android.api.JPushInterface;

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
        mBinding.tvRegister.setOnClickListener(this);
        mBinding.tvRetrievePwd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login_in:
                login();
                break;
            case R.id.btn_login_youke:
                startActivity(MainActivity.class);
                finish();
                break;
            case R.id.tv_register:
                startActivity(RegisterActivity.class);
                break;
            //找回密码
            case R.id.tv_retrievePwd:
                startActivity(RetrievePwdActivity.class);
                break;
        }
    }

    private void login() {
        String phone = mBinding.etPhone.getText().toString().trim();
        String pwd = mBinding.etPassword.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            showToast("请输入手机号!");
            return;
        }
        if (TextUtils.isEmpty(pwd)) {
            showToast("请输入密码!");
            return;
        }

        Api.getApi().getLogin(phone, pwd)
                .compose(callbackOnIOToMainThread())
                .subscribe(new BaseNetSubscriber<BaseBean>(true) {
                    @Override
                    public void onNext(BaseBean baseBean) {
                        super.onNext(baseBean);
                        JPushInterface.setAlias(aty, 1, String.valueOf(baseBean.getReturnData()));

                        CacheService.getIntance().setUser(new UserModel(String.valueOf(baseBean.getReturnData()), phone));//保存用户信息
                        showToast("登录成功！");
                        new Thread() {
                            @Override
                            public void run() {
                                super.run();
                                try {
                                    sleep(1500);
                                } catch (InterruptedException e) {
                                }
                                startActivity(MainActivity.class);
                                finish();
                            }
                        }.start();

                    }
                });

    }
}
