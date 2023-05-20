package Medium.DeFam.app.activity;


import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Medium.DeFam.app.R;
import Medium.DeFam.app.adapter.WelcomeGuideAdapter;
import Medium.DeFam.app.common.ActivityRouter;
import Medium.DeFam.app.common.base.BaseActivity;

public class Welcom extends BaseActivity {
    ViewPager vpGuide;
    Button btnStart;
    LinearLayout llIndicator;
    ImageView ivIndicator;
    RelativeLayout indicatorRelativeLayout;
    private List<ImageView> imgs;
    private int[] imgIds = new int[]{R.mipmap.splash1, R.mipmap.splash2,
            R.mipmap.splash3};
    private int pointDis; // 指示器的间距

    @Override
    protected boolean isNeedLogin() {
        return false;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_welcom;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState, @Nullable Bundle bundle) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        vpGuide = findViewById(R.id.vp_guide);
        btnStart = findViewById(R.id.btn_start);
        llIndicator = findViewById(R.id.ll_indicator);
        ivIndicator = findViewById(R.id.iv_indicator_selected);
        indicatorRelativeLayout = findViewById(R.id.indicatorRelativeLayout);
    }

    @Override
    protected void initData() {
        initMyData();
        vpGuide.setAdapter(new WelcomeGuideAdapter(imgs));

        // 计算两个圆点之间的距离
        // measure-->layout(确定位置)-->draw（onCreate方法执行结束后才会执行此流程）
        // 视图树：监听layout方法结束的事件，位置确定好之后再获取圆点的间距
        ivIndicator.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {

                    // layout方法执行后回调此方法
                    @Override
                    public void onGlobalLayout() {
                        pointDis = llIndicator.getChildAt(1).getLeft()
                                - llIndicator.getChildAt(0).getLeft();
                        // 移除，避免重复回调
                        ivIndicator.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }
                });

        vpGuide.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                if (position == imgs.size() - 1) {
                    indicatorRelativeLayout.setVisibility(View.GONE);
                    btnStart.setVisibility(View.VISIBLE);
                    /*AlphaAnimation anim = new AlphaAnimation(0, 1);
                    anim.setDuration(1500);
                    btnStart.setAnimation(anim);*/
                } else {
                    btnStart.setVisibility(View.GONE);
                    indicatorRelativeLayout.setVisibility(View.VISIBLE);
                }
            }

            // position：当前位置；positionOffset：偏移量百分比；positionOffsetPixels：偏移量像素
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
                int des = (int) (pointDis * positionOffset) + position
                        * pointDis;
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) ivIndicator
                        .getLayoutParams();
                params.leftMargin = des;
                ivIndicator.setLayoutParams(params);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        // 点击按钮，进入主Activity
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityRouter.toPoint(Welcom.this, ActivityRouter.Mall.MALL_MAIN);
                finish();
            }
        });

    }

    //初始化数据
    private void initMyData() {
        imgs = new ArrayList<>();
        for (int i = 0; i < imgIds.length; i++) {
            ImageView img = new ImageView(this);
            img.setImageResource(imgIds[i]);
            img.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imgs.add(img);

            // 初始化指示器
            ImageView indicator = new ImageView(this);
            indicator.setImageResource(R.mipmap.img75);
            // 设置左边距
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            if (i > 0) { // 从第二个开始
                params.leftMargin = 10;
            }
            llIndicator.addView(indicator, params);
        }
    }
}