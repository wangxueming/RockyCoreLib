package com.rockyw.projectcore.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.http.SslError;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.rockyw.core.util.Tip;
import com.rockyw.projectcore.common.router.CommonAction;
import com.rockyw.projectcore.common.router.RouterUrl;
import com.rockyw.core.base.mvc.BaseVcActivity;
import com.rockyw.projectcore.R;
import com.rockyw.projectcore.R2;

import butterknife.BindView;

/**
 * @author: WangXueMing
 * @version: 1.0.0
 * @date: 2019/1/21
 */
@Route(path = RouterUrl.OTHERS_SHOW_H5)
public class ShowH5Activity extends BaseVcActivity {

    private static final String BIND_CARD_SUCCESS_URL = "account/bank/success.html";
    private static final String RECHARGE_SUCCESS_URL = "awrecharge/wechat/recharge.html";

    @BindView(R2.id.core_webview)
    WebView mWebView;
    @Autowired(name = CommonAction.COM_ACT_DATA_1)
    public String mWebUrl;
    @Autowired(name = CommonAction.COM_ACT_DATA_2)
    public String mPostUrl;
    @Autowired(name = CommonAction.COM_ACT_DATA_3)
    public String mExtraData;

    @Override
    protected int getLayoutId() {
        return R.layout.core_activity_show_h5;
    }

    @Override
    public void initData() {
//        mWebUrl = getIntent().getStringExtra(CommonAction.COM_ACT_DATA_1);
//        mPostUrl = getIntent().getStringExtra(CommonAction.COM_ACT_DATA_2);
    }

    @Override
    protected void initTitle() {
    }

    @Override
    protected void initView() {
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }
        });
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.contains(BIND_CARD_SUCCESS_URL)) {
                    //#特别针对绑定银行卡成功，要回到上一级页面
                    Tip.onCommonNotice("绑定成功");
                    Intent intent = new Intent();
                    intent.putExtra(CommonAction.COM_REQ_DATA_1, true);
                    intent.putExtra(CommonAction.COM_REQ_DATA_2, mExtraData);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                    return true;
                } else if (url.contains(RECHARGE_SUCCESS_URL)) {
                    Tip.onCommonNotice("充值成功");
                    Intent intent = new Intent();
                    intent.putExtra(CommonAction.COM_REQ_DATA_1, true);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                    return true;
                } else {
                    return false;
                }
            }
        });
        mWebView.getSettings().setJavaScriptEnabled(true);

        if (mPostUrl != null) {
            mWebView.postUrl(mWebUrl, mPostUrl.getBytes());
        } else {
            mWebView.loadUrl(mWebUrl);
        }
    }
}
