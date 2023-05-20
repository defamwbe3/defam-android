package Medium.DeFam.app.common.utils;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class ScreenUtils {

    public static float dp2Px(Context context, float dp) {
        if (context == null) {
            return -1;
        }
        return dp * density(context);
    }

    public static float px2Dp(Context context, float px) {
        if (context == null) {
            return -1;
        }
        return px / density(context);
    }

    public static float density(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }


    public static int dp2PxInt(Context context, float dp) {
        return (int) (dp2Px(context, dp) + 0.5f);
    }

    public static float px2DpCeilInt(Context context, float px) {
        return (int) (px2Dp(context, px) + 0.5f);
    }


    public static int sp2px(Context context, float spValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static int px2sp(Context context, float pxValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    public static DisplayMetrics getDisplayMetrics(Context context) {
        Activity activity;
        if (!(context instanceof Activity) && context instanceof ContextWrapper) {
            activity = (Activity) ((ContextWrapper) context).getBaseContext();
        } else {
            activity = (Activity) context;
        }
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics;
    }

    /**
     * 获取屏幕大小
     *
     * @param context
     * @return
     */
    public static int[] getScreenPixelSize(Context context) {
        DisplayMetrics metrics = getDisplayMetrics(context);
        return new int[]{metrics.widthPixels, metrics.heightPixels};
    }

    public static void hideSoftInputKeyBoard(Context context, View focusView) {
        if (focusView != null) {
            InputMethodManager imm = (InputMethodManager) focusView.getContext()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(focusView.getWindowToken(),0);
            }
        }
    }

    public static void showSoftInputKeyBoard(Context context, View focusView) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(focusView, InputMethodManager.SHOW_FORCED);
    }

    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenWidthDP(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels/dm.densityDpi;
    }

    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static int getScreenHeightDP(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.heightPixels/dm.densityDpi;
    }

}