package Medium.DeFam.app.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.hjq.toast.Toaster;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import Medium.DeFam.app.R;
import Medium.DeFam.app.bean.MyMapBean;
import Medium.DeFam.app.common.Constants;
import Medium.DeFam.app.common.base.BaseActivity;
import Medium.DeFam.app.common.utils.GlideUtil;
import butterknife.BindView;
import butterknife.OnClick;


public class BaoZangDetail extends BaseActivity {
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.map_name)
    TextView map_name;
    @BindView(R.id.lian)
    TextView lian;
    @BindView(R.id.web)
    WebView web;
    @BindView(R.id.huizhiview)
    LinearLayout huizhiview;
    @BindView(R.id.huizhi)
    TextView huizhi;
    @BindView(R.id.huizhiview1)
    LinearLayout huizhiview1;
    @BindView(R.id.order_no)
    TextView order_no;
    @BindView(R.id.exchange_time)
    TextView exchange_time;
    @BindView(R.id.provide_time)
    TextView provide_time;
    private final String HTML_STYLE = "<style type=\"text/css\">* {font-size:16px}" +
            "img,iframe,video,table,div {height:auto; max-width:100%; width:100% !important; word-break:break-all;} </style>";//bak:


    @Override
    protected boolean isNeedLogin() {
        return false;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_baozangdetail;
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
        MyMapBean.DataBean alldata = (MyMapBean.DataBean) getIntent().getSerializableExtra("data");
        List<String> imglist = new ArrayList<>();
        imglist.add(alldata.getImage());
        banner.setAdapter(new BannerImageAdapter<String>(imglist) {
                              @Override
                              public void onBindView(BannerImageHolder holder, String data, int position, int size) {
                                  //图片加载自己实现
                                  GlideUtil.showImg(BaoZangDetail.this, data, holder.imageView);
                              }
                          }
                )
                .setOnBannerListener(new OnBannerListener<String>() {
                    @Override
                    public void OnBannerClick(String data, int position) {
                        Intent intent = new Intent(BaoZangDetail.this,
                                Photo.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("position", 0);
                        bundle.putSerializable("list", (Serializable) imglist);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                })
                .addBannerLifecycleObserver(BaoZangDetail.this)//添加生命周期观察者
                .setIndicator(new CircleIndicator(BaoZangDetail.this));
        map_name.setText(alldata.getMap_name());
        mTitleBar.getCenterTextView().setText(alldata.getMap_name());
        lian.setText("链上信息：" + alldata.getSuipian_name());
        lian.setTag(alldata.getSuipian_name());
        web.loadDataWithBaseURL(Constants.HOST, HTML_STYLE + alldata.getDetail(), "text/html",
                "utf-8", null);

        if ("2".equals(alldata.getStatus())) {
            huizhiview.setVisibility(View.VISIBLE);
            huizhi.setText(alldata.getReceipt());
            huizhiview1.setVisibility(View.VISIBLE);
            order_no.setText(alldata.getOrder_no());
            exchange_time.setText(alldata.getExchange_time());
            provide_time.setText(alldata.getProvide_time());
        } else {
            huizhiview.setVisibility(View.GONE);
            huizhiview1.setVisibility(View.GONE);
        }
    }


    @OnClick({R.id.fuzhi, R.id.fuzhi1, R.id.huizhiview})
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.fuzhi) {
            //获取剪贴板管理器：
            ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            // 创建普通字符型ClipData
            ClipData mClipData = ClipData.newPlainText("Label", lian.getTag().toString());
            // 将ClipData内容放到系统剪贴板里。
            cm.setPrimaryClip(mClipData);
            Toaster.show("复制成功");
        } else if (id == R.id.fuzhi1) {
            //获取剪贴板管理器：
            ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            // 创建普通字符型ClipData
            ClipData mClipData = ClipData.newPlainText("Label", huizhi.getText().toString());
            // 将ClipData内容放到系统剪贴板里。
            cm.setPrimaryClip(mClipData);
            Toaster.show("复制成功");
        }else if (id == R.id.huizhiview) {
            Web.startWebActivity(this, "",  huizhi.getText().toString(), "", true);

        }
    }


}