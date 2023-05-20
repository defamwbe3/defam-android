package Medium.DeFam.app.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

import Medium.DeFam.app.R;
import Medium.DeFam.app.bean.AuthenterpriseBean;
import Medium.DeFam.app.common.base.BaseActivity;
import Medium.DeFam.app.common.http.HttpClient;
import Medium.DeFam.app.common.http.JsonBean;
import Medium.DeFam.app.common.http.TradeHttpCallback;
import Medium.DeFam.app.common.utils.ToastUtil;
import Medium.DeFam.app.common.utils.UserUtil;
import Medium.DeFam.app.utils.HttpUtil;
import butterknife.BindView;
import butterknife.OnClick;

public class RenZheng extends BaseActivity {
    @BindView(R.id.title)
    EditText title;
    @BindView(R.id.introduction)
    EditText introduction;
    @BindView(R.id.website)
    EditText website;
    @BindView(R.id.applicant)
    EditText applicant;
    @BindView(R.id.contact)
    EditText contact;
    @BindView(R.id.twitter_url)
    EditText twitter_url;
    @BindView(R.id.telegram_url)
    EditText telegram_url;
    @BindView(R.id.discord)
    EditText discord;
    @BindView(R.id.tijiao)
    Button tijiao;

    @Override
    protected boolean isNeedLogin() {
        return false;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_renzheng;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState, @Nullable Bundle bundle) {

    }

    @Override
    protected void initData() {
        //是否企业认证:0未认证 1已认证2驳回3审核中
        if(!"0".equals(UserUtil.getUserBean().getIs_enterprise())){
            HttpClient.getInstance().gets(HttpUtil.AUTHENTERPRISEDETAIL, null, new TradeHttpCallback<JsonBean<AuthenterpriseBean>>() {
                @Override
                public void onSuccess(JsonBean<AuthenterpriseBean> data) {
                    if(null==data.getData()){
                        return;
                    }
                    title.setText(data.getData().getTitle());
                    introduction.setText(data.getData().getIntroduction());
                    website.setText(data.getData().getWebsite());
                    applicant.setText(data.getData().getApplicant());
                    contact.setText(data.getData().getContact());
                    twitter_url.setText(data.getData().getTwitter_url());
                    telegram_url.setText(data.getData().getTelegram_url());
                    discord.setText(data.getData().getDiscord());
                    //0未审核，1已审核 2拒绝
                    if ("0".equals(data.getData().getFlag())) {
                        tijiao.setBackgroundResource(R.drawable.r_hui50);
                        tijiao.setEnabled(false);
                        tijiao.setText("审核中");
                        title.setEnabled(false);
                        introduction.setEnabled(false);
                        website.setEnabled(false);
                        applicant.setEnabled(false);
                        contact.setEnabled(false);
                        twitter_url.setEnabled(false);
                        telegram_url.setEnabled(false);
                        discord.setEnabled(false);
                    }else  if ("1".equals(data.getData().getFlag())) {
                        tijiao.setVisibility(View.GONE);
                        title.setEnabled(false);
                        introduction.setEnabled(false);
                        website.setEnabled(false);
                        applicant.setEnabled(false);
                        contact.setEnabled(false);
                        twitter_url.setEnabled(false);
                        telegram_url.setEnabled(false);
                        discord.setEnabled(false);
                    }
                }

                @Override
                public boolean showLoadingDialog() {
                    return true;
                }
            });
        }

    }

    @OnClick({R.id.tijiao})
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tijiao) {
            if (TextUtils.isEmpty(title.getText().toString())) {
                ToastUtil.initToast("请填写项目名称/公司/DAO名称");
                return;
            }
            if (TextUtils.isEmpty(introduction.getText().toString())) {
                ToastUtil.initToast("请填写简介");
                return;
            }
            if (TextUtils.isEmpty(website.getText().toString())) {
                ToastUtil.initToast("请填写网站链接");
                return;
            }
            if (TextUtils.isEmpty(applicant.getText().toString())) {
                ToastUtil.initToast("请输入申请人职位");
                return;
            }
            if (TextUtils.isEmpty(contact.getText().toString())) {
                ToastUtil.initToast("请填写微信/手机号/邮箱均可");
                return;
            }
            if (TextUtils.isEmpty(twitter_url.getText().toString())) {
                ToastUtil.initToast("请填写网站链接");
                return;
            }
            if (TextUtils.isEmpty(telegram_url.getText().toString())) {
                ToastUtil.initToast("请填写网站链接");
                return;
            }
            if (TextUtils.isEmpty(discord.getText().toString())) {
                ToastUtil.initToast("请填写网站链接");
                return;
            }
            Map<String, String> map = new HashMap<>();
            map.put("id", "0");
            map.put("title", title.getText().toString());
            map.put("introduction", introduction.getText().toString());
            map.put("website", website.getText().toString());
            map.put("applicant", applicant.getText().toString());
            map.put("contact", contact.getText().toString());
            map.put("twitter_url", twitter_url.getText().toString());
            map.put("telegram_url", telegram_url.getText().toString());
            map.put("discord", discord.getText().toString());
            map.put("platform", "");
            HttpClient.getInstance().posts(HttpUtil.AUTHENTERPRISE, map, new TradeHttpCallback<JsonBean<String>>() {
                @Override
                public void onSuccess(JsonBean<String> data) {
                    ToastUtil.initToast("已提交");
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
