package com.ycblsc.utils;

import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;


import com.ycblsc.R;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by litie on 2017/10/31.
 */

public class UIUtil {
    /**
     * 通用获取验证码UI
     *
     * @param context
     * @param textView
     */
    public static void setCodeTextView(Context context, TextView textView) {
        Toast.makeText(context,"发送成功",Toast.LENGTH_SHORT).show();
      //  final int count = 60;//倒计时10秒
        final int count = 60;//倒计时10秒
        Observable.interval(0, 1, TimeUnit.SECONDS)//设置0延迟，每隔一秒发送一条数据
                .take(count + 1) //设置循环11次
                .map(aLong -> {
                    return count - aLong; //
                })
                .doOnSubscribe(() -> {
                    textView.setEnabled(false);//在发送数据的时候设置为不能点击
                   // textView.setBackgroundResource(R.drawable.shape_code_disable);//背景色设为灰色
                    textView.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                })

                .observeOn(AndroidSchedulers.mainThread())//操作UI主要在UI线程
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onCompleted() {
                        textView.setEnabled(true);
                        textView.setText("获取验证码");//数据发送完后设置为原来的文字
                        textView.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                       // textView.setBackgroundResource(R.drawable.shape_code_enable);//数据发送完后设置为原来背景色
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        textView.setEnabled(true);
                        textView.setText("获取验证码");//数据发送完后设置为原来的文字
                        textView.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                        //textView.setBackgroundResource(R.drawable.shape_code_enable);//数据发送完后设置为原来背景色
                    }

                    @Override
                    public void onNext(Long aLong) { //接受到一条就是会操作一次UI
                        textView.setText(aLong + "秒");
                        textView.setEnabled(false);
                        textView.setTextColor(context.getResources().getColor(R.color.colorAccent));
                    }
                });
    }

}
