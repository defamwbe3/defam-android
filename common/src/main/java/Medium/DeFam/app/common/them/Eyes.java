package Medium.DeFam.app.common.them;


import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.ColorInt;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Eyes {
    /**
     * 设置状态栏颜色
     *
     * @param activity
     * @param color
     */
    public static void setStatusBarLightMode(Activity activity, @ColorInt int color) {
        setStatusColor(activity, color, 0);
    }
    /**
     * 设置状态栏颜色以及明暗度
     *
     * @param activity
     * @param color
     * @param statusBarAlpha
     */
    public static void setStatusColor(Activity activity, @ColorInt int color, int statusBarAlpha) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            EyesLollipop.setStatusColor(activity,color,statusBarAlpha);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            EyesKitKat.setStatusColor(activity,color,statusBarAlpha);
        }
        //setStatusBarTextColor(activity,true);
    }
    /**
     * 在有fragment的activity中使用
     *fragment的最顶端加一个状态栏大小的view或者设置padding
     *
     * @param activity
     */
    public static void setTranslucent(Activity activity) {
        setARGBStatusBar(activity, 0, 0, 0, 0);
    }
    /**
     * 设置透明状态栏版本的状态栏的ARGB
     * @param activity
     * @param r
     * @param g
     * @param b
     * @param alpha
     */
    public static void setARGBStatusBar(Activity activity, int r, int g, int b, int alpha) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            EyesLollipop.setARGBStatusBar(activity,r,g,b,alpha);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            EyesKitKat.setARGBStatusBar(activity,r,g,b,alpha);
        }
        setStatusBarTextColor(activity,true);
    }


    /**
     * 真对view动态添加状态栏及背景色
     *
     * @param context
     * @param view
     * @param color
     */
    public static void addStatusBar(Activity context, ViewGroup view, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (null != view) {
                View statusView = new View(context);
                ViewGroup.LayoutParams statusViewLp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        getStatusBarHeight(context));
                //颜色的设置可抽取出来让子类实现之
                statusView.setBackgroundColor(color);
                view.addView(statusView, 0, statusViewLp);
            }
        }
    }


    /**
     * 状态栏字体状态
     *true 黑色，false白色
     * @param activity
     */
    public static void setStatusBarTextColor(Activity activity, boolean bDark) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            return;
        }
        //6.0以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decorView = activity.getWindow().getDecorView();
            if (decorView != null) {
                int vis = decorView.getSystemUiVisibility();
                if (bDark) {
                    vis |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                } else {
                    vis &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                }

                decorView.setSystemUiVisibility(vis);
            }
        }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
        /*    setStatusColor(activity, Color.WHITE, 30);
        }else{*/
            FlymeSetStatusBarLightMode(activity,bDark);
            MIUISetStatusBarLightMode(activity,bDark);
        }
    }
    /**
     * MIUI 6的沉浸支持透明白色字体和透明黑色字体
     */
    private static boolean MIUISetStatusBarLightMode(Activity activity, boolean darkmode) {
        boolean result = false;
        Class<? extends Window> clazz = activity.getWindow().getClass();
        try {
            int darkModeFlag = 0;
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(activity.getWindow(), darkmode ? darkModeFlag : 0, darkModeFlag);
            result = true;
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return result;
    }

    /**
     * 设置状态栏图标为深色和魅族特定的文字风格，Flyme4.0以上
     */
    private static boolean FlymeSetStatusBarLightMode(Activity activity, boolean darkmode) {
        boolean result = false;
        try {
            WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
            Field darkFlag = WindowManager.LayoutParams.class
                    .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
            Field meizuFlags = WindowManager.LayoutParams.class
                    .getDeclaredField("meizuFlags");
            darkFlag.setAccessible(true);
            meizuFlags.setAccessible(true);
            int bit = darkFlag.getInt(null);
            int value = meizuFlags.getInt(lp);
            if (darkmode) {
                value |= bit;
            } else {
                value &= ~bit;
            }
            meizuFlags.setInt(lp, value);
            activity.getWindow().setAttributes(lp);
            result = true;
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return result;
    }

    /**
     * 状态栏高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            return 0;
        }
        int result = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int resId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resId > 0) {
                result = context.getResources().getDimensionPixelOffset(resId);
            }
        }
        return result;
    }
    /**
     * 判断是否存在虚拟按键
     *
     * @return
     */
    public static boolean checkBar(Context context) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class<?> systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {

        }
        return hasNavigationBar;
    }
}
