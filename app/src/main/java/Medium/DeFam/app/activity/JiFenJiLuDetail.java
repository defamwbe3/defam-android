package Medium.DeFam.app.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.makeramen.roundedimageview.RoundedImageView;

import Medium.DeFam.app.R;
import Medium.DeFam.app.bean.JiFenJiLuDetailBean;
import Medium.DeFam.app.common.base.BaseActivity;
import Medium.DeFam.app.common.http.HttpClient;
import Medium.DeFam.app.common.http.JsonBean;
import Medium.DeFam.app.common.http.TradeHttpCallback;
import Medium.DeFam.app.common.utils.GlideUtil;
import Medium.DeFam.app.utils.HttpUtil;
import butterknife.BindView;


public class JiFenJiLuDetail extends BaseActivity {
    @BindView(R.id.status_name)
    TextView status_name;
    @BindView(R.id.goods_image)
    RoundedImageView goods_image;
    @BindView(R.id.goods_name)
    TextView goods_name;
    @BindView(R.id.integral)
    TextView integral;
    @BindView(R.id.order_no)
    TextView order_no;
    @BindView(R.id.pay_type_name)
    TextView pay_type_name;
    @BindView(R.id.paid_at)
    TextView paid_at;
    @BindView(R.id.delivered_at)
    TextView delivered_at;
    JiFenJiLuDetailBean alldata;
    String myid;

    @Override
    protected boolean isNeedLogin() {
        return false;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_jifenjiludetail;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState, @Nullable Bundle bundle) {

    }

    @Override
    protected void initData() {
        alldata = (JiFenJiLuDetailBean) getIntent().getSerializableExtra("data");
        myid = getIntent().getStringExtra("id");
        setUI();
        getData();
    }

    private void getData() {
        String id = null == alldata ? myid : alldata.getId();
        HttpClient.getInstance().gets(HttpUtil.APIORDERORDERID + id, null, new TradeHttpCallback<JsonBean<JiFenJiLuDetailBean>>() {
            @Override
            public void onSuccess(JsonBean<JiFenJiLuDetailBean> data) {
                if (null == data || null == data.getData()) {
                    return;
                }
                alldata = data.getData();
                setUI();
            }

        });
    }

    private void setUI() {
        if (null == alldata) {
            return;
        }
        status_name.setText(alldata.getStatus_name());
        GlideUtil.showImg(JiFenJiLuDetail.this, alldata.getGoods().get(0).getGoods_image(), goods_image);
        goods_name.setText(alldata.getGoods().get(0).getGoods_name());
        integral.setText(alldata.getGoods().get(0).getPrice() + "积分");
        order_no.setText(alldata.getOrder_no());
        pay_type_name.setText(alldata.getPay_type_name());
        paid_at.setText(alldata.getPaid_at());
        delivered_at.setText(TextUtils.isEmpty(alldata.getFinished_at()) ? "暂无" : alldata.getFinished_at());
    }
}
