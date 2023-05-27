package Medium.DeFam.app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;

import org.greenrobot.eventbus.EventBus;

import Medium.DeFam.app.R;
import Medium.DeFam.app.activity.BaoZang;
import Medium.DeFam.app.activity.DuiHuan;
import Medium.DeFam.app.activity.FenSi;
import Medium.DeFam.app.activity.GeRen;
import Medium.DeFam.app.activity.GuanZhu;
import Medium.DeFam.app.activity.HuoDongMy;
import Medium.DeFam.app.activity.JiFenShop;
import Medium.DeFam.app.activity.JiangLi;
import Medium.DeFam.app.activity.Setting;
import Medium.DeFam.app.activity.ShouCang;
import Medium.DeFam.app.activity.WalletAddress;
import Medium.DeFam.app.activity.XiaoXi;
import Medium.DeFam.app.activity.YaoQing;
import Medium.DeFam.app.activity.ZhuYe;
import Medium.DeFam.app.bean.InfoBean;
import Medium.DeFam.app.common.Constants;
import Medium.DeFam.app.common.base.BaseFragment;
import Medium.DeFam.app.common.bean.MessageEvent;
import Medium.DeFam.app.common.bean.UserBean;
import Medium.DeFam.app.common.http.HttpClient;
import Medium.DeFam.app.common.http.JsonBean;
import Medium.DeFam.app.common.http.TradeHttpCallback;
import Medium.DeFam.app.common.them.Eyes;
import Medium.DeFam.app.common.utils.GlideUtil;
import Medium.DeFam.app.common.utils.UserUtil;
import Medium.DeFam.app.utils.HttpUtil;
import butterknife.BindView;
import butterknife.OnClick;


public class MyFragment extends BaseFragment {
    public static MyFragment newInstance() {
        MyFragment fragment = new MyFragment();
        return fragment;
    }

    @BindView(R.id.avatar)
    RoundedImageView avatar;
    @BindView(R.id.nickname)
    TextView nickname;
    @BindView(R.id.integral)
    TextView integral;
    @BindView(R.id.suipian_total)
    TextView suipian_total;
    @BindView(R.id.fans_num)
    TextView fans_num;
    @BindView(R.id.follow_num)
    TextView follow_num;
    @BindView(R.id.zan_num)
    TextView zan_num;
    @BindView(R.id.collection_num)
    TextView collection_num;
    @BindView(R.id.level)
    TextView level;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        view.findViewById(R.id.topview).setPadding(0, Eyes.getStatusBarHeight(getActivity()), 0, 0);

    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    public void getData() {
        if (!isLogined()) {
            return;
        }
        HttpClient.getInstance().gets(HttpUtil.MEMBERINFO, null, new TradeHttpCallback<JsonBean<InfoBean>>() {
            @Override
            public void onSuccess(JsonBean<InfoBean> data) {
                if (null == data || null == data.getData()) {
                    return;
                }
                EventBus.getDefault().post(new MessageEvent<InfoBean>(Constants.INFO, data.getData()));

                UserBean userBean = UserUtil.getUserBean();
                userBean.setIs_enterprise(data.getData().getIs_enterprise());
                UserUtil.setUserBean(userBean);
                level.setText("Lv." + data.getData().getLevel());
                GlideUtil.showImg(getActivity(), data.getData().getAvatar(), avatar);
                nickname.setText(data.getData().getNickname());
                integral.setText("积分：" + data.getData().getIntegral());
                suipian_total.setText("碎片：" + data.getData().getSuipian_total());
                fans_num.setText(data.getData().getFans_num());
                follow_num.setText(data.getData().getFollow_num());
                zan_num.setText(data.getData().getZan_num());
                collection_num.setText(data.getData().getCollection_num());
            }
        });
    }

    @OnClick({R.id.xiaoxi, R.id.shezhi, R.id.fensi, R.id.guanzhu, R.id.shoucang, R.id.dizhi, R.id.jifenshangcheng
            , R.id.duihuan, R.id.yaoqing, R.id.jiangli, R.id.baozang, R.id.zhuye, R.id.wodehuodong, R.id.gerenziliao})
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.xiaoxi) {
            Intent intent = new Intent(getActivity(), XiaoXi.class);
            startActivity(intent);
        } else if (id == R.id.shezhi) {
            Intent intent = new Intent(getActivity(), Setting.class);
            startActivity(intent);
        } else if (id == R.id.fensi) {
            Intent intent = new Intent(getActivity(), FenSi.class);
            startActivity(intent);
        } else if (id == R.id.guanzhu) {
            Intent intent = new Intent(getActivity(), GuanZhu.class);
            startActivity(intent);
        } else if (id == R.id.shoucang) {
            Intent intent = new Intent(getActivity(), ShouCang.class);
            startActivity(intent);
        } else if (id == R.id.wodehuodong) {
            Intent intent = new Intent(getActivity(), HuoDongMy.class);
            startActivity(intent);
        } else if (id == R.id.dizhi) {
            Intent intent = new Intent(getActivity(), WalletAddress.class);
            startActivity(intent);
        } else if (id == R.id.jifenshangcheng) {
            Intent intent = new Intent(getActivity(), JiFenShop.class);
            startActivity(intent);
        } else if (id == R.id.duihuan) {
            Intent intent = new Intent(getActivity(), DuiHuan.class);
            startActivity(intent);
        } else if (id == R.id.yaoqing) {
            Intent intent = new Intent(getActivity(), YaoQing.class);
            startActivity(intent);
        } else if (id == R.id.jiangli) {
            Intent intent = new Intent(getActivity(), JiangLi.class);
            startActivity(intent);
        } else if (id == R.id.baozang) {
            Intent intent = new Intent(getActivity(), BaoZang.class);
            startActivity(intent);
            // Web.startWebActivity(About.this, name, data.getData(), "");

        } else if (id == R.id.zhuye) {
            Intent intent = new Intent(getActivity(), ZhuYe.class);
            intent.putExtra("id", UserUtil.getUserBean().getId());
            startActivity(intent);
        }else if (id == R.id.gerenziliao) {
            Intent intent = new Intent(getActivity(), GeRen.class);
            startActivity(intent);
        }
    }

}
