package Medium.DeFam.app.utils;

import android.app.Activity;
import android.content.Intent;
import android.webkit.JavascriptInterface;

import org.greenrobot.eventbus.EventBus;

import Medium.DeFam.app.activity.PutOk;
import Medium.DeFam.app.common.Constants;
import Medium.DeFam.app.common.bean.MessageEvent;

public class AndroidtoJs extends Object {
    public static final String METHOD_NAME = "androidapp";
    private Activity mContext;

    public AndroidtoJs(Activity mContext) {
        this.mContext = mContext;

    }

    @JavascriptInterface
    public void toFnish(String str) {
        if ("2".equals(str)) {//发布完成
            EventBus.getDefault().post(new MessageEvent<>(Constants.QUANZI));
            Intent intent = new Intent(mContext, PutOk.class);
            mContext.startActivity(intent);
        }
        mContext.finish();
    }
}
