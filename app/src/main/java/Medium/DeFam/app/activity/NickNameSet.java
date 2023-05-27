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
import Medium.DeFam.app.bean.InfoBean;
import Medium.DeFam.app.common.base.BaseActivity;
import Medium.DeFam.app.common.bean.UserBean;
import Medium.DeFam.app.common.http.HttpClient;
import Medium.DeFam.app.common.http.JsonBean;
import Medium.DeFam.app.common.http.TradeHttpCallback;
import Medium.DeFam.app.common.utils.UserUtil;
import Medium.DeFam.app.utils.HttpUtil;
import butterknife.BindView;
import butterknife.OnClick;


public class NickNameSet extends BaseActivity {
    @BindView(R.id.nickname)
    EditText nickname;

    @Override
    protected boolean isNeedLogin() {
        return false;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_nickname;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState, @Nullable Bundle bundle) {

    }

    @Override
    protected void initData() {
        nickname.setText(UserUtil.getUserBean().getNickname());
    }

    @OnClick({R.id.ok})
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.ok) {
            if (TextUtils.isEmpty(nickname.getText().toString())) {
                Toaster.show("请输入您的昵称");
                return;
            }
            Map<String, String> map = new HashMap<>();
            map.put("nickname", nickname.getText().toString());
            HttpClient.getInstance().posts(HttpUtil.MEMBERUPDATE, map, new TradeHttpCallback<JsonBean<InfoBean>>() {
                @Override
                public void onSuccess(JsonBean<InfoBean> data) {
                    if (null == data || null == data.getData()) {
                        return;
                    }
                    UserBean myuserBean = UserUtil.getUserBean();
                    myuserBean.setNickname(data.getData().getNickname());
                    UserUtil.setUserBean(myuserBean);
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
