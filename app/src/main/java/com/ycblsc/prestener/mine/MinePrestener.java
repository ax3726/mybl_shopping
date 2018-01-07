package com.ycblsc.prestener.mine;

import com.ycblsc.base.BaseFragmentPresenter;
import com.ycblsc.base.BaseFragmentView;
import com.ycblsc.base.EmptyState;
import com.ycblsc.common.Api;
import com.ycblsc.model.BaseBean;
import com.ycblsc.model.mine.NotificationModel;
import com.ycblsc.model.mine.PersonInfoModel;
import com.ycblsc.model.shopping.ImageDataModel;
import com.ycblsc.view.IMineView;

/**
 * Created by Administrator on 2017/12/25 0025.
 */

public class MinePrestener extends BaseFragmentPresenter<IMineView> {
    //个人信息
    public void getPersonInfo(int id) {
        Api.getApi().getPersonInfo(id)
                .compose(callbackOnIOToMainThread())
                .subscribe(new BaseNetSubscriber<PersonInfoModel>() {
                    @Override
                    public void onNext(PersonInfoModel baseBean) {
                        super.onNext(baseBean);
                        getView().getPersonInfo(baseBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        getView().setEmptyState(EmptyState.EMPTY);
                    }
                });
    }
   //广告位
    public void getImageData() {
        Api.getApi().getImageData("48").compose(callbackOnIOToMainThread()).subscribe(new BaseNetSubscriber<ImageDataModel>() {
            @Override
            public void onNext(ImageDataModel baseBean) {
                super.onNext(baseBean);
                getView().getImageData(baseBean);
            }
        });
    }
    //个人通知信息
    public void getPersonMessage(int id,int page,int rows) {
        Api.getApi().getPersonMessage(id,page,rows)
                .compose(callbackOnIOToMainThread())
                .subscribe(new BaseNetSubscriber<NotificationModel>() {
                    @Override
                    public void onNext(NotificationModel baseBean) {
                        super.onNext(baseBean);
                        getView().getPersonMessage(baseBean);
                    }
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        getView().setEmptyState(EmptyState.EMPTY);
                    }
                });
    }
    //个人登录
//    public void getLogin(String loginName, String password) {
//        Api.getApi().getLogin(loginName, password)
//                .compose(callbackOnIOToMainThread())
//                .subscribe(new BaseNetSubscriber<BaseBean>() {
//                    @Override
//                    public void onNext(BaseBean baseBean) {
//                        super.onNext(baseBean);
//                        getView().getLogin(baseBean);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        super.onError(e);
//                        getView().setEmptyState(EmptyState.EMPTY);
//                    }
//                });
//    }
}
