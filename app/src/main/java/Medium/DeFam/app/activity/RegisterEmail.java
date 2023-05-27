package Medium.DeFam.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.hjq.toast.Toaster;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Medium.DeFam.app.R;
import Medium.DeFam.app.bean.RegisterBean;
import Medium.DeFam.app.common.ActivityRouter;
import Medium.DeFam.app.common.Constants;
import Medium.DeFam.app.common.base.BaseActivity;
import Medium.DeFam.app.common.bean.MessageEvent;
import Medium.DeFam.app.common.bean.UserBean;
import Medium.DeFam.app.common.http.HttpClient;
import Medium.DeFam.app.common.http.JsonBean;
import Medium.DeFam.app.common.http.TradeHttpCallback;
import Medium.DeFam.app.common.utils.ACache;
import Medium.DeFam.app.common.utils.UserUtil;
import Medium.DeFam.app.utils.HttpUtil;
import butterknife.BindView;
import butterknife.OnClick;

public class RegisterEmail extends BaseActivity {
    @BindView(R.id.gouxuan)
    TextView gouxuan;
    @BindView(R.id.ll_shake)
    LinearLayout ll_shake;
    @BindView(R.id.register_agree)
    CheckBox register_agree;
    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.code)
    EditText code;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.invite_code)
    EditText invite_code;
    @BindView(R.id.iv_yanzhengma)
    TextView iv_yanzhengma;
    @BindView(R.id.polygin)
    EditText polygin;
    ACache aCache;

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
    protected boolean useEventBus() {
        return true;
    }

    @Override
    protected void onEventComing(MessageEvent event) {
        super.onEventComing(event);
        if (event.getCode() == Constants.ACTION_LOGIN && event.getData() != null) {
            finish();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_registeremail;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState, @Nullable Bundle bundle) {

    }

    @Override
    protected void initData() {
        aCache = ACache.get(this);
        String zhanghao = aCache.getAsString(Constants.ZHANGHAOEMAIL);
        if (!TextUtils.isEmpty(zhanghao)) {
            phone.setText(zhanghao);
            phone.setSelection(zhanghao.length());
        }
        String str = "注册登录即表示您已阅读并同意《用户协议》和《隐私政策》";
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        ssb.append(str);
        //第一个出现的位置
        final int start = str.indexOf("《");
        ssb.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                getXieYiData("user_service", "用户协议");
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                //设置文件颜色
                ds.setColor(getResources().getColor(R.color.color_999999));
                // 去掉下划线
                ds.setUnderlineText(false);
            }
        }, start, start + 6, 0);
        //最后一个出现的位置
        final int end = str.lastIndexOf("《");
        ssb.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                getXieYiData("private", "隐私政策");
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                //设置文件颜色
                ds.setColor(getResources().getColor(R.color.color_999999));
                // 去掉下划线
                ds.setUnderlineText(false);
            }
        }, end, end + 6, 0);
        gouxuan.setMovementMethod(LinkMovementMethod.getInstance());
        gouxuan.setText(ssb, TextView.BufferType.SPANNABLE);
    }


    @OnClick({R.id.denglu, R.id.gouxuan, R.id.iv_yanzhengma, R.id.guanbi, R.id.email})
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.denglu) {
            if (!register_agree.isChecked()) {
                Animation animation = AnimationUtils.loadAnimation(this, R.anim.translate_checkbox_shake);
                ll_shake.startAnimation(animation);
                Toaster.show("请先勾选同意《用户协议》和《隐私政策》");
                return;
            }
            if (TextUtils.isEmpty(phone.getText().toString())) {
                Toaster.show("请输入邮箱");
                return;
            }

            if (TextUtils.isEmpty(code.getText().toString())) {
                Toaster.show("请输入请输入验证码");
                return;
            }
            if (TextUtils.isEmpty(password.getText().toString())) {
                Toaster.show("请输入密码");
                return;
            }
            if (TextUtils.isEmpty(polygin.getText().toString())) {
                Toaster.show("请输入Polygon地址");
                return;
            }
            Map<String, String> map = new HashMap<>();
            map.put("email", phone.getText().toString());
            map.put("code", code.getText().toString());
            map.put("password", password.getText().toString());
            map.put("password_confirm", password.getText().toString());
            map.put("invite_code", invite_code.getText().toString());
            map.put("Polygin", polygin.getText().toString());
            HttpClient.getInstance().posts(HttpUtil.REGISTEREMAIL, map, new TradeHttpCallback<JsonBean<RegisterBean>>() {
                @Override
                public void onSuccess(JsonBean<RegisterBean> data) {
                    aCache.put(Constants.ZHANGHAOEMAIL, phone.getText().toString());
                    setLogin();
                }

                @Override
                public boolean showLoadingDialog() {
                    return true;
                }
            });

        } else if (v.getId() == R.id.gouxuan) {
            register_agree.setChecked(!register_agree.isChecked());
        } else if (id == R.id.iv_yanzhengma) {
            if (!register_agree.isChecked()) {
                Animation animation = AnimationUtils.loadAnimation(this, R.anim.translate_checkbox_shake);
                ll_shake.startAnimation(animation);
                Toaster.show("请阅读并勾选协议");
                return;
            }
            if (TextUtils.isEmpty(phone.getText().toString())) {
                Toaster.show("请输入邮箱");
                return;
            }

            Map<String, String> map = new HashMap<>();
            map.put("mobile", phone.getText().toString());
            map.put("type", "register");
            HttpClient.getInstance().gets(HttpUtil.SMSCODE, map, new TradeHttpCallback<JsonBean<List<String>>>() {
                @Override
                public void onSuccess(JsonBean<List<String>> data) {
                    //Toaster.show(data.getMessage());
                    iv_yanzhengma.setEnabled(false);
                    countDownTimer.start();
                }


                @Override
                public boolean showLoadingDialog() {
                    return true;
                }
            });
        } else if (v.getId() == R.id.guanbi) {
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
            finish();
        }else if (v.getId() == R.id.email) {
            Intent intent = new Intent(this, Register.class);
            startActivity(intent);
            finish();
        }
    }

    private void setLogin() {
        Map<String, String> map = new HashMap<>();
        map.put("mobile", phone.getText().toString());
        map.put("password", password.getText().toString());
        map.put("is_agree", "1");
        HttpClient.getInstance().posts(HttpUtil.APILOG, map, new TradeHttpCallback<JsonBean<UserBean>>() {
            @Override
            public void onSuccess(JsonBean<UserBean> data) {
                UserUtil.setUserBean(data.getData());
                Intent intent = ActivityRouter.getIntent(RegisterEmail.this, ActivityRouter.Mall.MALL_MAIN);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }

            @Override
            public boolean showLoadingDialog() {
                return true;
            }
        });
    }

    private void getXieYiData(String type, String name) {
        Map<String, String> map = new HashMap<>();
        map.put("code", type);
        HttpClient.getInstance().gets(HttpUtil.AGREEMENT, map, new TradeHttpCallback<JsonBean<String>>() {
            @Override
            public void onSuccess(JsonBean<String> data) {
                Web.startWebActivity(RegisterEmail.this, name, "", data.getData());

            }

            @Override
            public boolean showLoadingDialog() {
                return true;
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != countDownTimer) {
            countDownTimer.cancel();
        }
    }
}
