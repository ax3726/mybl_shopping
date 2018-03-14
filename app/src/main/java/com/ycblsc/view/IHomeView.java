package com.ycblsc.view;

import com.ycblsc.base.BaseFragmentView;
import com.ycblsc.model.home.ProductListModel;
import com.ycblsc.model.home.ProuductTypeModel;
import com.ycblsc.model.home.ShopInfoModel;
import com.ycblsc.model.shopping.ImageDataModel;

/**
 * Created by Administrator on 2018/1/2 0002.
 */

public interface IHomeView extends BaseFragmentView {

    void getProuductType(ProuductTypeModel typeModel);
    void getProuductList(ProductListModel typeModel);
    void getShopInfo(ShopInfoModel model);
    void getImageData(ImageDataModel model);
    void getRecommend(ProductListModel model);
}
