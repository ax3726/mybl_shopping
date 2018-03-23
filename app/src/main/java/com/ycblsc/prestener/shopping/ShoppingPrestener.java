package com.ycblsc.prestener.shopping;

import com.ycblsc.base.BaseFragmentPresenter;
import com.ycblsc.base.EmptyState;
import com.ycblsc.common.Api;
import com.ycblsc.model.home.ProductListModel;
import com.ycblsc.model.home.ProuductTypeModel;
import com.ycblsc.model.shopping.ImageDataModel;
import com.ycblsc.view.IShoppingView;

/**
 * Created by Administrator on 2017/12/25 0025.
 */

public class ShoppingPrestener extends BaseFragmentPresenter<IShoppingView> {
    public void getProductType(String shopId) {
        Api.getApi().getProductType(shopId)
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

    public void getImageData() {
        Api.getApi().getImageData("49").compose(callbackOnIOToMainThread()).subscribe(new BaseNetSubscriber<ImageDataModel>() {
            @Override
            public void onNext(ImageDataModel baseBean) {
                super.onNext(baseBean);
                getView().getImageData(baseBean);
            }
        });
    }

}
