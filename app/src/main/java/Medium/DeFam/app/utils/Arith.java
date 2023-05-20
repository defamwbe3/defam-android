package Medium.DeFam.app.utils;


import android.text.TextUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Arith {
    /**
     * 格式化
     */
    private static DecimalFormat dfs = null;
    public static DecimalFormat format(String pattern) {
        if (dfs == null) {
            dfs = new DecimalFormat();
        }
        dfs.setRoundingMode(RoundingMode.FLOOR);
        dfs.applyPattern(pattern);
        return dfs;
    }
    public static String formats(double format, int num) {
        String montynew = Double.valueOf(format).toString();
        return myformat(montynew, num);
    }

    public static String myformat(String format, int num) {
        if (TextUtils.isEmpty(format)) {
            return "0";
        }
        BigDecimal b = new BigDecimal(format);
        //保留5位并且属于四舍五入类型，当然这里的四舍五入没有任何意义，可以选择其他类型。
        return b.setScale(num, BigDecimal.ROUND_HALF_UP).toString();
        /*String[] str = format.split("\\.");
        if (str.length > 1 && str[1].length() > 0) {
            String ling = "";
            if (num > str[1].length()) {
                for (int i = 0, a = num - str[1].length(); i < a; i++) {
                    ling += "0";
                }
                num = str[1].length();
            }
            String xiaoshu = str[1].substring(0, num);
            if (xiaoshu.length() > 0 || ling.length() > 0) {
                return str[0] + "." + xiaoshu + ling;
            } else {
                return str[0];
            }

        }
        //1.特殊情况
        if (format.contains(".")) {
            if(num>0){
                String ling = "";
                for (int i = 0; i < num; i++) {
                    ling += "0";
                }
                return format  + ling;
            }
        }
        //整数情况补齐小数点
        if(num>0){
            String ling = "";
            for (int i = 0; i < num; i++) {
                ling += "0";
            }
            return format + "." + ling;
        }
        return format;*/
    }


    /**
     * 提供精确加法计算的add方法
     *
     * @param value1 被加数
     * @param value2 加数
     * @return 两个参数的和
     */
    public static double add(double value1, double value2) {
        BigDecimal b1 = new BigDecimal(Double.valueOf(value1));
        BigDecimal b2 = new BigDecimal(Double.valueOf(value2));
        return b1.add(b2).doubleValue();
    }

    public static double add(String value1, String value2) {
        if (TextUtils.isEmpty(value1)) {
            value1 = "0";
        }
        if (TextUtils.isEmpty(value2)) {
            return Double.parseDouble(value1);
        }
        BigDecimal b1 = new BigDecimal(value1);
        BigDecimal b2 = new BigDecimal(value2);
        return b1.add(b2).doubleValue();
    }

    public static String addtostring(String value1, String value2) {
        if (TextUtils.isEmpty(value1)) {
            value1 = "0";
        }
        if (TextUtils.isEmpty(value2)) {
            return value1;
        }
        BigDecimal b1 = new BigDecimal(value1);
        BigDecimal b2 = new BigDecimal(value2);
        return b1.add(b2).toPlainString();
    }

    /**
     * 提供精确减法运算的sub方法
     *
     * @param value1 被减数
     * @param value2 减数
     * @return 两个参数的差
     */
    public static double sub(double value1, double value2) {
        BigDecimal b1 = new BigDecimal(Double.valueOf(value1));
        BigDecimal b2 = new BigDecimal(Double.valueOf(value2));
        return b1.subtract(b2).doubleValue();
    }

    public static double sub(String value1, String value2) {
        if (TextUtils.isEmpty(value1)) {
            return 0;
        }
        if (TextUtils.isEmpty(value2)) {
            return Double.parseDouble(value1);
        }
        BigDecimal b1 = new BigDecimal(value1);
        BigDecimal b2 = new BigDecimal(value2);
        return b1.subtract(b2).doubleValue();
    }

    public static String subtostring(String value1, String value2) {
        if (TextUtils.isEmpty(value1)) {
            return "0";
        }
        if (TextUtils.isEmpty(value2)) {
            return value1;
        }
        BigDecimal b1 = new BigDecimal(value1);
        BigDecimal b2 = new BigDecimal(value2);
        return b1.subtract(b2).toPlainString();
    }

    /**
     * 提供精确乘法运算的mul方法
     *
     * @param value1 被乘数
     * @param value2 乘数
     * @return 两个参数的积
     */
    public static double mul(double value1, double value2) {
        BigDecimal b1 = new BigDecimal(Double.valueOf(value1));
        BigDecimal b2 = new BigDecimal(Double.valueOf(value2));
        return b1.multiply(b2).doubleValue();
    }

    public static String mul(String value1, String value2) {
        if (TextUtils.isEmpty(value1) || TextUtils.isEmpty(value2)) {
            return "0";
        }
        BigDecimal b1 = new BigDecimal(value1);
        BigDecimal b2 = new BigDecimal(value2);
        return b1.multiply(b2).toPlainString();
    }

    public static double mul1(String value1, String value2) {
        if (TextUtils.isEmpty(value1) || TextUtils.isEmpty(value2)) {
            return 0;
        }
        BigDecimal b1 = new BigDecimal(value1);
        BigDecimal b2 = new BigDecimal(value2);
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 提供精确的除法运算方法div
     *
     * @param value1 被除数
     * @param value2 除数
     * @param scale  精确范围
     * @return 两个参数的商
     */
    public static double div(double value1, double value2, int scale) {
        if (value2 == 0) {
            return 0;
        }
        //如果精确范围小于0，抛出异常信息
        BigDecimal b1 = new BigDecimal(Double.toString(value1));
        BigDecimal b2 = new BigDecimal(Double.toString(value2));
        //默认保留两位会有错误，这里设置保留小数点后4位
        return b1.divide(b2, 10, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static double div(String value1, String value2, int scale) {
        if (TextUtils.isEmpty(value1)) {
            return 0;
        }
        if (TextUtils.isEmpty(value2) || Double.parseDouble(value2) == 0) {
            return 0;
        }
        BigDecimal b1 = new BigDecimal(value1);
        BigDecimal b2 = new BigDecimal(value2);
        //默认保留两位会有错误，这里设置保留小数点后4位
        return b1.divide(b2, 10, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static String divtostring(String value1, String value2, int scale) {
        if (TextUtils.isEmpty(value1)) {
            return "0";
        }
        if (TextUtils.isEmpty(value2) || Double.parseDouble(value2) == 0) {
            return "0";
        }
        BigDecimal b1 = new BigDecimal(value1);
        BigDecimal b2 = new BigDecimal(value2);
        //默认保留两位会有错误，这里设置保留小数点后4位
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).toPlainString();
    }

    /**
     * 提供精确的小数位四舍五入处理。
     *
     * @param v     需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static String round(double v, int scale) {
        if (scale < 0) {
            return String.valueOf(v);
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return String.valueOf(b.divide(one, scale, BigDecimal.ROUND_HALF_UP));
    }

    /**
     * 提供精确的小数位四舍五入处理。
     *
     * @param v     需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static String round(String v, int scale) {
        if (scale < 0) {
            return v;
        }
        BigDecimal b = new BigDecimal(v);
        BigDecimal one = new BigDecimal("1");

        b.divide(one, scale, BigDecimal.ROUND_HALF_UP);

        return null;
    }
}

