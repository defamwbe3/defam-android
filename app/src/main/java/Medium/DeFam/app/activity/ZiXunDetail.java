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

import java.util.HashMap;
import java.util.Map;

import Medium.DeFam.app.R;
import Medium.DeFam.app.adapter.CommentAdapter;
import Medium.DeFam.app.adapter.TuiJianAdapter;
import Medium.DeFam.app.bean.CommentBean;
import Medium.DeFam.app.bean.JiangLiBean;
import Medium.DeFam.app.bean.WenZhangBean;
import Medium.DeFam.app.bean.WenZhangDetailBean;
import Medium.DeFam.app.bean.ZiXunDetailBean;
import Medium.DeFam.app.common.Constants;
import Medium.DeFam.app.common.base.BaseActivity;
import Medium.DeFam.app.common.http.HttpClient;
import Medium.DeFam.app.common.http.JsonBean;
import Medium.DeFam.app.common.http.TradeHttpCallback;
import Medium.DeFam.app.dialog.FenXiangDialogFragment;
import Medium.DeFam.app.dialog.JiFenDialog;
import Medium.DeFam.app.dialog.PingLunDialogFragment;
import Medium.DeFam.app.dialog.PingLunTopDialogFragment;
import Medium.DeFam.app.utils.HttpUtil;
import Medium.DeFam.app.view.MyTextView;
import butterknife.BindView;
import butterknife.OnClick;

public class ZiXunDetail extends BaseActivity {
    @BindView(R.id.scroll_view)
    NestedScrollView mSV;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.not)
    View not;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.title)
    MyTextView title;
    @BindView(R.id.author)
    MyTextView author;
    @BindView(R.id.created_at)
    MyTextView created_at;
    @BindView(R.id.read)
    MyTextView read;
    @BindView(R.id.web)
    WebView web;
    @BindView(R.id.comments)
    TextView comments;
    @BindView(R.id.good)
    TextView good;
    @BindView(R.id.forward)
    TextView forward;
    @BindView(R.id.is_good)
    ImageView is_good;
    @BindView(R.id.is_collection)
    ImageView is_collection;
    @BindView(R.id.commentsview)
    LinearLayout commentsview;
    @BindView(R.id.tuijian_recyclerView)
    RecyclerView tuijian_recyclerView;
    CommentAdapter commentAdapter;
    TuiJianAdapter tuiJianAdapter;
    private int page = 1;
    private ZiXunDetailBean alldata;
    private final String HTML_STYLE = "<style type=\"text/css\">* {font-size:16px}" +
            "img,iframe,video,table,div {height:auto; max-width:100%; width:100% !important; word-break:break-all;} </style>";//bak:

    @Override
    protected boolean isNeedLogin() {
        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_zixundetail;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState, @Nullable Bundle bundle) {
        refreshLayout.setEnableRefresh(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tuijian_recyclerView.setLayoutManager(new LinearLayoutManager(this));
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
        alldata = (ZiXunDetailBean) getIntent().getSerializableExtra("data");
        String myid = getIntent().getStringExtra("id");
        if(null==alldata&&TextUtils.isEmpty(myid)){
            finish();
           return;
        }
        if(!TextUtils.isEmpty(myid)){
            alldata = new ZiXunDetailBean();
            alldata.setId(myid);
        }else {
            setUI();
        }

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
                bundle.putString("type","1");
                pingLunDialogFragment.setArguments(bundle);
                pingLunDialogFragment.show(getSupportFragmentManager(), "xinxi", true);
            }
        });
        recyclerView.setAdapter(commentAdapter);
        tuiJianAdapter = new TuiJianAdapter(this);
        tuiJianAdapter.setRecyclerViewOnItemClickListener(new TuiJianAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(int pos, int type, WenZhangDetailBean data) {
                Intent intent = new Intent(ZiXunDetail.this, ZiXunDetail.class);
                intent.putExtra("id", data.getId());
                startActivity(intent);
                finish();
            }
        });
        tuijian_recyclerView.setAdapter(tuiJianAdapter);
        getData();
        getComment();
        getTuiJianData();
    }
    private void getTuiJianData() {
        HttpClient.getInstance().gets(HttpUtil.RECOMMENDLIST, null, new TradeHttpCallback<JsonBean<WenZhangBean>>() {
            @Override
            public void onSuccess(JsonBean<WenZhangBean> data) {
                if (null == data || null == data.getData()) {
                    return;
                }
                tuiJianAdapter.replaceAll(data.getData().getData());

            }

        });
    }

    private void getComment() {
        Map<String, String> map = new HashMap<>();
        map.put("page", page + "");
        map.put("type", "1");
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

    @OnClick({R.id.pinglun, R.id.fenxiang, R.id.is_good, R.id.is_collection, R.id.pinglunimg})
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.pinglun) {
            if(TextUtils.isEmpty(alldata.getId())){
                return;
            }
            PingLunTopDialogFragment addCommentDialogFragment = new PingLunTopDialogFragment();
            Bundle bundle = new Bundle();
            bundle.putString("id", alldata.getId());
            bundle.putString("type","1");
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
            bundle.putString("type","1");
            bundle.putString("share_link",alldata.getShare_link());
            fenXiangDialogFragment.setArguments(bundle);
            fenXiangDialogFragment.show(getSupportFragmentManager(), "xinxi", true);
        } else if (id == R.id.is_good) {
            Map<String, String> map = new HashMap<>();
            map.put("type", "1");
            map.put("id", alldata.getId());
            HttpClient.getInstance().gets(HttpUtil.THUMBSUPDOLIKE, map, new TradeHttpCallback<JsonBean<JiangLiBean>>() {
                @Override
                public void onSuccess(JsonBean<JiangLiBean> data) {
                    if (null == data || null == data.getData()) {
                        return;
                    }
                    if(!TextUtils.isEmpty(data.getData().getPoint())&&Integer.parseInt(data.getData().getPoint())>0){
                        JiFenDialog payDialog = new JiFenDialog(ZiXunDetail.this,"点赞成功获得"+data.getData().getPoint()+"积分");
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
            map.put("type", "1");
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
        } else if (id == R.id.pinglunimg) {
            int[] ln = new int[2];
            commentsview.getLocationInWindow(ln);
            int position = ln[1] - mTitleBar.getHeight();
            if (position < 0) {
                position = 0;
            }
            mSV.scrollTo(0, position);
        }
    }

    private void getData() {
        Map<String, String> map = new HashMap<>();
        map.put("id", alldata.getId());
        HttpClient.getInstance().gets(HttpUtil.REALINFOREAD, map, new TradeHttpCallback<JsonBean<ZiXunDetailBean>>() {
            @Override
            public void onSuccess(JsonBean<ZiXunDetailBean> data) {
                if (null == data || null == data.getData()) {
                    return;
                }
                alldata = data.getData();
                web.loadDataWithBaseURL(Constants.HOST, HTML_STYLE + alldata.getContent(), "text/html",
                        "utf-8", null);
                setUI();
            }

        });
    }

    private void setUI(){
        title.setText(alldata.getTitle());
        author.setText(alldata.getAuthor());
        created_at.setText(alldata.getCreated_at());
        read.setText(alldata.getRead() + "阅读");
        good.setText(alldata.getGood() + " 点赞");
        forward.setText(alldata.getForward() + " 转发");
        is_good.setTag(alldata.getIs_good());
        is_good.setImageResource("1".equals(alldata.getIs_good()) ? R.mipmap.img30 : R.mipmap.img29);
        is_collection.setTag(alldata.getIs_collection());
        is_collection.setImageResource("1".equals(alldata.getIs_collection()) ? R.mipmap.img68 : R.mipmap.img31);
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
