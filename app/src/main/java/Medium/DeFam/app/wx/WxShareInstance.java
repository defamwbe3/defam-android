package Medium.DeFam.app.wx;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.hjq.toast.Toaster;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMiniProgramObject;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.IOException;
import java.io.InputStream;

import Medium.DeFam.app.R;
import Medium.DeFam.app.common.Constants;
import Medium.DeFam.app.common.utils.ImageUtil;
import Medium.DeFam.app.wx.interfaces.ShareListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WxShareInstance {

    /**
     * 微信分享限制thumb image必须小于32Kb，否则点击分享会没有反应
     */
    private static ShareListener mShareListener;
    public static final int SHARE_SUCCESS = 0;//分享成功
    public static final int SHARE_FAIL = -4;//分享失败
    public static final int SHARE_CANCEL = -2;//分享取消

    private IWXAPI mIWXAPI;
    private static final int WX_THUMB_SIZE = 120;

    public WxShareInstance(Context context, String appId) {
        mIWXAPI = WXAPIFactory.createWXAPI(context, appId);
        mIWXAPI.registerApp(appId);
    }

    public static void notifyShareResult(int resultCode) {
        if (null != mShareListener) {
            switch (resultCode) {
                case SHARE_SUCCESS:
                    mShareListener.shareSuccess();
                    break;
                case SHARE_FAIL:
                    mShareListener.shareFailure();
                    break;
                case SHARE_CANCEL:

                    mShareListener.shareCancel();
                    break;
            }
        }
    }

    public void shareText(int platform, String text, Context activity) {
        //初始化一个WXTextObject对象，填写分享的文本内容
        WXTextObject textObject = new WXTextObject();
        textObject.text = text;
        //对象初始化一个WXMediaMessage对象
        WXMediaMessage message = new WXMediaMessage();
        message.mediaObject = textObject;
        message.description = text;
        sendMessage(platform, message, buildTransaction("text"));
    }

    public void shareXCX(Context activity, String path, String title, String img) {
        if (!mIWXAPI.isWXAppInstalled()) {//判断微信是否安装
            //listener.onError("未安装微信");
            return;
        }
        if (null == img) {
            Bitmap bitmap = BitmapFactory.decodeResource(activity.getResources(), R.mipmap.ic_launcher);
            Bitmap sendBitmap = Bitmap.createScaledBitmap(bitmap, 200, 200, true);
            bitmap.recycle();
            toshareXCX(activity, path, title, sendBitmap);
        } else {
            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(img)
                    .build();
            okHttpClient.newCall(request).enqueue(new Callback() {
                public void onFailure(Call call, IOException e) {
                    Toaster.show("图片下载失败");
                }

                public void onResponse(Call call, Response response) throws IOException {
                    InputStream inputStream = response.body().byteStream();//得到图片的流
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    toshareXCX(activity, path, title, bitmap);
                }
            });

         /*   Observable.just(true).doOnNext(aBoolean -> {
                GlideUtil.showImg(activity, img, null, bmt -> {//下载成功 后替换
                    if (null != bmt) {
                        toshareXCX(activity, path, title, bmt);
                    }
                });
            }).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread()).subscribe();
*/
        }


    }

    public void toshareXCX(Context activity, String path, String title, Bitmap img) {
        WXMiniProgramObject miniProgram = new WXMiniProgramObject();
        miniProgram.webpageUrl = Constants.HOST + "/wyapi.php?g=wyapi&c=AppDownload&a=index";//自定义
        //miniProgram.miniprogramType = WXMiniProgramObject.MINIPROGRAM_TYPE_PREVIEW;// 正式版:0，测试版:1，体验版:2
        miniProgram.userName = "gh_3e5566532d0d";//小程序端提供参数
        miniProgram.path = path;//小程序端提供参数
        WXMediaMessage mediaMessage = new WXMediaMessage(miniProgram);
        mediaMessage.title = title;//自定义
        mediaMessage.description = "吃喝玩乐尽在龙莱商城";//自定义
        try {
            mediaMessage.thumbData = ImageUtil.bmpToByteArray(img, true);
        } catch (Exception e) {

            Bitmap bitmap = BitmapFactory.decodeResource(activity.getResources(), R.mipmap.ic_launcher);
            Bitmap sendBitmap = Bitmap.createScaledBitmap(bitmap, 200, 200, true);
            bitmap.recycle();
            mediaMessage.thumbData = ImageUtil.bmpToByteArray(sendBitmap, true);
            if (null != sendBitmap) {
                sendBitmap.recycle();
            }
        }
        if (null != img) {
            img.recycle();
        }
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("xiaochengxu");
        req.scene = SendMessageToWX.Req.WXSceneSession;
        req.message = mediaMessage;
        mIWXAPI.sendReq(req);

    }

    /**
     *
     * @param platform
     *  SendMessageToWX.Req.WXSceneSession是分享到好友会话
     *  SendMessageToWX.Req.WXSceneTimeline是分享到朋友圈
     * @param title
     * @param content
     * @param url
     * @param bitmap
     * @param activity
     * @param shareListener
     */
    public void shareWeb(int platform, String title, String content, String url, Bitmap bitmap,
                         Context activity, ShareListener shareListener) {
        this.mShareListener = shareListener;
        if (mIWXAPI.isWXAppInstalled()) {
            Toaster.show("您还没有安装微信");
            return;
        }
        //初始化一个WXWebpageObject对象，填写分享的网页地址
        WXWebpageObject webObject = new WXWebpageObject();
        webObject.webpageUrl = url;
        //对象初始化一个WXMediaMessage对象
        WXMediaMessage message = new WXMediaMessage(webObject);
        message.title = "11111";//标题
        message.description = content;//描述
        /*if (null == bitmap) {
            bitmap = BitmapFactory.decodeResource(activity.getResources(), R.mipmap.ic_launcher);
        }
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bitmap, WX_THUMB_SIZE, WX_THUMB_SIZE, true);
//        bitmap.recycle();
        message.setThumbImage(thumbBmp);*/
        sendMessage(platform, message, buildTransaction("webpage"));
    }

    public void shareImg(int platform, String title, String content, String url, Bitmap bitmap,
                         Context activity, ShareListener shareListener) {
        if (null != bitmap) {
            WXImageObject imageObject = new WXImageObject(bitmap);
            WXMediaMessage message = new WXMediaMessage();
            message.mediaObject = imageObject;
            Bitmap thumbBmp = Bitmap.createScaledBitmap(bitmap, WX_THUMB_SIZE, WX_THUMB_SIZE, true);
            message.thumbData = ImageUtil.bitmap2Bytes(thumbBmp);
            sendMessage(platform, message, buildTransaction("webpage"));
        }
    }

    private void sendMessage(int platform, WXMediaMessage message, String transaction) {
        //构造一个RQ transaction用户标示类型
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = transaction;
        req.message = message;
        req.scene = platform;
        boolean flag =  mIWXAPI.sendReq(req);
        System.out.println(flag);
    }

    private String buildTransaction(String type) {
        return System.currentTimeMillis() + type;
    }

}
