package com.ycblsc.ui.buycart;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.ycblsc.R;
import com.ycblsc.base.BaseActivity;
import com.ycblsc.base.BasePresenter;
import com.ycblsc.common.Api;
import com.ycblsc.common.CacheService;
import com.ycblsc.common.MyApplication;
import com.ycblsc.databinding.ActivityPayBinding;
import com.ycblsc.model.BaseBean;
import com.ycblsc.model.CartEventModel;
import com.ycblsc.model.UserModel;
import com.ycblsc.model.WEXModel;
import com.ycblsc.model.home.ProductListModel;
import com.ycblsc.model.mine.PersonInfoModel;
import com.ycblsc.utils.PayHelper;
import com.ycblsc.utils.SharedPreferencesUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
        String str = (String) SharedPreferencesUtils.getParam(PaymentActivity.this, "pay", "");
        //蚁巢会员支付
        if (!TextUtils.isEmpty(str) && str.equals("1")) {
            mBinding.radBalance.setChecked(true);
            //1.
            setTextResource(mBinding.tvPaymentBalance, "默认支付方式", R.color.colorPrimaryDark, R.color.colorWhite);
            //2.
            setTextResource(mBinding.tvPaymentWeixin, "设置默认支付方式", R.color.shape_bg, R.color.color202020);
            //3.
            setTextResource(mBinding.tvPaymentZhifubao, "设置默认支付方式", R.color.shape_bg, R.color.color202020);
        } else if (!TextUtils.isEmpty(str) && str.equals("2")) {
            mBinding.radWeixin.setChecked(true);
            //1.
            setTextResource(mBinding.tvPaymentWeixin, "默认支付方式", R.color.colorPrimaryDark, R.color.colorWhite);
            //2.
            setTextResource(mBinding.tvPaymentBalance, "设置默认支付方式", R.color.shape_bg, R.color.color202020);
            //3.
            setTextResource(mBinding.tvPaymentZhifubao, "设置默认支付方式", R.color.shape_bg, R.color.color202020);
        } else if (!TextUtils.isEmpty(str) && str.equals("3")) {
            mBinding.radAlipay.setChecked(true);
            //1.
            setTextResource(mBinding.tvPaymentZhifubao, "默认支付方式", R.color.colorPrimaryDark, R.color.colorWhite);
            //2.
            setTextResource(mBinding.tvPaymentBalance, "设置默认支付方式", R.color.shape_bg, R.color.color202020);
            //3.
            setTextResource(mBinding.tvPaymentWeixin, "设置默认支付方式", R.color.shape_bg, R.color.color202020);
        }

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
            else if (mBinding.radWeixin.isChecked()) {
                WexPay();

            }
            else  if (mBinding.radAlipay.isChecked()) {
                AliPay();//支付宝支付

            }
        });
        cancel.setOnClickListener(v -> dialog.dismiss());
    }
    /**
     * 微信支付
     */
    private void WexPay() {

        String userId = CacheService.getIntance().getUserId();
        if (TextUtils.isEmpty(userId)) {
            userId = "0";
        } else {
            userId = CacheService.getIntance().getUserId();
        }
      Api.getApi().getWexPay(userId, "010203", shopCount, shopId, shopPrice, MyApplication.getInstance().getEasyId())
                .compose(callbackOnIOToMainThread())
                .subscribe(new BaseNetSubscriber<WEXModel>() {
                    @Override
                    public void onNext(WEXModel wexModel) {
                        super.onNext(wexModel);
                        if (wexModel.getReturnData() != null && wexModel.getReturnData().size() > 0) {
                            PayHelper.getInstance().WexPay(wexModel.getReturnData().get(0));
                        } else {
                            showToast("信息错误！");
                        }
                    }
                });


    }
    /**
     * 微信支付成功
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void UpdateNotice(String message) {
        if ("微信支付成功".equals(message)) {
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
    }

    /**
     * 支付宝支付
     *
     *
     */
    private void AliPay() {

        String userId = CacheService.getIntance().getUserId();
        if (TextUtils.isEmpty(userId)) {
            userId = "0";
        } else {
            userId = CacheService.getIntance().getUserId();
        }
        Api.getApi().getPay(userId, "010202", shopCount, shopId, shopPrice, MyApplication.getInstance().getEasyId())
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
        EventBus.getDefault().register(aty);
        proCount = new StringBuilder();
        proId = new StringBuilder();
        proPrice = new StringBuilder();

        mData = getIntent().getParcelableArrayListExtra("data");//获取购物车传过来的商品信息   其中count字段为商品的个数   并且count是从0开始的  即个数=count+1
        if (CacheService.getIntance().isLogin()) {
            initPersonData();
        }else {
            mBinding.relaPay.setVisibility(View.GONE);
            mBinding.linBanele.setVisibility(View.GONE);
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
        Api.getApi().getPay(CacheService.getIntance().getUserId(), "010207", shopCount, shopId, shopPrice, MyApplication.getInstance().getEasyId())
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
                //1.
                setTextResource(mBinding.tvPaymentBalance, "默认支付方式", R.color.colorPrimaryDark, R.color.colorWhite);
                //2.
                setTextResource(mBinding.tvPaymentWeixin, "设置默认支付方式", R.color.shape_bg, R.color.color202020);
                //3.
                setTextResource(mBinding.tvPaymentZhifubao, "设置默认支付方式", R.color.shape_bg, R.color.color202020);

                SharedPreferencesUtils.setParam(this, "pay", "1");
                break;
            case R.id.tv_payment_weixin:
                if (!mBinding.radWeixin.isChecked()) {
                    showToast("请先选中微信支付");
                    return;
                }
                //1.
                setTextResource(mBinding.tvPaymentWeixin, "默认支付方式", R.color.colorPrimaryDark, R.color.colorWhite);
                //2.
                setTextResource(mBinding.tvPaymentBalance, "设置默认支付方式", R.color.shape_bg, R.color.color202020);
                //3.
                setTextResource(mBinding.tvPaymentZhifubao, "设置默认支付方式", R.color.shape_bg, R.color.color202020);

                SharedPreferencesUtils.setParam(this, "pay", "2");

                break;
            case R.id.tv_payment_zhifubao:
                if (!mBinding.radAlipay.isChecked()) {
                    showToast("请先选中支付宝支付");
                    return;
                }
                //1.
                setTextResource(mBinding.tvPaymentZhifubao, "默认支付方式", R.color.colorPrimaryDark, R.color.colorWhite);
                //2.
                setTextResource(mBinding.tvPaymentBalance, "设置默认支付方式", R.color.shape_bg, R.color.color202020);
                //3.
                setTextResource(mBinding.tvPaymentWeixin, "设置默认支付方式", R.color.shape_bg, R.color.color202020);

                SharedPreferencesUtils.setParam(this, "pay", "3");
                break;
        }
    }

    /*
    * 通用设置背景颜色方法
    * */
    private void setTextResource(TextView tv, String text, int bgRes, int textColor) {
        tv.setText(text);
        tv.setBackgroundColor(getResources().getColor(bgRes));
        tv.setTextColor(getResources().getColor(textColor));
    }

    /**
     * 购买成功成功， 清除购物车
     */
    private void clearCart() {
        EventBus.getDefault().post(new CartEventModel(mData));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(aty);
    }
}
