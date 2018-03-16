package com.ycblsc.common;


import com.ycblsc.model.BaseBean;
import com.ycblsc.model.home.HeadListModel;
import com.ycblsc.model.home.ProductListModel;
import com.ycblsc.model.home.ProuductTypeModel;
import com.ycblsc.model.home.ShopInfoModel;
import com.ycblsc.model.mine.ConsumptionModel;
import com.ycblsc.model.mine.MineRechargeModel;
import com.ycblsc.model.mine.NotificationModel;
import com.ycblsc.model.mine.PersonInfoModel;
import com.ycblsc.model.shopping.ImageDataModel;

import java.util.HashMap;

import io.reactivex.Flowable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
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


    @GET("AppService.aspx?CMD=LoadDict")
        //获取信息填报电话
    Flowable<BaseBean> getDict(@Query("d_Code") String d_Code);

    @POST("AppServicePost.aspx?CMD=Message")
        //获取信息填报
    Flowable<BaseBean> addMessage(@Query("n_Contact") String n_Contact, @Query("n_Name") String n_Name, @Query("n_Note") String n_Note);


    @GET("AppService.aspx?CMD=LoadData")
        //获取广告位
    Flowable<ImageDataModel> getImageData(@Query("id") String id);//便利架广告(49)、个人中心广告（48）、充值协议（50）、充值说明（51）


    //上传文件
    @Multipart
    @POST("upload")
    Flowable<ResponseBody> upload(@Part("description") RequestBody description,
                                  @Part MultipartBody.Part file, @QueryMap HashMap<String, String> params);


    //下载文件
    @Streaming
    @GET
    Flowable<ResponseBody> download(@Url String url);

    @GET("AppService.aspx?CMD=LoadHeaderList")
    Flowable<HeadListModel> getHeadList();

    //注册会员
    @POST("AppServicePost.aspx?CMD=Register")
    Flowable<BaseBean> getLoginRegister(@Query("name") String name,
                                        @Query("gender") String gender,
                                        @Query("telphone") String telphone,
                                        @Query("address") String address,
                                        @Query("icon") String icon,
                                        @Query("password") String password);


    /*  //注册会员 上传图片
      @Multipart
      @POST("AppServicePost.aspx?CMD=Register")
      Flowable<BaseBean> getLoginRegisterImage(@PartMap HashMap<String, RequestBody> params);*/
    //注册会员 上传图片
    @Multipart
    @POST("AppServicePost.aspx?CMD=Register")
    Flowable<BaseBean> getLoginRegisterImage(@Query("name") String name,
                                             @Query("gender") String gender,
                                             @Query("telphone") String telphone,
                                             @Query("address") String address,
                                             @Query("icon") String icon,
                                             @Query("password") String password,
                                             @Part MultipartBody.Part file);

    //发送验证码
    @GET("AppServicePost.aspx?CMD=SendCode")
    Flowable<BaseBean> getSendCode(@Query("telphone") String telphone,
                                   @Query("validName") String validName);

    //个人信息
    @GET("AppService.aspx?CMD=LoadInfo")
    Flowable<PersonInfoModel> getPersonInfo(@Query("id") String id);

    //用户登录
    @GET("AppService.aspx?CMD=Login")
    Flowable<BaseBean> getLogin(@Query("loginName") String loginName,
                                @Query("password") String password);

    //个人通知信息
    @GET("AppService.aspx?CMD=LoadMessage")
    Flowable<NotificationModel> getPersonMessage(@Query("id") String id,
                                                 @Query("page") int page,
                                                 @Query("rows") int rows);

    //充值规则
    @GET("AppService.aspx?CMD=LoadMoneyOrder")
    Flowable<MineRechargeModel> getMoneyOrder();

    //消费记录
    @GET("AppService.aspx?CMD=LoadOrderList")
    Flowable<ConsumptionModel> getConsumptionOrderList(@Query("id") String id,
                                                       @Query("page") int page,
                                                       @Query("rows") int rows);

    //修改密码
    @POST("AppServicePost.aspx?CMD=UpdatePassword")
    Flowable<BaseBean> getUpdatePassword(@Query("id") String id,
                                         @Query("passwordNew") String passwordNew,
                                         @Query("passwordOld") String passwordOld);

    //修改手机号
    @POST("AppServicePost.aspx?CMD=UpdateTelphone")
    Flowable<BaseBean> getUpdatePhone(@Query("id") String id,
                                      @Query("telphone") String passwordNew);

    //修改昵称
    @POST("AppServicePost.aspx?CMD=UpdateName")
    Flowable<BaseBean> getUpdateNiceName(@Query("id") String id,
                                         @Query("name") String passwordNew);

    //修改收货地址
    @POST("AppServicePost.aspx?CMD=UpdateAddress")
    Flowable<BaseBean> getUpdateAddress(@Query("id") String id,
                                        @Query("address") String passwordNew);

    //修改头像
    @Multipart
    @POST("AppServicePost.aspx?CMD=UpdateIcon")
    Flowable<BaseBean> getUpdateImage(@Query("id") String id,
                                      @Part MultipartBody.Part file);

    //找回密码
    @GET("AppService.aspx?CMD=GetPassword")
    Flowable<BaseBean> getGetPassword(@Query("telphone") String telphone);

    //余额支付
    @POST("AppServicePost.aspx?CMD=Order")
    Flowable<BaseBean> getPay(@Query("id") String id
            , @Query("orderType") String orderType
            , @Query("proCount") String proCount
            , @Query("proId") String proId
            , @Query("proPrice") String proPrice
            , @Query("shopid") String shopid);
}
