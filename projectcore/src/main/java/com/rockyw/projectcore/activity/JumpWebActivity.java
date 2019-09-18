package com.rockyw.projectcore.activity;

import android.net.http.SslError;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.rockyw.core.base.mvc.BaseVcActivity;
import com.rockyw.customview.widget.WinTopBar;
import com.rockyw.projectcore.R;
import com.rockyw.projectcore.R2;
import com.rockyw.projectcore.common.router.CommonAction;
import com.rockyw.projectcore.common.router.RouterUrl;

import butterknife.BindView;

/**
 * @author: WangXueMing
 * @version: 1.0.0
 * @date: 2019/1/21
 */
@Route(path = RouterUrl.OTHERS_JUMP_WEB)
public class JumpWebActivity extends BaseVcActivity {

    @BindView(R2.id.core_webview)
    WebView mWebView;
    @BindView(R2.id.core_top_bar)
    WinTopBar mTopBar;
    @Autowired(name = CommonAction.COM_ACT_DATA_1)
    public String mWebUrl;
    @Autowired(name = CommonAction.COM_ACT_DATA_2)
    public String mTitle;

    @Override
    protected int getLayoutId() {
        return R.layout.core_activity_show_h5;
    }

    @Override
    public void initData() {
//        mWebUrl = getIntent().getStringExtra(CommonAction.COM_ACT_DATA_1);
//        mTitle = getIntent().getStringExtra(CommonAction.COM_ACT_DATA_2);
    }

    @Override
    public void onBackPressed() {
//        if (handlerByWeb()){
//            return;
//        }
        super.onBackPressed();
    }

    @Override
    protected void initTitle() {
        mTopBar.setTitleText(mTitle);
        mTopBar.setOnLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void initView() {
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }
        });
        mWebView.getSettings().setJavaScriptEnabled(true);
    }

    @Override
    protected void onLazyLoad() {
        mWebView.loadUrl(mWebUrl);
    }

    private boolean handlerByWeb() {
        if (mWebView.canGoBack()){
            mWebView.goBack();
            return true;
        }
        return false;
    }
}
