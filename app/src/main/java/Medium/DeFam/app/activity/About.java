package Medium.DeFam.app.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

import Medium.DeFam.app.R;
import Medium.DeFam.app.common.base.BaseActivity;
import Medium.DeFam.app.common.http.HttpClient;
import Medium.DeFam.app.common.http.JsonBean;
import Medium.DeFam.app.common.http.TradeHttpCallback;
import Medium.DeFam.app.utils.HttpUtil;
import butterknife.BindView;
import butterknife.OnClick;


public class About extends BaseActivity {
    @BindView(R.id.app_ver)
    TextView app_ver;

    @Override
    protected boolean isNeedLogin() {
        return false;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState, @Nullable Bundle bundle) {
        app_ver.setText("V " + getPackageInfo().versionName);
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.fuwu, R.id.yinsi})
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.fuwu) {
            getXieYiData("user_service", "用户协议");
        } else if (id == R.id.yinsi) {
            getXieYiData("private", "隐私政策");
        }
    }

    private void getXieYiData(String type, String name) {
        Map<String, String> map = new HashMap<>();
        map.put("code", type);
        HttpClient.getInstance().gets(HttpUtil.AGREEMENT, map, new TradeHttpCallback<JsonBean<String>>() {
            @Override
            public void onSuccess(JsonBean<String> data) {
                Web.startWebActivity(About.this, name, "", data.getData());

            }

            @Override
            public boolean showLoadingDialog() {
                return true;
            }
        });
    }

    /**
     * 获取app 信息
     *
     * @return
     */
    private PackageInfo getPackageInfo() {
        PackageInfo pinfo = null;
        try {
            pinfo = getPackageManager().getPackageInfo(
                    getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return pinfo;
    }
}
