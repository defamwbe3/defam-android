package Medium.DeFam.app.common.timeset;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * 创建者Z by on 2021/3/13.
 */
public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("com.destroy")) {
            //TODO
            //在这里写重新启动service的相关操作
            PollingUtils.startPollingService(context);

        }
        /*if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            System.out.println("手机开机了....");
            startUploadService(context);
        }
        if (Intent.ACTION_USER_PRESENT.equals(intent.getAction())) {
            startUploadService(context);
        }*/
    }

}
