package Medium.DeFam.app.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;

import java.util.ArrayList;

import Medium.DeFam.app.R;
import Medium.DeFam.app.adapter.ViewPagerAdapter;
import Medium.DeFam.app.common.base.BaseActivity;
import Medium.DeFam.app.fragment.HomeItemFragment;
import Medium.DeFam.app.fragment.HomeItemKolFragment;
import Medium.DeFam.app.fragment.HuoDongShouCangFragment;
import Medium.DeFam.app.fragment.JiangLiFragment;
import Medium.DeFam.app.fragment.JiangLiSuiPianFragment;
import Medium.DeFam.app.view.ScaleTransitionPagerTitleView;
import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.autosize.utils.AutoSizeUtils;


public class JiangLi extends BaseActivity {
    @BindView(R.id.indicator)
    MagicIndicator mIndicator;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    private ArrayList<Fragment> fragmentList = new ArrayList<>();
    private String[] toptitle = {"我的积分", "我的碎片"};

    @Override
    protected boolean isNeedLogin() {
        return false;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_jiangli;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState, @Nullable Bundle bundle) {
        CommonNavigator commonNavigatorTop = new CommonNavigator(JiangLi.this);
        commonNavigatorTop.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return toptitle.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ScaleTransitionPagerTitleView colorTransitionPagerTitleView = new ScaleTransitionPagerTitleView(context);
                colorTransitionPagerTitleView.setNormalColor(getResources().getColor(R.color.color_373748));
                colorTransitionPagerTitleView.setSelectedColor(getResources().getColor(R.color.color_1B1BDD));
                colorTransitionPagerTitleView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 17);
                //colorTransitionPagerTitleView.getPaint().setFakeBoldText(true);
                colorTransitionPagerTitleView.setPadding(AutoSizeUtils.dp2px(JiangLi.this, 20), 0, AutoSizeUtils.dp2px(JiangLi.this, 20), 0);
                colorTransitionPagerTitleView.setText(toptitle[index]);
                colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (viewPager != null) {
                            viewPager.setCurrentItem(index);
                        }
                    }
                });
                return colorTransitionPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setLineHeight(UIUtil.dip2px(context, 3));
                indicator.setLineWidth(UIUtil.dip2px(context, 12));
                indicator.setRoundRadius(UIUtil.dip2px(context, 2));
                indicator.setStartInterpolator(new AccelerateInterpolator());
                indicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
                indicator.setColors(getResources().getColor(R.color.color_1B1BDD));

                return indicator;
            }
        });
        mIndicator.setNavigator(commonNavigatorTop);
    }

    @Override
    protected void initData() {
        fragmentList.add(JiangLiFragment.newInstance(0));
        fragmentList.add(JiangLiSuiPianFragment.newInstance(0));
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragmentList);
        //给ViewPager设置适配器
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setOffscreenPageLimit(fragmentList.size());
        ViewPagerHelper.bind(mIndicator, viewPager);
        viewPager.setCurrentItem(0);
    }


}
