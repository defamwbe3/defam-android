package Medium.DeFam.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import Medium.DeFam.app.R;
import Medium.DeFam.app.adapter.SearchAdapter;
import Medium.DeFam.app.bean.SearchListBean;
import Medium.DeFam.app.common.base.BaseActivity;
import Medium.DeFam.app.common.http.HttpClient;
import Medium.DeFam.app.common.http.JsonBean;
import Medium.DeFam.app.common.http.TradeHttpCallback;
import Medium.DeFam.app.common.utils.AllUtils;
import Medium.DeFam.app.common.widget.flowlayout.FlowLayoutManager;
import Medium.DeFam.app.common.widget.flowlayout.SpaceItemDecoration;
import Medium.DeFam.app.utils.HttpUtil;
import butterknife.BindView;
import butterknife.OnClick;


public class Search extends BaseActivity {
    @BindView(R.id.des)
    RecyclerView des;
    @BindView(R.id.search)
    EditText search;
    @BindView(R.id.sousuo)
    TextView sousuo;
    @BindView(R.id.not)
    View not;
    @Override
    protected boolean isNeedLogin() {
        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState, @Nullable Bundle bundle) {
        FlowLayoutManager flowLayoutManager = new FlowLayoutManager();
        des.addItemDecoration(new SpaceItemDecoration(AllUtils.dip2px(this, 0), AllUtils.dip2px(this, 0), AllUtils.dip2px(this, 12), AllUtils.dip2px(this, 8)));
        des.setLayoutManager(flowLayoutManager);
    }

    @Override
    protected void initData() {
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0) {
                    sousuo.setText("搜索");
                } else {
                    sousuo.setText("取消");
                }
            }
        });
        getData();
    }


    private void getData() {
        HttpClient.getInstance().gets(HttpUtil.SEARCHLIST, null, new TradeHttpCallback<JsonBean<List<SearchListBean>>>() {
            @Override
            public void onSuccess(JsonBean<List<SearchListBean>> data) {
                if(data.getData().size()>0){
                    not.setVisibility(View.GONE);
                    des.setVisibility(View.VISIBLE);
                    des.setAdapter(new SearchAdapter(Search.this, data.getData(), new SearchAdapter.OnNoticeListener() {
                        @Override
                        public void setNoticeListener(int id, int position, SearchListBean mydata) {
                            Intent intent = new Intent(Search.this, SearchList.class);
                            intent.putExtra("key_word", mydata.getKey_word());
                            startActivity(intent);
                            finish();
                        }
                    }));
                }else {
                    des.setVisibility(View.GONE);
                    not.setVisibility(View.VISIBLE);
                }

            }
        });
    }

    @OnClick({R.id.sousuo, R.id.shanchu})
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.sousuo) {
            if ("取消".equals(sousuo.getText().toString())) {
                finish();
                return;
            }
            Intent intent = new Intent(this, SearchList.class);
            intent.putExtra("key_word", search.getText().toString());
            startActivity(intent);
            finish();
        } else if (id == R.id.shanchu) {
            HttpClient.getInstance().posts(HttpUtil.SEARCHDEL, null, new TradeHttpCallback<JsonBean<String>>() {
                @Override
                public void onSuccess(JsonBean<String> data) {
                    des.setVisibility(View.GONE);
                    not.setVisibility(View.VISIBLE);
                }
            });
        }
    }
}
