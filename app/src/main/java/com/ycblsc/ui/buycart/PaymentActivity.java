package com.ycblsc.ui.buycart;

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
import com.ycblsc.model.mine.PersonInfoModel;
import com.ycblsc.ui.main.LoginActivity;

/**
 * Created by Administrator on 2018/3/14.
 * 支付方式
 */

public class PaymentActivity extends BaseActivity<BasePresenter, ActivityPayBinding> {

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
    }

    @Override
    protected void initData() {
        super.initData();
        if (CacheService.getIntance().isLogin()) {
            initPersonData();
        }
        initPayMethod();
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
        Api.getApi().getPay(CacheService.getIntance().getUserId(), "0.01", "010207", "1", "123456", "0.01", "123")
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
}
