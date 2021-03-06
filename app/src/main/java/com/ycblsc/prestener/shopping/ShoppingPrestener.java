package com.ycblsc.prestener.shopping;

import com.ycblsc.base.BaseFragmentPresenter;
import com.ycblsc.base.EmptyState;
import com.ycblsc.common.Api;
import com.ycblsc.model.BaseBean;
import com.ycblsc.model.home.ProductListModel;
import com.ycblsc.model.home.ProuductTypeModel;
import com.ycblsc.model.home.ShopInfoModel;
import com.ycblsc.model.shopping.ImageDataModel;
import com.ycblsc.model.shopping.ShoppingInfoModel;
import com.ycblsc.view.IShoppingView;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Administrator on 2017/12/25 0025.
 */

public class ShoppingPrestener extends BaseFragmentPresenter<IShoppingView> {
    public void getProductType(String shopId) {
        Api.getApi2().getProductType2(shopId)
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

                    }
                });
    }

    /**
     * 获取推荐商品信息
     *
     * @param id
     */
    public void getRecommend(
            String id   //商品id
    ) {
        Api.getApi2().getProductList2("01", id, "1", "", "1", "10")
                .compose(callbackOnIOToMainThread())
                .subscribe(new BaseNetSubscriber<ProductListModel>() {
                    @Override
                    public void onNext(ProductListModel baseBean) {
                        super.onNext(baseBean);
                        getView().getRecommend(baseBean);
                    }
                });
    }

    public void getShopInfo2(String shopId) {
        Api.getApi2().getShopInfo2(shopId)
                .compose(callbackOnIOToMainThread())
                .subscribe(new BaseNetSubscriber<ShoppingInfoModel>() {
                    @Override
                    public void onNext(ShoppingInfoModel baseBean) {
                        super.onNext(baseBean);
                        getView().getShopInfo(baseBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                      //  super.onError(e);
                        EventBus.getDefault().post("Shopflag");
                    }
                });
    }

    public void getProductList2(String fenLei
            , String id   //便利架id
            , String isTuiJian//是否推荐（1是）
            , String name
            , String page
            , String rows) {
        Api.getApi2().getProductList2(fenLei, id, isTuiJian, name, page, rows)
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
        Api.getApi2().getImageData("54").compose(callbackOnIOToMainThread()).subscribe(new BaseNetSubscriber<ImageDataModel>() {
            @Override
            public void onNext(ImageDataModel baseBean) {
                super.onNext(baseBean);
                getView().getImageData(baseBean);
            }
        });
    }

}
