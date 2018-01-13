package com.ycblsc.ui.mine;

import android.text.Html;
import android.view.View;

import com.ycblsc.R;
import com.ycblsc.base.BaseActivity;
import com.ycblsc.base.BasePresenter;
import com.ycblsc.common.Api;
import com.ycblsc.common.CacheService;
import com.ycblsc.databinding.ActivityConsumpationListBinding;
import com.ycblsc.databinding.ActivityProtocolLayoutBinding;
import com.ycblsc.model.BaseBean;
import com.ycblsc.model.UserModel;
import com.ycblsc.model.shopping.ImageDataModel;
import com.ycblsc.ui.main.MainActivity;

/**
 * Created by Administrator on 2018/1/12.
 * 充值协议
 */

public class RechargeProtocolActivity extends BaseActivity<BasePresenter, ActivityProtocolLayoutBinding> implements View.OnClickListener {
    @Override
    protected BasePresenter createPresenter() {
        return new BasePresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_protocol_layout;
    }

    @Override
    protected void initTitleBar() {
        super.initTitleBar();
        mTitleBarLayout.setTitle("充值协议");
    }

    @Override
    protected void initEvent() {
        super.initEvent();
    }

    @Override
    protected void initData() {
        super.initData();
        Api.getApi().getImageData("50")
                .compose(callbackOnIOToMainThread())
                .subscribe(new BaseNetSubscriber<ImageDataModel>() {
                    @Override
                    public void onNext(ImageDataModel imageDataModel) {
                        super.onNext(imageDataModel);
                        mBinding.tvAgreement.setText(Html.fromHtml(imageDataModel.getReturnData().get(0).getI_Content()));
                    }
                });
    }

    @Override
    public void onClick(View v) {

    }
}
