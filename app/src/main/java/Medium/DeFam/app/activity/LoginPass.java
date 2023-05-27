package Medium.DeFam.app.activity;

import android.content.Intent;
import android.os.Bundle;
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
import java.util.Map;

import Medium.DeFam.app.R;
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
import Medium.DeFam.app.wx.WxLogin;
import Medium.DeFam.app.wx.interfaces.WxLoginResultListener;
import butterknife.BindView;
import butterknife.OnClick;


public class LoginPass extends BaseActivity {
    @BindView(R.id.gouxuan)
    TextView gouxuan;
    @BindView(R.id.ll_shake)
    LinearLayout ll_shake;
    @BindView(R.id.register_agree)
    CheckBox register_agree;
    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.pass)
    EditText pass;
    ACache aCache;


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
        return R.layout.activity_loginpass;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState, @Nullable Bundle bundle) {

    }

    @Override
    protected void initData() {
        aCache = ACache.get(this);
        String zhanghao = aCache.getAsString(Constants.ZHANGHAO);
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
                getXieYiData("user_service","用户协议");
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
                getXieYiData("private","隐私政策");
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


    @OnClick({R.id.denglu, R.id.gouxuan, R.id.mima, R.id.zhuce, R.id.yanzhengma, R.id.youxiang, R.id.weixin})
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.denglu) {
            if (!register_agree.isChecked()) {
                Animation animation = AnimationUtils.loadAnimation(this, R.anim.translate_checkbox_shake);
                ll_shake.startAnimation(animation);
                Toaster.show("注册登录即表示您已阅读并同意《用户协议》和《隐私政策》");
                return;
            }
            if (TextUtils.isEmpty(phone.getText().toString())) {
                Toaster.show("请输入手机号码");
                return;
            }

            if (TextUtils.isEmpty(pass.getText().toString())) {
                Toaster.show("请输入密码");
                return;
            }
            Map<String, String> map = new HashMap<>();
            map.put("mobile", phone.getText().toString());
            map.put("password", pass.getText().toString());
            map.put("is_agree", "1");
            HttpClient.getInstance().posts(HttpUtil.APILOG, map, new TradeHttpCallback<JsonBean<UserBean>>() {
                @Override
                public void onSuccess(JsonBean<UserBean> data) {
                    aCache.put(Constants.ZHANGHAO, phone.getText().toString());
                    UserUtil.setUserBean(data.getData());

                    Intent intent = ActivityRouter.getIntent(LoginPass.this, ActivityRouter.Mall.MALL_MAIN);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }

                @Override
                public boolean showLoadingDialog() {
                    return true;
                }
            });

        } else if (v.getId() == R.id.gouxuan) {
            register_agree.setChecked(!register_agree.isChecked());
        } else if (v.getId() == R.id.mima) {
            Intent intent = new Intent(this, PassSet.class);
            startActivity(intent);
        } else if (v.getId() == R.id.zhuce) {
            Intent intent = new Intent(this, Register.class);
            startActivity(intent);
        }else if (v.getId() == R.id.yanzhengma) {
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
            finish();
        }else if (v.getId() == R.id.youxiang) {
            Intent intent = new Intent(this, EmailLogin.class);
            startActivity(intent);
            finish();
        }else if (v.getId() == R.id.weixin) {
            if (!register_agree.isChecked()) {
                Animation animation = AnimationUtils.loadAnimation(this, R.anim.translate_checkbox_shake);
                ll_shake.startAnimation(animation);
                Toaster.show("注册登录即表示您已阅读并同意《用户协议》和《隐私政策》");
                return;
            }
            WxLogin.getInstance().login(new WxLoginResultListener() {
                @Override
                public void onSuccess(String code) {
                    //getInfo(code, "Wechat");
                }

                @Override
                public void onError(String errorDetail) {
                    if (!TextUtils.isEmpty(errorDetail)) {
                        Toaster.show(errorDetail);
                    }
                }

                @Override
                public void onCancel() {

                }
            });
        }
    }


    private void getXieYiData(String type, String name) {
       Map<String, String> map = new HashMap<>();
        map.put("code",type);
        HttpClient.getInstance().gets(HttpUtil.AGREEMENT, map, new TradeHttpCallback<JsonBean<String>>() {
            @Override
            public void onSuccess(JsonBean<String> data) {
                Web.startWebActivity(LoginPass.this, name,"",  data.getData());

            }

            @Override
            public boolean showLoadingDialog() {
                return true;
            }
        });
    }

}