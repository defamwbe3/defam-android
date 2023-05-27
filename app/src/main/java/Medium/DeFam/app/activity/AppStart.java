package Medium.DeFam.app.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import androidx.annotation.Nullable;


import com.umeng.commonsdk.UMConfigure;

import Medium.DeFam.app.R;
import Medium.DeFam.app.common.Constants;
import Medium.DeFam.app.common.utils.SpUtil;
import Medium.DeFam.app.dialog.QuanXianAlertDialog;
import Medium.DeFam.app.dialog.QuanXianNoAlertDialog;
import Medium.DeFam.app.view.CustomView;

import Medium.DeFam.app.common.ActivityRouter;
import Medium.DeFam.app.common.base.BaseActivity;

import butterknife.BindView;

/**
 * 技术支持https://blog.csdn.net/qq_38629981/article/details/89456406
 */
public class AppStart extends BaseActivity {
    @BindView(R.id.img)
    ImageView oneimg;

    @Override
    protected boolean isNeedLogin() {
        return false;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_oneactivity;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState, @Nullable Bundle bundle) {

    }

    @Override
    protected void initData() {
        AlphaAnimation aa = new AlphaAnimation(0.5f, 1.0f);
        aa.setDuration(2000);
        oneimg.startAnimation(aa);
        aa.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationEnd(Animation arg0) {
                setQuanXian();//可设置执行登陆
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationStart(Animation animation) {
            }
        });

    }

    private void setQuanXian() {
        if (SpUtil.getInstance().getBooleanValue(SpUtil.SETQUANXIAN)) {
            QuanXianAlertDialog dialogs = new QuanXianAlertDialog(AppStart.this);
            dialogs.setItemListener(new QuanXianAlertDialog.OnNoticeListener() {
                @Override
                public void setNoticeListener(int id, int position, String data) {
                    if (0 == id) {
                        dialogs.dismiss();
                        QuanXianNoAlertDialog dialogsno = new QuanXianNoAlertDialog(AppStart.this);
                        dialogsno.setItemListener(new QuanXianNoAlertDialog.OnNoticeListener() {
                            @Override
                            public void setNoticeListener(int ids, int positions, String datas) {
                                if (0 == ids) {
                                    finish();
                                } else {
                                    dialogsno.dismiss();
                                    dialogs.show();
                                }
                            }
                        });
                        dialogsno.show();
                    } else {
                        SpUtil.getInstance().setBooleanValue(SpUtil.SETQUANXIAN, false);
                        redirectTo();
                    }

                }
            });
            dialogs.show();
        } else {
            redirectTo();
        }
    }

    /**
     * 跳转到...
     */
    private void redirectTo() {
        UMConfigure.init(getApplicationContext(), Constants.UMENG_APPKEY
                ,"common",UMConfigure.DEVICE_TYPE_PHONE,"");
        if (SpUtil.getInstance().getBooleanValue(SpUtil.IS_ONE)) {
            Intent intent = new Intent(this, Welcom.class);
            startActivity(intent);
        } else {
            ActivityRouter.toPoint(AppStart.this, ActivityRouter.Mall.MALL_MAIN);
        }
        SpUtil.getInstance().setBooleanValue(SpUtil.IS_ONE, false);
        finish();
    }
}
