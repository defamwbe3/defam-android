package Medium.DeFam.app.common.them;


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.annotation.ColorInt;

import Medium.DeFam.app.common.R;


@TargetApi(Build.VERSION_CODES.KITKAT)
public class EyesKitKat {
    //状态栏设置所支持最高版本
    private static int HighestSDK = Build.VERSION_CODES.KITKAT;
    private static final int STATUS_VIEW_ID = R.id.status_view;
    private static final int TRANSLUCENT_VIEW_ID = R.id.translucent_view;

    /**
     * 设置状态栏颜色以及明暗度
     *
     * @param activity
     * @param color
     * @param statusBarAlpha
     */
    public static void setStatusColor(Activity activity, @ColorInt int color, int statusBarAlpha) {
        if (Build.VERSION.SDK_INT >= HighestSDK) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //动态添加自定义状态栏view
            setStatusViewToAct(activity, color, statusBarAlpha);
            //设置状态栏占位
            setRootView(activity);
        }
    }

    /**
     * 设置透明状态栏版本的状态栏的ARGB
     *
     * @param activity
     * @param r
     * @param g
     * @param b
     * @param alpha
     */
    public static void setARGBStatusBar(Activity activity, int r, int g, int b, int alpha) {
        if (Build.VERSION.SDK_INT >= HighestSDK) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            setARGBStatusViewToAct(activity, r, g, b, alpha);
        }
    }

    /**
     * 设置状态栏view的ARGB，如果找到状态栏view则直接设置，否则创建一个再设置
     *
     * @param activity
     * @param statusBarAlpha
     */
    private static void setARGBStatusViewToAct(Activity activity, int r, int g, int b, int statusBarAlpha) {

        ViewGroup contentView = (ViewGroup) activity.findViewById(android.R.id.content);
        View fakeStatusBarView = contentView.findViewById(TRANSLUCENT_VIEW_ID);
        if (fakeStatusBarView != null) {
            if (fakeStatusBarView.getVisibility() == View.GONE) {
                fakeStatusBarView.setVisibility(View.VISIBLE);
            }
            fakeStatusBarView.setBackgroundColor(Color.argb(statusBarAlpha, r, g, b));
        } else {
            contentView.addView(createARGBStatusBarView(activity, r, g, b, statusBarAlpha));
        }
    }

    /**
     * 创建和状态栏一样高的矩形，用于改变状态栏ARGB
     *
     * @param activity
     * @param r
     * @param g
     * @param b
     * @param alpha
     * @return
     */
    private static View createARGBStatusBarView(Activity activity, int r, int g, int b, int alpha) {
        // 绘制一个和状态栏一样高的矩形
        View statusBarView = new View(activity);

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity));
        statusBarView.setLayoutParams(params);
        statusBarView.setBackgroundColor(Color.argb(alpha, r, g, b));
        statusBarView.setId(TRANSLUCENT_VIEW_ID);
        return statusBarView;
    }

    /**
     * 计算状态栏颜色明暗度
     *
     * @param color color值
     * @param alpha alpha值
     * @return 最终的状态栏颜色
     */
    private static int statusColorIntensity(@ColorInt int color, int alpha) {
        if (alpha == 0) {
            return color;
        }
        float a = 1 - alpha / 255f;
        int red = color >> 16 & 0xff;
        int green = color >> 8 & 0xff;
        int blue = color & 0xff;
        red = (int) (red * a + 0.5);
        green = (int) (green * a + 0.5);
        blue = (int) (blue * a + 0.5);
        return 0xff << 24 | red << 16 | green << 8 | blue;
    }

    /**
     * 自定义添加状态栏view，并检测是否添加过避免重复添加
     *
     * @param activity
     * @param color
     * @param statusBarAlpha
     */
    private static void setStatusViewToAct(Activity activity, @ColorInt int color, int statusBarAlpha) {
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        View fakeStatusBarView = decorView.findViewById(STATUS_VIEW_ID);
        if (fakeStatusBarView != null) {
            if (fakeStatusBarView.getVisibility() == View.GONE) {
                fakeStatusBarView.setVisibility(View.VISIBLE);
            }
            fakeStatusBarView.setBackgroundColor(statusColorIntensity(color, statusBarAlpha));
        } else {
            decorView.addView(createStatusBarView(activity, color, statusBarAlpha));
        }
    }

    /**
     * 创建和状态栏一样高的矩形，用于改变状态栏颜色和明暗度
     *
     * @param activity
     * @param color
     * @param alpha
     * @return
     */
    private static View createStatusBarView(Activity activity, @ColorInt int color, int alpha) {
        // 绘制一个和状态栏一样高的矩形
        View statusBarView = new View(activity);
        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity));
        statusBarView.setLayoutParams(params);
        statusBarView.setBackgroundColor(statusColorIntensity(color, alpha));
        statusBarView.setId(STATUS_VIEW_ID);
        return statusBarView;
    }

    /**
     * 设置状态栏占位
     *
     * @param activity
     */
    private static void setRootView(Activity activity) {
        ViewGroup parent = (ViewGroup) activity.findViewById(android.R.id.content);
        for (int i = 0, count = parent.getChildCount(); i < count; i++) {
            View childView = parent.getChildAt(0);
            if (childView instanceof ViewGroup) {
                childView.setFitsSystemWindows(true);
                ((ViewGroup) childView).setClipToPadding(true);
            }
        }
    }

    /**
     * 状态栏高度
     *
     * @param context
     * @return
     */
    private static int getStatusBarHeight(Context context) {
        int result = 0;
        int resId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            result = context.getResources().getDimensionPixelOffset(resId);
        }
        return result;
    }
}
