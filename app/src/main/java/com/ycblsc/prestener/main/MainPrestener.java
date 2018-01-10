package com.ycblsc.prestener.main;

import com.ycblsc.base.BasePresenter;
import com.ycblsc.common.Api;
import com.ycblsc.model.BaseBean;
import com.ycblsc.model.home.HeadListModel;
import com.ycblsc.net.UploadFileRequestBody;
import com.ycblsc.view.IMainView;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

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

    public void getLoginRegiterImage(String name, String gender, String telphone, String address, String icon, String password) {
        File flie = new File(icon);

        UploadFileRequestBody fileRequestBody = new UploadFileRequestBody(flie, new UploadFileRequestBody.ProgressListener() {
            @Override
            public void onProgress(long hasWrittenLen, long totalLen, boolean hasFinish) {
                long l = hasWrittenLen * 100 / totalLen;
                getView().showToast(l + "%");
            }
        });

        // MultipartBody.Part  和后端约定好Key，这里的partName是用image
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", flie.getName(), fileRequestBody);

        Api.getApi().getLoginRegisterImage(name, gender, telphone, address,"", password, body)
                .compose(callbackOnIOToMainThread()).subscribe(new BaseNetSubscriber<BaseBean>() {
            @Override
            public void onNext(BaseBean headListModel) {
                super.onNext(headListModel);
                getView().getLoginRegister(headListModel);
            }
        });

    }

    public RequestBody toRequestBody(String value) {
        return RequestBody.create(MediaType.parse("text/plain"), value);
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
