package Medium.DeFam.app.dialog;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

import Medium.DeFam.app.R;
import Medium.DeFam.app.activity.Web;
import Medium.DeFam.app.common.http.HttpClient;
import Medium.DeFam.app.common.http.JsonBean;
import Medium.DeFam.app.common.http.TradeHttpCallback;
import Medium.DeFam.app.utils.HttpUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QuanXianAlertDialog extends Dialog {
    @BindView(R.id.gouxuan)
    TextView gouxuan;

    private Context mContext;
    private OnNoticeListener mItemSelectListener;

    public interface OnNoticeListener {
        void setNoticeListener(int id, int position, String data);
    }

    public void setItemListener(OnNoticeListener listener) {
        mItemSelectListener = listener;
    }

    public QuanXianAlertDialog(@NonNull Context context) {
        super(context);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_quanxian, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        setCancelable(false);
        setCanceledOnTouchOutside(false);

        Window dialogWindow = getWindow();
        dialogWindow.setWindowAnimations(R.style.dialogAnim);
        dialogWindow.setBackgroundDrawableResource(android.R.color.transparent);
        dialogWindow.setGravity(Gravity.CENTER);

        String str = "查看完整版《用户协议》和《隐私政策》";
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        ssb.append(str);
        //第一个出现的位置
        final int start = str.indexOf("《");
        ssb.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                getXieYiData("user_service", "用户协议");
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                //设置文件颜色
                ds.setColor(mContext.getResources().getColor(R.color.color_01C8E5));
                // 去掉下划线
                ds.setUnderlineText(false);
            }

        }, start, start + 6, 0);
        ssb.setSpan(new ClickableSpan() {

            @Override
            public void onClick(View widget) {
                getXieYiData("private", "隐私政策");
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                //设置文件颜色
                ds.setColor(mContext.getResources().getColor(R.color.color_01C8E5));
                // 去掉下划线
                ds.setUnderlineText(false);
            }

        }, start + 7, start + 13, 0);

        gouxuan.setMovementMethod(LinkMovementMethod.getInstance());
        gouxuan.setText(ssb, TextView.BufferType.SPANNABLE);

    }

    @OnClick({R.id.butongyi, R.id.yes})
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.butongyi) {
            mItemSelectListener.setNoticeListener(0, 0, "");
        } else if (id == R.id.yes) {
            mItemSelectListener.setNoticeListener(1, 0, "");
        }
    }

    private void getXieYiData(String type, String name) {
        Map<String, String> map = new HashMap<>();
        map.put("code", type);
        HttpClient.getInstance().gets(HttpUtil.AGREEMENT, map, new TradeHttpCallback<JsonBean<String>>() {
            @Override
            public void onSuccess(JsonBean<String> data) {
                Web.startWebActivity(mContext, name, "", data.getData());

            }

            @Override
            public boolean showLoadingDialog() {
                return true;
            }
        });
    }
}

