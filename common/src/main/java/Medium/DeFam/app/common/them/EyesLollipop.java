package Medium.DeFam.app.common.them;


import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.ColorInt;


@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class EyesLollipop {
    private static int HighestSDK = Build.VERSION_CODES.LOLLIPOP;
    /**
     * 设置状态栏颜色以及明暗度
     *
     * @param activity
     * @param color
     * @param statusBarAlpha
     */
    public static void setStatusColor(Activity activity, @ColorInt int color, int statusBarAlpha) {
        if (Build.VERSION.SDK_INT >= HighestSDK) {
            //原生设置状态栏
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            activity.getWindow().setStatusBarColor(statusColorIntensity(color, statusBarAlpha));
        }
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
        if (Build.VERSION.SDK_INT >= HighestSDK) {
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            activity.getWindow().setStatusBarColor(Color.argb(alpha, r, g, b));
        }
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



}
