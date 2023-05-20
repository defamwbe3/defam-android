package Medium.DeFam.app.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.lzy.okgo.model.Response;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Medium.DeFam.app.R;
import Medium.DeFam.app.activity.RenZheng;
import Medium.DeFam.app.activity.WalletAddress;
import Medium.DeFam.app.bean.MyMapBean;
import Medium.DeFam.app.bean.WalletAddressBean;
import Medium.DeFam.app.common.Constants;
import Medium.DeFam.app.common.http.HttpClient;
import Medium.DeFam.app.common.http.JsonBean;
import Medium.DeFam.app.common.http.TradeHttpCallback;
import Medium.DeFam.app.common.utils.GlideUtil;
import Medium.DeFam.app.common.utils.ToastUtil;
import Medium.DeFam.app.utils.HttpUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class TiXianDialog extends Dialog {
    @BindView(R.id.image)
    RoundedImageView image;
    @BindView(R.id.map_name)
    TextView map_name;
    @BindView(R.id.dizhi)
    TextView dizhi;
    private Context mContext;
    private OnNoticeListener mItemSelectListener;
    MyMapBean.DataBean alldata;
    public interface OnNoticeListener {
        void setNoticeListener(int id, int position, String data);
    }

    public void setItemListener(OnNoticeListener listener) {
        mItemSelectListener = listener;
    }

    public TiXianDialog(@NonNull Context context, MyMapBean.DataBean data) {
        super(context);
        mContext = context;
        alldata = data;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_tixian, null);
        setContentView(view);
        setCancelable(false);
        setCanceledOnTouchOutside(true);

        Window dialogWindow = getWindow();
        dialogWindow.setWindowAnimations(R.style.dialogAnim);
        dialogWindow.setBackgroundDrawableResource(android.R.color.transparent);
        dialogWindow.setGravity(Gravity.CENTER);
        ButterKnife.bind(this, view);
        GlideUtil.showImg(mContext, alldata.getImage(), image);
        map_name.setText(alldata.getMap_name());
        getData();
    }
    private void getData() {
        Map<String, String> map = new HashMap<>();
        map.put("page", "1");
        HttpClient.getInstance().gets(HttpUtil.APIWALLETADDRESS, map, new TradeHttpCallback<JsonBean<WalletAddressBean>>() {
            @Override
            public void onSuccess(JsonBean<WalletAddressBean> data) {
                if (null == data || null == data.getData()) {
                    dizhi.setText("");
                    return;
                }
                if (data.getData().getData().size() > 0) {
                    dizhi.setText(data.getData().getData().get(0).getAddress());
                }else {
                    dizhi.setText("");
                }
            }


        });
    }

    public void setDiZhi(WalletAddressBean.DataBean datadizhi){
        dizhi.setText(datadizhi.getAddress());
    }
    @OnClick({R.id.quxiao, R.id.renzheng, R.id.setdizhi})
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.quxiao) {
            dismiss();
        } else if (id == R.id.renzheng) {
            if(TextUtils.isEmpty(dizhi.getText().toString())){
                ToastUtil.initToast("请编辑添加钱包地址");
                return;
            }
            Map<String, String> map = new HashMap<>();
            map.put("id", alldata.getId());
            map.put("address", dizhi.getText().toString());
            HttpClient.getInstance().posts(HttpUtil.MAPWITH, map, new TradeHttpCallback<JsonBean<List<String>>>() {
                @Override
                public void onSuccess(JsonBean<List<String>> data) {
                    if (null == data || null == data.getData()) {
                        return;
                    }
                    ToastUtil.initToast("操作成功");
                    mItemSelectListener.setNoticeListener(1,0,"");
                    dismiss();
                }

                @Override
                public boolean showLoadingDialog() {
                    return true;
                }
            });
        } else if (id == R.id.setdizhi) {
            mItemSelectListener.setNoticeListener(0,0,"");

        }
    }

}
