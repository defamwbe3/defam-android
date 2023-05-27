package Medium.DeFam.app.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.hjq.toast.Toaster;

import java.util.HashMap;
import java.util.Map;

import Medium.DeFam.app.R;
import Medium.DeFam.app.bean.WalletAddressBean;
import Medium.DeFam.app.common.base.BaseActivity;
import Medium.DeFam.app.common.http.HttpClient;
import Medium.DeFam.app.common.http.JsonBean;
import Medium.DeFam.app.common.http.TradeHttpCallback;
import Medium.DeFam.app.utils.HttpUtil;
import butterknife.BindView;
import butterknife.OnClick;


public class WalletAddressAdd extends BaseActivity {
    @BindView(R.id.title)
    EditText title;
    @BindView(R.id.address)
    EditText address;
    WalletAddressBean.DataBean alldata;
    @Override
    protected boolean isNeedLogin() {
        return false;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_walletaddressadd;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState, @Nullable Bundle bundle) {

    }

    @Override
    protected void initData() {
        alldata = (WalletAddressBean.DataBean) getIntent().getSerializableExtra("data");
        if(null!=alldata){
            title.setText(alldata.getTitle());
            address.setText(alldata.getAddress());
        }
    }

    @OnClick({R.id.ok})
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.ok) {
            if (TextUtils.isEmpty(title.getText().toString())) {
                Toaster.show("请输入标签");
                return;
            }
            if (TextUtils.isEmpty(address.getText().toString())) {
                Toaster.show("请输入对应的地址");
                return;
            }
            if(null!=alldata){
                Map<String, String> map = new HashMap<>();
                map.put("title", title.getText().toString());
                map.put("address", address.getText().toString());
                HttpClient.getInstance().posts(HttpUtil.APIWALLETADDRESSUPDATE+alldata.getId(), map, new TradeHttpCallback<JsonBean<WalletAddressBean.DataBean>>() {
                    @Override
                    public void onSuccess(JsonBean<WalletAddressBean.DataBean> data) {
                        if (null == data || null == data.getData()) {
                            return;
                        }
                        setResult(RESULT_OK);
                        finish();
                    }

                    @Override
                    public boolean showLoadingDialog() {
                        return true;
                    }
                });
            }else {
                Map<String, String> map = new HashMap<>();
                map.put("title", title.getText().toString());
                map.put("address", address.getText().toString());
                HttpClient.getInstance().posts(HttpUtil.APIWALLETADDRESS, map, new TradeHttpCallback<JsonBean<WalletAddressBean.DataBean>>() {
                    @Override
                    public void onSuccess(JsonBean<WalletAddressBean.DataBean> data) {
                        if (null == data || null == data.getData()) {
                            return;
                        }
                        setResult(RESULT_OK);
                        finish();
                    }

                    @Override
                    public boolean showLoadingDialog() {
                        return true;
                    }
                });
            }

        }
    }

}
