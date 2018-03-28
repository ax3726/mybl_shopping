package com.ycblsc.prestener.mine;

import com.ycblsc.base.BaseFragmentPresenter;
import com.ycblsc.base.BasePresenter;
import com.ycblsc.base.EmptyState;
import com.ycblsc.common.Api;
import com.ycblsc.model.BaseBean;
import com.ycblsc.model.mine.NotificationModel;
import com.ycblsc.model.mine.PersonInfoModel;
import com.ycblsc.model.shopping.ImageDataModel;
import com.ycblsc.net.UploadFileRequestBody;
import com.ycblsc.view.IMineView;

import java.io.File;

import okhttp3.MultipartBody;

/**
 * Created by Administrator on 2017/12/25 0025.
 */

public class MinePrestener extends BaseFragmentPresenter<IMineView> {
    //个人信息
    public void getPersonInfo(String id) {
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
    public void getPersonMessage(String id,int page,int rows) {
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
    //修改头像
    public void getUpdateImage(String id, String icon) {
        File flie = new File(icon);

        UploadFileRequestBody fileRequestBody = new UploadFileRequestBody(flie, new UploadFileRequestBody.ProgressListener() {
            @Override
            public void onProgress(long hasWrittenLen, long totalLen, boolean hasFinish) {
                long l = hasWrittenLen * 100 / totalLen;
//                getView().showToast(l + "%");
            }
        });
        // MultipartBody.Part  和后端约定好Key，这里的partName是用image
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", flie.getName(), fileRequestBody);

        Api.getApi().getUpdateImage(id,body)
                .compose(callbackOnIOToMainThread())
                .subscribe(new BaseNetSubscriber<BaseBean>() {
                    @Override
                    public void onNext(BaseBean baseBean) {
                        super.onNext(baseBean);
                        getView().getUpdateImage(baseBean);
                    }
                });

    }
}
