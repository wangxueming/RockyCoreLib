package com.rocky.projectcore.net;

import android.content.Context;
import android.text.TextUtils;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.rocky.core.base.mvp.inter.IView;
import com.rocky.core.util.Tip;
import com.rocky.core.util.logger.L;
import com.rocky.core.util.logger.PrettyL;

/**
 *
 *
 * @author WangXueMing
 * @version 1.0.0
 * @date 2019/1/7
 */
public abstract class CommonObserver<T> extends BaseObserver<T> {

    public CommonObserver(Object tag) {
        super(tag);
        this.tag = tag;
        if (tag instanceof Context) {
            mContext = (Context) tag;
        }
    }

    public CommonObserver(IView view) {
        super(view);
        this.mView = view;
    }

    protected CommonObserver(IView view, boolean isShowHUD) {
        super(view, isShowHUD);
        this.mView = view;
        if (isShowHUD) {
            this.mDialogView = view;
        }
    }

    protected CommonObserver(IView view, SmartRefreshLayout rlRefresh) {
        super(view, rlRefresh);
        this.mView = view;
        this.rlRefreshLayout = rlRefresh;
    }


    protected CommonObserver(IView view, String msg1) {
        super(view, msg1);
        mView = view;
        mDialogView = view;
        msg = msg1;
    }

    @Override
    public void onStart() {
        super.onStart();
        //显示正在加载中的页面,由子页面实现
        if (mView != null && rlRefreshLayout == null) {
            mView.showLoading();
            L.d("空布局调用正在加载中");
        }

        if (mDialogView != null) {
            mDialogView.showHUD(msg);
        }
    }

    @Override
    public void onFail(String code, String error) {
        if (!TextUtils.isEmpty(error)) {
            Tip.onNotice(error);
        }
        PrettyL.w(error);

        if (mDialogView != null) {
            mDialogView.dismissHUD();
        }

        if (mView != null) {
            //是否显示错误页面,由子类去实现
            mView.showError();
        }
        onComplete();
    }

    @Override
    public void onEnd() {
        L.d("执行结果");
        if (mDialogView != null) {
            mDialogView.dismissHUD();
        } else if (rlRefreshLayout != null) {
            rlRefreshLayout.finishRefresh(0);
            rlRefreshLayout.finishLoadMore(0);
        }
    }

}
