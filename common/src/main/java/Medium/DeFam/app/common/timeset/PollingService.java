package Medium.DeFam.app.common.timeset;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;


import org.greenrobot.eventbus.EventBus;

import java.util.Timer;
import java.util.TimerTask;

import Medium.DeFam.app.common.Constants;
import Medium.DeFam.app.common.bean.MessageEvent;


/**
 * 短信推送服务类，在后台长期运行，每个一段时间就向服务器发送一次请求
 *
 * @author zmh
 */
public class PollingService extends Service {
    /* private MyThread myThread;*/
    public static boolean flag = false;
    private static final int PERIOD = 60 * 1000;
    private static final int DELAY = 6000;
    private Timer mTimer;
    private TimerTask mTimerTask;

    @SuppressLint("WrongConstant")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        flags = START_STICKY;
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        flag = true;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        mTimer = new Timer();
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                //在此添加轮询 可见则发出请求
                EventBus.getDefault().post(new MessageEvent(Constants.POLLING));
            }
        };
        mTimer.schedule(mTimerTask, DELAY, PERIOD);
    }

    @Override
    public void onDestroy() {
        this.flag = false;
        //关闭定时任务
        if (mTimer != null) {
            mTimer.cancel();
        }
        Intent intent = new Intent("com.destroy");
        sendBroadcast(intent);
        super.onDestroy();
    }
}