package com.ycblsc.view;

import com.ycblsc.base.BaseFragmentView;
import com.ycblsc.base.BaseView;
import com.ycblsc.model.BaseBean;
import com.ycblsc.model.home.HeadListModel;

/**
 * Created by Administrator on 2018/1/4.
 */

public interface IMainView extends BaseView {
    //头像列表
    void getHeadList(HeadListModel headListModel);
    //注册会员
    void getLoginRegister(BaseBean baseBean);
    //发送验证码
    void getSendCode(BaseBean baseBean);
}
