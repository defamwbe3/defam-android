package Medium.DeFam.app.activity;


import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.just.agentweb.AgentWeb;
import com.just.agentweb.AgentWebSettingsImpl;
import com.just.agentweb.IAgentWebSettings;
import com.ypx.imagepicker.ImagePicker;
import com.ypx.imagepicker.bean.ImageItem;
import com.ypx.imagepicker.bean.MimeType;
import com.ypx.imagepicker.bean.SelectMode;
import com.ypx.imagepicker.data.OnImagePickCompleteListener;

import java.util.ArrayList;

import Medium.DeFam.app.R;
import Medium.DeFam.app.common.base.BaseActivity;
import Medium.DeFam.app.common.them.Eyes;
import Medium.DeFam.app.common.widget.WeChatPresenter;
import Medium.DeFam.app.utils.AndroidtoJs;
import butterknife.BindView;

public class Web extends BaseActivity {

    private Intent intent;
    private AgentWeb mAgentWeb;
    @BindView(R.id.linearLayout)
    LinearLayout mLinearLayout;
    private String mTitle;
    private String mUrl;
    private String mHtml;
    private ValueCallback<Uri> uploadMessage;
    private ValueCallback<Uri[]> uploadMessageAboveL;
    private final String CSS_STYLE ="<style>* {font-size:16px;line-height:20px;}p {color:#373748;}</style>";

    @Override
    protected boolean isNeedLogin() {
        return false;
    }

    public static void startWebActivity(Context mContext, String mTitle, String url, String html) {
        Intent intent = new Intent(mContext, Web.class);
        intent.putExtra("url", url);
        intent.putExtra("title", mTitle);
        intent.putExtra("html", html);
        mContext.startActivity(intent);
    }
    public static void startWebActivity(Context mContext, String mTitle, String url, String html,boolean isTitle) {
        Intent intent = new Intent(mContext, Web.class);
        intent.putExtra("url", url);
        intent.putExtra("title", mTitle);
        intent.putExtra("html", html);
        intent.putExtra("isTitle", isTitle);
        mContext.startActivity(intent);
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_web;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState, @Nullable  Bundle bundle) {

    }

    @Override
    protected void initData() {
        intent = getIntent();
        mTitle = intent.getStringExtra("title");
        mUrl = intent.getStringExtra("url");
        mHtml = intent.getStringExtra("html");
        boolean isTitle = intent.getBooleanExtra("isTitle",true);
        if(!isTitle){
            mTitleBar.setVisibility(View.GONE);
            mLinearLayout.setPadding(0, Eyes.getStatusBarHeight(this), 0, 0);
        }
        mTitleBar.getCenterTextView().setText(TextUtils.isEmpty(mTitle) ?"网页":mTitle);
        if (!TextUtils.isEmpty(mUrl)) {
            mAgentWeb = AgentWeb.with(this)//传入Activity or Fragment
                    .setAgentWebParent(mLinearLayout, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))//传入AgentWeb 的父控件 ，如果父控件为 RelativeLayout ， 那么第二参数需要传入 RelativeLayout.LayoutParams ,第一个参数和第二个参数应该对应。
                    .useDefaultIndicator()// 使用默认进度条
                    .setWebChromeClient(mWebChromeClient)
                    .setWebViewClient(mWebViewClient)
                    .setAgentWebWebSettings(initSetting())
                    .createAgentWeb()//
                    .ready()
                    .go(mUrl);
        } else {
            mAgentWeb = AgentWeb.with(this)//传入Activity or Fragment
                    .setAgentWebParent(mLinearLayout, new LinearLayout.LayoutParams(-1, -1))//传入AgentWeb 的父控件 ，如果父控件为 RelativeLayout ， 那么第二参数需要传入 RelativeLayout.LayoutParams ,第一个参数和第二个参数应该对应。
                    .useDefaultIndicator()// 使用默认进度条
                    .setWebChromeClient(mWebChromeClient)
                    .setWebViewClient(mWebViewClient)
                    .createAgentWeb()//
                    .ready()
                    .go("");
            //使用loadData 可能有手机存在乱码 如红米note
            mAgentWeb.getWebCreator().getWebView().loadDataWithBaseURL(null, CSS_STYLE+mHtml, "text/html", "utf-8", null);
            //mAgentWeb.getWebCreator().getWebView().loadDataWithBaseURL(null, mHtml, "text/html", "utf-8", null);
        }
        //Android 端 ， AndroidtoJs 是一个与js交互的类 ，里面有参数方法：callAndroid
        mAgentWeb.getJsInterfaceHolder().addJavaObject(AndroidtoJs.METHOD_NAME, new AndroidtoJs(this));
        mAgentWeb.getWebCreator().getWebView().setBackgroundColor(getResources().getColor(R.color.bg)); // 设置背景色

        mAgentWeb.getWebCreator().getWebView().setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimeType, long contentLength) {
                //跳转出浏览器下载
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });
    }
    private IAgentWebSettings initSetting() {
        IAgentWebSettings webSettings = new AgentWebSettingsImpl().toSetting(new WebView(this));
        webSettings.getWebSettings().setJavaScriptEnabled(true);// 启用支持JavaScript
        webSettings.getWebSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.getWebSettings().setUseWideViewPort(true);// 设置此属性，可任意比例缩放
        webSettings.getWebSettings().setAllowFileAccess(true);// 设置可以访问文件
        webSettings.getWebSettings().setBuiltInZoomControls(false);// 设置支持缩放
        webSettings.getWebSettings().setBlockNetworkImage(false);// 加载需要显示的网页
        webSettings.getWebSettings().setBlockNetworkLoads(false);
        webSettings.getWebSettings().setDomStorageEnabled(true);// ??
        webSettings.getWebSettings().setLoadWithOverviewMode(true);
        webSettings.getWebSettings().setAppCacheEnabled(false);// 使用缓存
        webSettings.getWebSettings().setDefaultTextEncodingName("utf-8");
        webSettings.getWebSettings().setSaveFormData(false);
        //允许其加载混合网络协议内容
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.getWebSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        return webSettings;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (mAgentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        /**
         * 如果存在缓存问题，可以尝试放开清空cookie代码来解决问题
         * */
        //------清空所有Cookie  start-----
//        CookieSyncManager.createInstance(this);  //Create a singleton CookieSyncManager within a context
//        CookieManager cookieManager = CookieManager.getInstance(); // the singleton CookieManager instance
//        cookieManager.removeAllCookie();// Removes all cookies.
//        CookieSyncManager.getInstance().sync(); // forces sync manager to sync now
//        mAgentWeb.getWebCreator().getWebView().setWebChromeClient(null);
//        mAgentWeb.getWebCreator().getWebView().setWebViewClient(null);
//        mAgentWeb.getWebCreator().getWebView().getSettings().setJavaScriptEnabled(false);
//        mAgentWeb.getWebCreator().getWebView().clearCache(true);
        //------清空所有Cookie  end-----
        if(null!=mAgentWeb){
            mAgentWeb.getWebLifeCycle().onDestroy();
        }

        super.onDestroy();
    }

    private WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            try{
                if (url.startsWith("http:") || url.startsWith("https:")){
                    view.loadUrl(url);
                }else{
                    Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(url));
                    startActivity(intent);
                }
                return true;
            }catch (Exception e){
                return false;
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);
        }

    };
    private WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            //do you work
        }
        //上传 begin
        // For Android >= 5.0
        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
            uploadMessageAboveL = filePathCallback;
            pickPic();
            return true;
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            if (TextUtils.isEmpty(mTitle)) {
                mTitleBar.getCenterTextView().setText(title);
            }
        }
    };

    private void pickPic() {
        ImagePicker.withMulti(new WeChatPresenter())
                .setMaxCount(1)
                .mimeTypes(MimeType.ofImage())
                .filterMimeTypes(MimeType.GIF)
                .showCamera(true)//显示拍照
                .setPreview(false)//开启预览
                .setSelectMode(SelectMode.MODE_SINGLE)
                .setSinglePickWithAutoComplete(true)
                .pick( this, new OnImagePickCompleteListener() {
                    @Override
                    public void onImagePickComplete(ArrayList<ImageItem> items) {
                        Uri selectedImage = items.get(0).getUri();
                        if (uploadMessageAboveL != null) {
                            Uri[] results = new Uri[]{selectedImage};
                            uploadMessageAboveL.onReceiveValue(results);
                            uploadMessageAboveL = null;
                        } else if (uploadMessage != null) {
                            uploadMessage.onReceiveValue(selectedImage);
                            uploadMessage = null;
                        }
                    }
                });


    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
