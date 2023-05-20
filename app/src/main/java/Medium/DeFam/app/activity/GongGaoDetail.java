package Medium.DeFam.app.activity;

import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;

import Medium.DeFam.app.R;
import Medium.DeFam.app.bean.GongGaoBean;
import Medium.DeFam.app.common.Constants;
import Medium.DeFam.app.common.base.BaseActivity;
import Medium.DeFam.app.common.http.HttpClient;
import Medium.DeFam.app.common.http.JsonBean;
import Medium.DeFam.app.common.http.TradeHttpCallback;
import Medium.DeFam.app.utils.HttpUtil;
import Medium.DeFam.app.view.MyTextView;
import butterknife.BindView;

public class GongGaoDetail extends BaseActivity {
    @BindView(R.id.title)
    MyTextView title;
    @BindView(R.id.created_at)
    MyTextView created_at;
    @BindView(R.id.web)
    WebView web;
    private GongGaoBean alldata;
    private final String HTML_STYLE = "<style type=\"text/css\">* {font-size:14px}" +
            "img,iframe,video,table,div {height:auto; max-width:100%; width:100% !important; word-break:break-all;} </style>";//bak:


    @Override
    protected boolean isNeedLogin() {
        return false;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_gonggaodetail;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState, @Nullable Bundle bundle) {
        WebSettings settings = web.getSettings();
        settings.setJavaScriptEnabled(true); // 设置支持javascript脚本
        settings.setAllowFileAccess(true); // 允许访问文件
        settings.setBuiltInZoomControls(true); // 设置显示缩放按钮
        settings.setSupportZoom(true); // 支持缩放
        settings.setBuiltInZoomControls(true);//设置支持两指缩放手势
        settings.setDisplayZoomControls(false);//隐藏缩放按钮
        settings.setSavePassword(false);
        settings.setSaveFormData(false);
        /* localStorage 不支持问题 Start */
        settings.setDomStorageEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        web.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                int w = View.MeasureSpec.makeMeasureSpec(0,
                        View.MeasureSpec.UNSPECIFIED);
                int h = View.MeasureSpec.makeMeasureSpec(0,
                        View.MeasureSpec.UNSPECIFIED);
                //重新测量
                view.measure(w, h);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }
        });
    }

    @Override
    protected void initData() {
        alldata = (GongGaoBean) getIntent().getSerializableExtra("data");
        if(null==alldata){
            finish();
            return;
        }
        setUI();
        getData();
    }
    private void setUI(){
        title.setText(alldata.getTitle());
        created_at.setText(alldata.getCreated_at());
        web.loadDataWithBaseURL(Constants.HOST, HTML_STYLE + alldata.getContent(), "text/html",
                "utf-8", null);
    }
    private void getData() {
        HttpClient.getInstance().gets(HttpUtil.NOTICE+"/"+ alldata.getId(), null, new TradeHttpCallback<JsonBean<GongGaoBean>>() {
            @Override
            public void onSuccess(JsonBean<GongGaoBean> data) {
                if (null == data || null == data.getData()) {
                    return;
                }
                alldata = data.getData();
                setUI();
            }

        });
    }
    @Override
    protected void onDestroy() {
        if (web != null) {
            web.stopLoading();
            web.clearHistory();
            web.clearCache(true);
            web.freeMemory();
            web.destroy();
            web = null;
        }
        super.onDestroy();
    }
}
