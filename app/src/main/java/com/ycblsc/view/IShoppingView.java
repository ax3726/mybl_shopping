package com.ycblsc.view;

import com.ycblsc.base.BaseView;
import com.ycblsc.model.home.ProductListModel;
import com.ycblsc.model.home.ProuductTypeModel;
import com.ycblsc.model.shopping.ImageDataModel;

/**
 * Created by Administrator on 2018/1/2 0002.
 */

public interface IShoppingView extends BaseView {

    void getProuductType(ProuductTypeModel typeModel);
    void getProuductList(ProductListModel typeModel);
    void getImageData(ImageDataModel model);

}
