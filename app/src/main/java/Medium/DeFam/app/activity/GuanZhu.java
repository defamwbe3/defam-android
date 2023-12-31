package Medium.DeFam.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hjq.toast.Toaster;
import com.lzy.okgo.model.Response;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.HashMap;
import java.util.Map;

import Medium.DeFam.app.R;
import Medium.DeFam.app.adapter.GuanZhuAdapter;
import Medium.DeFam.app.bean.GuanZhuBean;
import Medium.DeFam.app.common.base.BaseActivity;
import Medium.DeFam.app.common.http.HttpClient;
import Medium.DeFam.app.common.http.JsonBean;
import Medium.DeFam.app.common.http.TradeHttpCallback;
import Medium.DeFam.app.utils.HttpUtil;
import butterknife.BindView;


public class GuanZhu extends BaseActivity {
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    GuanZhuAdapter adapter;
    @BindView(R.id.not)
    View not;
    private int page = 1;

    @Override
    protected boolean isNeedLogin() {
        return false;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_guanzhu;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState, @Nullable Bundle bundle) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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
        adapter = new GuanZhuAdapter(this);
        adapter.setRecyclerViewOnItemClickListener(new GuanZhuAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(int pos, int type, GuanZhuBean.DataBean data) {
                Intent intent = new Intent(GuanZhu.this, ZhuYe.class);
                intent.putExtra("id", data.getTo_member_id());
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
        //数据
        getData();
    }

    private void getData() {
        Map<String, String> map = new HashMap<>();
        map.put("page", page + "");
        HttpClient.getInstance().gets(HttpUtil.FOLLOWLIST, map, new TradeHttpCallback<JsonBean<GuanZhuBean>>() {
            @Override
            public void onSuccess(JsonBean<GuanZhuBean> data) {
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
                    adapter.addData(data.getData().getData());
                } else {
                    not.setVisibility(data.getData().getData().size() > 0 ? View.GONE : View.VISIBLE);

                    refreshLayout.finishRefresh();
                    adapter.replaceAll(data.getData().getData());
                }
                page++;
            }

            @Override
            public void onError(Response<JsonBean<GuanZhuBean>> response) {
                super.onError(response);
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
            }
        });
    }


}
