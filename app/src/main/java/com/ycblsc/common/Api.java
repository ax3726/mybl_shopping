package com.ycblsc.common;


import com.ycblsc.net.DownloadResponseBody;
import com.ycblsc.net.LoggerInterceptor;
import com.ycblsc.utils.MD5;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017/9/21.
 */

public class Api {

    private static Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJava2CallAdapterFactory.create();
    private static ApiService apiService;


    public static ApiService getApi() {
        if (apiService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(getOkHttpClient())
                    .baseUrl(Link.SEREVE)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            apiService = retrofit.create(ApiService.class);
        }
        return apiService;
    }

    public static String getKeyStr(Map<String, String> params) {
        List<Map.Entry<String, String>> list =
                new ArrayList<Map.Entry<String, String>>(params.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, String>>() {
            public int compare(Map.Entry<String, String> o1,
                               Map.Entry<String, String> o2) {
                return (o1.getKey().compareTo(o2.getKey()));
            }
        });
        String signStr = "gzqnkj";
        for (Map.Entry<String, String> map : list) {
            signStr = signStr + map.getKey() + map.getValue();
        }
        signStr = signStr + "gzqnkj";
        return MD5.toMD5Sign(signStr);

    }


    public static OkHttpClient getOkHttpClient(DownloadResponseBody.DownLoadListener... downLoadListener) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
               .addInterceptor(new LoggerInterceptor("msg", true))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .addInterceptor(chain -> {//添加公共信息
                    Request originalRequest = chain.request();
                    HashMap<String, String> rootMap = new HashMap<>();
                    //get请求的封装
                    if (originalRequest.method().equals("GET")) {
                        //获取到请求地址api
                        HttpUrl httpUrlurl = originalRequest.url();
                        //通过请求地址(最初始的请求地址)获取到参数列表
                        Set<String> parameterNames = httpUrlurl.queryParameterNames();
                        for (String key : parameterNames) {  //循环参数列表
                            if (!"CMD".equals(key))
                                rootMap.put(key, httpUrlurl.queryParameter(key));

                        }
                    } else {
                        if (originalRequest.body() instanceof FormBody) {
                            FormBody body = (FormBody) originalRequest.body();
                            for (int i = 0; i < body.size(); i++) {
                                rootMap.put(body.encodedName(i), body.encodedValue(i));
                            }
                        }
                    }
                    String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
                    rootMap.put("appKey", "gzqnkj");
                    rootMap.put("_timestamp", timestamp);
                    rootMap.put("appSecret", "gzqnkj123456");
                    // 添加新的参数
                    HttpUrl.Builder authorizedUrlBuilder = originalRequest.url()
                            .newBuilder()
                            .scheme(originalRequest.url().scheme())
                            .host(originalRequest.url().host())
                            .addQueryParameter("appKey", "gzqnkj")
                            .addQueryParameter("_timestamp", timestamp)
                            .addQueryParameter("_sign", getKeyStr(rootMap));


                    // 新的请求+请求头部
                    Request newRequest = originalRequest.newBuilder()
                            //  .header("Authorization", "token")
                            .method(originalRequest.method(), originalRequest.body())
                            .url(authorizedUrlBuilder.build())
                            .build();


                    return chain.proceed(newRequest);
                });
        if (downLoadListener.length > 0) {
            builder.addNetworkInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Response originalResponse = chain.proceed(chain.request());
                    DownloadResponseBody downloadResponseBody = new DownloadResponseBody(originalResponse.body(), downLoadListener[0]);
                    return originalResponse.newBuilder().body(downloadResponseBody).build();
                }
            });
        }
        return builder.build();
    }

    public static ApiService getDownLoadApi(String url, final DownloadResponseBody.DownLoadListener downLoadListener) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(getOkHttpClient(downLoadListener))
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(rxJavaCallAdapterFactory)
                .build();

        return retrofit.create(ApiService.class);
    }
}
