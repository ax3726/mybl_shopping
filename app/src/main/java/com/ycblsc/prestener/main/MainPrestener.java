package com.ycblsc.prestener.main;

import android.widget.Toast;

import com.ycblsc.base.BaseFragmentPresenter;
import com.ycblsc.base.BasePresenter;
import com.ycblsc.base.BaseView;
import com.ycblsc.common.Api;
import com.ycblsc.model.BaseBean;
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

    //会员注册
    public void getLoginRegiter(String name, String gender, String telphone, String address, String icon, String password) {
        Api.getApi().getLoginRegister(name, gender, telphone, address, icon, password)
                .compose(callbackOnIOToMainThread())
                .subscribe(new BaseNetSubscriber<BaseBean>() {
                    @Override
                    public void onNext(BaseBean headListModel) {
                        super.onNext(headListModel);
                        getView().getLoginRegister(headListModel);
                    }
                });
    }

    public void getSendCode(String telphone, String validName) {
        Api.getApi().getSendCode(telphone, validName)
                .compose(callbackOnIOToMainThread())
                .subscribe(new BaseNetSubscriber<BaseBean>() {
                    @Override
                    public void onNext(BaseBean headListModel) {
                        super.onNext(headListModel);
                        getView().getSendCode(headListModel);
                    }
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }
}
