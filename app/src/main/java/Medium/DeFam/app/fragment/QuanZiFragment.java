package Medium.DeFam.app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
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
import Medium.DeFam.app.activity.FaBu;
import Medium.DeFam.app.activity.Search;
import Medium.DeFam.app.activity.WenZhangDetail;
import Medium.DeFam.app.adapter.QuanZiAdapter;
import Medium.DeFam.app.bean.WenZhangBean;
import Medium.DeFam.app.bean.WenZhangDetailBean;
import Medium.DeFam.app.common.Constants;
import Medium.DeFam.app.common.base.BaseFragment;
import Medium.DeFam.app.common.bean.MessageEvent;
import Medium.DeFam.app.common.http.HttpClient;
import Medium.DeFam.app.common.http.JsonBean;
import Medium.DeFam.app.common.http.TradeHttpCallback;
import Medium.DeFam.app.common.them.Eyes;
import Medium.DeFam.app.dialog.FenXiangDialogFragment;
import Medium.DeFam.app.utils.HttpUtil;
import butterknife.BindView;
import butterknife.OnClick;


public class QuanZiFragment extends BaseFragment {
    public static QuanZiFragment newInstance(int type) {
        QuanZiFragment fragment = new QuanZiFragment();
        Bundle args = new Bundle();
        args.putInt("type", type);
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.not)
    View not;
    QuanZiAdapter adapter;
    private int page = 1;

    @Override
    protected boolean useEventBus() {
        return true;
    }

    @Override
    protected void onEventComing(MessageEvent event) {
        super.onEventComing(event);
        if (event.getCode() == Constants.QUANZI ) {
            page = 1;
            getData();
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_quanzi;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        view.findViewById(R.id.topview).setPadding(0, Eyes.getStatusBarHeight(getActivity()), 0, 0);
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
        adapter = new QuanZiAdapter(getContext());
        adapter.setRecyclerViewOnItemClickListener(new QuanZiAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(int pos, int type, WenZhangDetailBean data) {
                if(0==type){
                    Intent intent = new Intent(getActivity(), WenZhangDetail.class);
                    intent.putExtra("data", data);
                    startActivity(intent);
                }else  if(1==type){
                    FenXiangDialogFragment fenXiangDialogFragment = new FenXiangDialogFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("title",data.getTitle());
                    bundle.putString("content",data.getContent());
                    bundle.putString("action_id", data.getId());
                    bundle.putString("type","1");
                    bundle.putString("share_link",data.getShare_link());
                    fenXiangDialogFragment.setArguments(bundle);
                    fenXiangDialogFragment.show(getChildFragmentManager(), "xinxi", true);
                }

            }
        });
        recyclerView.setAdapter(adapter);
        //数据
        getData();
    }

    private void getData() {
        if(!isLogined()){
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("page", page + "");
        map.put("type", "short");
        map.put("title", "");
        HttpClient.getInstance().gets(HttpUtil.APIARTICLE, map, new TradeHttpCallback<JsonBean<WenZhangBean>>() {
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
            public void onError(Response<JsonBean<WenZhangBean>> response) {
                super.onError(response);
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
            }
        });

    }

    @OnClick({R.id.sousuo, R.id.img})
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.sousuo) {
            Intent intent = new Intent(getActivity(), Search.class);
            startActivity(intent);
        }else if (id == R.id.img) {
            Intent intent = new Intent(getActivity(), FaBu.class);
            startActivity(intent);
        }
    }
}
