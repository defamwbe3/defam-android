package Medium.DeFam.app.common.interfaces;

public interface LoadingInterface {

    void showLoading();

    void showNetworkError();

    void showError();

    void showEmpty();

    void dismissLoading();

    interface OnClickListener {
        void onClick();
    }
}
