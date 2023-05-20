package Medium.DeFam.app.common.utils;


import android.util.Log;

import Medium.DeFam.app.common.BuildConfig;


/**
 *
 * 日志封装
 */

public class LogUtils {
    static String className;//类名
    static String methodName;//方法名
    static int lineNumber;//行数

    /**
     * 判断是否可以调试
     * @return
     */
    public static boolean isDebuggable() {
        return BuildConfig.DEBUG;
    }

    private static String createLog(String log ) {
        StringBuffer buffer = new StringBuffer();
        //buffer.append("================");
        buffer.append(methodName);
        buffer.append("(").append(className).append(":").append(lineNumber).append("):");
        buffer.append(log);
        return buffer.toString();
    }

    /**
     * 获取文件名、方法名、所在行数
     * @param sElements
     */
    private static void getMethodNames(StackTraceElement[] sElements){
        className = sElements[1].getFileName();
        methodName = sElements[1].getMethodName();
        lineNumber = sElements[1].getLineNumber();
    }

    public static void e(String message){
        if (!isDebuggable())
            return;
        getMethodNames(new Throwable().getStackTrace());
        Log.e("mylog", createLog(message));
    }

    public static void i(String message){
        if (!isDebuggable())
            return;
        getMethodNames(new Throwable().getStackTrace());
        myi("mylog", createLog(message));
    }
    public static void myi(String tag, String msg) {  //信息太长,分段打印
        //因为String的length是字符数量不是字节数量所以为了防止中文字符过多，
        //  把4*1024的MAX字节打印长度改为2001字符数
        int max_str_length = 2001 - tag.length();
        //大于4000时
        while (msg.length() > max_str_length) {
            Log.i(tag, msg.substring(0, max_str_length));
            msg = msg.substring(max_str_length);
        }
        //剩余部分
        Log.i(tag, msg);
    }
    public static void d(String message){
        if (!isDebuggable())
            return;
        getMethodNames(new Throwable().getStackTrace());
        myi("mylog", createLog(message));
    }

    public static void v(String message){
        if (!isDebuggable())
            return;
        getMethodNames(new Throwable().getStackTrace());
        Log.v("mylog", createLog(message));
    }

    public static void w(String message){
        if (!isDebuggable())
            return;
        getMethodNames(new Throwable().getStackTrace());
        Log.w("mylog", createLog(message));
    }
}
