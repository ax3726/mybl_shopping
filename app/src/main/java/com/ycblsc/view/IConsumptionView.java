package com.ycblsc.view;

import com.ycblsc.base.BaseView;
import com.ycblsc.model.BaseBean;
import com.ycblsc.model.mine.ConsumptionModel;
import com.ycblsc.model.mine.MineRechargeModel;

/**
 * Created by Administrator on 2018/1/4.
 */

public interface IConsumptionView extends BaseView {
    //消费记录
    void getConsumptionOrderList(ConsumptionModel headListModel);

}
