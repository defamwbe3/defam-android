package Medium.DeFam.app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lzy.okgo.model.Response;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.HashMap;
import java.util.Map;

import Medium.DeFam.app.R;
import Medium.DeFam.app.activity.ZiXunDetail;
import Medium.DeFam.app.adapter.HomeItemAdapter;
import Medium.DeFam.app.bean.ZiXunBean;
import Medium.DeFam.app.bean.ZiXunDetailBean;
import Medium.DeFam.app.common.base.BaseFragment;
import Medium.DeFam.app.common.http.HttpClient;
import Medium.DeFam.app.common.http.JsonBean;
import Medium.DeFam.app.common.http.TradeHttpCallback;
import Medium.DeFam.app.common.utils.ToastUtil;
import Medium.DeFam.app.utils.HttpUtil;
import butterknife.BindView;


public class HomeItemFragment extends BaseFragment {
    public static HomeItemFragment newInstance(String status) {
        HomeItemFragment fragment = new HomeItemFragment();
        Bundle args = new Bundle();
        args.putString("status", status);
        fragment.setArguments(args);
        return fragment;
    }

    public static HomeItemFragment newInstance(String status, boolean load) {
        HomeItemFragment fragment = new HomeItemFragment();
        Bundle args = new Bundle();
        args.putString("status", status);
        args.putBoolean("load", load);
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    HomeItemAdapter adapter;
    @BindView(R.id.not)
    View not;
    private int page = 1;
    private String status;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_homeitem;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void initData() {
        super.initData();
        status = getArguments().getString("status");
        boolean load = getArguments().getBoolean("load", false);
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
        adapter = new HomeItemAdapter(getContext());
        adapter.setRecyclerViewOnItemClickListener(new HomeItemAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(int pos, int type, ZiXunDetailBean data) {
                Intent intent = new Intent(getActivity(), ZiXunDetail.class);
                intent.putExtra("data", data);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
        if (load) {
            getData();
        }
    }

    private void getData() {
        Map<String, String> map = new HashMap<>();
        map.put("page", page + "");
        HttpClient.getInstance().gets(HttpUtil.INDEXREALINFO, map, new TradeHttpCallback<JsonBean<ZiXunBean>>() {
            @Override
            public void onSuccess(JsonBean<ZiXunBean> data) {
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
                    not.setVisibility(data.getData().getData().size() > 0 ? View.GONE : View.VISIBLE);
                    refreshLayout.finishRefresh();
                    adapter.replaceAll(data.getData().getData());
                }
                page++;
            }

            @Override
            public void onError(Response<JsonBean<ZiXunBean>> response) {
                super.onError(response);
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
            }
        });
    }

}
