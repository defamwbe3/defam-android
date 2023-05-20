package Medium.DeFam.app.common.utils;

import android.text.TextUtils;

import com.google.gson.Gson;
import Medium.DeFam.app.common.Constants;
import Medium.DeFam.app.common.bean.MessageEvent;
import Medium.DeFam.app.common.bean.UserBean;

import org.greenrobot.eventbus.EventBus;

public class UserUtil {

    private static UserBean userBean;

    public static void initUserUtil() {
        String userStr = SpUtil.getInstance().getStringValue(Constants.KEY_USER_INFO);
        if (!TextUtils.isEmpty(userStr)) {
            userBean = new Gson().fromJson(userStr, UserBean.class);
        }
    }

    public static boolean isLogin() {
        return userBean != null;
    }

    public static UserBean getUserBean() {
        return UserUtil.userBean;
    }

    public static void setUserBean(UserBean userBean) {
        setUserBeans(userBean);
        EventBus.getDefault().post(new MessageEvent<UserBean>(Constants.ACTION_LOGIN, userBean));
    }
    public static void setUserBeans(UserBean userBean) {
        UserUtil.userBean = userBean;
        if (userBean == null) {
            SpUtil.getInstance().removeValue(Constants.KEY_USER_INFO);
        } else {
            SpUtil.getInstance().setStringValue(Constants.KEY_USER_INFO, new Gson().toJson(userBean));
        }
    }

    public static String getToken() {
        return UserUtil.userBean == null ? "" : UserUtil.userBean.getToken();
    }
  /*  public static String getUsetId() {
        return UserUtil.userBean == null ? "" : UserUtil.userBean.getId();
    }*/
}
