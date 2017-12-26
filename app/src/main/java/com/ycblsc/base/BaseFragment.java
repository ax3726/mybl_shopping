package com.ycblsc.base;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.trello.rxlifecycle.ActivityEvent;
import com.trello.rxlifecycle.LifecycleTransformer;
import com.ycblsc.R;
import com.ycblsc.databinding.WidgetLayoutEmptyBinding;
import com.ycblsc.net.RetryWithDelayFunc1;
import com.ycblsc.net.ex.ApiException;
import com.ycblsc.net.ex.ResultException;
import com.ycblsc.widget.TitleBarLayout;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by lm on 2017/11/22.
 * Description:
 */

public abstract class BaseFragment<P extends BasePresenter, B extends ViewDataBinding> extends Fragment implements BaseView {

    /**
     * Fragment根视图
     */
    protected View mFragmentRootView;
    protected P mPresenter;
    protected B mBinding;
    protected Activity aty;
    /**
     * 加载进度
     */
    private LoadingDialog mLoadingDialog;
    protected TitleBarLayout mTitleBarLayout = null;//头部控件
    protected StateModel mStateModel = new StateModel();//

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        aty = getActivity();
        mFragmentRootView = inflaterView(inflater, container, savedInstanceState);

        if (isPrestener()) {
            mPresenter = createPresenter();
            mPresenter.attachView(this);
        }
        initTitleBar();
        initView(savedInstanceState);
        initData();
        initEvent();
        return mFragmentRootView;
    }

    /**
     * 加载View
     *
     * @param inflater  LayoutInflater
     * @param container ViewGroup
     * @param bundle    Bundle
     * @return
     */
    protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        mBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        if (isTitleBar()) {
            mTitleBarLayout = new TitleBarLayout(aty);
            LinearLayout lly = new LinearLayout(aty);
            lly.setOrientation(LinearLayout.VERTICAL);
            lly.addView(mTitleBarLayout);
            FrameLayout fly = new FrameLayout(aty);
            fly.addView(mBinding.getRoot());
            WidgetLayoutEmptyBinding emptyBinding = DataBindingUtil.inflate(inflater, R.layout.widget_layout_empty, null, false);
            emptyBinding.setStateModel(mStateModel);
            fly.addView(emptyBinding.getRoot());
            lly.addView(fly);
            mTitleBarLayout.setLeftListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    aty.finish();
                }
            });
            return lly;
        } else {
            FrameLayout fly = new FrameLayout(aty);
            fly.addView(mBinding.getRoot());
            WidgetLayoutEmptyBinding emptyBinding = DataBindingUtil.inflate(inflater, R.layout.widget_layout_empty, null, false);
            emptyBinding.setStateModel(mStateModel);
            fly.addView(emptyBinding.getRoot());
            return fly;
        }

    }


    /**
     * @param url   图片路径
     * @param img   加载图片的控件
     * @param parms 第一个  默认图片  第二个 加载错误图片
     */
    protected void loadImag(String url, ImageView img, int... parms) {
        DrawableTypeRequest<String> load = Glide.with(aty).load(url);
        if (parms.length > 0) {
            for (int i = 0; i < parms.length; i++) {
                if (i == 0) {
                    load.placeholder(parms[0]);
                } else if (i == 2) {
                    load.error(parms[1]);
                }
            }
        }
        load.into(img);
    }


    protected void startActivity(Class<?> cls) {
        startActivity(new Intent(aty, cls));
    }


    protected void initEvent() {

    }

    protected void initView(Bundle savedInstanceState) {

    }

    protected void initTitleBar() {

    }

    protected void initData() {

    }


    /**
     * 是否显示头部
     *
     * @return
     */
    protected boolean isTitleBar() {
        return false;
    }

    /**
     * 是否加载Prestener
     *
     * @return
     */
    protected boolean isPrestener() {
        return true;
    }

    @Override
    public void showToast(final String s) {
        aty.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(aty, s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void showToast(final int id) {

        aty.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(aty, getResources().getString(id), Toast.LENGTH_SHORT).show();
            }
        });

    }


    protected abstract P createPresenter();

    /**
     * 显示（默认不可取消）
     *
     * @param message 消息
     * @return
     */

    public LoadingDialog showWaitDialog(String message) {
        return showWaitDialog(message, true, null);
    }

    @Override
    public LoadingDialog showWaitDialog(boolean isCancel, DialogInterface.OnCancelListener cancelListener) {
        return showWaitDialog("", isCancel, cancelListener);
    }


    /**
     * 显示（默认不可取消）
     *
     * @return
     */

    public LoadingDialog showWaitDialog() {
        return showWaitDialog("", true, null);
    }

    /**
     * 显示
     *
     * @param message        消息
     * @param isCancel       是否可取消
     * @param cancelListener 取消监听
     * @return
     */

    public LoadingDialog showWaitDialog(String message, boolean isCancel, DialogInterface.OnCancelListener cancelListener) {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(aty, message);
        }

        mLoadingDialog.setCancelable(isCancel);
        if (isCancel == true && cancelListener != null) {
            mLoadingDialog.setOnCancelListener(cancelListener);
        }

        if (!mLoadingDialog.isShowing()) {
            mLoadingDialog.show();
        }

        return mLoadingDialog;
    }
    /***************************************************************************
     * 弹出窗方法
     ***************************************************************************/
    /**
     * 隐藏
     */
    public void hideWaitDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (isPrestener()) {
            mPresenter.detachView();
        }
    }


    public abstract class BaseNetSubscriber<T> extends Subscriber<T> {
        @Override
        public void onStart() {
            super.onStart();
            if (aty != null) {
                // getView().showProgress();
            }
        }

        @Override
        public void onCompleted() {
            if (aty != null) {
                hideWaitDialog();
            }
        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
            if (aty != null) {
                return;
            }
            hideWaitDialog();
            if (e instanceof HttpException) {
                showToast("网络错误");
            } else if (e instanceof ApiException) {
                showToast("Aip异常");
            } else if (e instanceof SocketTimeoutException) {
                showToast("连接服务器超时");
            } else if (e instanceof ConnectException) {
                showToast("未能连接到服务器");
            } else if (e instanceof ResultException) {
                showToast(e.getMessage());
            } else {
                showToast("未知错误");
            }
        }

        @Override
        public void onNext(T t) {

        }
    }

    public <T> Observable.Transformer<T, T> callbackOnIOToMainThread() {
        return tObservable -> (Observable<T>) tObservable.subscribeOn(Schedulers.io())
                .retryWhen(RetryWithDelayFunc1.create())
                .filter(t -> aty != null).observeOn(AndroidSchedulers.mainThread()).compose(bindToLifecycle());
    }

    @Override
    public Observable<ActivityEvent> lifecycle() {
        return null;
    }

    @Override
    public <T> LifecycleTransformer<T> bindUntilEvent(ActivityEvent event) {
        return null;
    }

    @Override
    public <T> LifecycleTransformer<T> bindToLifecycle() {
        return null;
    }
}
