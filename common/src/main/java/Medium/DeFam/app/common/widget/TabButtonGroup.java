package Medium.DeFam.app.common.widget;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;


import java.util.ArrayList;
import java.util.List;

import Medium.DeFam.app.common.ActivityRouter;
import Medium.DeFam.app.common.utils.UserUtil;

/**
 * Created by zmh on 2018/9/22.
 */

public class TabButtonGroup extends LinearLayout implements View.OnClickListener {
    private Context mcontext;
    private List<TabButton> mList;
    private ViewPager mViewPager;
    private int mCurPosition;
    private ValueAnimator mAnimator;
    private View mAnimView;
    private Runnable mRunnable;

    public TabButtonGroup(Context context) {
        this(context, null);
    }

    public TabButtonGroup(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabButtonGroup(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mcontext = context;
        mList = new ArrayList<>();
        mCurPosition = 0;
        mAnimator = ValueAnimator.ofFloat(0.9f, 1.3f, 1f);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float v = (float) animation.getAnimatedValue();
                if (mAnimView != null) {
                    mAnimView.setScaleX(v);
                    mAnimView.setScaleY(v);
                }
            }
        });
        mAnimator.setDuration(300);
        mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mAnimView = null;
            }
        });
        mRunnable = new Runnable() {
            @Override
            public void run() {
                if (mViewPager != null) {
                    mViewPager.setCurrentItem(mCurPosition, false);
                }
            }
        };
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        for (int i = 0, count = getChildCount(); i < count; i++) {
            View v = getChildAt(i);
            v.setTag(i);
            v.setOnClickListener(this);
            mList.add((TabButton) v);
        }
    }

    @Override
    public void onClick(View v) {
        Object tag = v.getTag();
        if (tag != null) {
            int position = (int) tag;
            if (position == mCurPosition) {
                return;
            }
            /*if (4 == position || 3 == position || 2 == position || 1 == position) {//未登录状态
                if (null == UserUtil.getUserBean() || TextUtils.isEmpty(UserUtil.getUserBean().getToken())) {
                    ActivityRouter.startActivity(mcontext, ActivityRouter.Mall.MALL_LOGIN);
                    return;
                }
            }*/
            if (4 == position) {//未登录状态
                if (null == UserUtil.getUserBean() || TextUtils.isEmpty(UserUtil.getUserBean().getToken())) {
                    ActivityRouter.startActivity(mcontext, ActivityRouter.Mall.MALL_LOGIN);
                    return;
                }
            }
            mList.get(mCurPosition).setChecked(false);
            TabButton tbn = mList.get(position);
            tbn.setChecked(true);
            mCurPosition = position;
            mAnimView = tbn;
            mAnimator.start();
            postDelayed(mRunnable, 150);
        }
    }

    public void setCurrentItem(int position) {
        if (position == mCurPosition) {
            return;
        }
        mList.get(mCurPosition).setChecked(false);
        TabButton tbn = mList.get(position);
        tbn.setChecked(true);
        mCurPosition = position;
        mAnimView = tbn;
        mAnimator.start();
        postDelayed(mRunnable, 150);

    }

    public void setNum(int index, String num) {
        TabButton tbn = mList.get(index);
        tbn.setNum(num);
    }

    public void setViewPager(ViewPager viewPager) {
        mViewPager = viewPager;
    }

    public void cancelAnim() {
        if (mAnimator != null) {
            mAnimator.cancel();
        }
    }
}

