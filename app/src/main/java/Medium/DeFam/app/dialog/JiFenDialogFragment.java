package Medium.DeFam.app.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hjq.toast.Toaster;

import java.util.HashMap;
import java.util.Map;

import Medium.DeFam.app.R;
import Medium.DeFam.app.activity.JiFenJiLuDetail;
import Medium.DeFam.app.adapter.ProductAdapter;
import Medium.DeFam.app.bean.JiFenDetailBean;
import Medium.DeFam.app.bean.JiFenOkBean;
import Medium.DeFam.app.common.base.BaseDialogFragment;
import Medium.DeFam.app.common.http.HttpClient;
import Medium.DeFam.app.common.http.JsonBean;
import Medium.DeFam.app.common.http.TradeHttpCallback;
import Medium.DeFam.app.utils.HttpUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class JiFenDialogFragment extends BaseDialogFragment {
    private Context mContext;
    @BindView(R.id.product_view)
    RecyclerView productView;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.not)
    View not;
    @BindView(R.id.duihuan)
    Button duihuan;
    JiFenDetailBean alldata;
    private String sku_price_id;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mContext = getContext();
        int screenWidth = mContext.getResources().getDisplayMetrics().widthPixels;
        Dialog dialog = new Dialog(mContext, R.style.BottomViewTheme_Transparent);//dialog_no_background
        dialog.setCanceledOnTouchOutside(true);
        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_dialog_jifen, null);
        dialog.setContentView(view);
        window.setWindowAnimations(R.style.BottomToTopAnim);
        window.setGravity(Gravity.BOTTOM);

        window.setLayout(screenWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
        ButterKnife.bind(this, view);
        initData();
        return dialog;
    }

    private void initData() {
        alldata = (JiFenDetailBean) getArguments().getSerializable("data");
        if (null == alldata.getSku() || alldata.getSku().size() == 0) {
            not.setVisibility(View.VISIBLE);
            productView.setVisibility(View.GONE);

            if(null == alldata.getSku_price() || alldata.getSku_price().size() == 0){
                duihuan.setEnabled(false);
                duihuan.setBackgroundResource(R.drawable.r_hui50);
            }else {
                sku_price_id = alldata.getSku_price().get(0).getId();
            }

        } else {
            not.setVisibility(View.GONE);
            productView.setVisibility(View.VISIBLE);
            price.setText(alldata.getPrice() + "DD");

            setPrice();
            productView.setLayoutManager(new LinearLayoutManager(getActivity()));
            productView.setAdapter(new ProductAdapter(getActivity(), alldata.getSku(), new ProductAdapter.OnNoticeListener() {
                @Override
                public void setNoticeListener(int id, int position, String data) {
                    setPrice();

                }
            }));
        }


    }

    private void setPrice() {
        String goods_sku_ids = "";
        for (int i = 0, a = alldata.getSku().size(); i < a; i++) {
            for (int s = 0, b = alldata.getSku().get(i).getChildren().size(); s < b; s++) {
                if (alldata.getSku().get(i).getChildren().get(s).isCheck()) {
                    goods_sku_ids += alldata.getSku().get(i).getChildren().get(s).getId() + ",";
                    break;
                }
            }
        }
        if (goods_sku_ids.length() > 0) {
            goods_sku_ids = goods_sku_ids.substring(0, goods_sku_ids.length() - 1);
        }
        for (int i = 0, a = alldata.getSku_price().size(); i < a; i++) {
            if (goods_sku_ids.equals(alldata.getSku_price().get(i).getGoods_sku_ids())) {
                price.setText(alldata.getSku_price().get(i).getPrice() + "DD");
                sku_price_id = alldata.getSku_price().get(i).getId();
                break;
            }
        }
    }

    @OnClick({R.id.back, R.id.duihuan})
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.back) {
            dismiss();
        } else if (id == R.id.duihuan) {
               /* if("1".equals(alldata.getSku_type())){

            }*/
            if (TextUtils.isEmpty(sku_price_id)) {
                Toaster.show("请选择");
                return;
            }
            getData();
        }
    }

    private void getData() {
        Map<String, String> map = new HashMap<>();
        map.put("goods_id", alldata.getId());
        map.put("sku_price_id", sku_price_id);
        HttpClient.getInstance().posts(HttpUtil.APIORDERPUT, map, new TradeHttpCallback<JsonBean<JiFenOkBean>>() {
            @Override
            public void onSuccess(JsonBean<JiFenOkBean> data) {
                if (null == data || null == data.getData()) {
                    return;
                }
                Intent intent = new Intent(getActivity(), JiFenJiLuDetail.class);
                intent.putExtra("id", data.getData().getOrder_id());
                startActivity(intent);
                dismiss();
            }


        });
    }
}