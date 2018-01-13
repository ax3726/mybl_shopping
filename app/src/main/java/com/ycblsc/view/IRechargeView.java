package com.ycblsc.view;

import com.ycblsc.base.BaseView;
import com.ycblsc.model.BaseBean;
import com.ycblsc.model.home.HeadListModel;
import com.ycblsc.model.mine.MineRechargeModel;
import com.ycblsc.model.shopping.ImageDataModel;

/**
 * Created by Administrator on 2018/1/4.
 */

public interface IRechargeView extends BaseView {
    //充值规则
    void getMoneyOrder(MineRechargeModel headListModel);
    //充值协议
    void getImageData(ImageDataModel headListModel);
}
