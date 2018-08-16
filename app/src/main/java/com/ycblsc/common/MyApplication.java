package com.ycblsc.common;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreater;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.ycblsc.R;

import cn.jpush.android.api.JPushInterface;
import com.lm.base.library.utils.CacheUtils;
import com.lm.base.library.utils.Utils;

/**
 * Created by Administrator on 2017/11/22 0022.
 */

public class MyApplication extends Application {
    private static MyApplication instance;
    public static String Base_Path = "";

    public static MyApplication getInstance() {
        return instance;
    }

    public String mEasyId="";//便利架ID
    public String mShoppingId="";//体验店ID
    public String shouhuoid="";//收货地址ID

    public String getShouhuoid() {
        return shouhuoid;
    }

    public void setShouhuoid(String shouhuoid) {
        this.shouhuoid = shouhuoid;
    }

    public String getEasyId() {
        return mEasyId;
    }

    public void setEasyId(String mEasyId) {
        this.mEasyId = mEasyId;
    }

    public String getmShoppingId() {
        return mShoppingId;
    }

    public void setmShoppingId(String mShoppingId) {
        this.mShoppingId = mShoppingId;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Base_Path = Utils.getCacheDirectory(this, Environment.DIRECTORY_DOCUMENTS).getAbsolutePath();

        //缓存初始化
        CacheUtils.getInstance().init(CacheUtils.CacheMode.CACHE_MAX,
                Utils.getCacheDirectory(this, Environment.DIRECTORY_DOCUMENTS).getAbsolutePath());
        initJpush();//初始化极光

    }

    public void initJpush() {
        JPushInterface.setDebugMode(false);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);            // 初始化 JPush
        // 通过极光推送，推送了很多通知到客户端时，如果用户不去处理，就会有很多保留在那里。
//        新版本 SDK (v1.3.0) 增加此功能，限制保留的通知条数。默认为保留最近 5 条通知。
        JPushInterface.setLatestNotificationNumber(this, 5);
    }

    //static 代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
            //    layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);//全局设置主题颜色
                return new ClassicsHeader(context);//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreater(new DefaultRefreshFooterCreater() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }

    public static String getBase_Path() {
        return Base_Path;
    }
}
