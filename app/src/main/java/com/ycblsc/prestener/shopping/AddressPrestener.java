package com.ycblsc.prestener.shopping;

import com.ycblsc.base.BaseFragmentPresenter;
import com.ycblsc.common.Api;
import com.ycblsc.model.BaseBean;
import com.ycblsc.model.SelectAddressModel;
import com.ycblsc.model.home.ProductListModel;
import com.ycblsc.model.home.ProuductTypeModel;
import com.ycblsc.model.shopping.ImageDataModel;
import com.ycblsc.model.shopping.ShoppingInfoModel;
import com.ycblsc.model.shopping.TimeAddressModel;
import com.ycblsc.view.IAddressView;
import com.ycblsc.view.IShoppingView;

/**
 * Created by Administrator on 2017/12/25 0025.
 */

public class AddressPrestener extends BaseFragmentPresenter<IAddressView> {
    /*
    * 限时达服务说明
    * */
    public void getGetNote(String d_Code) {
        Api.getApi2().getGetNote(d_Code)
                .compose(callbackOnIOToMainThread())
                .subscribe(new BaseNetSubscriber<TimeAddressModel>() {
                    @Override
                    public void onNext(TimeAddressModel baseBean) {
                        super.onNext(baseBean);
                        getView().getGetNote(baseBean);
                    }
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);

                    }
                });
    }
    /*
       * 获取体验店id
       * */
    public void getLoadShopIdByAddress(int id) {
        Api.getApi2().getLoadShopIdByAddress(id)
                .compose(callbackOnIOToMainThread())
                .subscribe(new BaseNetSubscriber<BaseBean>() {
                    @Override
                    public void onNext(BaseBean baseBean) {
                        super.onNext(baseBean);
                        getView().getLoadShopIdByAddress(baseBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);

                    }
                });

    }
    //获取个人地址
    public void getAddressData(String id) {
        Api.getApi2().getLoadMemberAddressData(id)
                .compose(callbackOnIOToMainThread())
                .subscribe(new BaseNetSubscriber<SelectAddressModel>() {
                    @Override
                    public void onNext(SelectAddressModel baseBean) {
                        super.onNext(baseBean);
                        getView().getAddressData(baseBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);

                    }
                });
    }
}
