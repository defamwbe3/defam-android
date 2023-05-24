package Medium.DeFam.app.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Medium.DeFam.app.R;
import Medium.DeFam.app.activity.BaoZang;
import Medium.DeFam.app.activity.FaBu;
import Medium.DeFam.app.activity.GongGao;
import Medium.DeFam.app.activity.JiFenShop;
import Medium.DeFam.app.activity.Search;
import Medium.DeFam.app.adapter.ImageAdapter;
import Medium.DeFam.app.adapter.ViewPagerAdapter;
import Medium.DeFam.app.bean.BannerBean;
import Medium.DeFam.app.bean.GongGaoBean;
import Medium.DeFam.app.bean.InfoBean;
import Medium.DeFam.app.common.ActivityRouter;
import Medium.DeFam.app.common.Constants;
import Medium.DeFam.app.common.base.BaseFragment;
import Medium.DeFam.app.common.bean.MessageEvent;
import Medium.DeFam.app.common.http.HttpClient;
import Medium.DeFam.app.common.http.JsonBean;
import Medium.DeFam.app.common.http.TradeHttpCallback;
import Medium.DeFam.app.common.them.Eyes;
import Medium.DeFam.app.utils.HttpUtil;
import Medium.DeFam.app.view.ScaleTransitionPagerTitleView;
import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.autosize.utils.AutoSizeUtils;

public class HomeFragment extends BaseFragment {
    public static HomeFragment newInstance(int type) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putInt("type", type);
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.indicator)
    MagicIndicator mIndicator;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.marquee_view)
    ViewFlipper marqueeView;
    @BindView(R.id.integral)
    TextView integral;
    @BindView(R.id.suipian_total)
    TextView suipian_total;

    ViewPagerAdapter viewPagerAdapter;
    CommonNavigatorAdapter commonNavigatorAdapter;
    List<String> titles = new ArrayList<>();
    private ArrayList<Fragment> fragmentList = new ArrayList<>();
    private int oldIndex = 0;

    @Override
    protected boolean useEventBus() {
        return true;
    }

    @Override
    protected void onEventComing(MessageEvent event) {
        super.onEventComing(event);
        if (event.getCode() == Constants.INFO) {
            integral.setText(((InfoBean) event.getData()).getIntegral());
            suipian_total.setText(((InfoBean) event.getData()).getSuipian_total());
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        view.findViewById(R.id.topview).setPadding(0, Eyes.getStatusBarHeight(getActivity()), 0, 0);

        CommonNavigator commonNavigator = new CommonNavigator(getActivity());
        commonNavigatorAdapter = new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return titles == null ? 0 : titles.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ScaleTransitionPagerTitleView colorTransitionPagerTitleView = new ScaleTransitionPagerTitleView(context);
                colorTransitionPagerTitleView.setNormalColor(getResources().getColor(R.color.color_666666));
                colorTransitionPagerTitleView.setSelectedColor(getResources().getColor(R.color.color_1B1BDD));
                colorTransitionPagerTitleView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
                //colorTransitionPagerTitleView.getPaint().setFakeBoldText(true);
                colorTransitionPagerTitleView.setPadding(AutoSizeUtils.dp2px(getActivity(), 14), 0, AutoSizeUtils.dp2px(getActivity(), 14), 0);
                colorTransitionPagerTitleView.setText(titles.get(index));
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
                indicator.setLineHeight(UIUtil.dip2px(context, 2.5));
                indicator.setLineWidth(UIUtil.dip2px(context, 24));
                indicator.setRoundRadius(UIUtil.dip2px(context, 2));
                indicator.setStartInterpolator(new AccelerateInterpolator());
                indicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
                indicator.setColors(getResources().getColor(R.color.color_1B1BDD));

                return indicator;
            }
        };
        commonNavigator.setAdapter(commonNavigatorAdapter);
        mIndicator.setNavigator(commonNavigator);

    }

    @Override
    public void initData() {
        super.initData();
        useBanner();
//        getGongGao();

        viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager(), fragmentList);
        //给ViewPager设置适配器
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(new MyOnPageChangeListener());//页面变化时的监听器
        ViewPagerHelper.bind(mIndicator, viewPager);


        titles.add("热门资讯");
        fragmentList.add(HomeItemFragment.newInstance("", true));
//        titles.add("热门活动");
//        fragmentList.add(HomeItemHuoDongFragment.newInstance("hot"));
        /*titles.add("热门KOL");
        fragmentList.add(HomeItemKolFragment.newInstance(0));*/
//        titles.add("热门文章");
//        fragmentList.add(WenZhangFragment.newInstance(0));
        viewPagerAdapter.notifyDataSetChanged();
        commonNavigatorAdapter.notifyDataSetChanged();
        viewPager.setOffscreenPageLimit(fragmentList.size());
        viewPager.setCurrentItem(0);

    }

    private void useBanner() {
        Map<String, String> map = new HashMap<>();
        map.put("code", "index");
        HttpClient.getInstance().gets(HttpUtil.BANNER, map, new TradeHttpCallback<JsonBean<BannerBean>>() {
            @Override
            public void onSuccess(JsonBean<BannerBean> data) {
                banner.setAdapter(new ImageAdapter(getActivity(), data.getData().getData()))
//                        .setBannerGalleryMZ(20)
//                        .addPageTransformer(new ZoomOutPageTransformer())
                        .addBannerLifecycleObserver(getActivity())//添加生命周期观察者
                        .setIndicator(new CircleIndicator(getActivity()));
            }
        });
    }

    private void getGongGao() {
        Map<String, String> map = new HashMap<>();
        map.put("is_page", "0");
        map.put("page", "1");
        map.put("limit", "10");
        HttpClient.getInstance().gets(HttpUtil.NOTICE, map, new TradeHttpCallback<JsonBean<List<GongGaoBean>>>() {
            @Override
            public void onSuccess(JsonBean<List<GongGaoBean>> data) {
                if (null != data) {
                    setMarqueeView(data.getData());
                }
            }
        });
    }

    private void setMarqueeView(List<GongGaoBean> mNotifyList) {
        marqueeView.removeAllViews();
        for (int i = 0; i < mNotifyList.size(); i++) {
            View view = View.inflate(getActivity(), R.layout.item_notify, null);
            TextView notify = (TextView) view.findViewById(R.id.notify);
            notify.setText(mNotifyList.get(i).getTitle());
            marqueeView.addView(view);
        }
        if (mNotifyList.size() > 1) {
            marqueeView.setAutoStart(true);
            marqueeView.startFlipping();
        } else {
            marqueeView.setAutoStart(false);
            marqueeView.stopFlipping();
        }
    }

    private class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageSelected(int position) {
            if ((1 == position || 2 == position) && !isLogined()) {
                ActivityRouter.toPoint(getActivity(), ActivityRouter.Mall.MALL_LOGIN);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        viewPager.setCurrentItem(oldIndex);
                    }
                }, 200);  //延迟
                return;
            }
            oldIndex = position;
            if (fragmentList.get(position) instanceof HomeItemHuoDongFragment) {
                HomeItemHuoDongFragment fragment = (HomeItemHuoDongFragment) fragmentList.get(position);//懒加载
                if (null != fragment) {
                    fragment.loadData();
                }
            } else if (fragmentList.get(position) instanceof WenZhangFragment) {
                WenZhangFragment fragment = (WenZhangFragment) fragmentList.get(position);//懒加载
                if (null != fragment) {
                    fragment.loadData();
                }
            }

        }
    }

    @OnClick({R.id.sousuo, R.id.gonggao, R.id.integral, R.id.suipian_total, R.id.img})
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.sousuo) {
            Intent intent = new Intent(getActivity(), Search.class);
            startActivity(intent);
        } else if (id == R.id.gonggao) {
            Intent intent = new Intent(getActivity(), GongGao.class);
            startActivity(intent);
        } else if (id == R.id.integral) {
            Intent intent = new Intent(getActivity(), JiFenShop.class);
            startActivity(intent);
        } else if (id == R.id.suipian_total) {
            Intent intent = new Intent(getActivity(), BaoZang.class);
            startActivity(intent);
        } else if (id == R.id.img) {
            Intent intent = new Intent(getActivity(), FaBu.class);
            startActivity(intent);
        }
    }
}
