package Medium.DeFam.app.common.http;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hjq.toast.Toaster;
import com.lzy.okgo.callback.Callback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import Medium.DeFam.app.common.ActivityRouter;
import Medium.DeFam.app.common.R;
import Medium.DeFam.app.common.utils.AppManager;
import Medium.DeFam.app.common.utils.DialogUitl;
import Medium.DeFam.app.common.utils.LogUtils;
import Medium.DeFam.app.common.utils.UserUtil;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.net.UnknownServiceException;


public abstract class TradeHttpCallback<T> implements Callback<T> {
    private Dialog mLoadingDialog;
    private Type type;
    private Class<T> clazz;

    @Override
    public T convertResponse(okhttp3.Response response) throws Throwable {
        // String str = response.body().string();
        // LogUtils.myi("zmh",response.request().url().toString() + " >>> " + str);
        if (type == null) {
            if (clazz == null) {
                Type genType = getClass().getGenericSuperclass();
                type = ((ParameterizedType) genType).getActualTypeArguments()[0];
            } else {
                JsonConvert<T> convert = new JsonConvert<>(clazz);
                return convert.convertResponse(response);
            }
        }
        JsonConvert<T> convert = new JsonConvert<>(type);
        return convert.convertResponse(response);
    }

    @Override
    public void onSuccess(Response<T> response) {
        JsonBean<T> bean = (JsonBean<T>) response.body();
        if (bean != null) {

            if (10000 == bean.getCode()) {
                try {
                    if (null != (T) bean) {
                        onSuccess((T) bean);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (401 == bean.getCode()) {
                //登录过期，退出登录
                showDialog();
            } else {
                //服务器返回值异常--->ret: " + bean.getRet() + " msg: " + bean.getMsg());
                Toaster.show(bean.getMessage());
            }
        }

    }

    @Override
    public void onError(Response<T> response) {
        Throwable t = response.getException();
        LogUtils.i("网络请求错误---->" + t.getClass() + " : " + t.getMessage());
        if (t instanceof SocketTimeoutException || t instanceof ConnectException || t instanceof UnknownHostException || t instanceof UnknownServiceException || t instanceof SocketException) {
            Toaster.show(R.string.load_failure);
            return;
        }
        String err = t.getMessage();
        if (!TextUtils.isEmpty(err)) {
            try {
                JsonBean jsonBean = new Gson().fromJson(err, new TypeToken<JsonBean>() {
                }.getType());
                if (jsonBean != null) {
                    if (401 == jsonBean.getCode()) {
                        //登录过期，退出登录
                        showDialog();
                    } else if (!TextUtils.isEmpty(jsonBean.getMessage())) {
                        Toaster.show(jsonBean.getMessage());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    public abstract void onSuccess(T data);

    @Override
    public void onStart(Request<T, ? extends Request> request) {
        if (showLoadingDialog()) {
            if (mLoadingDialog == null) {
                mLoadingDialog = createLoadingDialog();
            }
            mLoadingDialog.show();
        }
    }

    private void showDialog() {
        if (null != UserUtil.getUserBean()) {
            UserUtil.setUserBean(null);
            Activity activity = AppManager.getAppManager().currentActivity();
            Toaster.show(activity.getString(R.string.dengluguoqi));
            Intent intent = ActivityRouter.getIntent(activity, ActivityRouter.Mall.MALL_LOGIN);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            activity.startActivity(intent);
        }

    }


    @Override
    public void onFinish() {
        if (showLoadingDialog() && null != mLoadingDialog) {
            mLoadingDialog.dismiss();
        }
    }

    public Dialog createLoadingDialog() {
        return DialogUitl.loadingDialog(AppManager.getAppManager().currentActivity());
    }

    public boolean showLoadingDialog() {
        return false;
    }

    @Override
    public void onCacheSuccess(Response<T> response) {
    }

    @Override
    public void uploadProgress(Progress progress) {

    }

    @Override
    public void downloadProgress(Progress progress) {

    }
}
