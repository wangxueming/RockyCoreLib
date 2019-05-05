package com.rocky.core.rx.rxtimer;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 基于RxJava2实现的一个定时器
 *
 * @author WangXueMing
 * @version 1.0.0
 * @date 2019/1/4
 */
public class RxTimer {

    public interface ActionListener {
        void onAction(Long aLong);
    }

    /**
     * disposable，用于停止计时器
     */
    private Disposable mDisposable;

    /**
     * 计时监听
     */
    private ActionListener mActionListener;

    /**
     * 计时周期
     */
    private long mPeriod;

    /**
     * 时间单位
     */
    private TimeUnit mTimeUnit;

    public RxTimer(long period, TimeUnit timeUnit, ActionListener actionListener) {
        mPeriod = period;
        mTimeUnit = timeUnit;
        mActionListener = actionListener;
    }

    /**
     * 启动计时器
     */
    public void start() {
        stop();
        mDisposable = Observable.interval(mPeriod, mTimeUnit)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {
                        if (mActionListener != null) {
                            mActionListener.onAction(aLong);
                        }
                    }
                });
    }

    /**
     * 销毁停止计时器
     */
    public void stop() {
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
            mDisposable = null;
        }
        mActionListener = null;
    }

}
