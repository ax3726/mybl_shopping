package com.ycblsc.common;


import com.ycblsc.model.home.HeadListModel;
import com.ycblsc.model.home.ProductListModel;
import com.ycblsc.model.home.ProuductTypeModel;
import com.ycblsc.model.home.ShopInfoModel;
import com.ycblsc.model.shopping.ImageDataModel;

import java.util.HashMap;

import io.reactivex.Flowable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;


/**
 * Created by lm on 2017/11/22.
 * Description:
 */

public interface ApiService {

    @GET("AppService.aspx?CMD=LoadProductType")
        //获取商品分类信息
    Flowable<ProuductTypeModel> getProductType();


    @GET("AppService.aspx?CMD=LoadProductList")
        //获取商品列表信息
    Flowable<ProductListModel> getProductList(@Query("fenLei") String fenLei
            , @Query("id") String id   //便利架id
            , @Query("isTuiJian") String isTuiJian//是否推荐（1是）
            , @Query("page") String page
            , @Query("rows") String rows
    );

    @GET("AppService.aspx?CMD=LoadShopInfo")
        //获取便利架信息
    Flowable<ShopInfoModel> getShopInfo(@Query("id") String id);

    @GET("AppService.aspx?CMD=LoadData")
        //获取广告位
    Flowable<ImageDataModel> getImageData(@Query("id") String id);//便利架广告(49)、个人中心广告（48）、充值协议（50）、充值说明（51）


    //上传文件
    @Multipart
    @POST("upload")
    Flowable<ResponseBody> upload(@Part("description") RequestBody description,
                                  @Part MultipartBody.Part file,@QueryMap HashMap<String, String> params);




    //下载文件
    @Streaming
    @GET
    Flowable<ResponseBody> download(@Url String url);

    @GET("AppService.aspx?CMD=LoadHeaderList")
    Flowable<HeadListModel> getHeadList();

}
