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

import java.util.HashMap;
import java.util.Map;

import Medium.DeFam.app.R;
import Medium.DeFam.app.activity.WenZhangDetail;
import Medium.DeFam.app.adapter.WenZhangAdapter;
import Medium.DeFam.app.bean.WenZhangBean;
import Medium.DeFam.app.bean.WenZhangDetailBean;
import Medium.DeFam.app.common.base.BaseFragment;
import Medium.DeFam.app.common.http.HttpClient;
import Medium.DeFam.app.common.http.JsonBean;
import Medium.DeFam.app.common.http.TradeHttpCallback;
import Medium.DeFam.app.common.utils.ToastUtil;
import Medium.DeFam.app.dialog.FenXiangDialogFragment;
import Medium.DeFam.app.utils.HttpUtil;
import butterknife.BindView;

public class WenZhangFragment extends BaseFragment {
    public static WenZhangFragment newInstance(int type) {
        WenZhangFragment fragment = new WenZhangFragment();
        Bundle args = new Bundle();
        args.putInt("type", type);
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    WenZhangAdapter adapter;
    @BindView(R.id.not)
    View not;
    private int page = 1;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_wenzhang;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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
        adapter = new WenZhangAdapter(getContext());
        adapter.setRecyclerViewOnItemClickListener(new WenZhangAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(int pos, int type, WenZhangDetailBean data) {
                if(0==type){
                    Intent intent = new Intent(getActivity(), WenZhangDetail.class);
                    intent.putExtra("data", data);
                    startActivity(intent);
                }else  if(1==type){
                    FenXiangDialogFragment fenXiangDialogFragment = new FenXiangDialogFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("action_id", data.getId());
                    bundle.putString("type","1");
                    bundle.putString("share_link",data.getShare_link());
                    fenXiangDialogFragment.setArguments(bundle);
                    fenXiangDialogFragment.show(getChildFragmentManager(), "xinxi", true);
                }

            }
        });
        recyclerView.setAdapter(adapter);

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
        HttpClient.getInstance().gets(HttpUtil.INDEX, map, new TradeHttpCallback<JsonBean<WenZhangBean>>() {
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
