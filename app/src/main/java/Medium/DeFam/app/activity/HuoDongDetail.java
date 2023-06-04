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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hjq.toast.Toaster;
import com.lzy.okgo.model.Response;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
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
import Medium.DeFam.app.adapter.CommentAdapter;
import Medium.DeFam.app.adapter.DesAdapter;
import Medium.DeFam.app.bean.CommentBean;
import Medium.DeFam.app.bean.HuoDongDetailBean;
import Medium.DeFam.app.bean.JiangLiBean;
import Medium.DeFam.app.common.Constants;
import Medium.DeFam.app.common.base.BaseActivity;
import Medium.DeFam.app.common.http.HttpClient;
import Medium.DeFam.app.common.http.JsonBean;
import Medium.DeFam.app.common.http.TradeHttpCallback;
import Medium.DeFam.app.common.utils.AllUtils;
import Medium.DeFam.app.common.utils.GlideUtil;
import Medium.DeFam.app.common.widget.flowlayout.FlowLayoutManager;
import Medium.DeFam.app.common.widget.flowlayout.SpaceItemDecoration;
import Medium.DeFam.app.dialog.FenXiangDialogFragment;
import Medium.DeFam.app.dialog.JiFenDialog;
import Medium.DeFam.app.dialog.PingLunDialogFragment;
import Medium.DeFam.app.dialog.PingLunTopDialogFragment;
import Medium.DeFam.app.dialog.YuYueDialog;
import Medium.DeFam.app.utils.HttpUtil;
import butterknife.BindView;
import butterknife.OnClick;


public class HuoDongDetail extends BaseActivity {
    @BindView(R.id.scroll_view)
    NestedScrollView mSV;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.not)
    View not;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.des)
    RecyclerView des;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.status)
    TextView status;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.special)
    TextView special;
    @BindView(R.id.baoming)
    TextView baoming;
    @BindView(R.id.start_time)
    TextView start_time;
    @BindView(R.id.registered_number)
    TextView registered_number;
    @BindView(R.id.number)
    TextView number;
    @BindView(R.id.nickname)
    TextView nickname;
    @BindView(R.id.web)
    WebView web;
    @BindView(R.id.commentsview)
    LinearLayout commentsview;
    @BindView(R.id.is_good)
    ImageView is_good;
    @BindView(R.id.is_collection)
    ImageView is_collection;
    @BindView(R.id.comments)
    TextView comments;
    @BindView(R.id.good)
    TextView good;
    @BindView(R.id.forward)
    TextView forward;
    CommentAdapter commentAdapter;
    private int page = 1;
    private HuoDongDetailBean alldata;
    private final String HTML_STYLE = "<style type=\"text/css\">* {font-size:16px}" +
            "img,iframe,video,table,div {height:auto; max-width:100%; width:100% !important; word-break:break-all;} </style>";//bak:

    @Override
    protected boolean isNeedLogin() {
        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_huodongdetail;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState, @Nullable Bundle bundle) {
        refreshLayout.setEnableRefresh(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FlowLayoutManager flowLayoutManager = new FlowLayoutManager();
        des.addItemDecoration(new SpaceItemDecoration(AllUtils.dip2px(this, 0), AllUtils.dip2px(this, 0), AllUtils.dip2px(this, 12), AllUtils.dip2px(this, 5)));
        des.setLayoutManager(flowLayoutManager);
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
        alldata = (HuoDongDetailBean) getIntent().getSerializableExtra("data");
        if (null == alldata) {
            finish();
            return;
        }
        setUI();
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                getComment();
            }
        });
        commentAdapter = new CommentAdapter(this);
        commentAdapter.setRecyclerViewOnItemClickListener(new CommentAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(int pos, int type, CommentBean.DataBean data) {
                PingLunDialogFragment pingLunDialogFragment = new PingLunDialogFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("data", data);
                bundle.putString("id", alldata.getId());
                bundle.putString("type","3");
                pingLunDialogFragment.setArguments(bundle);
                pingLunDialogFragment.show(getSupportFragmentManager(), "xinxi", true);
            }
        });
        recyclerView.setAdapter(commentAdapter);
        getData();
        getComment();
    }

    private void getComment() {
        Map<String, String> map = new HashMap<>();
        map.put("page", page + "");
        map.put("type", "3");
        map.put("id", alldata.getId());
        HttpClient.getInstance().gets(HttpUtil.APICOMMENT, map, new TradeHttpCallback<JsonBean<CommentBean>>() {
            @Override
            public void onSuccess(JsonBean<CommentBean> data) {
                if (null == data || null == data.getData()) {
                    refreshLayout.finishRefresh();
                    refreshLayout.finishLoadMore();
                    return;
                }
                if (page > 1) {
                    refreshLayout.finishLoadMore();
                    if (data.getData().getData().size() == 0) {
                        Toaster.show("暂无更多数据");
                        return;
                    }
                    commentAdapter.addData(data.getData().getData());
                } else {
                    not.setVisibility(data.getData().getData().size() > 0 ? View.GONE : View.VISIBLE);
                    comments.setText("评论 " + data.getData().getTotal());
                    refreshLayout.finishRefresh();
                    commentAdapter.replaceAll(data.getData().getData());
                }
                page++;
            }

            @Override
            public void onError(Response<JsonBean<CommentBean>> response) {
                super.onError(response);
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
            }
        });
    }

    @OnClick({R.id.pinglun, R.id.fenxiang, R.id.baoming, R.id.faqiren, R.id.pinglunimg, R.id.is_good, R.id.is_collection})
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.pinglun) {
            PingLunTopDialogFragment addCommentDialogFragment = new PingLunTopDialogFragment();
            Bundle bundle = new Bundle();
            bundle.putString("id", alldata.getId());
            bundle.putString("type", "3");
            addCommentDialogFragment.setArguments(bundle);
            addCommentDialogFragment.setOnNoticeListener(new PingLunTopDialogFragment.OnNoticeListener() {
                @Override
                public void setNoticeListener(String mEntity) {
                    page = 1;
                    getComment();
                }
            });
            addCommentDialogFragment.showDialog(getSupportFragmentManager());
        } else if (id == R.id.fenxiang) {
            FenXiangDialogFragment fenXiangDialogFragment = new FenXiangDialogFragment();
            Bundle bundle = new Bundle();
            bundle.putString("title",alldata.getTitle());
            bundle.putString("content",alldata.getContent());
            bundle.putString("action_id", alldata.getId());
            bundle.putString("type","3");
            bundle.putString("share_link",alldata.getShare_link());
            fenXiangDialogFragment.setArguments(bundle);
            fenXiangDialogFragment.show(getSupportFragmentManager(), "xinxi", true);
        } else if (id == R.id.baoming) {
            if ("0".equals(alldata.getIs_registered())) {
                Map<String, String> map = new HashMap<>();
                map.put("activity_id", alldata.getId());
                HttpClient.getInstance().posts(HttpUtil.ACTIVITYSIGNUP, map, new TradeHttpCallback<JsonBean<String>>() {
                    @Override
                    public void onSuccess(JsonBean<String> data) {
                        if (null == data || null == data.getData()) {
                            return;
                        }
                        getData();
                    }

                    @Override
                    public boolean showLoadingDialog() {
                        return true;
                    }
                });
            } else if ("1".equals(alldata.getIs_registered())) {
                if (TextUtils.isEmpty(alldata.getAfter_content())) {
                    return;
                }
                YuYueDialog payDialog = new YuYueDialog(this, alldata.getAfter_content());
                payDialog.show();
            }
        } else if (id == R.id.faqiren) {
            if(TextUtils.isEmpty(alldata.getMember_id())){
                return;
            }
            Intent intent = new Intent(this, ZhuYe.class);
            intent.putExtra("id", alldata.getMember_id());
            startActivity(intent);
        } else if (id == R.id.pinglunimg) {
            int[] ln = new int[2];
            commentsview.getLocationInWindow(ln);
            int position = ln[1] - mTitleBar.getHeight();
            if (position < 0) {
                position = 0;
            }
            mSV.scrollTo(0, position);
        } else if (id == R.id.is_good) {
            Map<String, String> map = new HashMap<>();
            map.put("type", "3");
            map.put("id", alldata.getId());
            HttpClient.getInstance().gets(HttpUtil.THUMBSUPDOLIKE, map, new TradeHttpCallback<JsonBean<JiangLiBean>>() {
                @Override
                public void onSuccess(JsonBean<JiangLiBean> data) {
                    if (null == data || null == data.getData()) {
                        return;
                    }
                    if(!TextUtils.isEmpty(data.getData().getPoint())&&Integer.parseInt(data.getData().getPoint())>0){
                        JiFenDialog payDialog = new JiFenDialog(HuoDongDetail.this,"点赞成功获得"+data.getData().getPoint()+"积分");
                        payDialog.show();
                    }
                    if ("0".equals(is_good.getTag())) {
                        is_good.setTag("1");
                        is_good.setImageResource(R.mipmap.img30);
                    } else {
                        is_good.setTag("0");
                        is_good.setImageResource(R.mipmap.img29);
                    }

                }

            });
        } else if (id == R.id.is_collection) {
            Map<String, String> map = new HashMap<>();
            map.put("type", "3");
            map.put("action_id", alldata.getId());
            HttpClient.getInstance().gets(HttpUtil.COLLECTIONSAVE, map, new TradeHttpCallback<JsonBean<String>>() {
                @Override
                public void onSuccess(JsonBean<String> data) {
                    if (null == data || null == data.getData()) {
                        return;
                    }
                    if ("0".equals(is_collection.getTag())) {
                        is_collection.setTag("1");
                        is_collection.setImageResource(R.mipmap.img68);
                    } else {
                        is_collection.setTag("0");
                        is_collection.setImageResource(R.mipmap.img31);
                    }

                }

            });
        }
    }

    private void getData() {
        Map<String, String> map = new HashMap<>();
        map.put("id", alldata.getId());
        HttpClient.getInstance().gets(HttpUtil.ACTIVITYDETAIL, map, new TradeHttpCallback<JsonBean<HuoDongDetailBean>>() {
            @Override
            public void onSuccess(JsonBean<HuoDongDetailBean> data) {
                if (null == data || null == data.getData()) {
                    return;
                }
                alldata = data.getData();
                web.loadDataWithBaseURL(Constants.HOST, HTML_STYLE + alldata.getContent(), "text/html",
                        "utf-8", null);
                is_good.setTag(alldata.getIs_good());
                is_good.setImageResource("1".equals(alldata.getIs_good()) ? R.mipmap.img30 : R.mipmap.img29);
                setUI();
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
                                  GlideUtil.showImg(HuoDongDetail.this, data, holder.imageView);
                              }
                          }
                )
                .setOnBannerListener(new OnBannerListener<String>() {
                    @Override
                    public void OnBannerClick(String data, int position) {
                        Intent intent = new Intent(HuoDongDetail.this,
                                Photo.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("position", 0);
                        bundle.putSerializable("list", (Serializable) imglist);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                })
                .addBannerLifecycleObserver(HuoDongDetail.this)//添加生命周期观察者
                .setIndicator(new CircleIndicator(HuoDongDetail.this));
        title.setText(alldata.getTitle());
        if (null!=alldata.getTags()) {
           // String[] stringList = alldata.getTags().split(",");
            des.setAdapter(new DesAdapter(HuoDongDetail.this, alldata.getTags(), new DesAdapter.OnNoticeListener() {
                @Override
                public void setNoticeListener(int id, int position, String mydata) {

                }
            }));
        }
        if(alldata.getLink()!=null){
            name.setText(alldata.getLink().getName());
        }
        special.setText(TextUtils.isEmpty(alldata.getSpecial())?"暂无":alldata.getSpecial());
        if ("0".equals(alldata.getIs_registered())) {
            baoming.setBackgroundResource(R.drawable.r_lan50);
            baoming.setText("报名活动");
            baoming.setTextColor(getResources().getColor(R.color.white));
        } else {
            baoming.setBackgroundResource(R.drawable.r_baoming);
            baoming.setText("已报名");
            baoming.setTextColor(getResources().getColor(R.color.color_666666));
        }
        if ("normal".equals(alldata.getStatus())) {
            status.setText("未开始");
        } else if ("ing".equals(alldata.getStatus())) {
            status.setText("进行中");
        } else if ("hidden".equals(alldata.getStatus())) {
            baoming.setBackgroundResource(R.drawable.r_baoming);
            baoming.setText("已结束");
            baoming.setTextColor(getResources().getColor(R.color.color_666666));
            status.setText("已结束");
        }
        start_time.setText(alldata.getStart_time() + " ~ " + alldata.getEnd_time());
        registered_number.setText(alldata.getRegistered_number());
        number.setText("/" + alldata.getNumber() + "人");
        if ("0".equals(alldata.getMember().getIs_enterprise())) {
            nickname.setText(alldata.getMember().getNickname());
        } else {
            nickname.setText(alldata.getMember().getNickname() + "（已认证）");
        }

        good.setText(alldata.getGood() + " 点赞");
        forward.setText(alldata.getForward() + " 转发");
        is_collection.setTag(alldata.getIs_collection());
        is_collection.setImageResource("1".equals(alldata.getIs_collection()) ? R.mipmap.img68 : R.mipmap.img31);
    }
}
