package Medium.DeFam.app.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import Medium.DeFam.app.common.Constants;
import Medium.DeFam.app.wx.interfaces.PayResultListener;


public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;

    private static PayResultListener mPayResultListener;

    public static void setPayResultListener(PayResultListener listener) {
        mPayResultListener = listener;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.pay_result);

        api = WXAPIFactory.createWXAPI(this, Constants.WEIXIN_APP_ID);
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
    }

    @Override
    public void onResp(BaseResp resp) {
        Log.i("hhh", "onPayFinish, errCode = " + resp.errCode + "," + resp.errStr);
        //Toaster.show("onPayFinish, errCode = " + resp.errCode + "," + resp.errStr);
        int code = resp.errCode;
		/*
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("result");
		builder.setMessage(getString(R.string.pay_result_callback_msg, String.valueOf(resp.errCode)));

		builder.create().show();
		*/
        if (code == 0) {//支付完成
            mPayResultListener.onSuccess(null);
            this.finish();
        } else if (code == -1) {//支付失败
            //Toast.makeText(this,"支付失败", Toast.LENGTH_SHORT).show();
            mPayResultListener.onFail("支付失败");
            this.finish();
        } else if (code == -2) {//用户取消
            //Toast.makeText(this,"取消支付", Toast.LENGTH_SHORT).show();
            mPayResultListener.onFail("取消支付");
            this.finish();
        } else {
            mPayResultListener.onFail(null);
            this.finish();
        }
    }

    @Override
    public void finish() {
        ViewGroup view = (ViewGroup) getWindow().getDecorView();
        view.removeAllViews();
        super.finish();
    }
}
