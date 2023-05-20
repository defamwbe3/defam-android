package Medium.DeFam.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.lzy.okgo.model.Response;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Medium.DeFam.app.R;
import Medium.DeFam.app.adapter.PublishGridAdapter;
import Medium.DeFam.app.bean.WenZhangBean;
import Medium.DeFam.app.common.Constants;
import Medium.DeFam.app.common.base.BaseActivity;
import Medium.DeFam.app.common.bean.MessageEvent;
import Medium.DeFam.app.common.bean.UserBean;
import Medium.DeFam.app.common.http.HttpClient;
import Medium.DeFam.app.common.http.JsonBean;
import Medium.DeFam.app.common.http.TradeHttpCallback;
import Medium.DeFam.app.common.interfaces.OnUpdateImgListener;
import Medium.DeFam.app.common.titlebar.CommonTitleBar;
import Medium.DeFam.app.common.utils.ToastUtil;
import Medium.DeFam.app.common.utils.UpdateImageUtil;
import Medium.DeFam.app.common.widget.NoScrollGridView;
import Medium.DeFam.app.utils.HttpUtil;
import butterknife.BindView;
import butterknife.OnClick;


public class GuanDianPut extends BaseActivity {
    @BindView(R.id.gridviewimg)
    NoScrollGridView noScrollgridview;
    @BindView(R.id.yijian)
    EditText yijian;
    Button bt;
    private PublishGridAdapter adapterimg = null;
    private List<String> checkedItems = new ArrayList<>();

    @Override
    protected boolean isNeedLogin() {
        return true;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_fabuput;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState, @Nullable Bundle bundle) {
        bt = mTitleBar.getRightCustomView().findViewById(R.id.bt);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(yijian.getText().toString())) {
                    ToastUtil.initToast("请输入表你的观点");
                    return;
                }
                JSONArray jsonArray = new JSONArray();
                for (int i = 0, a = checkedItems.size(); i < a; i++) {
                    jsonArray.put(checkedItems.get(i));
                }
                if (jsonArray.length() == 0) {
                    ToastUtil.initToast("请选择图片");
                    return;
                }

                Map<String, String> map = new HashMap<>();
                map.put("content", yijian.getText().toString());
                map.put("images", jsonArray.toString());
                map.put("platform", "android");
                HttpClient.getInstance().posts(HttpUtil.RELEASESHORT, map, new TradeHttpCallback<JsonBean<String>>() {
                    @Override
                    public void onSuccess(JsonBean<String> data) {
                        if (null == data || null == data.getData()) {
                            return;
                        }
                        EventBus.getDefault().post(new MessageEvent<>(Constants.QUANZI));
                        Intent intent = new Intent(GuanDianPut.this, PutOk.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public boolean showLoadingDialog() {
                        return true;
                    }
                });

            }
        });
        adapterimg = new PublishGridAdapter(this, checkedItems);
        adapterimg.setMaxNum(9);
        noScrollgridview.setAdapter(adapterimg);
        noScrollgridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                if (arg2 == checkedItems.size()) {
                    if (checkedItems.size() >= 5) {
                        ToastUtil.initToast("最多上传5张图片");
                        return;
                    }
                    UpdateImageUtil.getInstance().startSelector(GuanDianPut.this, "face", new OnUpdateImgListener() {
                        @Override
                        public void onSuccess(String url, File file) {
                            checkedItems.add(url);
                            adapterimg.notifyDataSetChanged();
                        }
                    });
                } else {
                    Intent intent = new Intent(GuanDianPut.this,
                            Photo.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("position", arg2);
                    bundle.putSerializable("list", (Serializable) checkedItems);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void initData() {
        yijian.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0) {
                    bt.setBackgroundResource(R.drawable.r_lan);
                } else {
                    bt.setBackgroundResource(R.drawable.r_hui50);
                }
            }
        });
    }

}
