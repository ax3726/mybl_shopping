package com.ycblsc.ui.mine;

import android.text.TextUtils;
import android.view.View;

import com.ycblsc.R;
import com.ycblsc.base.BaseActivity;
import com.ycblsc.base.BasePresenter;
import com.ycblsc.common.Api;
import com.ycblsc.common.CacheService;
import com.ycblsc.databinding.ActivityProtocolLayoutBinding;
import com.ycblsc.databinding.ActivityRetrieveLayoutBinding;
import com.ycblsc.model.BaseBean;
import com.ycblsc.ui.main.LoginActivity;
import com.ycblsc.utils.UIUtil;

/**
 * Created by Administrator on 2018/1/29.
 * 找回密码
 */

public class RetrievePwdActivity extends BaseActivity<BasePresenter, ActivityRetrieveLayoutBinding> implements View.OnClickListener {

    private String phone;
    private String code;
    private String strCode;

    @Override
    protected BasePresenter createPresenter() {
        return new BasePresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_retrieve_layout;
    }

    @Override
    protected void initTitleBar() {
        super.initTitleBar();
        mTitleBarLayout.setTitle("找回密码");
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mBinding.btnCode.setOnClickListener(this);
        mBinding.tvGetPwd.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        super.initData();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //发送验证码
            case R.id.btn_code:
                phone = mBinding.edRetrieve.getText().toString().trim();
                if (TextUtils.isEmpty(phone)) {
                    showToast("请输入手机号码");
                    return;
                }
                getSendCode(phone, "GetPassword");
                UIUtil.setCodeTextView(this, mBinding.btnCode);
                break;
            //找回密码
            case R.id.tv_getPwd:
                code = mBinding.edCode.getText().toString().trim();
                phone = mBinding.edRetrieve.getText().toString().trim();
                if (TextUtils.isEmpty(phone)) {
                    showToast("请输入手机号码");
                    return;
                }
                if (TextUtils.isEmpty(code)) {
                    showToast("请输入验证码");
                    return;
                }
                if (!code.equals(strCode)) {
                    showToast("验证码输入不正确");
                    return;
                }
                initPwd(phone);
                break;
        }
    }

    private void initPwd(String phone) {
        Api.getApi().getGetPassword(phone)
                .compose(callbackOnIOToMainThread())
                .subscribe(new BaseNetSubscriber<BaseBean>() {
                    @Override
                    public void onNext(BaseBean baseBean) {
                        super.onNext(baseBean);
                        startActivity(LoginActivity.class);
                        finish();
                    }
                });
    }


    //获取验证码
    private void getSendCode(String telphone, String validName) {
        Api.getApi().getSendCode(telphone, validName)
                .compose(callbackOnIOToMainThread())
                .subscribe(new BaseNetSubscriber<BaseBean>() {
                    @Override
                    public void onNext(BaseBean headListModel) {
                        super.onNext(headListModel);
                        strCode = (String) headListModel.getReturnData();
                        showToast("验证码：" + strCode);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }
}
