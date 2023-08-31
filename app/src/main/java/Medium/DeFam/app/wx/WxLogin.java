package Medium.DeFam.app.wx;



import static Medium.DeFam.app.common.Constants.WEIXIN_APP_ID;

import android.content.Context;

import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import Medium.DeFam.app.AppContext;
import Medium.DeFam.app.wx.interfaces.WxLoginResultListener;

public class WxLogin {
    //微信登录标示
    public static final String WX_LOGIN_STATE = "newlonglaimall";
    private static WxLogin INSTANCE;
    private IWXAPI mWxApi;
    private Context mContext = AppContext.getInstance().getApplicationContext();

    private WxLogin(){
        mWxApi = WXAPIFactory.createWXAPI(mContext, WEIXIN_APP_ID);
    }

    public static synchronized WxLogin getInstance(){
        if (null == INSTANCE) {
            INSTANCE = new WxLogin();
        }
        return INSTANCE;
    }

    public void login(WxLoginResultListener listener) {
        if (null == mWxApi) {
            listener.onError("unknow error!");
        }
        if (!mWxApi.isWXAppInstalled()) {//判断微信是否安装
            listener.onError("未安装微信");
            return;
        }
//        WXEntryActivity.setWxLoginListener(listener);//设置登录结果回调
        //启动微信登录
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";//snsapi_base或者snsapi_userinfo
        req.state = WX_LOGIN_STATE;
        mWxApi.sendReq(req);
    }
}
