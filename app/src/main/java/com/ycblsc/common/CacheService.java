package com.ycblsc.common;

import com.ycblsc.model.UserModel;

import ml.gsy.com.library.utils.CacheUtils;

/**
 * Created by Administrator on 2017/11/22 0022.
 * 缓存统一处理服务类
 */

public class CacheService {

    private static CacheService mCacheService = null;

    private CacheService() {

    }

    public static CacheService getIntance() {
        if (mCacheService == null) {
            return new CacheService();
        }
        return mCacheService;
    }

    /**
     * 读取用户信息
     *
     * @return
     */
    public UserModel getUser() {
        return (UserModel) CacheUtils.getInstance().loadCache(Constant.USER_INFO);
    }

    /**
     * 读取用户id
     *
     * @return
     */
    public String getUserId() {
        UserModel userModel = (UserModel) CacheUtils.getInstance().loadCache(Constant.USER_INFO);
        if (userModel != null) {
            return userModel.getId();
        }
        return "";
    }

    /**
     * 是否登录
     *
     * @return
     */
    public boolean isLogin() {
        return CacheUtils.getInstance().loadCache(Constant.USER_INFO) != null;
    }

    /**
     * 缓存用户信息
     *
     * @param userInfo
     */
    public void setUser(UserModel userInfo) {
        CacheUtils.getInstance().saveCache(Constant.USER_INFO, userInfo);
    }

    /**
     * 清除用户信息
     *
     * @param userInfo
     */
    public void clearUser(UserModel userInfo) {
        CacheUtils.getInstance().removeCache(Constant.USER_INFO);
    }
}
