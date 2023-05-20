package Medium.DeFam.app;

import static com.xuexiang.xupdate.entity.UpdateError.ERROR.CHECK_NO_NEW_VERSION;
import static com.xuexiang.xupdate.entity.UpdateError.ERROR.PROMPT_ACTIVITY_DESTROY;

import android.util.Log;

import androidx.multidex.MultiDex;

import com.xuexiang.xupdate.XUpdate;
import com.xuexiang.xupdate.entity.UpdateError;
import com.xuexiang.xupdate.listener.OnUpdateFailureListener;
import com.xuexiang.xupdate.utils.UpdateUtils;

import Medium.DeFam.app.activity.Setting;
import Medium.DeFam.app.common.CommonAppContext;
import Medium.DeFam.app.common.http.HttpClient;
import Medium.DeFam.app.common.utils.AppManager;
import Medium.DeFam.app.common.utils.FileUtilMy;
import Medium.DeFam.app.common.utils.ToastUtil;
import Medium.DeFam.app.common.utils.UserUtil;
import Medium.DeFam.app.utils.OKGoUpdateHttpService;

public class AppContext extends CommonAppContext {
    private static AppContext instance;

    public static AppContext instance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        MultiDex.install(this);
        HttpClient.getInstance().init();
        //文件管理
        FileUtilMy.getInstance().createRootPath();
        UserUtil.initUserUtil();
        initXUpdate();

    }

    private void initXUpdate() {
        XUpdate.get()
                .debug(BuildConfig.DEBUG)
                .isWifiOnly(false)                                               //默认设置只在wifi下检查版本更新
                .isGet(false)                                                    //默认设置使用get请求检查版本
                .isAutoMode(false)                                              //默认设置非自动模式，可根据具体使用配置
                .param("type", "android")
                .param("versionCode", UpdateUtils.getVersionCode(this))         //设置默认公共请求参数
                .setOnUpdateFailureListener(new OnUpdateFailureListener() {
                    @Override
                    public void onFailure(UpdateError error) {
                        Log.i("mylog1", "XUpdate: " + error.getDetailMsg());
                        try {
                            if (AppManager.getAppManager().currentActivity().getComponentName().getClassName().equals(Setting.class.getName())) {
                                if (error.getCode() == CHECK_NO_NEW_VERSION) {
                                    ToastUtil.initToast(error.toString());
                                } else if (error.getCode() != PROMPT_ACTIVITY_DESTROY) {
                                    ToastUtil.initToast(error.toString());
                                }
                            }
                        } catch (Exception e) {
                        }
                    }
                })
                .supportSilentInstall(false)                                     //设置是否支持静默安装，默认是true
                .setIUpdateHttpService(new OKGoUpdateHttpService())           //这个必须设置！实现网络请求功能。
                .init(this);                                                    //这个必须初始化
    }

}
