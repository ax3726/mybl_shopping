package com.ycblsc.ui.mine;

import android.text.TextUtils;
import android.view.View;

import com.ycblsc.R;
import com.ycblsc.base.BaseActivity;
import com.ycblsc.base.BasePresenter;
import com.ycblsc.common.Api;
import com.ycblsc.common.CacheService;
import com.ycblsc.databinding.ActivityModifyLayoutBinding;
import com.ycblsc.databinding.ActivityModifyPhoneBinding;
import com.ycblsc.model.BaseBean;
import com.ycblsc.ui.main.LoginActivity;

/**
 * Created by Administrator on 2018/1/14.
 * 修改手机号码
 */

public class ModifyPhoneActivity extends BaseActivity<BasePresenter, ActivityModifyPhoneBinding> {
    private String phone;

    @Override
    protected BasePresenter createPresenter() {
        return new BasePresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_modify_phone;
    }
    @Override
    protected void initTitleBar() {
        super.initTitleBar();
        mTitleBarLayout.setTitle("修改手机号");
        mTitleBarLayout.setRightShow(true);
        mTitleBarLayout.setRightTxt("确定");
        mTitleBarLayout.setRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phone = mBinding.etPhone.getText().toString().trim();

                if (TextUtils.isEmpty(phone)) {
                    showToast("请输入手机号");
                    return;
                }
                initUpdate(); //修改手机号
            }
        });
    }

    @Override
    protected void initEvent() {
        super.initEvent();
    }

    @Override
    protected void initData() {
        super.initData();
    }

    //修改手机号码
    private void initUpdate() {
        Api.getApi().getUpdatePhone(CacheService.getIntance().getUserId(), phone)
                .compose(callbackOnIOToMainThread())
                .subscribe(new BaseNetSubscriber<BaseBean>(true) {
                    @Override
                    public void onNext(BaseBean baseBean) {
                        super.onNext(baseBean);
                        showToast("修改成功！");
                        new Thread() {
                            @Override
                            public void run() {
                                super.run();
                                try {
                                    sleep(1000);
                                } catch (InterruptedException e) {
                                }
                                startActivity(LoginActivity.class);
                                finish();
                            }
                        }.start();

                    }
                });

    }
}
