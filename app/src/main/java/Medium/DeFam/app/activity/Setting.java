package Medium.DeFam.app.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import Medium.DeFam.app.R;
import Medium.DeFam.app.common.ActivityRouter;
import Medium.DeFam.app.common.Constants;
import Medium.DeFam.app.common.base.BaseActivity;
import Medium.DeFam.app.common.bean.MessageEvent;
import Medium.DeFam.app.common.bean.UserBean;
import Medium.DeFam.app.common.http.HttpClient;
import Medium.DeFam.app.common.http.JsonBean;
import Medium.DeFam.app.common.http.TradeHttpCallback;
import Medium.DeFam.app.common.utils.UserUtil;
import Medium.DeFam.app.dialog.AllDialog;
import Medium.DeFam.app.utils.HttpUtil;
import butterknife.BindView;
import butterknife.OnClick;


public class Setting extends BaseActivity {
    @BindView(R.id.app_ver)
    TextView app_ver;
    @BindView(R.id.phone)
    TextView phone;
    @Override
    protected boolean useEventBus() {
        return true;
    }

    @Override
    protected void onEventComing(MessageEvent event) {
        super.onEventComing(event);
        if (event.getCode() == Constants.ACTION_LOGIN && event.getData() != null) {
            String mobile = UserUtil.getUserBean().getMobile();
            if (!TextUtils.isEmpty(mobile) && mobile.length() > 6) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < mobile.length(); i++) {
                    char c = mobile.charAt(i);
                    if (i >= 3 && i <= 6) {
                        sb.append('*');
                    } else {
                        sb.append(c);
                    }
                }
                phone.setText(sb.toString());
            }
        }
    }
    @Override
    protected boolean isNeedLogin() {
        return false;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState, @Nullable Bundle bundle) {
        app_ver.setText("V " + getPackageInfo().versionName);
    }

    @Override
    protected void initData() {
        String mobile = UserUtil.getUserBean().getMobile();
        if (!TextUtils.isEmpty(mobile) && mobile.length() > 6) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mobile.length(); i++) {
                char c = mobile.charAt(i);
                if (i >= 3 && i <= 6) {
                    sb.append('*');
                } else {
                    sb.append(c);
                }
            }
            phone.setText(sb.toString());
        }

    }

    @OnClick({R.id.xinxi, R.id.guanyu, R.id.mima, R.id.shoujihao, R.id.tuichu, R.id.zhuxiao})
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.xinxi) {
            Intent intent = new Intent(this, GeRen.class);
            startActivity(intent);
        } else if (id == R.id.guanyu) {
            Intent intent = new Intent(this, About.class);
            startActivity(intent);
        } else if (id == R.id.mima) {
            Intent intent = new Intent(this, PassSet.class);
            startActivity(intent);
        } else if (id == R.id.shoujihao) {
            Intent intent = new Intent(this, PhoneSet.class);
            startActivity(intent);
        } else if (id == R.id.tuichu) {
            AllDialog payDialog = new AllDialog(this, "退出","确定退出当前登录？");
            payDialog.setItemListener(new AllDialog.OnNoticeListener() {
                @Override
                public void setNoticeListener(int id, int position, String data) {
                    if(0==id){
                        //ProgressPopWinFactory.getInstance().show(getActivity(), "正在加载");
                        UserUtil.setUserBean(null);
                        Intent intent = ActivityRouter.getIntent(Setting.this, ActivityRouter.Mall.MALL_MAIN);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                }
            });
            payDialog.show();
        }else if (id == R.id.zhuxiao) {
            AllDialog payDialog = new AllDialog(this, "温馨提示","确定注销当前帐号？");
            payDialog.setItemListener(new AllDialog.OnNoticeListener() {
                @Override
                public void setNoticeListener(int id, int position, String data) {
                    if(0==id){
                        delMemebr();
                    }
                }
            });
            payDialog.show();
        }
    }
    private void delMemebr() {
        HttpClient.getInstance().posts(HttpUtil.DELMEMBER, null, new TradeHttpCallback<JsonBean<UserBean>>() {
            @Override
            public void onSuccess(JsonBean<UserBean> data) {
                UserUtil.setUserBean(null);
                Intent intent = ActivityRouter.getIntent(Setting.this, ActivityRouter.Mall.MALL_MAIN);
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
