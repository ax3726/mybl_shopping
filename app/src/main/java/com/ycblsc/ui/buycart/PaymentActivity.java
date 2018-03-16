package com.ycblsc.ui.buycart;

import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;

import com.ycblsc.R;
import com.ycblsc.base.BaseActivity;
import com.ycblsc.base.BaseFragmentPresenter;
import com.ycblsc.base.BasePresenter;
import com.ycblsc.base.EmptyState;
import com.ycblsc.common.Api;
import com.ycblsc.common.CacheService;
import com.ycblsc.databinding.ActivityPayBinding;
import com.ycblsc.databinding.ActivityRegisterBinding;
import com.ycblsc.model.BaseBean;
import com.ycblsc.model.home.ProductListModel;
import com.ycblsc.model.mine.PersonInfoModel;
import com.ycblsc.ui.main.LoginActivity;
import com.ycblsc.utils.CustomNestRadioGroup;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/3/14.
 * 支付方式
 */

public class PaymentActivity extends BaseActivity<BasePresenter, ActivityPayBinding> implements View.OnClickListener {

    private ArrayList<ProductListModel.ReturnDataBean> mData = new ArrayList<>();//选择的商品
    private String shopCount;
    private String shopId;
    private String shopPrice;
    private String mTotal;//总价、订单金额

    @Override
    public int getLayoutId() {
        return R.layout.activity_pay;
    }

    @Override
    protected void initTitleBar() {
        super.initTitleBar();
        mTitleBarLayout.setTitle("选择支付方式");
    }

    @Override
    protected BasePresenter createPresenter() {
        return new BasePresenter();
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mTotal = getIntent().getStringExtra("mTotal");
        mBinding.tvPaymentBalance.setOnClickListener(this);
        mBinding.tvPaymentWeixin.setOnClickListener(this);
        mBinding.tvPaymentZhifubao.setOnClickListener(this);
        mBinding.radPay.setOnClickListener(this);
        mBinding.radBalance.setOnClickListener(this);
        mBinding.radWeixin.setOnClickListener(this);
        mBinding.radAlipay.setOnClickListener(this);
        //单选监听
//        mBinding.radPay.setOnCheckedChangeListener(new CustomNestRadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CustomNestRadioGroup group, int checkedId) {
//                if (group.getId() == R.id.rad_pay) {
//                    switch (checkedId){
//                        case R.id.rad_balance:
//                            showToast("余额支付");
//                            break;
//                        case R.id.rad_weixin:
//                            showToast("微信支付");
//                            break;
//                        case R.id.rad_alipay:
//                            showToast("支付宝支付");
//                            break;
//                    }
//                }
//            }
//        });

        mBinding.btnDetermine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBinding.radBalance.isChecked()) {
                    initPayMethod();//余额支付接口
                    //  return;
                }
                if (mBinding.radWeixin.isChecked()) {
                    showToast("暂未开通");
                    return;
                }
                if (mBinding.radAlipay.isChecked()) {
                    showToast("支付宝支付");
                    return;
                }
            }
        });
    }

    StringBuilder proCount;//数量
    StringBuilder proId;//商品id
    StringBuilder proPrice;//单价

    @Override
    protected void initData() {
        super.initData();
        proCount = new StringBuilder();
        proId = new StringBuilder();
        proPrice = new StringBuilder();

        mData = getIntent().getParcelableArrayListExtra("data");//获取购物车传过来的商品信息   其中count字段为商品的个数   并且count是从0开始的  即个数=count+1
        if (CacheService.getIntance().isLogin()) {
            initPersonData();
        }

        if (mData.size() > 0) {
            for (int i = 0; i < mData.size(); i++) {
                proCount.append(mData.get(i).getCount() + 1 + "|");
                proId.append(mData.get(i).getId() + "|");
                proPrice.append(mData.get(i).getS_price() + "|");
            }
            shopCount = proCount.substring(0, proCount.length() - 1);
            shopId = proId.substring(0, proId.length() - 1);
            shopPrice = proPrice.substring(0, proPrice.length() - 1);

//            Log.d("Tas", "商品数量===" + shopCount + "");
//            Log.d("Tas", "商品id==" + shopId);
//            Log.d("Tas", "单价===" + shopPrice);
        }


    }

    /*
     * 个人信息
     * */
    private void initPersonData() {
        Api.getApi().getPersonInfo(CacheService.getIntance().getUserId())
                .compose(callbackOnIOToMainThread())
                .subscribe(new BaseNetSubscriber<PersonInfoModel>(true) {
                    @Override
                    public void onNext(PersonInfoModel personInfoList) {
                        super.onNext(personInfoList);
                        if (personInfoList.getReturnData().size() > 0) {
                            mBinding.tvBalance.setText("￥" + personInfoList.getReturnData().get(0).getTf_money());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });

    }

    /*
    * 支付方式（支付宝010202；微信010203；余额010207）
    * */
    private void initPayMethod() {
        Api.getApi().getPay(CacheService.getIntance().getUserId(), "010207", shopCount, shopId, shopPrice, "18")
                .compose(callbackOnIOToMainThread())
                .subscribe(new BaseNetSubscriber<BaseBean>(true) {
                    @Override
                    public void onNext(BaseBean baseBean) {
                        super.onNext(baseBean);
                        showToast("支付成功！");
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

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_payment_balance:
                if (!mBinding.radBalance.isChecked()) {
                    showToast("请先选中会员支付");
                    return;
                }
                mBinding.tvPaymentBalance.setText("默认支付方式");
                mBinding.tvPaymentBalance.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                mBinding.tvPaymentBalance.setTextColor(getResources().getColor(R.color.colorWhite));
                //
                mBinding.tvPaymentWeixin.setText("设置默认支付方式");
                mBinding.tvPaymentWeixin.setBackgroundColor(getResources().getColor(R.color.shape_bg));
                mBinding.tvPaymentWeixin.setTextColor(getResources().getColor(R.color.color202020));
                //
                mBinding.tvPaymentZhifubao.setText("设置默认支付方式");
                mBinding.tvPaymentZhifubao.setBackgroundColor(getResources().getColor(R.color.shape_bg));
                mBinding.tvPaymentZhifubao.setTextColor(getResources().getColor(R.color.color202020));

                break;
            case R.id.tv_payment_weixin:
                if (!mBinding.radWeixin.isChecked()) {
                    showToast("请先选中微信支付");
                    return;
                }
                mBinding.tvPaymentWeixin.setText("默认支付方式");
                mBinding.tvPaymentWeixin.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                mBinding.tvPaymentWeixin.setTextColor(getResources().getColor(R.color.colorWhite));
                //
                mBinding.tvPaymentBalance.setText("设置默认支付方式");
                mBinding.tvPaymentBalance.setBackgroundColor(getResources().getColor(R.color.shape_bg));
                mBinding.tvPaymentBalance.setTextColor(getResources().getColor(R.color.color202020));
                //
                mBinding.tvPaymentZhifubao.setText("设置默认支付方式");
                mBinding.tvPaymentZhifubao.setBackgroundColor(getResources().getColor(R.color.shape_bg));
                mBinding.tvPaymentZhifubao.setTextColor(getResources().getColor(R.color.color202020));
                break;
            case R.id.tv_payment_zhifubao:
                if (!mBinding.radAlipay.isChecked()) {
                    showToast("请先选中支付宝支付");
                    return;
                }
                mBinding.tvPaymentZhifubao.setText("默认支付方式");
                mBinding.tvPaymentZhifubao.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                mBinding.tvPaymentZhifubao.setTextColor(getResources().getColor(R.color.colorWhite));
                //
                mBinding.tvPaymentBalance.setText("设置默认支付方式");
                mBinding.tvPaymentBalance.setBackgroundColor(getResources().getColor(R.color.shape_bg));
                mBinding.tvPaymentBalance.setTextColor(getResources().getColor(R.color.color202020));
                //
                mBinding.tvPaymentWeixin.setText("设置默认支付方式");
                mBinding.tvPaymentWeixin.setBackgroundColor(getResources().getColor(R.color.shape_bg));
                mBinding.tvPaymentWeixin.setTextColor(getResources().getColor(R.color.color202020));
                break;
        }
    }
}
