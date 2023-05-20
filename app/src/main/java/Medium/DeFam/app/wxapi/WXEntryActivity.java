package Medium.DeFam.app.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.ShowMessageFromWX;
import com.tencent.mm.opensdk.modelmsg.WXAppExtendObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import Medium.DeFam.app.common.Constants;
import Medium.DeFam.app.wx.WxLogin;
import Medium.DeFam.app.wx.WxShareInstance;
import Medium.DeFam.app.wx.interfaces.WxLoginResultListener;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    private static final String mState = WxLogin.WX_LOGIN_STATE;
    private IWXAPI api;
    private static WxLoginResultListener mWxLoginResultListener = null;

    public static void setWxLoginListener(WxLoginResultListener listener) {
        mWxLoginResultListener = listener;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, Constants.WEIXIN_APP_ID, false);
        try {
            Intent intent = getIntent();
            api.handleIntent(intent, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
        Log.i("zmh666", "----" + req.toString());
        switch (req.getType()) {
            case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:
                //Log.i("zmh666", "111111");
                break;
            case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:
                goToShowMsg((ShowMessageFromWX.Req) req);
                break;
            default:
                break;
        }
        finish();
    }

    @Override
    public void onResp(BaseResp resp) {
        //Log.i("zmh666", "+++++"+resp.toString());
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                //发送成功
                switch (resp.getType()) {
                    case ConstantsAPI.COMMAND_SENDAUTH: {
                        //微信登录
                        SendAuth.Resp sendResp = (SendAuth.Resp) resp;
                        if (mState != null && mState.equals(sendResp.state)) {
                            Log.i("zmh666", sendResp.code);
                            if (null != mWxLoginResultListener) {
                                mWxLoginResultListener.onSuccess(sendResp.code);
                                mWxLoginResultListener = null;
                            }
                        }
                        break;
                    }
                    case ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX: {
                        //微信分享
                        WxShareInstance.notifyShareResult(WxShareInstance.SHARE_SUCCESS);
                        break;
                    }
                }
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                //发送取消
                switch (resp.getType()) {
                    case ConstantsAPI.COMMAND_SENDAUTH: {
                        //微信登录取消
                        if (null != mWxLoginResultListener) {
                            mWxLoginResultListener.onCancel();
                            mWxLoginResultListener = null;
                        }
                        break;
                    }
                    case ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX: {
                        //微信分享取消
                        WxShareInstance.notifyShareResult(WxShareInstance.SHARE_CANCEL);

                        break;
                    }
                }
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                //发送被拒绝
                switch (resp.getType()) {
                    case ConstantsAPI.COMMAND_SENDAUTH: {
                        //微信登录被拒绝
                        if (null != mWxLoginResultListener) {
                            mWxLoginResultListener.onCancel();
                            mWxLoginResultListener = null;
                        }
                        break;
                    }
                    case ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX: {
                        //微信分享失败
                        WxShareInstance.notifyShareResult(WxShareInstance.SHARE_FAIL);
                        break;
                    }
                }
                break;
            case BaseResp.ErrCode.ERR_UNSUPPORT:
                //不支持错误
                if (null != mWxLoginResultListener) {
                    mWxLoginResultListener.onError("不支持错误");
                    mWxLoginResultListener = null;
                }
                break;
            default:
                //发送返回
                break;
        }



		/*if (resp.getType() == ConstantsAPI.COMMAND_SUBSCRIBE_MESSAGE) {
			SubscribeMessage.Resp subscribeMsgResp = (SubscribeMessage.Resp) resp;
			String text = String.format("openid=%s\ntemplate_id=%s\nscene=%d\naction=%s\nreserved=%s",
					subscribeMsgResp.openId, subscribeMsgResp.templateID, subscribeMsgResp.scene, subscribeMsgResp.action, subscribeMsgResp.reserved);

			Toast.makeText(this, text, Toast.LENGTH_LONG).show();
		}

        if (resp.getType() == ConstantsAPI.COMMAND_LAUNCH_WX_MINIPROGRAM) {
            WXLaunchMiniProgram.Resp launchMiniProgramResp = (WXLaunchMiniProgram.Resp) resp;
            String text = String.format("openid=%s\nextMsg=%s\nerrStr=%s",
                    launchMiniProgramResp.openId, launchMiniProgramResp.extMsg,launchMiniProgramResp.errStr);

            Toast.makeText(this, text, Toast.LENGTH_LONG).show();
        }

        if (resp.getType() == ConstantsAPI.COMMAND_OPEN_BUSINESS_VIEW) {
            WXOpenBusinessView.Resp launchMiniProgramResp = (WXOpenBusinessView.Resp) resp;
            String text = String.format("openid=%s\nextMsg=%s\nerrStr=%s\nbusinessType=%s",
                    launchMiniProgramResp.openId, launchMiniProgramResp.extMsg,launchMiniProgramResp.errStr,launchMiniProgramResp.businessType);

            Toast.makeText(this, text, Toast.LENGTH_LONG).show();
        }

        if (resp.getType() == ConstantsAPI.COMMAND_OPEN_BUSINESS_WEBVIEW) {
            WXOpenBusinessWebview.Resp response = (WXOpenBusinessWebview.Resp) resp;
            String text = String.format("businessType=%d\nresultInfo=%s\nret=%d",response.businessType,response.resultInfo,response.errCode);

            Toast.makeText(this, text, Toast.LENGTH_LONG).show();
        }*/

		/*if (resp.getType() == ConstantsAPI.COMMAND_SENDAUTH) {
			SendAuth.Resp authResp = (SendAuth.Resp)resp;
			final String code = authResp.code;
			NetworkUtil.sendWxAPI(handler, String.format("https://api.weixin.qq.com/sns/oauth2/access_token?" +
							"appid=%s&secret=%s&code=%s&grant_type=authorization_code", "wxd930ea5d5a258f4f",
					"1d6d1d57a3dd063b36d917bc0b44d964", code), NetworkUtil.GET_TOKEN);
		}*/
        finish();
    }


    private void goToShowMsg(ShowMessageFromWX.Req showReq) {
        WXMediaMessage wxMsg = showReq.message;
        WXAppExtendObject obj = (WXAppExtendObject) wxMsg.mediaObject;
        StringBuffer msg = new StringBuffer();
        msg.append("description: ");
        msg.append(wxMsg.description);
        msg.append("\n");
        msg.append("extInfo: ");
        msg.append(obj.extInfo);
        msg.append("\n");
        msg.append("filePath: ");
        msg.append(obj.filePath);
        Log.i("zmh666", msg.toString());


        String str = obj.extInfo;
        if (TextUtils.isEmpty(str)) {
            return;
        }
        String[] all = str.split("&");
        if (all.length != 2) {
            return;
        }
        //自动登录

    }


}