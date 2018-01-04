package com.ycblsc.base;


import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.ycblsc.net.ex.ApiException;
import com.ycblsc.net.ex.ResultException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.adapter.rxjava.HttpException;


/**
 * Created by lm on 2017/11/22.
 * Description:
 */

public class BasePresenter<V extends BaseView> implements IBasePresenter<V> , LifecycleProvider<ActivityEvent> {


    protected V mView;

    @Override
    public void attachView(V view) {
        mView = view;
    }

    @Override
    public void detachView() {
        if (mView != null) {
            mView = null;
        }
    }

    /**
     * 检测是否关联
     *
     * @return
     */
    protected boolean isViewAttach() {
        return mView != null;
    }

    /**
     * 获取接口
     *
     * @return
     */
    protected V getView() {
        return mView;
    }




    public <T> ObservableTransformer<T, T> callbackOnIOToMainThread() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(@NonNull Observable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                       // .retryWhen(RetryWithDelayFunction.create())
                        .filter(t -> BasePresenter.this.isViewAttach())
                        .compose(BasePresenter.this.bindToLifecycle());
            }


        };
    }

    @NonNull
    @Override
    public Observable<ActivityEvent> lifecycle() {
        return  getView().lifecycle();
    }

    @NonNull
    @Override
    public <T> LifecycleTransformer<T> bindUntilEvent(ActivityEvent event) {
        return getView().bindUntilEvent(event);
    }

    @NonNull
    @Override
    public <T> LifecycleTransformer<T> bindToLifecycle() {
        return getView().bindToLifecycle();
    }

    public abstract class BaseNetObserver<T> implements Observer<T> {


        @Override
        public void onSubscribe(@NonNull Disposable d) {
            if (isViewAttach()) {
                // getView().showProgress();
            }
        }

        @Override
        public void onComplete() {
            if (isViewAttach()) {
                getView().hideWaitDialog();
            }
        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
            if (!isViewAttach()) {
                return;
            }
            getView().hideWaitDialog();
            if (e instanceof HttpException) {
                getView().showToast("网络错误");
            } else if (e instanceof ApiException) {
                getView().showToast("Aip异常");
            } else if (e instanceof SocketTimeoutException) {
                getView().showToast("连接服务器超时");
            } else if (e instanceof ConnectException) {
                getView().showToast("未能连接到服务器");
            } else if (e instanceof ResultException) {
                getView().showToast(e.getMessage());
            } else {
                getView().showToast("未知错误");
            }
        }

        @Override
        public void onNext(T t) {

        }
    }


}
