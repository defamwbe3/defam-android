package Medium.DeFam.app.common.timeset;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;

public class PollingUtils {


    //开启轮询服务
    public static void startPollingService(Context context) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(context, PollingService.class);
                context.startService(intent);
            }
        }, 2000);

    }

    //停止轮询服务
    public static void stopPollingService(Context context) {
        //停止Service
        Intent intentFour = new Intent(context, PollingService.class);
        context.stopService(intentFour);
    }
}
