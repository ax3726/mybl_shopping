package com.ycblsc.ui.mine;

import android.content.Intent;
import android.view.View;

import com.ycblsc.R;
import com.ycblsc.base.BaseActivity;
import com.ycblsc.base.BasePresenter;
import com.ycblsc.common.CacheService;
import com.ycblsc.databinding.ActivitySettingLayoutBinding;
import com.ycblsc.model.UserModel;
import com.ycblsc.ui.main.LoginActivity;

/**
 * Created by Administrator on 2018/1/12.
 */

public class PersonalSettingActivity extends BaseActivity<BasePresenter, ActivitySettingLayoutBinding> implements View.OnClickListener {
    @Override
    protected void initTitleBar() {
        super.initTitleBar();
        //
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
        mBinding.linModifyPwd.setOnClickListener(this);
        mBinding.linModifyPhone.setOnClickListener(this);
        mBinding.linModifyNickname.setOnClickListener(this);
        mBinding.linModifyAddress.setOnClickListener(this);
        mBinding.btnExit.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //修改密码
            case R.id.lin_modifyPwd:
                startActivity(new Intent(PersonalSettingActivity.this, ModifyPwdActivity.class));
                break;
            //修改手机号码
            case R.id.lin_modifyPhone:
                startActivity(new Intent(PersonalSettingActivity.this, ModifyPhoneActivity.class));
                break;
            //修改昵称
            case R.id.lin_modifyNickname:
                startActivity(new Intent(PersonalSettingActivity.this, ModifyNickNameActivity.class));
                break;
            //修改地址
            case R.id.lin_modifyAddress:
                startActivity(new Intent(PersonalSettingActivity.this, ModifyAddressActivity.class));
                break;
            //退出登录
            case R.id.btn_exit:
                CacheService.getIntance().clearUser(new UserModel());
                startActivity(new Intent(PersonalSettingActivity.this, LoginActivity.class));
                finish();
                break;
        }
    }
}
