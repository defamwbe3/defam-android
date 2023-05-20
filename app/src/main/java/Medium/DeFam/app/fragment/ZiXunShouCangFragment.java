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
import Medium.DeFam.app.adapter.ZiXunShouCangAdapter;
import Medium.DeFam.app.bean.ShouCangZiXunBean;
import Medium.DeFam.app.bean.ZiXunDetailBean;
import Medium.DeFam.app.common.base.BaseFragment;
import Medium.DeFam.app.common.http.HttpClient;
import Medium.DeFam.app.common.http.JsonBean;
import Medium.DeFam.app.common.http.TradeHttpCallback;
import Medium.DeFam.app.common.utils.ToastUtil;
import Medium.DeFam.app.utils.HttpUtil;
import butterknife.BindView;

public class ZiXunShouCangFragment extends BaseFragment {

    public static ZiXunShouCangFragment newInstance(boolean load) {
        ZiXunShouCangFragment fragment = new ZiXunShouCangFragment();
        Bundle args = new Bundle();
        args.putBoolean("load", load);
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.not)
    View not;
    ZiXunShouCangAdapter adapter;
    private int page = 1;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_zixunshoucang;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void initData() {
        super.initData();
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
        adapter = new ZiXunShouCangAdapter(getContext());
        adapter.setRecyclerViewOnItemClickListener(new ZiXunShouCangAdapter.OnRecyclerViewItemClickListener() {
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

    public void loadData() {
        if (adapter.getItemCount() == 0) {
            //数据
            getData();
        }
    }

    private void getData() {
        Map<String, String> map = new HashMap<>();
        map.put("page", page + "");
        map.put("type", "1");
        HttpClient.getInstance().gets(HttpUtil.MEMBERCOLLECTIONLIST, map, new TradeHttpCallback<JsonBean<ShouCangZiXunBean>>() {
            @Override
            public void onSuccess(JsonBean<ShouCangZiXunBean> data) {
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
            public void onError(Response<JsonBean<ShouCangZiXunBean>> response) {
                super.onError(response);
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
            }
        });
    }

}
