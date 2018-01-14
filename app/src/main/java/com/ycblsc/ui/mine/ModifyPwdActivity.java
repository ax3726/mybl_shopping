package com.ycblsc.ui.mine;

import android.text.TextUtils;
import android.view.View;

import com.ycblsc.R;
import com.ycblsc.base.BaseActivity;
import com.ycblsc.base.BasePresenter;
import com.ycblsc.common.Api;
import com.ycblsc.common.CacheService;
import com.ycblsc.databinding.ActivityModifyLayoutBinding;
import com.ycblsc.model.BaseBean;
import com.ycblsc.model.UserModel;
import com.ycblsc.ui.main.LoginActivity;
import com.ycblsc.ui.main.MainActivity;

/**
 * Created by Administrator on 2018/1/14.
 * 修改密码
 */

public class ModifyPwdActivity extends BaseActivity<BasePresenter, ActivityModifyLayoutBinding> {
    private String oldpwd;
    private String newpwd;
    private String aginpwd;

    @Override
    protected BasePresenter createPresenter() {
        return new BasePresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_modify_layout;
    }

    @Override
    protected void initTitleBar() {
        super.initTitleBar();
        mTitleBarLayout.setTitle("修改密码");
        mTitleBarLayout.setRightShow(true);
        mTitleBarLayout.setRightTxt("确定");
        mTitleBarLayout.setRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                oldpwd = mBinding.edOldpwd.getText().toString().trim();
                newpwd = mBinding.edNewpwd.getText().toString().trim();
                aginpwd = mBinding.edAgainpwd.getText().toString().trim();
                if (TextUtils.isEmpty(oldpwd)) {
                    showToast("请输入旧密码");
                    return;
                }
                if (TextUtils.isEmpty(newpwd)) {
                    showToast("请输入新密码");
                    return;
                }
                if (TextUtils.isEmpty(aginpwd)) {
                    showToast("请再输入新密码");
                    return;
                }
                if (!aginpwd.equals(newpwd)) {
                    showToast("两次密码输入不一致");
                    return;
                }
                initUpdate(); //修改密码
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

    //修改密码
    private void initUpdate() {
        Api.getApi().getUpdatePassword(CacheService.getIntance().getUserId(), newpwd, oldpwd)
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
