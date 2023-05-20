package Medium.DeFam.app.wx;


import android.content.Context;

import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import Medium.DeFam.app.common.Constants;
import Medium.DeFam.app.wx.bean.GoPayBean;
import Medium.DeFam.app.wx.interfaces.PayResultListener;
import Medium.DeFam.app.wxapi.WXPayEntryActivity;

public class WxPay {

    private IWXAPI api;

    private static WxPay INSTANCE;

    private Context mContext;

    private WxPay(Context context) {
        this.mContext = context;
        api = WXAPIFactory.createWXAPI(mContext, Constants.WEIXIN_APP_ID);
    }

    public static synchronized WxPay getInstance(Context context) {
        if (null == INSTANCE) {
            INSTANCE = new WxPay(context);
        }
        return INSTANCE;
    }


    public void pay(GoPayBean payParams, PayResultListener listener) {
        WXPayEntryActivity.setPayResultListener(listener);//设置支付结果回调
        PayReq req = new PayReq();
        req.appId = payParams.appid;//微信开放平台审核通过的应用APPID
        req.partnerId = payParams.partnerid;//微信支付分配的商户号
        req.prepayId = payParams.prepayid;//微信返回的支付交易会话ID
        req.packageValue = "Sign=WXPay";//payParams.packages;//暂填写固定值Sign=WXPay
        req.nonceStr = payParams.noncestr;//随机字符串，不长于32位
        req.timeStamp = payParams.timestamp;//时间戳
        req.sign = payParams.sign;//签名
        api.sendReq(req);
    }
}

