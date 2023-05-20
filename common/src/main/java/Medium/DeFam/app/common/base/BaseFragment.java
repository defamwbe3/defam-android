package Medium.DeFam.app.common.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import Medium.DeFam.app.common.bean.MessageEvent;
import Medium.DeFam.app.common.interfaces.LoadingInterface;
import Medium.DeFam.app.common.interfaces.PermissionListener;
import Medium.DeFam.app.common.utils.UserUtil;
import Medium.DeFam.app.common.widget.LoadingController;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment {
    public Activity mActivity;
    protected LoadingController loadingController;
    /**
     * 获得全局的，防止使用getActivity()为空
     *
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        this.mActivity = (Activity) context;
        super.onAttach(context);
    }

    // 初始化Fragment布局
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        ButterKnife.bind(this, view);
        initView(view, savedInstanceState);
        return view;
    }

    // activity创建结束
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        if (useEventBus()) {
            EventBus.getDefault().register(this);
        }
    }

    public void request(View view) {
        loadingController = new LoadingController.Builder(mActivity, view)
                .setOnErrorRetryClickListener(new LoadingInterface.OnClickListener() {
                    @Override
                    public void onClick() {
                        getBaseFData();
                    }
                })
                .build();
        /*loadingController = new LoadingController.Builder(this, view)
                .setLoadingImageResource(R.drawable.loading_frame_anim)
                .setLoadingMessage("加载中...")
                .setErrorMessage("加载失败，请稍后重试~")
                .setErrorImageResoruce(R.drawable.error)
                .setEmptyMessage("暂无数据~")
                .setOnNetworkErrorRetryClickListener(new LoadingInterface.OnClickListener() {
                    @Override
                    public void onClick() {
                        getBaseData();
                    }
                })
                .setOnErrorRetryClickListener("点我重试", new LoadingInterface.OnClickListener() {
                    @Override
                    public void onClick() {
                        getBaseData();
                    }
                })
                .build();*/
        loadingController.showLoading();
        getBaseFData();
       /*
        videoLoadingController.dismissLoading();
       loadingController.showNetworkError();
        loadingController.showError();
        loadingController.showEmpty();*/
    }

    protected void getBaseFData() {

    }

    /**
     * 初始化布局, 子类必须实现
     */
    protected abstract void initView(View view, Bundle savedInstanceState);

    /**
     * 初始化数据, 子类可以不实现
     */
    public void initData() {

    }

    /**
     * 初始化布局, 子类必须实现
     */
    protected abstract int getLayoutId();

    protected boolean isLogined() {//是否已登录
        if (null == UserUtil.getUserBean() || TextUtils.isEmpty(UserUtil.getUserBean().getToken())) {//未登录状态
            return false;
        }
        return true;
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
    public void onDestroy() {
        super.onDestroy();
        if (useEventBus()) {
            EventBus.getDefault().unregister(this);
        }
    }
}
