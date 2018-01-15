package com.ycblsc.view;

import com.ycblsc.base.BaseFragmentView;
import com.ycblsc.model.BaseBean;
import com.ycblsc.model.mine.NotificationModel;
import com.ycblsc.model.mine.PersonInfoModel;
import com.ycblsc.model.shopping.ImageDataModel;

/**
 * Created by Administrator on 2018/1/4.
 */

public interface IMineView extends BaseFragmentView {
    void getPersonInfo(PersonInfoModel personInfoModel);
    void getImageData(ImageDataModel model);
    void getPersonMessage(NotificationModel model);
    void getUpdateImage(BaseBean baseBean);
}
