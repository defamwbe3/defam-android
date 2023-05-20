package Medium.DeFam.app.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Medium.DeFam.app.R;
import Medium.DeFam.app.activity.KOLDetail;
import Medium.DeFam.app.activity.WenZhangDetail;
import Medium.DeFam.app.adapter.ImageAdapter;
import Medium.DeFam.app.adapter.KOLItemAdapter;
import Medium.DeFam.app.adapter.ViewPagerAdapter;
import Medium.DeFam.app.bean.BannerBean;
import Medium.DeFam.app.bean.CategoryBean;
import Medium.DeFam.app.bean.KOLBean;
import Medium.DeFam.app.common.Constants;
import Medium.DeFam.app.common.base.BaseFragment;
import Medium.DeFam.app.common.http.HttpClient;
import Medium.DeFam.app.common.http.JsonBean;
import Medium.DeFam.app.common.http.TradeHttpCallback;
import Medium.DeFam.app.common.utils.ToastUtil;
import Medium.DeFam.app.dialog.FenXiangDialogFragment;
import Medium.DeFam.app.utils.HttpUtil;
import Medium.DeFam.app.view.ScaleTransitionPagerTitleView;
import butterknife.BindView;
import me.jessyan.autosize.utils.AutoSizeUtils;


public class ZiXunKOLFragment extends BaseFragment {
    public static ZiXunKOLFragment newInstance(int type) {
        ZiXunKOLFragment fragment = new ZiXunKOLFragment();
        Bundle args = new Bundle();
        args.putInt("type", type);
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.not)
    View not;
    KOLItemAdapter adapter;
    private int page = 1;
    //KOLItemFragment

    @Override
    public int getLayoutId() {
        return R.layout.fragment_zixunkol;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void initData() {
        super.initData();

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                page = 1;
                getData();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                getData();
            }
        });
        adapter = new KOLItemAdapter(getContext());
        adapter.setRecyclerViewOnItemClickListener(new KOLItemAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(int pos, int type, KOLBean.DataBean data) {
                if(0==type){
                    /*Intent intent = new Intent(getActivity(), KOLDetail.class);
                intent.putExtra("data",data);
                startActivity(intent);*/
                }else  if(1==type){
                    FenXiangDialogFragment fenXiangDialogFragment = new FenXiangDialogFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("action_id", data.getId());
                    bundle.putString("type","1");
                    bundle.putString("share_link",data.getHref());
                    fenXiangDialogFragment.setArguments(bundle);
                    fenXiangDialogFragment.show(getChildFragmentManager(), "xinxi", true);
                }

            }
        });
        recyclerView.setAdapter(adapter);

        getData();

        useBanner();
    }

    private void useBanner() {
        if (!isLogined()) {
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("code", "realinfo");
        HttpClient.getInstance().gets(HttpUtil.BANNER, map, new TradeHttpCallback<JsonBean<BannerBean>>() {
            @Override
            public void onSuccess(JsonBean<BannerBean> data) {
                banner.setAdapter(new ImageAdapter(getActivity(), data.getData().getData()))
                        .setBannerGalleryMZ(20)
                        .addPageTransformer(new ZoomOutPageTransformer())
                        .addBannerLifecycleObserver(getActivity())//添加生命周期观察者
                        .setIndicator(new CircleIndicator(getActivity()));
            }
        });
    }

    private void getData() {
        Map<String, String> map = new HashMap<>();
        map.put("page", page + "");
        HttpClient.getInstance().gets(HttpUtil.KOLLIST, map, new TradeHttpCallback<JsonBean<KOLBean>>() {
            @Override
            public void onSuccess(JsonBean<KOLBean> data) {
                if (null == data || null == data.getData()) {
                    refreshLayout.finishRefresh();
                    refreshLayout.finishLoadMore();
                    return;
                }
                if (page > 1) {
                    refreshLayout.finishLoadMore();
                    if (data.getData().getData().size() == 0) {
                        ToastUtil.initToast("暂无更多数据");
                        return;
                    }
                    adapter.addData(data.getData().getData());
                } else {
                    not.setVisibility(data.getData().getData().size() > 0 ? View.GONE : View.VISIBLE);
                    refreshLayout.finishRefresh();
                    adapter.replaceAll(data.getData().getData());
                }
                page++;
            }

            @Override
            public void onError(Response<JsonBean<KOLBean>> response) {
                super.onError(response);
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
            }
        });
        /*HashMap<String, String> map = new HashMap<>();
        map.put("page", page + "");
        OkGo.<String>get(Constants.HOST + HttpUtil.KOLLIST).params(map).execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
                JsonObject alldata = new JsonObject();
                try {
                    JSONObject jsonObject = new JSONObject((String) response.body());
                    List<JsonObject> dataList = new Gson().fromJson(jsonObject.getJSONObject("data").getJSONArray("data").toString(), new TypeToken<List<JsonObject>>(){}.getType());
                    if (page > 1) {
                        refreshLayout.finishLoadMore();
                        if (dataList.size() == 0) {
                            ToastUtil.initToast("暂无更多数据");
                            return;
                        }
                        adapter.addData(dataList);
                    } else {
                        not.setVisibility(dataList.size() > 0 ? View.GONE : View.VISIBLE);
                        refreshLayout.finishRefresh();
                        adapter.replaceAll(dataList);
                    }
                    page++;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
            }
        });*/


    }

}