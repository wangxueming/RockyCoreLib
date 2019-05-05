package com.rocky.core;

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;

/**
 * 基本的倒计时计数
 *
 * @author wangxueming
 * @date 2018/1/29
 */
public abstract class BaseCountDownTimer {

    /**
     * Millis since epoch when alarm should stop.
     */
    private final long mMillisInFuture;

    /**
     * The interval in millis that the user receives callbacks
     */
    private final long mCountdownInterval;

    private long mStopTimeInFuture;
    private long mStartTime;
    private long mCurrentTime;

    /**
     * boolean representing if the timer was cancelled
     */
    private boolean mCancelled = false;

    /**
     * @param millisInFuture The number of millis in the future from the call
     *   to {@link #start()} until the countdown is done and {@link #onFinish()}
     *   is called.
     * @param countDownInterval The interval along the way to receive
     *   {@link #onTick(long)} callbacks.
     */
    public BaseCountDownTimer(long millisInFuture, long countDownInterval) {
        mMillisInFuture = millisInFuture;
        mCountdownInterval = countDownInterval;
    }

    /**
     * Cancel the countdown.
     */
    public synchronized final void cancel() {
        mCancelled = true;
        mHandler.removeMessages(MSG);
        onCancel();
    }

    /**
     * Start the countdown.
     */
    public synchronized final BaseCountDownTimer start() {
        mCancelled = false;
        if (mMillisInFuture <= 0) {
            onFinish();
            return this;
        }
        mStartTime = mCurrentTime = SystemClock.elapsedRealtime();
        mStopTimeInFuture = SystemClock.elapsedRealtime() + mMillisInFuture;
        mHandler.sendMessage(mHandler.obtainMessage(MSG));
        onStart(mStartTime);
        return this;
    }

    /**
     * AbstractCallback fired once started.
     * @param startTime startTime The amount of time until started.
     */
    public abstract void onStart(long startTime);
    /**
     * AbstractCallback fired on regular interval.
     * @param millisUntilFinished The amount of time until finished.
     */
    public abstract void onTick(long millisUntilFinished);

    /**
     * AbstractCallback fired when the time is up.
     */
    public abstract void onFinish();

    public void onCancel(){}

    private static final int MSG = 1;

    /**
     * handles counting down
     */
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            synchronized (this) {
                if (mCancelled) {
                    return;
                }

                mCurrentTime = mCurrentTime + mCountdownInterval;

                if (mCurrentTime >= mStopTimeInFuture) {
                    onFinish();
                } else {
                    onTick(mStopTimeInFuture - mCurrentTime);

                    // take into account user's onTick taking time to execute
//                    long delay = SystemClock.elapsedRealtime() + mCountdownInterval + mCountdownInterval - mCurrentTime;
                    long delay = mCurrentTime - SystemClock.elapsedRealtime();
                    sendMessageDelayed(obtainMessage(MSG), delay);
                }
            }
        }
    };
}
