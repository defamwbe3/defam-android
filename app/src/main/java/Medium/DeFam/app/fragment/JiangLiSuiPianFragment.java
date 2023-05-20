package Medium.DeFam.app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

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
import Medium.DeFam.app.activity.BaoZangDetail;
import Medium.DeFam.app.activity.DuiHuan;
import Medium.DeFam.app.activity.DuiHuanDetail;
import Medium.DeFam.app.adapter.DuiHuanAdapter;
import Medium.DeFam.app.adapter.JiangLiSuiPianAdapter;
import Medium.DeFam.app.bean.SuiPianBean;
import Medium.DeFam.app.bean.WalletAddressBean;
import Medium.DeFam.app.common.base.BaseFragment;
import Medium.DeFam.app.common.http.HttpClient;
import Medium.DeFam.app.common.http.JsonBean;
import Medium.DeFam.app.common.http.TradeHttpCallback;
import Medium.DeFam.app.common.utils.ToastUtil;
import Medium.DeFam.app.utils.HttpUtil;
import butterknife.BindView;

public class JiangLiSuiPianFragment extends BaseFragment {
    public static JiangLiSuiPianFragment newInstance(int type) {
        JiangLiSuiPianFragment fragment = new JiangLiSuiPianFragment();
        Bundle args = new Bundle();
        args.putInt("type", type);
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.listview)
    GridView listview;
    private JiangLiSuiPianAdapter adapter;
    private int page = 1;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_jianglisuipian;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        View notview = view.findViewById(R.id.not);
        listview.setEmptyView(notview);
    }

    @Override
    public void initData() {
        super.initData();
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
        adapter = new JiangLiSuiPianAdapter(getActivity());
        listview.setAdapter(adapter);
        getData();
    }


    private void getData() {
        Map<String, String> map = new HashMap<>();
        map.put("page", page + "");
        HttpClient.getInstance().gets(HttpUtil.FRAGMENTLIST, map, new TradeHttpCallback<JsonBean<SuiPianBean>>() {
            @Override
            public void onSuccess(JsonBean<SuiPianBean> data) {
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
            public void onError(Response<JsonBean<SuiPianBean>> response) {
                super.onError(response);
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
            }
        });
    }
}
