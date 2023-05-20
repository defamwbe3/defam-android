package Medium.DeFam.app.common.utils;


import static android.net.ConnectivityManager.TYPE_MOBILE;
import static android.net.ConnectivityManager.TYPE_WIFI;
import static android.telephony.TelephonyManager.NETWORK_TYPE_CDMA;
import static android.telephony.TelephonyManager.NETWORK_TYPE_EDGE;
import static android.telephony.TelephonyManager.NETWORK_TYPE_EVDO_0;
import static android.telephony.TelephonyManager.NETWORK_TYPE_GPRS;
import static android.telephony.TelephonyManager.NETWORK_TYPE_HSDPA;
import static android.telephony.TelephonyManager.NETWORK_TYPE_LTE;
import static android.telephony.TelephonyManager.NETWORK_TYPE_UMTS;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetUtils {
    /**
     * 判断是否有网络连接
     *
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
        NetworkInfo info = getNetworkInfo(context.getApplicationContext());
        return info != null && info.isConnected();
    }


    /**
     * 判断当前网络是否是wifi网络
     *
     * @param context
     * @return boolean
     */
    public static boolean isWifi(Context context) {
        NetworkInfo info = getNetworkInfo(context.getApplicationContext());
        return info != null && info.getType() == TYPE_WIFI;
    }



    /**
     * 判断当前网络是否是2G网络
     * 2G 移动和联通的2G为GPRS或EGDE，电信的2G为CDMA
     *
     * @param context
     * @return boolean
     */
    public static boolean is2G(Context context) {
        NetworkInfo info = getNetworkInfo(context.getApplicationContext());
        if (info != null) {
            int subType = info.getSubtype();
            return subType == NETWORK_TYPE_EDGE || subType == NETWORK_TYPE_GPRS || subType == NETWORK_TYPE_CDMA;
        } else {
            return false;
        }
    }

    /**
     * 判断当前网络是否是3G网络
     * 3G 联通的3G为UMTS或HSDPA 电信的3G为EVDO
     *
     * @param context
     * @return boolean
     */
    public static boolean is3G(Context context) {
        NetworkInfo info = getNetworkInfo(context.getApplicationContext());
        if (info != null) {
            int subType = info.getSubtype();
            return subType == NETWORK_TYPE_UMTS || subType == NETWORK_TYPE_HSDPA || subType == NETWORK_TYPE_EVDO_0;
        } else {
            return false;
        }
    }

    /**
     * 判断当前网络是否是4G网络
     *
     * @param context
     * @return boolean
     */
    public static boolean is4G(Context context) {
        NetworkInfo info = getNetworkInfo(context.getApplicationContext());
        if (info != null) {
            int subType = info.getSubtype();
            return subType == NETWORK_TYPE_LTE;
        } else {
            return false;
        }
    }

    /**
     * 判断当前网络是否是移动网络
     *
     * @param context
     * @return boolean
     */
    public static boolean isMobie(Context context) {
        NetworkInfo info = getNetworkInfo(context.getApplicationContext());
        return info != null && info.getType() == TYPE_MOBILE && info.isAvailable();
    }
    /**
     * 获取手机所有连接管理对象(包括对wi-fi,net等连接的管理)
     *
     * @param context
     * @return
     */
    private static NetworkInfo getNetworkInfo(Context context) {
        return getConnectivityService(context).getActiveNetworkInfo();
    }
    /**
     * 获取ConnectivityManager服务
     *
     * @param context
     * @return
     */
    private static ConnectivityManager getConnectivityService(Context context) {
        return (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

}

