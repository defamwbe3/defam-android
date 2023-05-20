package Medium.DeFam.app.common.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import Medium.DeFam.app.common.CommonAppContext;
import Medium.DeFam.app.common.R;


/**
 * Created by zmh on 2017/8/3.
 */

public class ToastUtil extends Toast{
    private ToastUtil (Context context) {
        super(context);
    }
    private static ToastUtil toast;
    private static void cancelToast() {
        //为什么把这个if语句去掉，就会异常终止呢？
        //因为程序第一次进来时， toast时为空的，第二次及以后， 它才不为空，才能执行toast.cancel()。
        if (toast != null) {
            toast.cancel();
        }
    }
    public void cancel() {
        super.cancel();
    }
    public void show() {
        super.show();
    }
    public static void initToast(int... resources) {
        initToast(WordUtil.getString(resources));
    }
    public static void initToast( CharSequence text) {
        //第二次点击及以后，它会先取消上一次的Toast, 然后show本次的Toast。
        cancelToast();
        toast = new ToastUtil(CommonAppContext.sInstance);
        View mView = LayoutInflater.from(CommonAppContext.sInstance).inflate(R.layout.view_toast, null);
        TextView mText = mView.findViewById(R.id.toast_show);
        mText.setText(text);
        toast.setView(mView);
        toast.setGravity(Gravity.CENTER, 0, 70);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }


   /* private static Toast sToast;
    private static long sLastTime;
    private static String sLastString;

    static {
        sToast = makeToast();
    }

    private static Toast makeToast() {
        Toast toast = new Toast(CommonAppContext.sInstance);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        *//*View view = LayoutInflater.from(CommonAppContext.sInstance).inflate(R.layout.view_toast, null);
        toast.setView(view);*//*
        return toast;
    }


    public static void show(int... resources) {
        show(WordUtil.getString(resources));
    }

    public static void show(String s) {
        if (TextUtils.isEmpty(s)) {
            return;
        }
        long curTime = System.currentTimeMillis();
        if (curTime - sLastTime > 2000) {
            sLastTime = curTime;
            sLastString = s;
            sToast.setText(s);
            sToast.show();
        } else {
            if (!s.equals(sLastString)) {
                sLastTime = curTime;
                sLastString = s;
                sToast = makeToast();
                sToast.setText(s);
                sToast.show();
            }
        }

    }*/

}
