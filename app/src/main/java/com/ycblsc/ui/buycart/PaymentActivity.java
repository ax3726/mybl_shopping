package com.ycblsc.ui.buycart;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.ycblsc.R;
import com.ycblsc.base.BaseActivity;
import com.ycblsc.base.BasePresenter;
import com.ycblsc.common.Api;
import com.ycblsc.common.CacheService;
import com.ycblsc.databinding.ActivityPayBinding;
import com.ycblsc.model.BaseBean;
import com.ycblsc.model.CartEventModel;
import com.ycblsc.model.home.ProductListModel;
import com.ycblsc.model.mine.PersonInfoModel;
import com.ycblsc.utils.PayHelper;

import org.greenrobot.eventbus.EventBus;

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
    private String mPayMode;//支付方式

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


        mBinding.btnDetermine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBinding.radBalance.isChecked()) {
                    mPayMode = "蚁巢会员支付";
                } else if (mBinding.radWeixin.isChecked()) {
                    mPayMode = "微信支付";
                } else if (mBinding.radAlipay.isChecked()) {
                    mPayMode = "支付宝支付";
                }
                showPayDialog(aty);
            }
        });
    }


    public void showPayDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mView = inflater.inflate(R.layout.dialog_custom, null);
        TextView tv_common_pay = (TextView) mView.findViewById(R.id.tv_common_pay);
        TextView tv_common_mode = (TextView) mView.findViewById(R.id.tv_common_mode);
        TextView submit = (TextView) mView.findViewById(R.id.btn_common_dialog_double_right);
        TextView cancel = (TextView) mView.findViewById(R.id.btn_common_dialog_double_left);
        tv_common_pay.setText("￥" + mTotal);
        tv_common_mode.setText(mPayMode);
        builder.setView(mView);
        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable());
        submit.setOnClickListener(view -> {
            dialog.dismiss();
            if (mBinding.radBalance.isChecked()) {
                initPayMethod();
            }
            if (mBinding.radWeixin.isChecked()) {
                showToast("暂未开通");
                return;
            }
            if (mBinding.radAlipay.isChecked()) {
                AliPay();//支付宝支付
                return;
            }
        });
        cancel.setOnClickListener(v -> dialog.dismiss());
    }

    /**
     * 支付宝支付
     */
    private void AliPay() {

        Api.getApi().getPay(CacheService.getIntance().getUserId(), "010202", shopCount, shopId, shopPrice, "18")
                .compose(callbackOnIOToMainThread())
                .subscribe(new BaseNetSubscriber<BaseBean>(true) {
                    @Override
                    public void onNext(BaseBean baseBean) {
                        super.onNext(baseBean);

                        PayHelper.getInstance().AliPay(aty, String.valueOf(baseBean.getReturnData()));
                        PayHelper.getInstance().setIPayListener(new PayHelper.IPayListener() {
                            @Override
                            public void onSuccess() {
                                showToast("支付成功!");
                                clearCart();//清除购物车
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
                            public void onFail() {
                                showToast("支付失败!");
                            }
                        });
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
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
    * 余额支付
    * （支付宝010202；微信010203；余额010207）
    * */
    private void initPayMethod() {
        Api.getApi().getPay(CacheService.getIntance().getUserId(), "010207", shopCount, shopId, shopPrice, "18")
                .compose(callbackOnIOToMainThread())
                .subscribe(new BaseNetSubscriber<BaseBean>(true) {
                    @Override
                    public void onNext(BaseBean baseBean) {
                        super.onNext(baseBean);
                        showToast("支付成功！");
                        clearCart();//清除购物车
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

    /**
     * 购买成功成功， 清除购物车
     */
    private void clearCart() {
        EventBus.getDefault().post(new CartEventModel(mData));
    }
}
