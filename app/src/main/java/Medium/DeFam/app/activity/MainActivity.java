package Medium.DeFam.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.xuexiang.xupdate.XUpdate;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.transformer.ZoomOutPageTransformer;

import Medium.DeFam.app.R;
import Medium.DeFam.app.adapter.ImageAdapter;
import Medium.DeFam.app.bean.BannerBean;
import Medium.DeFam.app.common.CommonAppContext;
import Medium.DeFam.app.common.Constants;
import Medium.DeFam.app.common.base.BaseActivity;
import Medium.DeFam.app.common.bean.MessageEvent;
import Medium.DeFam.app.common.http.HttpClient;
import Medium.DeFam.app.common.http.JsonBean;
import Medium.DeFam.app.common.http.TradeHttpCallback;
import Medium.DeFam.app.common.utils.SpUtil;
import Medium.DeFam.app.common.utils.ToastUtil;
import Medium.DeFam.app.common.widget.MyViewPager;
import Medium.DeFam.app.common.widget.TabButtonGroup;
import Medium.DeFam.app.fragment.HomeFragment;
import Medium.DeFam.app.fragment.HomeItemHuoDongFragment;
import Medium.DeFam.app.fragment.HuoDongFragment;
import Medium.DeFam.app.fragment.MyFragment;
import Medium.DeFam.app.fragment.QuanZiFragment;
import Medium.DeFam.app.fragment.ZiXunFragment;
import Medium.DeFam.app.utils.CustomUpdateParser;
import Medium.DeFam.app.utils.HttpUtil;
import Medium.DeFam.app.view.MoveView;
import butterknife.BindView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends BaseActivity {
    @BindView(R.id.tab_groups)
    TabButtonGroup mTabButtonGroup;
    @BindView(R.id.viewPager)
    MyViewPager mViewPager;
    private List<Fragment> mViewList;
    boolean mBackKeyPressed = false;

    @Override
    protected boolean isNeedLogin() {
        return false;
    }

    @Override
    protected boolean useEventBus() {
        return true;
    }

    @Override
    protected void onEventComing(MessageEvent event) {
        super.onEventComing(event);
        if (event.getCode() == Constants.POLLING) {
            if (CommonAppContext.getInstance().mFront) {
                setReadReward();
            }
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }


    @Override
    protected void initView(@Nullable Bundle savedInstanceState, @Nullable Bundle bundle) {
        XUpdate.newBuild(this)
                .updateUrl(Constants.HOST + HttpUtil.GETUPGRADE)
                .updateParser(new CustomUpdateParser(this)) //设置自定义的版本更新解析器
                .update();
    }

    private void setReadReward() {
        if (!isLogined()) {
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("time", "6");
        HttpClient.getInstance().gets(HttpUtil.READREWARD, map, new TradeHttpCallback<JsonBean<List<String>>>() {
            @Override
            public void onSuccess(JsonBean<List<String>> data) {

            }
        });
    }

    @Override
    protected void initData() {
        mViewList = new ArrayList<>();
        mViewList.add(HomeFragment.newInstance(0));
        mViewList.add(ZiXunFragment.newInstance(0));
        mViewList.add(QuanZiFragment.newInstance(0));
        mViewList.add(HuoDongFragment.newInstance(0));
        mViewList.add(MyFragment.newInstance());
        mViewPager.setOffscreenPageLimit(mViewList.size());
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mViewList.get(position);
            }

            @Override
            public int getCount() {
                return mViewList.size();
            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (mViewList.get(position) instanceof MyFragment) {
                    MyFragment fragment = (MyFragment) mViewList.get(position);//懒加载
                    if (null != fragment) {
                        fragment.getData();
                    }
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mTabButtonGroup.setViewPager(mViewPager);
        mTabButtonGroup.setCurrentItem(0);
       /* MoveView chatView = new MoveView(this);
        chatView.setItemListener(new MoveView.OnNoticeListener() {
            @Override
            public void setNoticeListener(int id, int position, String data) {
                Intent intent = new Intent(MainActivity.this, FaBu.class);
                startActivity(intent);

            }
        });
        chatView.show();*/
    }

    /**
     * 返回处理，防止点击返回按钮的时候，会直接退出当前app
     **/
    @Override
    public void onBackPressed() {
        if (!mBackKeyPressed) {
            ToastUtil.initToast("再按一次退出程序");
            mBackKeyPressed = true;
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    mBackKeyPressed = false;
                }
            }, 2000);
        } else {
            System.exit(0);//正常退出App
        }
    }
}
