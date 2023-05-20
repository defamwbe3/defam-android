package Medium.DeFam.app.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.AppBarLayout;
import com.lzy.okgo.model.Response;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Medium.DeFam.app.R;
import Medium.DeFam.app.adapter.ViewPagerAdapter;
import Medium.DeFam.app.bean.InfoBean;
import Medium.DeFam.app.bean.YaoQingListBean;
import Medium.DeFam.app.common.base.BaseActivity;
import Medium.DeFam.app.common.bean.UserBean;
import Medium.DeFam.app.common.http.HttpClient;
import Medium.DeFam.app.common.http.JsonBean;
import Medium.DeFam.app.common.http.TradeHttpCallback;
import Medium.DeFam.app.common.interfaces.OnUpdateImgListener;
import Medium.DeFam.app.common.them.Eyes;
import Medium.DeFam.app.common.utils.GlideUtil;
import Medium.DeFam.app.common.utils.ToastUtil;
import Medium.DeFam.app.common.utils.UpdateImageUtil;
import Medium.DeFam.app.common.utils.UserUtil;
import Medium.DeFam.app.fragment.HomeFragment;
import Medium.DeFam.app.fragment.HomeItemFragment;
import Medium.DeFam.app.fragment.HomeItemKolFragment;
import Medium.DeFam.app.fragment.ZhuYeFragment;
import Medium.DeFam.app.utils.HttpUtil;
import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.autosize.AutoSize;
import me.jessyan.autosize.utils.AutoSizeUtils;


public class ZhuYe extends BaseActivity implements AppBarLayout.OnOffsetChangedListener {
    @BindView(R.id.indicator)
    MagicIndicator mIndicator;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.titleView)
    TextView mTitleView;
    @BindView(R.id.topview)
    RelativeLayout topview;
    @BindView(R.id.btn_back)
    ImageView mBtnBack;
    @BindView(R.id.appBarLayout)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.wap_cover_address)
    ImageView wap_cover_address;
    @BindView(R.id.avatar)
    ImageView avatar;
    @BindView(R.id.nickname)
    TextView nickname;
    @BindView(R.id.sign)
    TextView sign;
    @BindView(R.id.url)
    TextView url;
    @BindView(R.id.fans_num)
    TextView fans_num;
    @BindView(R.id.follow_num)
    TextView follow_num;
    @BindView(R.id.zan_num)
    TextView zan_num;
    @BindView(R.id.belong_to)
    TextView belong_to;
    @BindView(R.id.is_follow)
    ImageView is_follow;
    private float mRate;
    private int[] mWhiteColorArgb;
    private int[] mBlackColorArgb;
    private ArrayList<Fragment> fragmentList = new ArrayList<>();
    private String myid;

    @Override
    protected boolean isNeedLogin() {
        return false;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_zhuye;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState, @Nullable Bundle bundle) {
        topview.setPadding(0, Eyes.getStatusBarHeight(this), 0, 0);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(mBtnBack.getLayoutParams());
        lp.setMargins(0, Eyes.getStatusBarHeight(this), 0, 0);
        mBtnBack.setLayoutParams(lp);
        mWhiteColorArgb = getColorArgb(0xffffffff);
        mBlackColorArgb = getColorArgb(0xff323232);
        mAppBarLayout.addOnOffsetChangedListener(this);
        final String[] titles = new String[]{"全部", "帖子", "文章"};
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return titles.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                simplePagerTitleView.setNormalColor(getResources().getColor(R.color.color_BEBEC0));
                simplePagerTitleView.setSelectedColor(getResources().getColor(R.color.color_373748));
                simplePagerTitleView.setText(titles[index]);
                simplePagerTitleView.setTextSize(16);
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (viewPager != null) {
                            viewPager.setCurrentItem(index);
                        }
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
                linePagerIndicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                linePagerIndicator.setLineWidth(AutoSizeUtils.dp2px(context, 24));
                linePagerIndicator.setLineHeight(AutoSizeUtils.dp2px(context, 2));
                linePagerIndicator.setRoundRadius(AutoSizeUtils.dp2px(context, 0));
                linePagerIndicator.setColors(getResources().getColor(R.color.color_373748));
                return linePagerIndicator;
            }

        });
        mIndicator.setNavigator(commonNavigator);
    }

    @Override
    protected void initData() {
        myid = getIntent().getStringExtra("id");
        fragmentList.add(ZhuYeFragment.newInstance("all", myid, true));
        fragmentList.add(ZhuYeFragment.newInstance("short", myid, false));
        fragmentList.add(ZhuYeFragment.newInstance("long", myid, false));
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragmentList);
        //给ViewPager设置适配器
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(new MyOnPageChangeListener());//页面变化时的监听器
        viewPager.setOffscreenPageLimit(fragmentList.size());
        ViewPagerHelper.bind(mIndicator, viewPager);
        getData();
    }

    private void getData() {
        Map<String, String> map = new HashMap<>();
        map.put("id", myid);
        HttpClient.getInstance().posts(HttpUtil.MEMBERMEMBERINFO, map, new TradeHttpCallback<JsonBean<InfoBean>>() {
            @Override
            public void onSuccess(JsonBean<InfoBean> data) {
                if (null == data || null == data.getData()) {
                    return;
                }
                if (!TextUtils.isEmpty(data.getData().getWap_cover_address())) {
                    GlideUtil.showImg(ZhuYe.this, data.getData().getWap_cover_address(), wap_cover_address);
                }
                GlideUtil.showImg(ZhuYe.this, data.getData().getAvatar(), avatar);
                nickname.setText(data.getData().getNickname());
                if(TextUtils.isEmpty(data.getData().getSign())){
                    sign.setVisibility(View.GONE);
                }else {
                    sign.setVisibility(View.VISIBLE);
                    sign.setText(data.getData().getSign());
                }
                if(TextUtils.isEmpty(data.getData().getUrl())){
                    url.setVisibility(View.GONE);
                }else {
                    url.setVisibility(View.VISIBLE);
                    url.setText(data.getData().getUrl());
                }

                fans_num.setText(data.getData().getFans_num());
                follow_num.setText(data.getData().getFollow_num());
                zan_num.setText(data.getData().getZan_num());
                mTitleView.setText(data.getData().getNickname());
                if ("other".equals(data.getData().getBelong_to())) {
                    belong_to.setVisibility(View.GONE);
                    is_follow.setVisibility(View.VISIBLE);
                    is_follow.setImageResource("0".equals(data.getData().getIs_follow()) ? R.mipmap.img14 : R.mipmap.img15);
                } else {
                    belong_to.setVisibility(View.VISIBLE);
                    is_follow.setVisibility(View.GONE);
                }


            }


        });
    }

    @OnClick({R.id.btn_back, R.id.belong_to, R.id.is_follow})
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_back) {
            finish();
        } else if (id == R.id.belong_to) {
            UpdateImageUtil.getInstance().startSelectorWithCrop(ZhuYe.this, "face", new OnUpdateImgListener() {
                @Override
                public void onSuccess(String url, File file) {
                    Map<String, String> map = new HashMap<>();
                    map.put("wap_cover_address", url);
                    HttpClient.getInstance().posts(HttpUtil.MEMBERUPDATE, map, new TradeHttpCallback<JsonBean<InfoBean>>() {
                        @Override
                        public void onSuccess(JsonBean<InfoBean> data) {
                            if (null == data || null == data.getData()) {
                                return;
                            }
                            GlideUtil.showImg(ZhuYe.this, data.getData().getWap_cover_address(), wap_cover_address);
                            UserBean myuserBean = UserUtil.getUserBean();
                            myuserBean.setAvatar(data.getData().getWap_cover_address());
                            UserUtil.setUserBean(myuserBean);
                        }

                        @Override
                        public boolean showLoadingDialog() {
                            return true;
                        }
                    });

                }
            });
        } else if (id == R.id.is_follow) {
            Map<String, String> map = new HashMap<>();
            map.put("to_member_id", myid);
            map.put("type", "1");
            map.put("member_id", UserUtil.getUserBean().getId());
            HttpClient.getInstance().gets(HttpUtil.ISFOLLOWMEMBER, map, new TradeHttpCallback<JsonBean<String>>() {
                @Override
                public void onSuccess(JsonBean<String> urldata) {
                    if (null == urldata || null == urldata.getData()) {
                        return;
                    }
                    getData();
                }

                @Override
                public boolean showLoadingDialog() {
                    return true;
                }
            });
        }
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        float totalScrollRange = appBarLayout.getTotalScrollRange();
        float rate = -1 * verticalOffset / totalScrollRange * 2;
        if (rate >= 1) {
            rate = 1;
        }
        if (mRate != rate) {
            mRate = rate;
            topview.setAlpha(rate);
            int a = (int) (mWhiteColorArgb[0] * (1 - rate) + mBlackColorArgb[0] * rate);
            int r = (int) (mWhiteColorArgb[1] * (1 - rate) + mBlackColorArgb[1] * rate);
            int g = (int) (mWhiteColorArgb[2] * (1 - rate) + mBlackColorArgb[2] * rate);
            int b = (int) (mWhiteColorArgb[3] * (1 - rate) + mBlackColorArgb[3] * rate);
            int color = Color.argb(a, r, g, b);
            mBtnBack.setColorFilter(color);
        }
    }

    /**
     * 获取颜色的argb
     */
    private int[] getColorArgb(int color) {
        return new int[]{Color.alpha(color), Color.red(color), Color.green(color), Color.blue(color)};
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
            if (fragmentList.get(position) instanceof ZhuYeFragment) {
                ZhuYeFragment fragment = (ZhuYeFragment) fragmentList.get(position);//懒加载
                if (null != fragment) {
                    fragment.loadData();
                }
            }

        }
    }
}
