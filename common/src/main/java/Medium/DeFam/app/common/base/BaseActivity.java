package Medium.DeFam.app.common.base;

import static Medium.DeFam.app.common.timeset.PollingService.flag;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import Medium.DeFam.app.common.ActivityRouter;
import Medium.DeFam.app.common.bean.MessageEvent;
import Medium.DeFam.app.common.interfaces.LoadingInterface;
import Medium.DeFam.app.common.them.Eyes;
import Medium.DeFam.app.common.timeset.PollingUtils;
import Medium.DeFam.app.common.titlebar.CommonTitleBar;
import Medium.DeFam.app.common.utils.AppManager;
import Medium.DeFam.app.common.utils.UserUtil;
import Medium.DeFam.app.common.widget.LoadingController;
import Medium.DeFam.app.common.widget.ProgressDialog;
import butterknife.ButterKnife;


public abstract class BaseActivity extends AppCompatActivity {
    protected CommonTitleBar mTitleBar;
    protected LoadingController loadingController;
    private ProgressDialog progress;

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

    public ProgressDialog showProgress(String msg) {
        if (progress == null) {
            progress = new ProgressDialog(this);
        }
        progress.setMsg(msg);
        progress.show();
        return progress;
    }

    public ProgressDialog showProgressCancelable(String msg) {
        if (progress == null) {
            progress = new ProgressDialog(this, msg);
        }
        progress.setCancelable(true);
        progress.show();
        return progress;
    }

    /**
     * 关闭等待框
     */
    public void closeProgress() {
        try {
            if (progress != null) {
                progress.dismiss();
            }
        } catch (Exception e) {
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
