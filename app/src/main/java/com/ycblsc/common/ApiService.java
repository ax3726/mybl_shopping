package com.ycblsc.common;


import com.ycblsc.model.BaseBean;
import com.ycblsc.model.SelectAddressModel;
import com.ycblsc.model.WEXModel;
import com.ycblsc.model.home.HeadListModel;
import com.ycblsc.model.home.ProductListModel;
import com.ycblsc.model.home.ProuductTypeModel;
import com.ycblsc.model.home.ShopInfoModel;
import com.ycblsc.model.mine.ConsumptionModel;
import com.ycblsc.model.mine.MineRechargeModel;
import com.ycblsc.model.mine.NotificationModel;
import com.ycblsc.model.mine.PersonInfoModel;
import com.ycblsc.model.shopping.GoodsInfoModel;
import com.ycblsc.model.shopping.ImageDataModel;
import com.ycblsc.model.shopping.NewAddressModel;
import com.ycblsc.model.shopping.ShoppingInfoModel;
import com.ycblsc.model.shopping.ShoppingOrderListModel;
import com.ycblsc.model.shopping.TimeAddressModel;

import java.util.HashMap;

import io.reactivex.Flowable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

import static com.ycblsc.common.Constant.API_NO_SIGN;
import static com.ycblsc.common.Constant.NO_SIGN;


/**
 * Created by lm on 2017/11/22.
 * Description:
 */

public interface ApiService {

    @GET("AppService.aspx?CMD=LoadProductType")
        //获取商品分类信息 、新增参数shopId
    Flowable<ProuductTypeModel> getProductType(@Query("shopId") String shopId); //便利架id;


    @GET("AppService2.aspx?CMD=LoadProductType")
        //获取商品分类信息 、新增参数shopId
    Flowable<ProuductTypeModel> getProductType2(@Query("shopId") String shopId); //便利架id;

    @GET("AppService.aspx?CMD=LoadProductList")
        //获取商品列表信息
    Flowable<ProductListModel> getProductList(@Query("fenLei") String fenLei
            , @Query("id") String id   //便利架id
            , @Query("isTuiJian") String isTuiJian//是否推荐（1是）
            , @Query("page") String page
            , @Query("rows") String rows
    );

    @GET("AppService2.aspx?CMD=LoadProductList")
        //获取商品列表信息
    Flowable<ProductListModel> getProductList2(@Query("fenLei") String fenLei
            , @Query("id") String id   //便利架id
            , @Query("isTuiJian") String isTuiJian//是否推荐（1是）
            , @Query("name") String name//
            , @Query("page") String page
            , @Query("rows") String rows
    );

    @GET("AppService.aspx?CMD=LoadShopInfo")
        //获取便利架信息
    Flowable<ShopInfoModel> getShopInfo(@Query("id") String id);

    @GET("AppService2.aspx?CMD=LoadShopInfo")
        //获取商城信息
    Flowable<ShoppingInfoModel> getShopInfo2(@Query("id") String id);

    @GET("AppService.aspx?CMD=LoadDict")
        //获取信息填报电话
    Flowable<BaseBean> getDict(@Query("d_Code") String d_Code);

    @POST("AppServicePost.aspx?CMD=Message")
        //获取信息填报
    Flowable<BaseBean> addMessage(@Query("n_Contact") String n_Contact, @Query("n_Name") String n_Name, @Query("n_Note") String n_Note);


    @Headers({API_NO_SIGN + "no_sign_parm"})
    @GET("AppService.aspx?CMD=LoadData")
        //获取广告位
    Flowable<ImageDataModel> getImageData(@Query("id") String id);//便利架广告(49)、个人中心广告（48）、充值协议（50）、充值说明（51）、注册说明（52）  体验店广告（54）


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

    //余额支付
    @POST("AppServicePost.aspx?CMD=Order")
    Flowable<WEXModel> getWexPay(@Query("id") String id
            , @Query("orderType") String orderType
            , @Query("proCount") String proCount
            , @Query("proId") String proId
            , @Query("proPrice") String proPrice
            , @Query("shopid") String shopid);

    //在线充值
    @POST("AppServicePost.aspx?CMD=InsertMoneyData")
    Flowable<BaseBean> getRechargePay(@Query("id") String id
            , @Query("tf_Money") String tf_Money
            , @Query("orderType") String orderType);

    //在线充值（微信支付）
    @POST("AppServicePost.aspx?CMD=InsertMoneyData")
    Flowable<WEXModel> getRechargePay1(@Query("id") String id
            , @Query("tf_Money") String tf_Money
            , @Query("orderType") String orderType);

    //获取商品详细信息
    @POST("AppService2.aspx?CMD=LoadProductInfo")
    Flowable<GoodsInfoModel> getLoadProductInfo(@Query("id") String id, @Query("shopId") String shopId);

    //限时达服务说明
    @GET("AppService2.aspx?CMD=GetNote")
    Flowable<TimeAddressModel> getGetNote(@Query("d_Code") String d_Code); //serviceNote;

    //获取个人收货地址
    @GET("AppService2.aspx?CMD=LoadMemberAddressData")
    Flowable<SelectAddressModel> getLoadMemberAddressData(@Query("id") String id);

    //新增地址
    @POST("AppServicePost2.aspx?CMD=InsertMemberAddressData")
    Flowable<BaseBean> getInsertMemberAddressData(@Query("b_addr_qu") String b_addr_qu
            , @Query("b_addr_sheng") String b_addr_sheng
            , @Query("b_addr_shi") String b_addr_shi
            , @Query("id") String id
            , @Query("s_name") String s_name
            , @Query("s_weizhi") String s_weizhi
            , @Query("telphone") String telphone);

    //获取省
    @GET("AppService2.aspx?CMD=LoadDict")
    Flowable<NewAddressModel> getLoadDict(@Query("d_Code") String d_Code
            , @Query("isTrue") String isTrue);

    //获取市
    @GET("AppService2.aspx?CMD=LoadDict")
    Flowable<NewAddressModel> getLoadDictCity(@Query("d_Code") String d_Code
            , @Query("isTrue") String isTrue);

    //获取县
    @GET("AppService2.aspx?CMD=LoadDict")
    Flowable<NewAddressModel> getLoadDictCounty(@Query("d_Code") String d_Code
            , @Query("isTrue") String isTrue);

    //获取体验店id
    @GET("AppService2.aspx?CMD=LoadShopIdByAddress")
    Flowable<BaseBean> getLoadShopIdByAddress(@Query("id") int id);


    //获取限时达订单列表
    @GET("AppService2.aspx?CMD=LoadOrderListFull")
    Flowable<ShoppingOrderListModel> getLoadOrderList(@Query("customId") String id, @Query("page") int page, @Query("rows") int rows, @Query("status") String status);

    //二期支付接口
    //余额/支付宝支付
    @POST("AppServicePost2.aspx?CMD=Order")
    Flowable<BaseBean> getPay2(@Query("id") String id
            , @Query("orderType") String orderType
            , @Query("proCount") String proCount
            , @Query("proId") String proId
            , @Query("proPrice") String proPrice
            , @Query("shopid") String shopid
            , @Query("shouhuoid") String shouhuoid);

    //微信支付
    @POST("AppServicePost2.aspx?CMD=Order")
    Flowable<WEXModel> getWexPay2(@Query("id") String id
            , @Query("orderType") String orderType
            , @Query("proCount") String proCount
            , @Query("proId") String proId
            , @Query("proPrice") String proPrice
            , @Query("shopid") String shopid
            , @Query("shouhuoid") String shouhuoid);

    //确认收货
    @POST("AppServicePost2.aspx?CMD=GetOrder")
    Flowable<BaseBean> confirmOrder(@Query("id") String id, @Query("orderId") int orderId);

    //限时达匹配不到数据显示  参数falseNote
    @POST("AppService2.aspx?CMD=GetNote")
    Flowable<BaseBean> GetNote(@Query("d_Code") String d_Code);

    //选择地址确认返回
    @POST("AppServicePost2.aspx?CMD=SetAddrByDefault")
    Flowable<BaseBean> GetSetAddrByDefault(@Query("defaultAddrId") int defaultAddrId
            , @Query("id") String id);
}
