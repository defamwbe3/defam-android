package Medium.DeFam.app.common.base;

import static Medium.DeFam.app.common.timeset.PollingService.flag;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnCancelListener;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import Medium.DeFam.app.common.ActivityRouter;
import Medium.DeFam.app.common.bean.MessageEvent;
import Medium.DeFam.app.common.interfaces.LoadingInterface;
import Medium.DeFam.app.common.interfaces.PermissionListener;
import Medium.DeFam.app.common.them.Eyes;
import Medium.DeFam.app.common.timeset.PollingUtils;
import Medium.DeFam.app.common.titlebar.CommonTitleBar;
import Medium.DeFam.app.common.utils.AppManager;
import Medium.DeFam.app.common.utils.NetUtils;
import Medium.DeFam.app.common.utils.UserUtil;
import Medium.DeFam.app.common.widget.LoadingController;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import Medium.DeFam.app.common.R;
import butterknife.ButterKnife;


public abstract class BaseActivity extends AppCompatActivity {
    protected CommonTitleBar mTitleBar;
    protected LoadingController loadingController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        if (isNeedLogin() && !isLogined()) {
            ActivityRouter.toPoint(this, ActivityRouter.Mall.MALL_LOGIN);
            finish();
            return;
        }
        setContentView(getLayoutId());
        ButterKnife.bind(this);

        //默认透明 个性化可调用修改
        if (isEyes()) {
            Eyes.setTranslucent(this);
        }
        mTitleBar = findViewById(getResources().getIdentifier("titleBar", "id", getPackageName()));
        if (mTitleBar != null) {
            mTitleBar.setListener(new CommonTitleBar.OnTitleBarListener() {
                @Override
                public void onClicked(View v, int action, String extra) {
                    onTitleListener(v, action, extra);
                }
            });
        }

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            bundle = new Bundle();
        }
        initView(savedInstanceState, bundle);
        initData();
        ClassicsHeader.REFRESH_HEADER_PULLDOWN = getString(R.string.refresh_header_pulling);
        ClassicsHeader.REFRESH_HEADER_REFRESHING = getString(R.string.refresh_header_refreshing);
        ClassicsHeader.REFRESH_HEADER_LOADING = getString(R.string.refresh_header_loading);
        ClassicsHeader.REFRESH_HEADER_RELEASE = getString(R.string.refresh_header_release);
        ClassicsHeader.REFRESH_HEADER_FINISH = getString(R.string.refresh_header_finish);
        ClassicsHeader.REFRESH_HEADER_FAILED = getString(R.string.refresh_header_failed);
        ClassicsHeader.REFRESH_HEADER_LASTTIME = getString(R.string.refresh_header_update);


        ClassicsFooter.REFRESH_FOOTER_PULLUP = getString(R.string.refresh_footer_pulling);
        ClassicsFooter.REFRESH_FOOTER_RELEASE = getString(R.string.refresh_footer_release);
        ClassicsFooter.REFRESH_FOOTER_REFRESHING = getString(R.string.refresh_footer_loading);
        ClassicsFooter.REFRESH_FOOTER_LOADING = getString(R.string.refresh_footer_refreshing);
        ClassicsFooter.REFRESH_FOOTER_FINISH = getString(R.string.refresh_footer_finish);
        ClassicsFooter.REFRESH_FOOTER_FAILED = getString(R.string.refresh_footer_failed);
        ClassicsFooter.REFRESH_FOOTER_ALLLOADED = getString(R.string.refresh_footer_nothing);
        if (useEventBus()) {
            EventBus.getDefault().register(this);
        }
        getBaseData();
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (!flag) {
            PollingUtils.startPollingService(this);
        }
    }
    public void request(View view) {
        loadingController = new LoadingController.Builder(this, view)
                .setOnErrorRetryClickListener(new LoadingInterface.OnClickListener() {
                    @Override
                    public void onClick() {
                        getBaseData();
                    }
                })
                .build();
        loadingController.showLoading();

    }


    protected void onTitleListener(View v, int action, String extra) {
        // CommonTitleBar.ACTION_LEFT_BUTTON;      // 左边ImageBtn被点击
        // CommonTitleBar.ACTION_RIGHT_BUTTON;     // 右边ImageBtn被点击
        // CommonTitleBar.ACTION_SEARCH;           // 搜索框被点击,搜索框不可输入的状态下会被触发
        // CommonTitleBar.ACTION_SEARCH_SUBMIT;    // 搜索框输入状态下,键盘提交触发，此时，extra为输入内容
        // CommonTitleBar.ACTION_SEARCH_VOICE;     // 语音按钮被点击
        // CommonTitleBar.ACTION_SEARCH_DELETE;    // 搜索删除按钮被点击
        if (action == CommonTitleBar.ACTION_LEFT_BUTTON) {
            finish();
        }
    }


    protected boolean isLogined() {//是否已登录
        if (null == UserUtil.getUserBean() || TextUtils.isEmpty(UserUtil.getUserBean().getToken())) {//未登录状态
            return false;
        }
        return true;
    }

    /**
     * 是否需要验证登录信息
     */
    protected boolean isNeedLogin() {//如果想让本界面需要登录 则重写该方法
        return true;
    }

    protected boolean isEyes() {
        return true;
    }


    /**
     * 初始化 layoutResID, 框架则不会调用 {@link Activity#setContentView(int)}
     *
     * @return layoutResID
     */
    protected abstract int getLayoutId();

    /**
     * 初始化 View
     *
     * @param savedInstanceState
     * @return layoutResID
     */
    protected abstract void initView(@Nullable Bundle savedInstanceState, @Nullable Bundle bundle);

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 获取初始化数据
     */
    protected void getBaseData() {

    }

    /**
     * EventBus接收消息
     *
     * @return 是否接收消息，默认false
     */
    protected boolean useEventBus() {
        return false;
    }

    /**
     * EventBus接收消息
     *
     * @param event 获取事件总线信息
     */
    protected void onEventComing(MessageEvent event) {
    }

    /**
     * 发送消息
     *
     * @param event
     */
    protected void postEvent(MessageEvent event) {
        if (event != null) {
            EventBus.getDefault().post(event);
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        if (null != event) {
            onEventComing(event);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (useEventBus()) {
            EventBus.getDefault().unregister(this);
        }
        AppManager.getAppManager().removeActivity(this);
    }

}
