package com.ycblsc.net;


import com.ycblsc.model.ResponseCodeEnum;
import com.ycblsc.net.ex.ApiException;
import com.ycblsc.net.ex.ResultException;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;


/**
 * Created by lm on 2017/11/22.
 * Description：
 */
public class RetryWithDelayFunction implements Function<Observable<? extends Throwable>, Observable<?>> {
    private static final String TAG = "RetryWithDelayFunction";

    public static RetryWithDelayFunction create() {
        return new RetryWithDelayFunction();
    }

    @Override
    public Observable<?> apply(@NonNull Observable<? extends Throwable> observable) throws Exception {
        return observable.flatMap(new Function<Throwable, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(@NonNull Throwable throwable) throws Exception {
                if (throwable instanceof ApiException) {
                    ResponseCodeEnum responseCode = ((ApiException) throwable).getResponseCode();
                    switch (responseCode) {
                        case NODATA:
                            return Observable.error(throwable);
                        default:
                            return Observable.error(throwable);
                    }
                }

                if (throwable instanceof ResultException) {
                    return Observable.error(throwable);
                }
                return Observable.error(throwable);
            }
        });
    }
}
