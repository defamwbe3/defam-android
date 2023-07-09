package Medium.DeFam.app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.NonNull;

import com.hjq.toast.Toaster;
import com.lzy.okgo.model.Response;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.HashMap;
import java.util.Map;

import Medium.DeFam.app.R;
import Medium.DeFam.app.activity.JiFenShop;
import Medium.DeFam.app.activity.JiangLiAdapter;
import Medium.DeFam.app.activity.Web;
import Medium.DeFam.app.bean.InfoBean;
import Medium.DeFam.app.bean.JiFenBean;
import Medium.DeFam.app.common.base.BaseFragment;
import Medium.DeFam.app.common.http.HttpClient;
import Medium.DeFam.app.common.http.JsonBean;
import Medium.DeFam.app.common.http.TradeHttpCallback;
import Medium.DeFam.app.utils.HttpUtil;
import Medium.DeFam.app.view.CountView;
import butterknife.BindView;
import butterknife.OnClick;

public class JiangLiFragment extends BaseFragment {
    public static JiangLiFragment newInstance(int type) {
        JiangLiFragment fragment = new JiangLiFragment();
        Bundle args = new Bundle();
        args.putInt("type", type);
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.listview)
    ListView listview;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.integral)
    CountView integral;
    private int page = 1;
    private JiangLiAdapter adapter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_jiangli;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        View notview = view.findViewById(R.id.not);
        listview.setEmptyView(notview);
    }

    @Override
    public void initData() {
        super.initData();
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
        adapter = new JiangLiAdapter(getActivity());
        listview.setAdapter(adapter);

        getData();
        getTopData();
    }

    private void getTopData() {
        HttpClient.getInstance().gets(HttpUtil.MEMBERINFO, null, new TradeHttpCallback<JsonBean<InfoBean>>() {
            @Override
            public void onSuccess(JsonBean<InfoBean> data) {
                if (null == data || null == data.getData()) {
                    return;
                }
                integral.setOnEnd(new CountView.EndListener() {
                    @Override
                    public void onEndFinish() {
                        integral.setText(data.getData().getIntegral());
                    }
                });
                integral.withNumber(Float.parseFloat(data.getData().getIntegral())).start();
            }
        });
    }


    private void getData() {
        Map<String, String> map = new HashMap<>();
        map.put("page", page + "");
        HttpClient.getInstance().gets(HttpUtil.FINANCE, map, new TradeHttpCallback<JsonBean<JiFenBean>>() {
            @Override
            public void onSuccess(JsonBean<JiFenBean> data) {
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
                    refreshLayout.finishRefresh();
                    adapter.replaceAll(data.getData().getData());
                }
                page++;
            }

            @Override
            public void onError(Response<JsonBean<JiFenBean>> response) {
                super.onError(response);
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
            }
        });
    }

    @OnClick({R.id.guize, R.id.shop})
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.guize) {
            Map<String, String> map = new HashMap<>();
            map.put("code", "point");
            HttpClient.getInstance().gets(HttpUtil.AGREEMENT, map, new TradeHttpCallback<JsonBean<String>>() {
                @Override
                public void onSuccess(JsonBean<String> data) {
                    Web.startWebActivity(getActivity(), "DD规则", "", data.getData());

                }

                @Override
                public boolean showLoadingDialog() {
                    return true;
                }
            });
        } else if (id == R.id.shop) {
            Intent intent = new Intent(getActivity(), JiFenShop.class);
            startActivity(intent);
        }
    }
}
