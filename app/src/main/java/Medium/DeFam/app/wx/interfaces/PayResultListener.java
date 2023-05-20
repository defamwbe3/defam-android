package Medium.DeFam.app.wx.interfaces;

/**
 * 支付结果回调
 */

public interface PayResultListener {
    void onSuccess(String info);
    void onFail(String info);
}
