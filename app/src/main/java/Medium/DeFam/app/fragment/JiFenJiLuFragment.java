package Medium.DeFam.app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.lzy.okgo.model.Response;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;

import java.util.HashMap;
import java.util.Map;

import Medium.DeFam.app.R;
import Medium.DeFam.app.activity.JiFenJiLuDetail;
import Medium.DeFam.app.adapter.JiFenJiLuAdapter;
import Medium.DeFam.app.bean.JiFenJiLuBean;
import Medium.DeFam.app.bean.JiFenJiLuDetailBean;
import Medium.DeFam.app.common.base.BaseFragment;
import Medium.DeFam.app.common.http.HttpClient;
import Medium.DeFam.app.common.http.JsonBean;
import Medium.DeFam.app.common.http.TradeHttpCallback;
import Medium.DeFam.app.common.utils.ToastUtil;
import Medium.DeFam.app.utils.HttpUtil;
import butterknife.BindView;

public class JiFenJiLuFragment extends BaseFragment {
    public static JiFenJiLuFragment newInstance(String status, boolean load) {
        JiFenJiLuFragment fragment = new JiFenJiLuFragment();
        Bundle args = new Bundle();
        args.putString("status", status);
        args.putBoolean("load", load);
        fragment.setArguments(args);
        return fragment;
    }
    @BindView(R.id.listview)
    ListView listview;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private int page = 1;
    private JiFenJiLuAdapter adapter;
    private String status;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_jifenjilu;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        View notview = view.findViewById(R.id.not);
        listview.setEmptyView(notview);
    }
    @Override
    public void initData() {
        super.initData();
        status = getArguments().getString("status");
        boolean load = getArguments().getBoolean("load", false);
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                getData();
            }
        });
        adapter = new JiFenJiLuAdapter(getActivity());
        adapter.setOnNoticeListener(new JiFenJiLuAdapter.OnNoticeListener() {
            @Override
            public void setNoticeListener(int id, int position, JiFenJiLuDetailBean data) {
                Intent intent = new Intent(getActivity(), JiFenJiLuDetail.class);
                intent.putExtra("data",data);
                startActivity(intent);
            }
        });
        listview.setAdapter(adapter);
        if (load) {
            getData();
        }
    }
    public void loadData() {
        if (adapter.getCount() == 0) {
            //数据
            getData();
        }
    }
    private void getData() {
        Map<String, String> map = new HashMap<>();
        map.put("page", page + "");
        map.put("status", status);
        HttpClient.getInstance().gets(HttpUtil.APIORDER, map, new TradeHttpCallback<JsonBean<JiFenJiLuBean>>() {
            @Override
            public void onSuccess(JsonBean<JiFenJiLuBean> data) {
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
            public void onError(Response<JsonBean<JiFenJiLuBean>> response) {
                super.onError(response);
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
            }
        });
    }

}
