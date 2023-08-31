package Medium.DeFam.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.hjq.toast.Toaster;
import com.lzy.okgo.model.Response;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Medium.DeFam.app.R;
import Medium.DeFam.app.bean.JiFenDetailBean;
import Medium.DeFam.app.bean.WalletAddressBean;
import Medium.DeFam.app.common.Constants;
import Medium.DeFam.app.common.base.BaseActivity;
import Medium.DeFam.app.common.http.HttpClient;
import Medium.DeFam.app.common.http.JsonBean;
import Medium.DeFam.app.common.http.TradeHttpCallback;
import Medium.DeFam.app.common.utils.GlideUtil;
import Medium.DeFam.app.dialog.AllDialog;
import Medium.DeFam.app.dialog.JiFenDialogFragment;
import Medium.DeFam.app.utils.HttpUtil;
import butterknife.BindView;
import butterknife.OnClick;


public class JiFenDetail extends BaseActivity {

    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.sale_num)
    TextView sale_num;
    @BindView(R.id.integral)
    TextView integral;
    @BindView(R.id.web)
    WebView web;
    JiFenDetailBean alldata;
    private Activity mContext;

    private final String HTML_STYLE = "<style type=\"text/css\">* {font-size:16px}" +
            "img,iframe,video,table,div {height:auto; max-width:100%; width:100% !important; word-break:break-all;} </style>";//bak:

    @Override
    protected boolean isNeedLogin() {
        return false;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_jifendetail;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState, @Nullable Bundle bundle) {
        mContext = this;
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
        alldata = (JiFenDetailBean) getIntent().getSerializableExtra("data");
        if (null == alldata) {
            finish();
            return;
        }
        setUI();
        getData();
    }


    @OnClick({R.id.duihuan})
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.duihuan) {
            if(null==alldata){
                return;
            }
            //判断有没绑定钱包地址
            getWalletData();
        }
    }

    private void getData() {
        HttpClient.getInstance().gets(HttpUtil.APIGOODSGOODSID + alldata.getId(), null, new TradeHttpCallback<JsonBean<JiFenDetailBean>>() {
            @Override
            public void onSuccess(JsonBean<JiFenDetailBean> data) {
                if (null == data || null == alldata) {
                    return;
                }
                alldata = data.getData();
                setUI();
            }

        });
    }

    private void getWalletData() {
        showProgress("加载中...");
        Map<String, String> map = new HashMap<>();
        map.put("page", "1");
        HttpClient.getInstance().gets(HttpUtil.APIWALLETADDRESS, map, new TradeHttpCallback<JsonBean<WalletAddressBean>>() {
            @Override
            public void onSuccess(JsonBean<WalletAddressBean> data) {
                closeProgress();
                if (null == data || null == data.getData() || data.getData().getData()==null || data.getData().getData().size()==0) {
                    AllDialog payDialog = new AllDialog(mContext, "温馨提示", "未绑定钱包地址，请绑定");
                    payDialog.seOkBtnText("去绑定");
                    payDialog.setItemListener(new AllDialog.OnNoticeListener() {
                        @Override
                        public void setNoticeListener(int id, int positionDialog, String data) {
                            if (0 == id) {
                                Intent intent = new Intent(mContext, WalletAddressAdd.class);
                                startActivityForResult(intent, Constants.TYPE_1);
                            }
                        }
                    });
                    payDialog.show();
                }else{
                    JiFenDialogFragment jiFenDialogFragment = new JiFenDialogFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("data", alldata);
                    jiFenDialogFragment.setArguments(bundle);
                    jiFenDialogFragment.show(getSupportFragmentManager(), "xinxi", true);
                }
            }

            @Override
            public void onError(Response<JsonBean<WalletAddressBean>> response) {
                closeProgress();
                Toaster.show("加载失败");
            }
        });
    }

    private void setUI() {
        List<String> imglist = new ArrayList<>();
        imglist.add(alldata.getImage());
        banner.setAdapter(new BannerImageAdapter<String>(imglist) {
                              @Override
                              public void onBindView(BannerImageHolder holder, String data, int position, int size) {
                                  //图片加载自己实现
                                  GlideUtil.showImg(JiFenDetail.this, data, holder.imageView);
                              }
                          }
                )
                .setOnBannerListener(new OnBannerListener<String>() {
                    @Override
                    public void OnBannerClick(String data, int position) {
                        Intent intent = new Intent(JiFenDetail.this,
                                Photo.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("position", 0);
                        bundle.putSerializable("list", (Serializable) imglist);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                })
                .addBannerLifecycleObserver(JiFenDetail.this)//添加生命周期观察者
                .setIndicator(new CircleIndicator(JiFenDetail.this));
        name.setText(alldata.getName());
        sale_num.setText("已兑:" + alldata.getSale_num());
        integral.setText(alldata.getPrice() + "DD");
        web.loadDataWithBaseURL(Constants.HOST, HTML_STYLE + alldata.getDetail(), "text/html",
                "utf-8", null);
    }
}
