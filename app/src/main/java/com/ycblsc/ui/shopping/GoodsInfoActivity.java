package com.ycblsc.ui.shopping;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;

import com.ycblsc.R;
import com.ycblsc.base.BaseActivity;
import com.ycblsc.base.BaseFragmentPresenter;
import com.ycblsc.base.BasePresenter;
import com.ycblsc.common.Api;
import com.ycblsc.databinding.ActivityGoodsInfoBinding;
import com.ycblsc.model.AddCartEvent;
import com.ycblsc.model.BaseBean;
import com.ycblsc.model.shopping.GoodsInfoModel;
import com.ycblsc.ui.buycart.PaymentActivity;

import org.greenrobot.eventbus.EventBus;

public class GoodsInfoActivity extends BaseActivity<BasePresenter, ActivityGoodsInfoBinding> {

    private String mId = "";
    private String mShopping = "";
    private String mMaxTime = "";//配送时间
    private String mAddress = "";//配送范围
    private GoodsInfoModel.ReturnDataBean mDataBean=null;//商品信息
    @Override
    public int getLayoutId() {
        return R.layout.activity_goods_info;
    }

    @Override
    protected boolean isTitleBar() {
        return true;
    }

    @Override
    protected void initTitleBar() {
        super.initTitleBar();
        mTitleBarLayout.setLeftImg(R.drawable.back);
        mTitleBarLayout.setTitle("");
        mTitleBarLayout.setRightShow(true);
        mTitleBarLayout.setRightImg(R.drawable.close);
        mTitleBarLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected boolean isPrestener() {
        return false;
    }

    @Override
    protected void initData() {
        super.initData();
        mId = getIntent().getStringExtra("id");
        mShopping = getIntent().getStringExtra("shoppingId");
        mMaxTime = getIntent().getStringExtra("time");
        mAddress = getIntent().getStringExtra("address");

        mBinding.tvTime.setText(mMaxTime + "分钟");
        mBinding.tvAddress.setText(mAddress);

        getLoadProductInfo();

    }

    private void getLoadProductInfo() {
        Api.getApi2().getLoadProductInfo(mId, mShopping)
                .compose(callbackOnIOToMainThread())
                .subscribe(new BaseNetSubscriber<GoodsInfoModel>() {
                    @Override
                    public void onNext(GoodsInfoModel baseBean) {
                        super.onNext(baseBean);
                        initView(baseBean);
                    }
                });
    }

    private void initView(GoodsInfoModel baseBean) {
        if (baseBean.getReturnData() == null || baseBean.getReturnData().size() == 0) {
            return;
        }
        mDataBean = baseBean.getReturnData().get(0);
        loadImag(mDataBean.getImg(), mBinding.img);
        mBinding.tvTitle.setText(mDataBean.getS_products());
        mBinding.tvPrice.setText(mDataBean.getS_price() + "");
        mBinding.tvGoodsInfo.setText(Html.fromHtml(mDataBean.getNote() + ""));
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mBinding.imgCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new AddCartEvent(mId));
                showToast("添加购物车成功!");
            }
        });
        mBinding.imgBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDataBean != null) {
                    startActivity(
                            new Intent(aty, PaymentActivity.class)
                                    .putExtra("mTotal", mDataBean.getS_price()+"")
                                    .putExtra("goods_data", mDataBean));
                } else {
                    showToast("商品信息有误!");
                }

            }
        });
    }
}
