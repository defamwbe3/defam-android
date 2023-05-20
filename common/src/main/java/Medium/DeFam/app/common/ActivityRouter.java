package Medium.DeFam.app.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

/**
 * 自定义路由
 */
public class ActivityRouter {

    //商城
    public interface Mall {
        public static final String MALL_MAIN = "Medium.DeFam.app.activity.MainActivity";
        //public static final String MALL_MAIN = "com.longlai.newmall.longlai.MainLongLai";
        public static final String MALL_LOGIN = "Medium.DeFam.app.activity.Login";
    }

    /**
     * 此处可判断去哪个模块
     *
     * @param from
     */
    public static void toPoint(Context from, String router) {
        Intent intent = ActivityRouter.getIntent(from, router);
        from.startActivity(intent);
    }


    public static void startActivity(Context from, Intent intent) {
        from.startActivity(intent);
    }

    public static Intent getIntent(Context from, String activityName) {
        try {
            Class clazz = Class.forName(activityName);
            Intent intent = new Intent(from, clazz);
            return intent;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void startActivity(Context from, String activityName) {
        try {
            Class clazz = Class.forName(activityName);
            Intent intent = new Intent(from, clazz);
            from.startActivity(intent);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void startActivityForResult(Activity from, String activityName, int requestCode) {
        try {
            Class clazz = Class.forName(activityName);
            Intent intent = new Intent(from, clazz);
            from.startActivityForResult(intent, requestCode);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


}
