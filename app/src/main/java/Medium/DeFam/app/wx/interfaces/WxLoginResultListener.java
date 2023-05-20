package Medium.DeFam.app.wx.interfaces;

public interface WxLoginResultListener {

    void onSuccess(String code);

    void onError(String errorDetail);

    void onCancel();
}
