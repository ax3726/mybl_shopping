package com.ycblsc.ui.buycart;

import com.ycblsc.R;
import com.ycblsc.base.BaseActivity;
import com.ycblsc.base.BasePresenter;
import com.ycblsc.common.Api;
import com.ycblsc.common.CacheService;
import com.ycblsc.databinding.ActivityNewAddressBinding;
import com.ycblsc.databinding.ActivitySelectAddressBinding;
import com.ycblsc.model.BaseBean;

/**
 * Created by Administrator on 2018/1/14.
 * 新建收货地址
 */

public class NewAddressActivity extends BaseActivity<BasePresenter, ActivityNewAddressBinding> {
    private String address;

    @Override
    protected BasePresenter createPresenter() {
        return new BasePresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_new_address;
    }

    @Override
    protected void initTitleBar() {
        super.initTitleBar();
        mTitleBarLayout.setTitle("新增收货地址");
    }

    @Override
    protected void initEvent() {
        super.initEvent();
    }

    @Override
    protected void initData() {
        super.initData();
        getAddressData(CacheService.getIntance().getUserId());//个人地址
    }

    //获取个人地址
    public void getAddressData(String id) {
        Api.getApi2().getLoadMemberAddressData(id)
                .compose(callbackOnIOToMainThread())
                .subscribe(new BaseNetSubscriber<BaseBean>() {
                    @Override
                    public void onNext(BaseBean baseBean) {
                        super.onNext(baseBean);

                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);

                    }
                });
    }
}
