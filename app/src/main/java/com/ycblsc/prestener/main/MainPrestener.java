package com.ycblsc.prestener.main;

import com.ycblsc.base.BaseFragmentPresenter;
import com.ycblsc.base.BasePresenter;
import com.ycblsc.base.BaseView;
import com.ycblsc.common.Api;
import com.ycblsc.model.home.HeadListModel;
import com.ycblsc.model.home.ShopInfoModel;
import com.ycblsc.view.IMainView;

/**
 * Created by Administrator on 2017/12/25 0025.
 */

public class MainPrestener extends BasePresenter<IMainView> {
    public void getHeadList() {
        Api.getApi().getHeadList()
                .compose(callbackOnIOToMainThread())
                .subscribe(new BaseNetSubscriber<HeadListModel>() {
                    @Override
                    public void onNext(HeadListModel headListModel) {
                        super.onNext(headListModel);
                        getView().getHeadList(headListModel);
                    }
                });
    }
}
