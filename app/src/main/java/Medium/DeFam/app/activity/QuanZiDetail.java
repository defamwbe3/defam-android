package Medium.DeFam.app.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import Medium.DeFam.app.R;
import Medium.DeFam.app.adapter.CommentAdapter;
import Medium.DeFam.app.common.base.BaseActivity;
import Medium.DeFam.app.common.widget.NoScrollListView;
import Medium.DeFam.app.dialog.FenXiangDialogFragment;
import Medium.DeFam.app.dialog.PingLunDialogFragment;
import butterknife.BindView;
import butterknife.OnClick;


public class QuanZiDetail extends BaseActivity {
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    CommentAdapter commentAdapter;
    private int page = 1;

    @Override
    protected boolean isNeedLogin() {
        return false;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_quanzidetail;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState, @Nullable Bundle bundle) {
        refreshLayout.setEnableRefresh(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void initData() {
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                getData();
            }
        });
        commentAdapter = new CommentAdapter(this);
        recyclerView.setAdapter(commentAdapter);
        initString();
    }

    private void initString() {
        List<String> stringList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            stringList.add("");
            stringList.add("");
            stringList.add("");
        }
        //adapter.replaceAll(stringList);
    }
    @OnClick({R.id.pinglun,R.id.fenxiang})
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.pinglun) {
            PingLunDialogFragment mGiftDialogFragment = new PingLunDialogFragment();
          /*  Bundle chatargs = new Bundle();
            chatargs.putSerializable("conversationInfo", conversationInfo);
            mGiftDialogFragment.setArguments(chatargs);*/
            mGiftDialogFragment.show(getSupportFragmentManager(), "xinxi", true);
        }else if (id == R.id.fenxiang) {
            FenXiangDialogFragment mGiftDialogFragment = new FenXiangDialogFragment();
            mGiftDialogFragment.show(getSupportFragmentManager(), "xinxi", true);
        }
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
