package com.ycblsc.prestener.home;

import com.ycblsc.base.BaseFragmentPresenter;
import com.ycblsc.base.EmptyState;
import com.ycblsc.common.Api;
import com.ycblsc.model.home.ProductListModel;
import com.ycblsc.model.home.ProuductTypeModel;
import com.ycblsc.model.home.ShopInfoModel;
import com.ycblsc.net.UploadFileRequestBody;
import com.ycblsc.view.IHomeView;

import java.io.File;
import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2017/12/25 0025.
 */

public class HomePrestener extends BaseFragmentPresenter<IHomeView> {
    public void getProductType() {
        Api.getApi().getProductType()
                .compose(callbackOnIOToMainThread())
                .subscribe(new BaseNetSubscriber<ProuductTypeModel>() {
                    @Override
                    public void onNext(ProuductTypeModel baseBean) {
                        super.onNext(baseBean);
                        getView().getProuductType(baseBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        getView().setEmptyState(EmptyState.EMPTY);
                    }
                });
    }

    public void getProductList(String fenLei
            , String id   //便利架id
            , String isTuiJian//是否推荐（1是）
            , String page
            , String rows) {
        Api.getApi().getProductList(fenLei, id, isTuiJian, page, rows)
                .compose(callbackOnIOToMainThread())
                .subscribe(new BaseNetSubscriber<ProductListModel>() {
                    @Override
                    public void onNext(ProductListModel baseBean) {
                        super.onNext(baseBean);
                        getView().getProuductList(baseBean);
                    }
                });
    }

    public void getShopInfo(String id) {
        Api.getApi().getShopInfo(id)
                .compose(callbackOnIOToMainThread())
                .subscribe(new BaseNetSubscriber<ShopInfoModel>() {
                    @Override
                    public void onNext(ShopInfoModel shopInfoModel) {
                        super.onNext(shopInfoModel);
                        getView().getShopInfo(shopInfoModel);
                    }
                });
    }


    /**
     * 文件上传
     */
    public void upLoadFile() {
        File file = new File("filepath");
        UploadFileRequestBody fileRequestBody = new UploadFileRequestBody(file, new UploadFileRequestBody.ProgressListener() {
            @Override
            public void onProgress(long hasWrittenLen, long totalLen, boolean hasFinish) {

            }
        });




        // MultipartBody.Part  和后端约定好Key，这里的partName是用image
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("image", file.getName(), fileRequestBody);
        HashMap<String, String> params = new HashMap<>();
        params.put("img","img");

        Api.getApi().upload(fileRequestBody, body, params)
                .compose(callbackOnIOToMainThread()).subscribe(new BaseNetSubscriber<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {
                super.onNext(responseBody);


            }
        });

    }

  /*  *//**
     * 文件下载
     *//*
    public void downLoadFile() {
        String all_url = "https://github.com/wzgiceman/RxjavaRetrofitDemo-master/archive/master.zip";//全路径
        Uri url = Uri.parse(all_url);
        //拆分两个
        String base_url = "https://github.com/";
        String jie_url = "wzgiceman/RxjavaRetrofitDemo-master/archive/master.zip";
        Api.getDownLoadApi(base_url, (total, progress) -> {
            getView().downProgress(total, progress * 100 / total);
        }).download(jie_url)
                .subscribeOn(Schedulers.io())
                .subscribe(new BaseNetSubscriber<ResponseBody>() {
                    @Override
                    public void onNext(ResponseBody responseBody) {
                        super.onNext(responseBody);
                        InputStream is = null;
                        byte[] buf = new byte[2048];
                        int len;
                        FileOutputStream fos = null;
                        try {
                            is = responseBody.byteStream();
                            File dir = new File(MyApplication.Base_Path + "/zip");
                            if (!dir.exists()) {
                                dir.mkdirs();
                            }
                            File file = new File(dir, "RxjavaRetrofitDemo-master-master.zip");
                            fos = new FileOutputStream(file);
                            while ((len = is.read(buf)) != -1) {
                                fos.write(buf, 0, len);
                            }
                            fos.flush();
                            getView().showToast("下载完毕!");
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                if (is != null) is.close();
                                if (fos != null) fos.close();
                            } catch (IOException e) {
                                Log.e("saveFile", e.getMessage());
                            }
                        }
                    }
                });
    } */

}
