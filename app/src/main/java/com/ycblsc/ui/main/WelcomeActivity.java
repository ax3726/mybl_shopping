package com.ycblsc.ui.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ycblsc.R;
import com.ycblsc.base.BaseActivity;
import com.ycblsc.base.BasePresenter;
import com.ycblsc.common.Api;
import com.ycblsc.common.CacheService;
import com.ycblsc.databinding.ActivityWebviewLayoutBinding;
import com.ycblsc.databinding.ActivityWelcomeBinding;
import com.ycblsc.model.BaseBean;
import com.ycblsc.model.UserModel;

import cn.jpush.android.api.JPushInterface;

public class WelcomeActivity extends BaseActivity<BasePresenter, ActivityWelcomeBinding> {


    @Override
    public int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected boolean isPrestener() {
        return false;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected boolean isTitleBar() {
        return false;
    }

    @Override
    protected void initData() {
        super.initData();
        UserModel user = CacheService.getIntance().getUser();
        if (user != null) {
            Api.getApi().getLogin(user.getPhone(), user.getPwd())
                    .compose(callbackOnIOToMainThread())
                    .subscribe(new BaseNetSubscriber<BaseBean>() {
                        @Override
                        public void onNext(BaseBean baseBean) {
                            super.onNext(baseBean);
                            JPushInterface.setAlias(aty, 1, String.valueOf(baseBean.getReturnData()));


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

                        @Override
                        public void onError(Throwable e) {
                            super.onError(e);
                            startActivity(LoginActivity.class);
                            finish();
                        }
                    });

        } else {
            startActivity(LoginActivity.class);
            finish();
        }


    }
}
