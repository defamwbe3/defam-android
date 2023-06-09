package Medium.DeFam.app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hjq.toast.Toaster;
import com.lzy.okgo.model.Response;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Medium.DeFam.app.R;
import Medium.DeFam.app.activity.BaoZang;
import Medium.DeFam.app.activity.JiFenShop;
import Medium.DeFam.app.activity.Search;
import Medium.DeFam.app.activity.ZiXunDetail;
import Medium.DeFam.app.adapter.HomeHotAdapter;
import Medium.DeFam.app.adapter.HomeItemAdapter;
import Medium.DeFam.app.adapter.ImageAdapter;
import Medium.DeFam.app.bean.BannerBean;
import Medium.DeFam.app.bean.InfoBean;
import Medium.DeFam.app.bean.ZiXunBean;
import Medium.DeFam.app.bean.ZiXunDetailBean;
import Medium.DeFam.app.common.Constants;
import Medium.DeFam.app.common.base.BaseFragment;
import Medium.DeFam.app.common.bean.MessageEvent;
import Medium.DeFam.app.common.http.HttpClient;
import Medium.DeFam.app.common.http.JsonBean;
import Medium.DeFam.app.common.http.TradeHttpCallback;
import Medium.DeFam.app.common.them.Eyes;
import Medium.DeFam.app.utils.HttpUtil;
import Medium.DeFam.app.view.recycle.RecyclerViewDivider;
import butterknife.BindView;
import butterknife.OnClick;

public class HomeFragment extends BaseFragment {
    public static HomeFragment newInstance(int type) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putInt("type", type);
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.integral)
    TextView integral;
    @BindView(R.id.suipian_total)
    TextView suipian_total;

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.hot_rv)
    RecyclerView hotRV;
    HomeHotAdapter homeHotAdapter;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    HomeItemAdapter adapter;

    private int page = 1;

    @Override
    protected boolean useEventBus() {
        return true;
    }

    @Override
    protected void onEventComing(MessageEvent event) {
        super.onEventComing(event);
        if (event.getCode() == Constants.INFO) {
            integral.setText(((InfoBean) event.getData()).getIntegral());
            suipian_total.setText(((InfoBean) event.getData()).getSuipian_total());
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        view.findViewById(R.id.topview).setPadding(0, Eyes.getStatusBarHeight(getActivity()), 0, 0);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        RecyclerViewDivider rd = new RecyclerViewDivider(getActivity(), LinearLayoutManager.VERTICAL);
        rd.setDividerHeight(0);
        hotRV.addItemDecoration(rd);
        hotRV.setLayoutManager(layoutManager);
        homeHotAdapter = new HomeHotAdapter(getActivity());
        hotRV.setAdapter(homeHotAdapter);
        homeHotAdapter.setRecyclerViewOnItemClickListener(new HomeHotAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(int pos, int type, ZiXunDetailBean data) {
                Intent intent = new Intent(getActivity(), ZiXunDetail.class);
                intent.putExtra("data", data);
                startActivity(intent);
            }
        });
    }

    @Override
    public void initData() {
        super.initData();
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
        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                getData();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                useBanner();
                getData();
            }
        });
        refreshLayout.autoRefresh();
    }

    @OnClick({R.id.sousuo, R.id.integral, R.id.suipian_total})
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.sousuo) {
            Intent intent = new Intent(getActivity(), Search.class);
            startActivity(intent);
        }else if (id == R.id.integral) {
            Intent intent = new Intent(getActivity(), JiFenShop.class);
            startActivity(intent);
        } else if (id == R.id.suipian_total) {
            Intent intent = new Intent(getActivity(), BaoZang.class);
            startActivity(intent);
        }
    }

    private void useBanner() {
        Map<String, String> map = new HashMap<>();
        map.put("code", "index");
        HttpClient.getInstance().gets(HttpUtil.BANNER, map, new TradeHttpCallback<JsonBean<BannerBean>>() {
            @Override
            public void onSuccess(JsonBean<BannerBean> data) {
                banner.setAdapter(new ImageAdapter(getActivity(), data.getData().getData()))
//                        .setBannerGalleryMZ(20)
//                        .addPageTransformer(new ZoomOutPageTransformer())
                        .addBannerLifecycleObserver(getActivity())//添加生命周期观察者
                        .setIndicator(new CircleIndicator(getActivity()));
            }
        });
    }

    private void getData() {
        Map<String, String> map = new HashMap<>();
        map.put("page", page + "");
        HttpClient.getInstance().gets(HttpUtil.INDEXREALINFO, map, new TradeHttpCallback<JsonBean<ZiXunBean>>() {
            @Override
            public void onSuccess(JsonBean<ZiXunBean> data) {
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
                if (null == data || null == data.getData()) {
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
                    List<ZiXunDetailBean> list = data.getData().getData();
                    //取前面4条记录当做精选
                    if(list.size()>4){
                        List<ZiXunDetailBean> hotList = new ArrayList<>();
                        for(int i=0;i<4;i++){
                            hotList.add(list.remove(0));
                        }
                        homeHotAdapter.replaceAll(hotList);
                    }else{
                        homeHotAdapter.replaceAll(list);
                        list.clear();
                    }
                    adapter.replaceAll(list);
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
