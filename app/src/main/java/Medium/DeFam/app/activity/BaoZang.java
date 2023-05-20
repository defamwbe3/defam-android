package Medium.DeFam.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lzy.okgo.model.Response;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;

import java.util.HashMap;
import java.util.Map;

import Medium.DeFam.app.R;
import Medium.DeFam.app.adapter.BaoZangAdapter;
import Medium.DeFam.app.bean.MyMapBean;
import Medium.DeFam.app.bean.WalletAddressBean;
import Medium.DeFam.app.common.Constants;
import Medium.DeFam.app.common.base.BaseActivity;
import Medium.DeFam.app.common.http.HttpClient;
import Medium.DeFam.app.common.http.JsonBean;
import Medium.DeFam.app.common.http.TradeHttpCallback;
import Medium.DeFam.app.common.titlebar.CommonTitleBar;
import Medium.DeFam.app.common.utils.ToastUtil;
import Medium.DeFam.app.common.widget.SmartRefreshView;
import Medium.DeFam.app.dialog.TiXianDialog;
import Medium.DeFam.app.utils.HttpUtil;
import butterknife.BindView;


public class BaoZang extends BaseActivity {
    @BindView(R.id.refreshLayout)
    SmartRefreshView refreshLayout;
    @BindView(R.id.listview)
    GridView listview;
    private BaoZangAdapter adapter;
    private int page = 1;
    TiXianDialog tiXianDialog;
    @Override
    protected boolean isNeedLogin() {
        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_baozang;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState, @Nullable Bundle bundle) {
        refreshLayout.setEnableRefresh(false);
        View notview = findViewById(R.id.not);
        listview.setEmptyView(notview);
    }

    @Override
    protected void onTitleListener(View v, int action, String extra) {
        if (action == CommonTitleBar.ACTION_LEFT_BUTTON) {
            finish();
        } else if (action == CommonTitleBar.ACTION_RIGHT_BUTTON) {
            Intent intent = new Intent(this, DuiHuanJiLu.class);
            startActivity(intent);
        }
    }

    @Override
    protected void initData() {
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                getData();
            }
        });
        adapter = new BaoZangAdapter(this);
        adapter.setOnNoticeListener(new BaoZangAdapter.OnNoticeListener() {
            @Override
            public void setNoticeListener(int position, MyMapBean.DataBean data) {
                if(0==position){
                    Intent intent = new Intent(BaoZang.this, BaoZangDetail.class);
                    intent.putExtra("data", data);
                    startActivity(intent);
                }else {
                     tiXianDialog = new TiXianDialog(BaoZang.this,data);
                    tiXianDialog.setItemListener(new TiXianDialog.OnNoticeListener() {
                        @Override
                        public void setNoticeListener(int id, int position, String data) {
                            if(0==id){
                                Intent intent = new Intent(BaoZang.this, WalletAddress.class);
                                intent.putExtra("isSelect",true);
                                startActivityForResult(intent, Constants.TYPE_1);
                            }else {
                                page = 1;
                                getData();
                            }

                        }
                    });
                    tiXianDialog.show();
                }

            }
        });
        listview.setAdapter(adapter);
        getData();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.TYPE_1 && resultCode == RESULT_OK) {
            WalletAddressBean.DataBean dizhi = (WalletAddressBean.DataBean) data.getSerializableExtra("data");
            if(null!=tiXianDialog){
                tiXianDialog.setDiZhi(dizhi);
            }
        }
    }

    private void getData() {
        Map<String, String> map = new HashMap<>();
        map.put("page", page + "");
        map.put("type", "map");
        HttpClient.getInstance().gets(HttpUtil.USERMAP, map, new TradeHttpCallback<JsonBean<MyMapBean>>() {
            @Override
            public void onSuccess(JsonBean<MyMapBean> data) {
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
            public void onError(Response<JsonBean<MyMapBean>> response) {
                super.onError(response);
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
            }
        });
    }
}
