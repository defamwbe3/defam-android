package Medium.DeFam.app.common.base;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import Medium.DeFam.app.common.utils.KeyboardUtils;


/**
 * Created by apple on 2018/3/30.
 */

public class BaseDialogFragment extends DialogFragment {


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            onCurrentAttach(context);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            onCurrentAttach(activity);
        }
    }

    /**
     * 重写此方法替代onAttach()高低版本不兼容
     *
     * @param mContext
     */

    protected void onCurrentAttach(Context mContext) {

    }

    /**
     * 判断弹窗是否显示
     *
     * @return
     */
    public boolean isShowing() {
        return getDialog() != null && getDialog().isShowing();
    }

    /**
     * 显示DialogFragment(注此方法会衍生出状态值问题(本人在正常使用时并未出现过))
     *
     * @param manager
     * @param tag
     * @param isResume 在Fragment中使用可直接传入isResumed()
     *                 在FragmentActivity中使用可自定义全局变量 boolean isResumed 在onResume()和onPause()中分别传人判断为true和false
     */
    public void show(FragmentManager manager, String tag, boolean isResume) {

        if (!isShowing()) {
            if (isResume) {
                //正常显示
                if (!isAdded() && manager.findFragmentByTag(tag) == null) {
                    try {
                        show(manager, tag);
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    }
                } else {
                    FragmentTransaction ft = manager.beginTransaction();
                    ft.show(this);
                    ft.commitAllowingStateLoss();
                }
            } else {
                //注 此方法会衍生出一些状态问题,慎用（在原代码中 需要设置  mDismissed = false 和 mShownByMe = true 并未在此引用到,如果需要用到相关判断值,此方法不可用）
                FragmentTransaction ft = manager.beginTransaction();
                if (!isAdded()) {
                    ft.add(this, tag);
                } else {
                    ft.show(this);
                }
                ft.commitAllowingStateLoss();
            }
        }
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        try {
            //在每个add事务前增加一个remove事务，防止连续的add
            manager.beginTransaction().remove(this).commitAllowingStateLoss();
            super.show(manager, tag);
        } catch (Exception e) {
            //同一实例使用不同的tag会异常,这里捕获一下
            e.printStackTrace();
        }
    }

    /**
     * 关闭DialogFragment
     *
     * @param isResume 在Fragment中使用可直接传入isResumed()
     *                 在FragmentActivity中使用可自定义全局变量 boolean isResumed 在onResume()和onPause()中分别传人判断为true和false
     */
    public void dismiss(boolean isResume) {
        if (isResume) {
            dismiss();
        } else {
            dismissAllowingStateLoss();
        }
    }



    /**
     * 隐藏键盘
     */
    protected void hideInput() {
        if (getActivity() == null) {
            return;
        }

        KeyboardUtils.hideSoftInput(getActivity());

    }

}
