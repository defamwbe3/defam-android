package Medium.DeFam.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lzy.okgo.model.Response;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;

import java.util.HashMap;
import java.util.Map;

import Medium.DeFam.app.R;
import Medium.DeFam.app.adapter.ImageRadiusAdapter;
import Medium.DeFam.app.adapter.JiFenAdapter;
import Medium.DeFam.app.bean.BannerBean;
import Medium.DeFam.app.bean.JiFenDetailBean;
import Medium.DeFam.app.common.base.BaseActivity;
import Medium.DeFam.app.common.http.HttpClient;
import Medium.DeFam.app.common.http.JsonBean;
import Medium.DeFam.app.common.http.TradeHttpCallback;
import Medium.DeFam.app.common.titlebar.CommonTitleBar;
import Medium.DeFam.app.common.utils.ToastUtil;
import Medium.DeFam.app.common.widget.NoScrollGridView;
import Medium.DeFam.app.utils.HttpUtil;
import butterknife.BindView;
import butterknife.OnClick;

public class JiFenShop extends BaseActivity {
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.listview)
    NoScrollGridView listview;
    @BindView(R.id.banner)
    Banner banner;
    private JiFenAdapter adapter;
    private int page = 1;

    @Override
    protected boolean isNeedLogin() {
        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_jifenshop;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState, @Nullable Bundle bundle) {
        View notview = findViewById(R.id.not);
        listview.setEmptyView(notview);
    }

    @Override
    protected void initData() {
        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                getData();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                getData();
            }
        });
        adapter = new JiFenAdapter(this);
        adapter.setOnNoticeListener(new JiFenAdapter.OnNoticeListener() {
            @Override
            public void setNoticeListener(int id, int position, JiFenDetailBean data) {
                Intent intent = new Intent(JiFenShop.this, JiFenDetail.class);
                intent.putExtra("data", data);
                startActivity(intent);
            }
        });
        listview.setAdapter(adapter);
        getData();
        useBanner();
    }

    private void useBanner() {
        Map<String, String> map = new HashMap<>();
        map.put("code", "shop");
        HttpClient.getInstance().gets(HttpUtil.BANNER, map, new TradeHttpCallback<JsonBean<BannerBean>>() {
            @Override
            public void onSuccess(JsonBean<BannerBean> data) {
                banner.setAdapter(new ImageRadiusAdapter(JiFenShop.this, data.getData().getData()))
                        .addBannerLifecycleObserver(JiFenShop.this)//添加生命周期观察者
                        .setIndicator(new CircleIndicator(JiFenShop.this));

            }
        });
    }

    @Override
    protected void onTitleListener(View v, int action, String extra) {
        if (action == CommonTitleBar.ACTION_LEFT_BUTTON) {
            finish();
        } else if (action == CommonTitleBar.ACTION_RIGHT_BUTTON) {
            Intent intent = new Intent(this, JiFenJiLu.class);
            startActivity(intent);
        }
    }


    @OnClick({R.id.duihuan})
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.duihuan) {
            Intent intent = new Intent(this, JiFenJiLu.class);
            startActivity(intent);
        }
    }

    private void getData() {
        Map<String, String> map = new HashMap<>();
        map.put("page", page + "");
        HttpClient.getInstance().gets(HttpUtil.APIGOODS, map, new TradeHttpCallback<JsonBean<JiFenShopBean>>() {
            @Override
            public void onSuccess(JsonBean<JiFenShopBean> data) {
                if (null == data || null == data.getData()) {
                    refreshLayout.finishRefresh();
                    refreshLayout.finishLoadMore();
                    return;
                }
                if (page > 1) {
                    refreshLayout.finishLoadMore();
                    if (data.getData().getData().size() == 0) {
                        ToastUtil.initToast("暂无更多数据");
                        return;
                    }
                    adapter.addData(data.getData().getData());
                } else {
                    refreshLayout.finishRefresh();
                    adapter.replaceAll(data.getData().getData());
                }
                page++;
            }

            @Override
            public void onError(Response<JsonBean<JiFenShopBean>> response) {
                super.onError(response);
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
            }
        });
    }
}
