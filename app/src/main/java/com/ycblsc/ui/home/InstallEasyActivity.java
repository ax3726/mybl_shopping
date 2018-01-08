package com.ycblsc.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;

import com.ycblsc.R;
import com.ycblsc.base.BaseActivity;
import com.ycblsc.base.BasePresenter;
import com.ycblsc.base.EmptyState;
import com.ycblsc.base.StateModel;
import com.ycblsc.common.Api;
import com.ycblsc.databinding.ActivityInstallEasyBinding;
import com.ycblsc.model.BaseBean;

import java.net.URLEncoder;

public class InstallEasyActivity extends BaseActivity<BasePresenter, ActivityInstallEasyBinding> {

    @Override
    public int getLayoutId() {
        return R.layout.activity_install_easy;
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
    protected void initTitleBar() {
        super.initTitleBar();
        mTitleBarLayout.setTitle("便利架安装申请");
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mBinding.imgSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = mBinding.etName.getText().toString().trim();
                String phone = mBinding.etPhont.getText().toString().trim();
                String address = mBinding.etAddress.getText().toString().trim();

                if (TextUtils.isEmpty(name)) {
                    showToast("请输入姓名！");
                    return;
                }
                if (TextUtils.isEmpty(phone)) {
                    showToast("请输入手机号！");
                    return;
                }
                if (TextUtils.isEmpty(address)) {
                    showToast("请输入您的地址！");
                    return;
                }

                try {
                    Api.getApi().addMessage(phone, URLEncoder.encode(name, "utf-8"), URLEncoder.encode(address, "utf-8"))
                            .compose(callbackOnIOToMainThread())
                            .subscribe(new BaseNetSubscriber<BaseBean>(true) {
                                @Override
                                public void onNext(BaseBean baseBean) {
                                    super.onNext(baseBean);
                                    showToast("提交成功!");
                                    new Thread() {
                                        @Override
                                        public void run() {
                                            super.run();
                                            try {
                                                sleep(1500);
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }
                                            finish();
                                        }
                                    }.start();

                                }
                            });
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        mBinding.imgPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = mBinding.tvPhone.getText().toString().trim();
                if (TextUtils.isEmpty(phone)) {
                    showToast("手机号错误！");
                    return;
                }

                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        // AndroidBug5497Workaround.assistActivity(findViewById(android.R.id.content));// 解决输入法弹出时布局被顶上去的BUG
        mStateModel.setEmptyState(EmptyState.PROGRESS);
        mStateModel.setIOnClickListener(new StateModel.IOnClickListener() {
            @Override
            public void click(View view) {
                mStateModel.setEmptyState(EmptyState.PROGRESS);
                getDict();
            }
        });
        getDict();


    }

    public void getDict() {
        Api.getApi().getDict("010108").compose(callbackOnIOToMainThread()).subscribe(new BaseNetSubscriber<BaseBean>() {
            @Override
            public void onNext(BaseBean baseBean) {
                super.onNext(baseBean);
                mStateModel.setEmptyState(EmptyState.NORMAL);
                mBinding.tvPhone.setText(baseBean.getReturnData() + "");
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mStateModel.setEmptyState(EmptyState.EMPTY);
            }
        });
    }
}
