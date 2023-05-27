package Medium.DeFam.app.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;

import Medium.DeFam.app.R;
import Medium.DeFam.app.activity.FaBu;
import Medium.DeFam.app.activity.Search;
import Medium.DeFam.app.common.base.BaseFragment;
import Medium.DeFam.app.common.them.Eyes;
import Medium.DeFam.app.view.ScaleTransitionPagerTitleView;
import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.autosize.utils.AutoSizeUtils;


public class ZiXunFragment extends BaseFragment {
    public static ZiXunFragment newInstance(int type) {
        ZiXunFragment fragment = new ZiXunFragment();
        Bundle args = new Bundle();
        args.putInt("type", type);
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.indicator_top)
    MagicIndicator mIndicatorTop;
    private FragmentManager fManager;
//    private String[] toptitle = {"文章", "快讯", "KOL言论"};
    private String[] toptitle = {"快讯", "KOL"};
    private ZiXunWenZhangFragment ziXunWenZhangFragment;
    private ZiXunKuaiXunFragment ziXunKuaiXunFragment;
    private ZiXunKOLFragment ziXunKOLFragment;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_zixun;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        view.findViewById(R.id.topview).setPadding(0, Eyes.getStatusBarHeight(getActivity()), 0, 0);

        CommonNavigator commonNavigatorTop = new CommonNavigator(getActivity());
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
                colorTransitionPagerTitleView.setPadding(AutoSizeUtils.dp2px(getActivity(), 14), 0, AutoSizeUtils.dp2px(getActivity(), 14), 0);
                colorTransitionPagerTitleView.setText(toptitle[index]);
                colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setChioceItem(index);
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
        mIndicatorTop.setNavigator(commonNavigatorTop);


    }

    @Override
    public void initData() {
        super.initData();
        fManager = getChildFragmentManager();
        setChioceItem(0);
    }
    public void setChioceItem(int index) {
        FragmentTransaction transaction = fManager.beginTransaction();
        mIndicatorTop.onPageSelected(index);
        mIndicatorTop.onPageScrolled(index, 0.0F, 0);
        hideFragments(transaction);
        switch (index) {
            /*case 0:
                if (ziXunWenZhangFragment == null) {
                    ziXunWenZhangFragment = new ZiXunWenZhangFragment();
                    transaction.add(R.id.content, ziXunWenZhangFragment);

                } else {
                    transaction.show(ziXunWenZhangFragment);
                }
                break;
            case 1:
                if (ziXunKuaiXunFragment == null) {
                    ziXunKuaiXunFragment = new ZiXunKuaiXunFragment();
                    transaction.add(R.id.content, ziXunKuaiXunFragment);

                } else {
                    transaction.show(ziXunKuaiXunFragment);
                }
                break;
            case 2:
                if (ziXunKOLFragment == null) {
                    ziXunKOLFragment = new ZiXunKOLFragment();
                    transaction.add(R.id.content, ziXunKOLFragment);

                } else {
                    transaction.show(ziXunKOLFragment);
                }
                break;*/
            case 0:
                if (ziXunKuaiXunFragment == null) {
                    ziXunKuaiXunFragment = new ZiXunKuaiXunFragment();
                    transaction.add(R.id.content, ziXunKuaiXunFragment);

                } else {
                    transaction.show(ziXunKuaiXunFragment);
                }
                break;
            case 1:
                if (ziXunKOLFragment == null) {
                    ziXunKOLFragment = new ZiXunKOLFragment();
                    transaction.add(R.id.content, ziXunKOLFragment);

                } else {
                    transaction.show(ziXunKOLFragment);
                }
                break;
        }
        transaction.commit();
    }


    private void hideFragments(FragmentTransaction transaction) {
        if (ziXunWenZhangFragment != null) {
            transaction.hide(ziXunWenZhangFragment);
        }
        if (ziXunKuaiXunFragment != null) {
            transaction.hide(ziXunKuaiXunFragment);
        }
        if (ziXunKOLFragment != null) {
            transaction.hide(ziXunKOLFragment);
        }
    }

    @OnClick({R.id.sousuo, R.id.img})
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.sousuo) {
            Intent intent = new Intent(getActivity(), Search.class);
            startActivity(intent);
        }else if (id == R.id.img) {
            Intent intent = new Intent(getActivity(), FaBu.class);
            startActivity(intent);
        }
    }
}
