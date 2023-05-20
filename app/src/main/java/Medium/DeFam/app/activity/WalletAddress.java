package Medium.DeFam.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.Nullable;

import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Medium.DeFam.app.R;
import Medium.DeFam.app.adapter.WalletAddressAdapter;
import Medium.DeFam.app.bean.ActivePartyBean;
import Medium.DeFam.app.bean.WalletAddressBean;
import Medium.DeFam.app.common.Constants;
import Medium.DeFam.app.common.base.BaseActivity;
import Medium.DeFam.app.common.bean.MessageEvent;
import Medium.DeFam.app.common.bean.UserBean;
import Medium.DeFam.app.common.http.HttpClient;
import Medium.DeFam.app.common.http.JsonBean;
import Medium.DeFam.app.common.http.TradeHttpCallback;
import Medium.DeFam.app.common.utils.ToastUtil;
import Medium.DeFam.app.utils.HttpUtil;
import butterknife.BindView;
import butterknife.OnClick;


public class WalletAddress extends BaseActivity {
    @BindView(R.id.listview)
    ListView listview;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private int page = 1;
    private List<WalletAddressBean.DataBean> listBeans = new ArrayList<>();
    private WalletAddressAdapter adapter;
    private boolean isSelect;

    @Override
    protected boolean isNeedLogin() {
        return false;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_walletaddress;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState, @Nullable Bundle bundle) {
        View notview = findViewById(R.id.not);
        listview.setEmptyView(notview);
    }

    @Override
    protected void initData() {
        isSelect = getIntent().getBooleanExtra("isSelect",false);
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
        adapter = new WalletAddressAdapter(this, listBeans);
        adapter.setOnNoticeListener(new WalletAddressAdapter.OnNoticeListener() {
            @Override
            public void setNoticeListener(int position, WalletAddressBean.DataBean data) {
                if(0==position&&isSelect){
                    Intent intent = new Intent();
                    intent.putExtra("data",data);
                    setResult(RESULT_OK,intent);
                    //EventBus.getDefault().post(new MessageEvent<WalletAddressBean.DataBean>(Constants.DIZHI, listBeans.get(position)));
                    finish();
                    return;
                }
                Intent intent = new Intent(WalletAddress.this, WalletAddressAdd.class);
                intent.putExtra("data", data);
                startActivityForResult(intent, Constants.TYPE_1);
            }
        });
        listview.setAdapter(adapter);
        getData();
    }

    private void getData() {
        Map<String, String> map = new HashMap<>();
        map.put("page", page + "");
        HttpClient.getInstance().gets(HttpUtil.APIWALLETADDRESS, map, new TradeHttpCallback<JsonBean<WalletAddressBean>>() {
            @Override
            public void onSuccess(JsonBean<WalletAddressBean> data) {
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
                    listBeans.addAll(data.getData().getData());
                } else {
                    refreshLayout.finishRefresh();
                    listBeans.clear();
                    listBeans.addAll(data.getData().getData());
                }
                adapter.notifyDataSetChanged();
                page++;
            }

            @Override
            public void onError(Response<JsonBean<WalletAddressBean>> response) {
                super.onError(response);
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
            }
        });
    }

    @OnClick({R.id.xinzeng})
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.xinzeng) {
            Intent intent = new Intent(this, WalletAddressAdd.class);
            startActivityForResult(intent, Constants.TYPE_1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.TYPE_1 && resultCode == RESULT_OK) {
            page = 1;
            getData();
        }
    }
}