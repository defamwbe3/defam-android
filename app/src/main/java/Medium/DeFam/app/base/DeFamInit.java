package Medium.DeFam.app.base;

import static com.xuexiang.xupdate.entity.UpdateError.ERROR.CHECK_NO_NEW_VERSION;
import static com.xuexiang.xupdate.entity.UpdateError.ERROR.PROMPT_ACTIVITY_DESTROY;

import android.app.Application;
import android.util.Log;

import com.hjq.toast.Toaster;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.xuexiang.xupdate.XUpdate;
import com.xuexiang.xupdate.entity.UpdateError;
import com.xuexiang.xupdate.listener.OnUpdateFailureListener;
import com.xuexiang.xupdate.utils.UpdateUtils;

import java.util.concurrent.atomic.AtomicBoolean;

import Medium.DeFam.app.BuildConfig;
import Medium.DeFam.app.activity.Setting;
import Medium.DeFam.app.common.Constants;
import Medium.DeFam.app.common.http.HttpClient;
import Medium.DeFam.app.common.utils.AppManager;
import Medium.DeFam.app.common.utils.FileUtilMy;
import Medium.DeFam.app.common.utils.LogUtils;
import Medium.DeFam.app.common.utils.UserUtil;
import Medium.DeFam.app.utils.OKGoUpdateHttpService;

public class DeFamInit {

    private static AtomicBoolean isInited = new AtomicBoolean(false);

    public static synchronized boolean init(Application context) {
        if (isInited.get()) {
            return true;
        }
        if (context == null) {
            LogUtils.i("unexcepted - context was null");
            return false;
        }
        // 初始化 Toast 框架
        Toaster.init(context);

        HttpClient.getInstance().init();
        //文件管理
        FileUtilMy.getInstance().createRootPath();
        UserUtil.initUserUtil();
        initXUpdate(context);
        UMConfigure.preInit(context, Constants.UMENG_APPKEY, "common");
        PlatformConfig.setWeixin(Constants.WEIXIN_APP_ID, Constants.WEIXIN_APPKEY);

        isInited.set(true);
        return true;
    }

    private static void initXUpdate(Application context) {
        XUpdate.get()
                .debug(BuildConfig.DEBUG)
                .isWifiOnly(false)                                               //默认设置只在wifi下检查版本更新
                .isGet(false)                                                    //默认设置使用get请求检查版本
                .isAutoMode(false)                                              //默认设置非自动模式，可根据具体使用配置
                .param("type", "android")
                .param("versionCode", UpdateUtils.getVersionCode(context))         //设置默认公共请求参数
                .setOnUpdateFailureListener(new OnUpdateFailureListener() {
                    @Override
                    public void onFailure(UpdateError error) {
                        Log.i("mylog1", "XUpdate: " + error.getDetailMsg());
                        try {
                            if (AppManager.getAppManager().currentActivity().getComponentName().getClassName().equals(Setting.class.getName())) {
                                if (error.getCode() == CHECK_NO_NEW_VERSION) {
                                    Toaster.show(error.toString());
                                } else if (error.getCode() != PROMPT_ACTIVITY_DESTROY) {
                                    Toaster.show(error.toString());
                                }
                            }
                        } catch (Exception e) {
                        }
                    }
                })
                .supportSilentInstall(false)                                     //设置是否支持静默安装，默认是true
                .setIUpdateHttpService(new OKGoUpdateHttpService())           //这个必须设置！实现网络请求功能。
                .init(context);                                                    //这个必须初始化
    }
}