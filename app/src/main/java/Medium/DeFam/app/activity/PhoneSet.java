package Medium.DeFam.app.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Medium.DeFam.app.R;
import Medium.DeFam.app.bean.InfoBean;
import Medium.DeFam.app.common.base.BaseActivity;
import Medium.DeFam.app.common.bean.UserBean;
import Medium.DeFam.app.common.http.HttpClient;
import Medium.DeFam.app.common.http.JsonBean;
import Medium.DeFam.app.common.http.TradeHttpCallback;
import Medium.DeFam.app.common.utils.AllUtils;
import Medium.DeFam.app.common.utils.GlideUtil;
import Medium.DeFam.app.common.utils.ToastUtil;
import Medium.DeFam.app.common.utils.UserUtil;
import Medium.DeFam.app.utils.HttpUtil;
import butterknife.BindView;
import butterknife.OnClick;



public class PhoneSet extends BaseActivity {
    @BindView(R.id.iv_yanzhengma)
    TextView iv_yanzhengma;
    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.code)
    EditText code;
    CountDownTimer countDownTimer = new CountDownTimer(60 * 1000, 1000) {
        public void onTick(long millisUntilFinished) {
            iv_yanzhengma.setText(millisUntilFinished / 1000 + "后重新获取");
        }

        public void onFinish() {
            iv_yanzhengma.setText("获取验证码");
            iv_yanzhengma.setEnabled(true);
        }
    };

    @Override
    protected boolean isNeedLogin() {
        return false;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_phoneset;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState, @Nullable Bundle bundle) {

    }

    @Override
    protected void initData() {

    }

    @OnClick({ R.id.iv_yanzhengma,R.id.ok})
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_yanzhengma) {
            if (TextUtils.isEmpty(phone.getText().toString())) {
                ToastUtil.initToast("请输入手机号码");
                return;
            }

            Map<String, String> map = new HashMap<>();
            map.put("mobile", phone.getText().toString());
            map.put("type", "change_mobile");
            HttpClient.getInstance().gets(HttpUtil.SMSCODE, map, new TradeHttpCallback<JsonBean<List<String>>>() {
                @Override
                public void onSuccess(JsonBean<List<String>> data) {
                    //ToastUtil.initToast(data.getMessage());
                    iv_yanzhengma.setEnabled(false);
                    code.setText("");
                    countDownTimer.start();
                }


                @Override
                public boolean showLoadingDialog() {
                    return true;
                }
            });
        }else  if (id == R.id.ok) {
            if (TextUtils.isEmpty(phone.getText().toString())) {
                ToastUtil.initToast("请输入手机号码");
                return;
            }

            if (TextUtils.isEmpty(code.getText().toString())) {
                ToastUtil.initToast("请输入请输入验证码");
                return;
            }
            Map<String, String> map = new HashMap<>();
            map.put("new_mobile", phone.getText().toString());
            map.put("code", code.getText().toString());
            HttpClient.getInstance().posts(HttpUtil.MEMBERMOBILE, map, new TradeHttpCallback<JsonBean<InfoBean>>() {
                @Override
                public void onSuccess(JsonBean<InfoBean> data) {
                    if (null == data || null == data.getData()) {
                        return;
                    }
                    UserBean myuserBean = UserUtil.getUserBean();
                    myuserBean.setMobile(data.getData().getMobile());
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
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != countDownTimer) {
            countDownTimer.cancel();
        }
    }
}
