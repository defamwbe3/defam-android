package Medium.DeFam.app.common.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AllUtils {
    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public static void backgroundAlpha(float bgAlpha, Window window) {
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = bgAlpha;
        if (bgAlpha == 1) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//不移除该Flag的话,在有视频的页面上的视频会出现黑屏的bug
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//此行代码主要是解决在华为手机上半透明效果无效的bug
        }
        window.setAttributes(lp);

    }

    public static final int MSEC = 1;
    public static final int SEC = 1000;
    public static final int MIN = 60000;
    public static final int HOUR = 3600000;
    public static final int DAY = 86400000;
    public static String settimeNYRSFNM(String time) {
        try {
            if (TextUtils.isEmpty(time)) {
                return "暂无";
            }
            SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            int i = Integer.parseInt(time);
            String times = sdr.format(new Date(i * 1000L));
            return times;
        } catch (Exception e) {
            return "暂无";
        }

    }
    /**
     * 返回文字描述的日期
     *
     * @param datestr
     * @return
     */
    public static String getTimeFormatText(final String datestr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date date = new Date();
        try {
            date = sdf.parse(datestr);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return sdf.format(date);
        /*long millis = date.getTime();
        long now = System.currentTimeMillis();
        long span = now - millis;
        if (span < 0)
            // U can read http://www.apihome.cn/api/java/Formatter.html to understand it.
            return String.format("%tc", millis);
        if (span < 1000) {
            return "刚刚";
        } else if (span < MIN) {
            return String.format(Locale.getDefault(), "%d秒前", span / SEC);
        } else if (span < HOUR) {
            return String.format(Locale.getDefault(), "%d分钟前", span / MIN);
        }
        // 获取当天 00:00
        long wee = getWeeOfToday();
        if (millis >= wee) {
            return String.format("今天%tR", millis);
        } else if (millis >= wee - DAY) {
            return String.format("昨天%tR", millis);
        } else {
            return String.format("%tF", millis);
        }*/
    }

    private static long getWeeOfToday() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
        //return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, context.getResources().getDisplayMetrics());
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static String getTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");// HH:mm:ss
//获取当前时间
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

    //判断手机号码的正则表达式
    private static final String MOBILE_NUM_REGEX = "^((13[0-9])|(14[0-9])|(15[^4,\\D])|(18[0-9])|(17[0-9])|(16[0-9])|(19[0-9]))\\d{8}$";

    /**
     * 验证一个号码是不是手机号，以前这个地方是用正则判断的，现在改由服务端验证
     *
     * @param mobileNumber
     */
    public static boolean validateMobileNumber(String mobileNumber) {
        Pattern p = Pattern.compile(MOBILE_NUM_REGEX);
        Matcher m = p.matcher(mobileNumber);
        return m.matches();
    }

    /*
    设置手机号中间四位不可见
     */
    public static String hidePhone(String phone) {
        if (!TextUtils.isEmpty(phone) && phone.length() == 11) {
            phone = phone.substring(0, 3) + "****" + phone.substring(7, 11);
        }
        return phone;

    }

}
