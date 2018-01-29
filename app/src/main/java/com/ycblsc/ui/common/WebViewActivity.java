package com.ycblsc.ui.common;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.lm.base.library.widget.webview.AppProgressWebView;
import com.ycblsc.R;
import com.ycblsc.base.BaseActivity;
import com.ycblsc.base.BasePresenter;
import com.ycblsc.common.MyApplication;
import com.ycblsc.databinding.ActivityWebviewLayoutBinding;


/**
 * 网页模块
 *
 * @author Administrator
 */
public class WebViewActivity extends BaseActivity<BasePresenter, ActivityWebviewLayoutBinding>  {


    /**
     * 视频全屏参数
     */
    protected static final FrameLayout.LayoutParams COVER_SCREEN_PARAMS = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    private View customView;
    private FrameLayout fullscreenContainer;
    private AppProgressWebView.WebChromeClient.CustomViewCallback customViewCallback;
    private String mUrl = "";


    @Override
    public int getLayoutId() {
        return R.layout.activity_webview_layout;
    }


    @Override
    protected void initTitleBar() {
        super.initTitleBar();

    }

    @Override
    public void initData() {
        super.initData();

        init();
    }

    @Override
    protected boolean isPrestener() {
        return false;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    protected void init() {

        mUrl = getIntent().getStringExtra("url");
        mBinding.appProgresswebview.setProgressHandler(mHandler);
        mBinding.appProgresswebview.setTitleText(mTitleBarLayout.getTitleView());

        mBinding.appProgresswebview.loadUrl(mUrl);
        mBinding.appProgresswebview.setWebChromeClient(new AppProgressWebView.WebChromeClient() {
            /*** 视频播放相关的方法 **/
            @Override
            public View getVideoLoadingProgressView() {
                FrameLayout frameLayout = new FrameLayout(WebViewActivity.this);
                frameLayout.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
                return frameLayout;
            }

            @Override
            public void onShowCustomView(View view, WebChromeClient.CustomViewCallback callback) {
                showCustomView(view, callback);
            }

            @Override
            public void onHideCustomView() {
                hideCustomView();
            }
        });
    }


    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case -1:
                    //加载网页失败
                    Toast.makeText(MyApplication.getInstance(), "加载网页失败", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    //加载成功
                    break;

            }
            super.handleMessage(msg);
        }

    };

    /**
     * 获取返回，看看是否是服务器错误，报错404,403等！
     *
     * @param code
     */
    @JavascriptInterface
    public void getWebViewCode(String code) {
        if (!code.isEmpty()) {
            if (code.contains("服务器错误")) {
                mHandler.sendEmptyMessage(-1);
            }
        }
    }

    @Override
    protected void onDestroy() {
        mBinding.appProgresswebview.setVisibility(View.GONE);
        mBinding.appProgresswebview.reload();
        mBinding.appProgresswebview.destroy();
        super.onDestroy();


    }

    /**
     * 视频播放全屏
     **/
    private void showCustomView(View view, AppProgressWebView.WebChromeClient.CustomViewCallback callback) {
        // if a view already exists then immediately terminate the new one
        if (customView != null) {
            callback.onCustomViewHidden();
            return;
        }

        WebViewActivity.this.getWindow().getDecorView();

        FrameLayout decor = (FrameLayout) getWindow().getDecorView();
        fullscreenContainer = new FullscreenHolder(WebViewActivity.this);
        fullscreenContainer.addView(view, COVER_SCREEN_PARAMS);
        decor.addView(fullscreenContainer, COVER_SCREEN_PARAMS);
        customView = view;
        setStatusBarVisibility(false);
        customViewCallback = callback;
    }

    /**
     * 隐藏视频全屏
     */
    private void hideCustomView() {
        if (customView == null) {
            return;
        }

        setStatusBarVisibility(true);
        FrameLayout decor = (FrameLayout) getWindow().getDecorView();
        decor.removeView(fullscreenContainer);
        fullscreenContainer = null;
        customView = null;
        customViewCallback.onCustomViewHidden();
        mBinding.appProgresswebview.setVisibility(View.VISIBLE);
    }

    /**
     * 全屏容器界面
     */
    static class FullscreenHolder extends FrameLayout {

        public FullscreenHolder(Context ctx) {
            super(ctx);
            setBackgroundColor(ctx.getResources().getColor(android.R.color.black));
        }

        @Override
        public boolean onTouchEvent(MotionEvent evt) {
            return true;
        }
    }

    private void setStatusBarVisibility(boolean visible) {
        int flag = visible ? 0 : WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setFlags(flag, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            /** 回退键 事件处理 优先级:视频播放全屏-网页回退-关闭页面 */
            if (customView != null) {
                hideCustomView();
            } else if (mBinding.appProgresswebview.canGoBack()) {
                mBinding.appProgresswebview.goBack();
            } else {

                finish();
            }
        }
        return false;
    }


}
