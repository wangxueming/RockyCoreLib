package com.rockyw.core.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.rockyw.core.R;
import com.rockyw.core.exception.NotFindLayoutException;

import butterknife.ButterKnife;

/**
 * 基础库
 *
 * @author WangXueMing
 * @version 1.0.0
 * @date 2019/3/18
 */
public abstract class BaseCustomDialog extends Dialog {

    protected Context mContext;
    private View mRootView;
    private SparseArray<View> mViews;

    protected int getAnimation() {
        return R.style.base_CommonDialogAnimation;
    }

    public BaseCustomDialog(Context context) {
        this(context, R.style.base_Win_Dialog_CustomStyle);
    }

    public BaseCustomDialog(Context context, int themeResId) {
        super(context, themeResId);
        mContext = context;
        //sWeakReference = new WeakReference<>(mContext);
        if (getLayoutId() == 0) {
            throw new NotFindLayoutException(this.getClass());
        }
        mRootView = LayoutInflater.from(mContext).inflate(getLayoutId(), null);
        setContentView(mRootView);

        ButterKnife.bind(this);

        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = getWindowGravity();
        window.setAttributes(lp);
        window.setWindowAnimations(getAnimation());
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initView(mRootView);
    }

    public View getRootView() {
        return mRootView;
    }

    public abstract int getLayoutId();

    public int getWindowGravity() {
        return Gravity.BOTTOM;
    }

    public abstract void initView(View view);

  /*  public boolean checkContext() {

        if (sWeakReference == null || sWeakReference.get() == null) {
            return false;
        }

        return SystemUtil.isActivityRunning(sWeakReference.get());
    }*/

    /**
     * 通过控件的Id获取对于的控件，如果没有则加入views
     *
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int viewId) {
        if (mViews == null) {
            mViews = new SparseArray<>();
        }
        View view = mViews.get(viewId);
        if (view == null) {
            view = getRootView().findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 为TextView设置字符串
     *
     * @param viewId
     * @param text
     * @return
     */
    public void setText(int viewId, String text) {
        TextView view = getView(viewId);
        view.setText(text);
    }

    protected int getResColor(int resId) {
        return ContextCompat.getColor(getContext(), resId);
    }

    protected Drawable getResDrawable(int resId) {
        return ContextCompat.getDrawable(getContext(), resId);
    }

    protected String getString(int resId) {
        return getContext().getString(resId);
    }

}
