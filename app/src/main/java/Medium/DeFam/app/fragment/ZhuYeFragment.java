package Medium.DeFam.app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Medium.DeFam.app.R;
import Medium.DeFam.app.activity.WenZhangDetail;
import Medium.DeFam.app.adapter.WenZhangAdapter;
import Medium.DeFam.app.adapter.ZhuYeAdapter;
import Medium.DeFam.app.bean.WenZhangBean;
import Medium.DeFam.app.bean.WenZhangDetailBean;
import Medium.DeFam.app.common.base.BaseFragment;
import Medium.DeFam.app.common.http.HttpClient;
import Medium.DeFam.app.common.http.JsonBean;
import Medium.DeFam.app.common.http.TradeHttpCallback;
import Medium.DeFam.app.common.utils.ToastUtil;
import Medium.DeFam.app.utils.HttpUtil;
import butterknife.BindView;


public class ZhuYeFragment extends BaseFragment {
    public static ZhuYeFragment newInstance(String type, String member_id, boolean load) {
        ZhuYeFragment fragment = new ZhuYeFragment();
        Bundle args = new Bundle();
        args.putString("type", type);
        args.putString("member_id", member_id);
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
    ZhuYeAdapter adapter;
    private int page = 1;
    private String type, member_id;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_zhuye;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void initData() {
        super.initData();
        type = getArguments().getString("type");
        member_id = getArguments().getString("member_id");
        boolean load = getArguments().getBoolean("load", false);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                page = 1;
                getData();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                getData();
            }
        });
        adapter = new ZhuYeAdapter(getContext());
        adapter.setRecyclerViewOnItemClickListener(new ZhuYeAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(int pos, int type, WenZhangDetailBean data) {
                Intent intent = new Intent(getActivity(), WenZhangDetail.class);
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
        map.put("type", type);
        map.put("member_id", member_id);
        HttpClient.getInstance().gets(HttpUtil.MEMBERARTICLE, map, new TradeHttpCallback<JsonBean<WenZhangBean>>() {
            @Override
            public void onSuccess(JsonBean<WenZhangBean> data) {
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
            public void onError(Response<JsonBean<WenZhangBean>> response) {
                super.onError(response);
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
            }
        });
    }

}
