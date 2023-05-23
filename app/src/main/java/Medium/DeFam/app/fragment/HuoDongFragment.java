package Medium.DeFam.app.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.transformer.ZoomOutPageTransformer;

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
import Medium.DeFam.app.activity.GuanDianPut;
import Medium.DeFam.app.activity.JiFenJiLuDetail;
import Medium.DeFam.app.activity.Login;
import Medium.DeFam.app.activity.RenZheng;
import Medium.DeFam.app.activity.Web;
import Medium.DeFam.app.adapter.ImageAdapter;
import Medium.DeFam.app.adapter.ViewPagerAdapter;
import Medium.DeFam.app.bean.BannerBean;
import Medium.DeFam.app.bean.InfoBean;
import Medium.DeFam.app.bean.JiFenOkBean;
import Medium.DeFam.app.common.Constants;
import Medium.DeFam.app.common.base.BaseFragment;
import Medium.DeFam.app.common.bean.MessageEvent;
import Medium.DeFam.app.common.http.HttpClient;
import Medium.DeFam.app.common.http.JsonBean;
import Medium.DeFam.app.common.http.TradeHttpCallback;
import Medium.DeFam.app.common.them.Eyes;
import Medium.DeFam.app.common.utils.GlideUtil;
import Medium.DeFam.app.common.utils.UserUtil;
import Medium.DeFam.app.dialog.RenZhengDialog;
import Medium.DeFam.app.dialog.YuYueDialog;
import Medium.DeFam.app.utils.HttpUtil;
import Medium.DeFam.app.view.ScaleTransitionPagerTitleView;
import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.autosize.utils.AutoSizeUtils;


public class HuoDongFragment extends BaseFragment {
    public static HuoDongFragment newInstance(int type) {
        HuoDongFragment fragment = new HuoDongFragment();
        Bundle args = new Bundle();
        args.putInt("type", type);
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.indicator)
    MagicIndicator mIndicator;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.renzheng)
    ImageView renzheng;
    @BindView(R.id.banner)
    Banner banner;
    ViewPagerAdapter viewPagerAdapter;
    CommonNavigatorAdapter commonNavigatorAdapter;
    List<String> titles = new ArrayList<>();
    private ArrayList<Fragment> fragmentList = new ArrayList<>();
    @Override
    protected boolean useEventBus() {
        return true;
    }

    @Override
    protected void onEventComing(MessageEvent event) {
        super.onEventComing(event);
        if (event.getCode() == Constants.ACTION_LOGIN && event.getData() != null) {
            //是否企业认证:0未认证 1已认证2驳回3审核中
            //renzheng.setVisibility("1".equals(UserUtil.getUserBean().getIs_enterprise()) ? View.GONE : View.VISIBLE);
        }
    }
    @Override
    public int getLayoutId() {
        return R.layout.fragment_huodong;
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
        viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager(), fragmentList);
        //给ViewPager设置适配器
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(new MyOnPageChangeListener());//页面变化时的监听器
        viewPager.setOffscreenPageLimit(fragmentList.size());
        ViewPagerHelper.bind(mIndicator, viewPager);

        titles.add("热门资讯");
        fragmentList.add(HomeItemHuoDongFragment.newInstance("hot", true));
        titles.add("排行榜");
        fragmentList.add(HomeItemHuoDongFragment.newInstance("ranking"));
        titles.add("空投活动");
        fragmentList.add(HomeItemHuoDongFragment.newInstance("airdrop"));
        titles.add("线上活动");
        fragmentList.add(HomeItemHuoDongFragment.newInstance("online"));
        titles.add("活动方列表");
        fragmentList.add(HuoDongItemFragment.newInstance(0));
        viewPagerAdapter.notifyDataSetChanged();
        commonNavigatorAdapter.notifyDataSetChanged();
        viewPager.setOffscreenPageLimit(fragmentList.size());
        viewPager.setCurrentItem(0);
        useBanner();
    }

    private void useBanner() {
        Map<String, String> map = new HashMap<>();
        map.put("code", "activity");
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
            if (fragmentList.get(position) instanceof HomeItemHuoDongFragment) {
                HomeItemHuoDongFragment fragment = (HomeItemHuoDongFragment) fragmentList.get(position);//懒加载
                if (null != fragment) {
                    fragment.loadData();
                }
            } else if (fragmentList.get(position) instanceof HuoDongItemFragment) {
                HuoDongItemFragment fragment = (HuoDongItemFragment) fragmentList.get(position);//懒加载
                if (null != fragment) {
                    fragment.loadData();
                }
            }

        }
    }

    @OnClick({R.id.renzheng})
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.renzheng) {
            if("1".equals(UserUtil.getUserBean().getIs_enterprise())){
                Intent intent = new Intent(getActivity(), RenZheng.class);
                startActivity(intent);
            }else {
                RenZhengDialog payDialog = new RenZhengDialog(getActivity());
                payDialog.show();
            }

        }
    }

}
