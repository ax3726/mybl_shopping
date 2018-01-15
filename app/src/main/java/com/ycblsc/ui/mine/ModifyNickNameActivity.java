package com.ycblsc.ui.mine;

import android.text.TextUtils;
import android.view.View;

import com.ycblsc.R;
import com.ycblsc.base.BaseActivity;
import com.ycblsc.base.BasePresenter;
import com.ycblsc.common.Api;
import com.ycblsc.common.CacheService;
import com.ycblsc.databinding.ActivityModifyNicknameBinding;
import com.ycblsc.databinding.ActivityModifyPhoneBinding;
import com.ycblsc.model.BaseBean;
import com.ycblsc.ui.main.LoginActivity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Administrator on 2018/1/14.
 * 修改昵称
 */

public class ModifyNickNameActivity extends BaseActivity<BasePresenter, ActivityModifyNicknameBinding> {
    private String nickname;

    @Override
    protected BasePresenter createPresenter() {
        return new BasePresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_modify_nickname;
    }

    @Override
    protected void initTitleBar() {
        super.initTitleBar();
        mTitleBarLayout.setTitle("修改昵称");
        mTitleBarLayout.setRightShow(true);
        mTitleBarLayout.setRightTxt("确定");
        mTitleBarLayout.setRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nickname = mBinding.etNickname.getText().toString().trim();
                try {
                    nickname = URLEncoder.encode(nickname, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                if (TextUtils.isEmpty(nickname)) {
                    showToast("请输入昵称");
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

    //修改昵称
    private void initUpdate() {
        Api.getApi().getUpdateNiceName(CacheService.getIntance().getUserId(), nickname)
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
