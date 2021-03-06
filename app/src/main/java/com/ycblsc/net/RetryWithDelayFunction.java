package com.ycblsc.net;


import com.ycblsc.model.ResponseCodeEnum;
import com.ycblsc.net.ex.ApiException;
import com.ycblsc.net.ex.ResultException;

import org.reactivestreams.Publisher;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;


/**
 * Created by lm on 2017/11/22.
 * Description：
 */
public class RetryWithDelayFunction implements Function<Flowable<Throwable>, Publisher<?>> {
    private static final String TAG = "RetryWithDelayFunction";

    public static RetryWithDelayFunction create() {
        return new RetryWithDelayFunction();
    }

    @Override
    public Publisher<?> apply(@NonNull Flowable<Throwable> throwableFlowable) throws Exception {
        return throwableFlowable.flatMap(new Function<Throwable, Publisher<?>>() {
            @Override
            public Publisher<?> apply(@NonNull Throwable throwable) throws Exception {
                if (throwable instanceof ApiException) {
                    ResponseCodeEnum responseCode = ((ApiException) throwable).getResponseCode();

                    switch (responseCode) {
                        case NODATA:
                            return Flowable.error(throwable);
                       /* case SERVER_ERR:
                            return count>1?Flowable.error(throwable):Flowable.timer(500, TimeUnit.MILLISECONDS);*/
                        default:
                            return Flowable.error(throwable);
                    }
                }
                if (throwable instanceof ResultException) {
                    return Flowable.error(throwable);
                }
                return Flowable.error(throwable);
            }
        });
    }
}
