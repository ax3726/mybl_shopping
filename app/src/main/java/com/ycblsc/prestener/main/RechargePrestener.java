package com.ycblsc.prestener.main;

import com.ycblsc.base.BasePresenter;
import com.ycblsc.common.Api;
import com.ycblsc.model.BaseBean;
import com.ycblsc.model.home.HeadListModel;
import com.ycblsc.model.mine.MineRechargeModel;
import com.ycblsc.view.IMainView;
import com.ycblsc.view.IRechargeView;

/**
 * Created by Administrator on 2017/12/25 0025.
 * 充值
 */

public class RechargePrestener extends BasePresenter<IRechargeView> {
    //充值规则
    public void getMoneyOrder() {
        Api.getApi().getMoneyOrder()
                .compose(callbackOnIOToMainThread())
                .subscribe(new BaseNetSubscriber<MineRechargeModel>() {
                    @Override
                    public void onNext(MineRechargeModel headListModel) {
                        super.onNext(headListModel);
                        getView().getMoneyOrder(headListModel);
                    }
         });
    }


}
