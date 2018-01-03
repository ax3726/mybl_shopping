package com.ycblsc.prestener.home;

import com.ycblsc.base.BasePresenter;
import com.ycblsc.base.EmptyState;
import com.ycblsc.common.Api;
import com.ycblsc.model.home.ProductListModel;
import com.ycblsc.model.home.ProuductTypeModel;
import com.ycblsc.model.home.ShopInfoModel;
import com.ycblsc.view.IHomeView;

/**
 * Created by Administrator on 2017/12/25 0025.
 */

public class HomePrestener extends BasePresenter<IHomeView> {
    public void getProductType() {
        Api.getApi().getProductType()
                .compose(callbackOnIOToMainThread())
                .subscribe(new BaseNetSubscriber<ProuductTypeModel>() {
                    @Override
                    public void onNext(ProuductTypeModel baseBean) {
                        super.onNext(baseBean);
                        getView().getProuductType(baseBean);
                    }
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        getView().setEmptyState(EmptyState.EMPTY);
                    }
                });
    }

    public void getProductList(String fenLei
            , String id   //便利架id
            , String isTuiJian//是否推荐（1是）
            , String page
            , String rows) {
        Api.getApi().getProductList(fenLei, id, isTuiJian, page, rows)
                .compose(callbackOnIOToMainThread())
                .subscribe(new BaseNetSubscriber<ProductListModel>() {
                    @Override
                    public void onNext(ProductListModel baseBean) {
                        super.onNext(baseBean);
                        getView().getProuductList(baseBean);
                    }
                });
    }

    public void getShopInfo(String id) {
        Api.getApi().getShopInfo(id)
                .compose(callbackOnIOToMainThread())
                .subscribe(new BaseNetSubscriber<ShopInfoModel>() {
                    @Override
                    public void onNext(ShopInfoModel shopInfoModel) {
                        super.onNext(shopInfoModel);
                        getView().getShopInfo(shopInfoModel);
                    }
                });
    }

}
