package Medium.DeFam.app.fragment;

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
import Medium.DeFam.app.adapter.KOLItemAdapter;
import Medium.DeFam.app.bean.KOLBean;
import Medium.DeFam.app.common.base.BaseFragment;
import Medium.DeFam.app.common.http.HttpClient;
import Medium.DeFam.app.common.http.JsonBean;
import Medium.DeFam.app.common.http.TradeHttpCallback;
import Medium.DeFam.app.utils.HttpUtil;
import butterknife.BindView;

public class KOLItemFragment extends BaseFragment {

    public static KOLItemFragment newInstance(String title,String category_id, boolean load) {
        KOLItemFragment fragment = new KOLItemFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("category_id", category_id);
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
    KOLItemAdapter adapter;
    private int page = 1;
    private String title,category_id;
    @Override
    public int getLayoutId() {
        return R.layout.fragment_zixunitem;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void initData() {
        super.initData();
        title = getArguments().getString("title");
        category_id = getArguments().getString("category_id");
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
        adapter = new KOLItemAdapter(getContext());
        adapter.setRecyclerViewOnItemClickListener(new KOLItemAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(int pos, int type, KOLBean.DataBean data) {
                /*Intent intent = new Intent(getActivity(), ZiXunDetail.class);
                intent.putExtra("data",data);
                startActivity(intent);*/
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
        map.put("name", title);
        //map.put("category_id", category_id);
        HttpClient.getInstance().gets(HttpUtil.KOLLIST, map, new TradeHttpCallback<JsonBean<KOLBean>>() {
            @Override
            public void onSuccess(JsonBean<KOLBean> data) {
                if (null == data || null == data.getData()) {
                    refreshLayout.finishRefresh();
                    refreshLayout.finishLoadMore();
                    return;
                }

               /* if (page > 1) {
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
                page++;*/
            }
            @Override
            public void onError(Response<JsonBean<KOLBean>> response) {
                super.onError(response);
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
            }
        });
    }

}
