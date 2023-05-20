package Medium.DeFam.app.dialog;

import static Medium.DeFam.app.common.Constants.WEIXIN_APP_ID;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ListView;

import androidx.annotation.NonNull;


import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.transformer.ZoomOutPageTransformer;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Medium.DeFam.app.R;
import Medium.DeFam.app.activity.WenZhangDetail;
import Medium.DeFam.app.adapter.ImageAdapter;
import Medium.DeFam.app.adapter.PingLunDialogAdapter;
import Medium.DeFam.app.bean.BannerBean;
import Medium.DeFam.app.bean.JiangLiBean;
import Medium.DeFam.app.common.base.BaseDialogFragment;
import Medium.DeFam.app.common.http.HttpClient;
import Medium.DeFam.app.common.http.JsonBean;
import Medium.DeFam.app.common.http.TradeHttpCallback;
import Medium.DeFam.app.common.utils.ToastUtil;
import Medium.DeFam.app.utils.HttpUtil;
import Medium.DeFam.app.wx.WxShareInstance;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;


public class FenXiangDialogFragment extends BaseDialogFragment {
    private Context mContext;
    private String type, action_id,share_link;
    private WxShareInstance wxShare;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mContext = getContext();
        int screenWidth = mContext.getResources().getDisplayMetrics().widthPixels;
        Dialog dialog = new Dialog(mContext, R.style.BottomViewTheme_Transparent);//dialog_no_background
        dialog.setCanceledOnTouchOutside(true);
        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_dialog_fenxiang, null);
        dialog.setContentView(view);
        window.setWindowAnimations(R.style.BottomToTopAnim);
        window.setGravity(Gravity.BOTTOM);

        window.setLayout(screenWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
        ButterKnife.bind(this, view);
        wxShare = new WxShareInstance(mContext, WEIXIN_APP_ID);
        initData();
        return dialog;
    }

    private void initData() {
        if (getArguments() != null) {
            type = getArguments().getString("type");
            action_id = getArguments().getString("action_id");
            share_link = getArguments().getString("share_link");
        }

    }

    @OnClick({R.id.fuzhi, R.id.quxiao, R.id.weixin})
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.quxiao) {
            dismiss();
        } else if (id == R.id.fuzhi) {
            Map<String, String> map = new HashMap<>();
            map.put("type", type);
            map.put("action_id", action_id);
            HttpClient.getInstance().gets(HttpUtil.MEMBERSHARE, map, new TradeHttpCallback<JsonBean<JiangLiBean>>() {
                @Override
                public void onSuccess(JsonBean<JiangLiBean> data) {
                    //获取剪贴板管理器：
                    ClipboardManager cm = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                    // 创建普通字符型ClipData
                    ClipData mClipData = ClipData.newPlainText("Label", share_link);
                    // 将ClipData内容放到系统剪贴板里。
                    cm.setPrimaryClip(mClipData);
                    ToastUtil.initToast("复制成功");
                    dismiss();
                    if(!TextUtils.isEmpty(data.getData().getPoint())&&Integer.parseInt(data.getData().getPoint())>0){
                        JiFenDialog payDialog = new JiFenDialog(mContext,"分享成功获得"+data.getData().getPoint()+"积分");
                        payDialog.show();
                    }
                }

                @Override
                public boolean showLoadingDialog() {
                    return true;
                }
            });
        } else if (id == R.id.weixin) {
            //share2WX(SendMessageToWX.Req.WXSceneSession);
           /* if (null == alldata) {//5 默认平台分享推广
                wxShare.shareXCX(context, "pages/mall/index/index", "龙莱商城", null);
            } else {
                wxShare.shareXCX(context, "pages/multi-merchant/mall/detail/detail?goodId=" + alldata.getGoods_rs().getId(), alldata.getGoods_rs().getName(), null);
            }*/
        }/*else if (stringList.get(position).equals("朋友圈")) {
            if (!isWeixinAvilible(context)) {
                ToastUtil.show("未检测到微信");
                return;
            }
            //share2WX(SendMessageToWX.Req.WXSceneTimeline);
            Observable.just(true).doOnNext(aBoolean -> {
                GlideUtil.showImg(context, alldata.getGoods_rs().getImage(), null, bmt -> {//下载成功 后替换
                    if (null != bmt) {
                        //Log.i("zmh",HttpMethods.BASE_SITE + "/wyapi.php?g=wyapi&c=AppDownload&a=index&share_uid="+AppContext.getInstance().getAccount().getUid());
                        wxShare.shareImg(SendMessageToWX.Req.WXSceneTimeline, "龙莱商城", alldata.getGoods_rs().getName(), alldata.getGoods_rs().getImage(), bmt, context, shareListener);
                    }
                });
            }).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread()).subscribe();

        }*/
    }

}