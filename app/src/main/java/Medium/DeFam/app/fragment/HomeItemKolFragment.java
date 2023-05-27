package Medium.DeFam.app.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

import Medium.DeFam.app.R;
import Medium.DeFam.app.adapter.HomeItemKolAdapter;
import Medium.DeFam.app.common.base.BaseFragment;
import butterknife.BindView;

public class HomeItemKolFragment extends BaseFragment {
    public static HomeItemKolFragment newInstance(int type) {
        HomeItemKolFragment fragment = new HomeItemKolFragment();
        Bundle args = new Bundle();
        args.putInt("type", type);
        fragment.setArguments(args);
        return fragment;
    }
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    HomeItemKolAdapter adapter;
    private int page = 1;
    @Override
    public int getLayoutId() {
        return R.layout.fragment_homeitemkol;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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
        adapter = new HomeItemKolAdapter(getContext());
        recyclerView.setAdapter(adapter);
        //数据
        initString();
    }
    private void initString() {
   /*     List<String> stringList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {

            stringList.add("");
        }
        adapter.replaceAll(stringList);*/
    }
    private void getData() {
       /* Map<String, String> map = new HashMap<>();
        map.put("page", page + "");
        map.put("page_size", "20");
        map.put("goods_custom_recommend_tag_id", goods_custom_recommend_tag_id);
        HttpClient.getInstance().gets(MallHttpUtil.GOODSCUSTOMRECOMMENDLIST, map, new TradeHttpCallback<JsonBean<GoodsBean>>() {
            @Override
            public void onSuccess(JsonBean<GoodsBean> data) {
                if (null == data || null == data.getData()) {
                    return;
                }
                if (page > 1) {
                    if (data.getData().getData().size() == 0) {
                        Toaster.show("暂无更多数据");
                        return;
                    }
                    adapter.setLoadmoreBean(data.getData().getData());
                } else {
                    adapter.setRefreshBean(data.getData().getData());
                }
                page++;
            }


            @Override
            public void onError(Response<JsonBean<GoodsBean>> response) {
                super.onError(response);
                adapter.changeState(0);
            }
        });*/
    }

}
