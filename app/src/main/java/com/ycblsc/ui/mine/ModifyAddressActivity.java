package com.ycblsc.ui.mine;

import android.text.TextUtils;
import android.view.View;

import com.ycblsc.R;
import com.ycblsc.base.BaseActivity;
import com.ycblsc.base.BasePresenter;
import com.ycblsc.common.Api;
import com.ycblsc.common.CacheService;
import com.ycblsc.databinding.ActivityModifyAddressBinding;
import com.ycblsc.databinding.ActivityModifyNicknameBinding;
import com.ycblsc.model.BaseBean;
import com.ycblsc.ui.main.LoginActivity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Administrator on 2018/1/14.
 * 修改地址
 */

public class ModifyAddressActivity extends BaseActivity<BasePresenter, ActivityModifyAddressBinding> {
    private String address;

    @Override
    protected BasePresenter createPresenter() {
        return new BasePresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_modify_address;
    }

    @Override
    protected void initTitleBar() {
        super.initTitleBar();

        mTitleBarLayout.setTitle("修改地址");
        mTitleBarLayout.setRightShow(true);
        mTitleBarLayout.setRightTxt("确定");
        mTitleBarLayout.setRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                address = mBinding.etAddress.getText().toString().trim();
                try {
                    address = URLEncoder.encode(address, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                if (TextUtils.isEmpty(address)) {
                    showToast("请输入地址");
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

    //修改地址
    private void initUpdate() {
        Api.getApi().getUpdateAddress(CacheService.getIntance().getUserId(), address)
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
                                finish();
                            }
                        }.start();

                    }
                });

    }
}
