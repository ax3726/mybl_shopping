package com.ycblsc.prestener.mine;

import com.ycblsc.base.BasePresenter;
import com.ycblsc.common.Api;
import com.ycblsc.model.BaseBean;
import com.ycblsc.model.mine.ConsumptionModel;
import com.ycblsc.model.mine.MineRechargeModel;
import com.ycblsc.view.IConsumptionView;

/**
 * Created by Administrator on 2018/1/11.
 */

public class ConsumptionPrestener extends BasePresenter<IConsumptionView> {
    //消费记录
    public void getConsumptionOrderList(String id, int page, int rows) {
        Api.getApi().getConsumptionOrderList(id, page, rows)
                .compose(callbackOnIOToMainThread())
                .subscribe(new BaseNetSubscriber<ConsumptionModel>() {
                    @Override
                    public void onNext(ConsumptionModel headListModel) {
                        super.onNext(headListModel);
                        getView().getConsumptionOrderList(headListModel);
                    }
                });
    }
}

