package Medium.DeFam.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.Nullable;

import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import Medium.DeFam.app.R;
import Medium.DeFam.app.adapter.DuiHuanAdapter;
import Medium.DeFam.app.common.base.BaseActivity;
import Medium.DeFam.app.common.titlebar.CommonTitleBar;
import butterknife.BindView;



public class DuiHuan extends BaseActivity {
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.listview)
    GridView listview;
    private DuiHuanAdapter adapter;
    private int page = 1;

    @Override
    protected boolean isNeedLogin() {
        return false;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_duihuan;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState, @Nullable Bundle bundle) {
        refreshLayout.setEnableRefresh(false);
        View notview = findViewById(R.id.not);
        listview.setEmptyView(notview);
    }

    @Override
    protected void initData() {
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                getData();
            }
        });
        adapter = new DuiHuanAdapter(this);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(DuiHuan.this, DuiHuanDetail.class);
                startActivity(intent);
            }
        });
        initString();
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
    private void initString() {
        List<String> stringList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            stringList.add("线上");
            stringList.add("有空投");
            stringList.add("Twitter Space");
        }
        adapter.replaceAll(stringList);
    }

    /*   @OnClick({R.id.zan})
       public void onClick(View v) {
           int id = v.getId();
           if (id == R.id.zan) {
               Intent intent = new Intent(this, XiaoXiList.class);
               startActivity(intent);
           }
       }*/
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
                        ToastUtil.initToast("暂无更多数据");
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
