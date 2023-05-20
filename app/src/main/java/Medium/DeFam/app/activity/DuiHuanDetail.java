package Medium.DeFam.app.activity;

import android.content.Intent;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.annotation.Nullable;

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
import Medium.DeFam.app.adapter.DuiHuanTiaoJianAdapter;
import Medium.DeFam.app.bean.MapBean;
import Medium.DeFam.app.common.Constants;
import Medium.DeFam.app.common.base.BaseActivity;
import Medium.DeFam.app.common.http.HttpClient;
import Medium.DeFam.app.common.http.JsonBean;
import Medium.DeFam.app.common.http.TradeHttpCallback;
import Medium.DeFam.app.common.utils.GlideUtil;
import Medium.DeFam.app.common.widget.NoScrollGridView;
import Medium.DeFam.app.utils.HttpUtil;
import butterknife.BindView;
import butterknife.OnClick;


public class DuiHuanDetail extends BaseActivity {
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.gridview)
    NoScrollGridView gridview;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.sale_num)
    TextView sale_num;
    @BindView(R.id.web)
    WebView web;
    @BindView(R.id.is_enough)
    TextView is_enough;
    @BindView(R.id.duihuan)
    TextView duihuan;
    private String map_id;
    private DuiHuanTiaoJianAdapter gridviewadapter;
    private final String HTML_STYLE = "<style type=\"text/css\">* {font-size:16px}" +
            "img,iframe,video,table,div {height:auto; max-width:100%; width:100% !important; word-break:break-all;} </style>";//bak:

    @Override
    protected boolean isNeedLogin() {
        return false;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_duihuandetail;
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
        map_id = getIntent().getStringExtra("map_id");
        if (TextUtils.isEmpty(map_id)) {
            finish();
            return;
        }
        gridviewadapter = new DuiHuanTiaoJianAdapter(this);
        gridview.setAdapter(gridviewadapter);
        getData();
    }

    @OnClick({R.id.duihuan})
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.duihuan) {
            Map<String, String> map = new HashMap<>();
            map.put("map_id", map_id);
            HttpClient.getInstance().posts(HttpUtil.EXCHANGE, map, new TradeHttpCallback<JsonBean<List<String>>>() {
                @Override
                public void onSuccess(JsonBean<List<String>> data) {
                    if (null == data || null == data.getData()) {
                        return;
                    }
                    Intent intent = new Intent(DuiHuanDetail.this, DuiHuanJiLu.class);
                    startActivity(intent);
                }

                @Override
                public boolean showLoadingDialog() {
                    return super.showLoadingDialog();
                }
            });
        }
    }

    private void getData() {
        Map<String, String> map = new HashMap<>();
        map.put("map_id", map_id);
        HttpClient.getInstance().posts(HttpUtil.USERMAPDETAIL, map, new TradeHttpCallback<JsonBean<MapBean>>() {
            @Override
            public void onSuccess(JsonBean<MapBean> data) {
                if (null == data || null == data.getData()) {
                    return;
                }
                mTitleBar.getCenterTextView().setText(data.getData().getName());
                List<String> imglist = new ArrayList<>();
                imglist.add(data.getData().getImage());
                banner.setAdapter(new BannerImageAdapter<String>(imglist) {
                                      @Override
                                      public void onBindView(BannerImageHolder holder, String data, int position, int size) {
                                          //图片加载自己实现
                                          GlideUtil.showImg(DuiHuanDetail.this, data, holder.imageView);
                                      }
                                  }
                        )
                        .setOnBannerListener(new OnBannerListener<String>() {
                            @Override
                            public void OnBannerClick(String data, int position) {
                                Intent intent = new Intent(DuiHuanDetail.this,
                                        Photo.class);
                                Bundle bundle = new Bundle();
                                bundle.putInt("position", 0);
                                bundle.putSerializable("list", (Serializable) imglist);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        })
                        .addBannerLifecycleObserver(DuiHuanDetail.this)//添加生命周期观察者
                        .setIndicator(new CircleIndicator(DuiHuanDetail.this));
                name.setText(data.getData().getName());
                sale_num.setText("已兑:" + data.getData().getSale_num());
            /*    List<MapBean.UserMapBean> dataList = new ArrayList<>();
                dataList.add(data.getData().getUsermap());*/
                gridviewadapter.replaceAll(data.getData().getSynthesis_needed());
                web.loadDataWithBaseURL(Constants.HOST, HTML_STYLE + data.getData().getDetail(), "text/html",
                        "utf-8", null);
               /* if ("0".equals(data.getData().getIs_enough())) {
                    is_enough.setText("不可兑换");
                    duihuan.setBackgroundResource(R.drawable.r_hui50);
                    duihuan.setEnabled(false);
                } else {
                    is_enough.setText("可兑换");
                    duihuan.setBackgroundResource(R.drawable.r_lan50);
                }*/
            }


        });
    }
}
