package com.ycblsc.ui.mine;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.lm.base.library.adapters.recyclerview.CommonAdapter;
import com.lm.base.library.adapters.recyclerview.base.ViewHolder;
import com.ycblsc.R;
import com.ycblsc.base.BaseActivity;
import com.ycblsc.base.BasePresenter;
import com.ycblsc.common.Api;
import com.ycblsc.common.CacheService;
import com.ycblsc.databinding.ActivityShoppingOrderListBinding;
import com.ycblsc.model.BaseBean;

import java.util.ArrayList;
import java.util.List;

public class ShoppingOrderListActivity extends BaseActivity<BasePresenter, ActivityShoppingOrderListBinding> {

    private List<String> mDataList = new ArrayList<>();
    private CommonAdapter<String> mAdapter;
    private int mPage = 1;
    private int mSize = 10;

    @Override
    protected boolean isTitleBar() {
        return true;
    }

    @Override
    protected void initTitleBar() {
        super.initTitleBar();
        mTitleBarLayout.setTitle("订单信息");
    }

    @Override
    protected boolean isPrestener() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_shopping_order_list;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initData() {
        super.initData();
        if (CacheService.getIntance().isLogin()) {
            initAdapter();
            getData();
        }
    }

    private void initAdapter() {
        mAdapter = new CommonAdapter<String>(this, R.layout.item_shopping_order_list, mDataList) {
            @Override
            protected void convert(ViewHolder holder, String s, int position) {

            }
        };
        mBinding.rcBody.setLayoutManager(new LinearLayoutManager(this));
        mBinding.rcBody.setAdapter(mAdapter);
    }

    private void getData() {
        Api.getApi2().getLoadOrderList(CacheService.getIntance().getUserId(), mPage, mSize, "")
                .compose(callbackOnIOToMainThread())
                .subscribe(new BaseNetSubscriber<BaseBean>() {
                    @Override
                    public void onNext(BaseBean baseBean) {
                        super.onNext(baseBean);
                    }
                });
    }
}
