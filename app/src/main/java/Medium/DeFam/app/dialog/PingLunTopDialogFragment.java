package Medium.DeFam.app.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import Medium.DeFam.app.R;
import Medium.DeFam.app.bean.CommentBean;
import Medium.DeFam.app.bean.JiangLiBean;
import Medium.DeFam.app.common.base.BaseDialogFragment;
import Medium.DeFam.app.common.http.HttpClient;
import Medium.DeFam.app.common.http.JsonBean;
import Medium.DeFam.app.common.http.TradeHttpCallback;
import Medium.DeFam.app.common.utils.ToastUtil;
import Medium.DeFam.app.utils.HttpUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class PingLunTopDialogFragment extends BaseDialogFragment {
    @BindView(R.id.content)
    EditText content;
    private String myid,type;

    private OnNoticeListener onNoticeListener;

    public interface OnNoticeListener {
        void setNoticeListener(String mEntity);
    }

    public void setOnNoticeListener(OnNoticeListener onNoticeListener) {
        this.onNoticeListener = onNoticeListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.dialog_gift);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_dialog_addcomment, null);
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.BottomToTopAnim);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.BOTTOM;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
        window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        ButterKnife.bind(this, view);
        //getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        initView();
        return dialog;
    }

    private void initView() {
        content.requestFocus();
        content.setFocusableInTouchMode(true);
        if (getArguments() != null) {
            myid = getArguments().getString("id");
            type = getArguments().getString("type");
        }
    }

    @OnClick({R.id.fabu})
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.fabu) {
            if (TextUtils.isEmpty(content.getText().toString())) {
                ToastUtil.initToast("说点什么吧~");
                return;
            }
            Map<String, String> map = new HashMap<>();
            map.put("type",type);
            map.put("id","0");
            map.put("article_id",myid);
            map.put("content",content.getText().toString());
            HttpClient.getInstance().posts(HttpUtil.COMMENTSAVE, map, new TradeHttpCallback<JsonBean<JiangLiBean>>() {
                @Override
                public void onSuccess(JsonBean<JiangLiBean> data) {
                    if (null == data || null == data.getData()) {
                        return;
                    }
                    content.setText("");
                    onNoticeListener.setNoticeListener("");
                    dismiss();
                    if(!TextUtils.isEmpty(data.getData().getPoint())&&Integer.parseInt(data.getData().getPoint())>0){
                        JiFenDialog payDialog = new JiFenDialog(getActivity(),"评论成功获得"+data.getData().getPoint()+"积分");
                        payDialog.show();
                    }
                }

            });

        }
    }


    /**
     * 弹出对话框
     *
     * @param fragmentManager
     */
    public void showDialog(FragmentManager fragmentManager) {
        if (this == null) return;
        FragmentTransaction ft = fragmentManager.beginTransaction();
        Fragment prev = fragmentManager.findFragmentByTag(getTag());
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        show(fragmentManager, getTag());
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(this, tag);
        // 这里吧原来的commit()方法换成了commitAllowingStateLoss()
        ft.commitAllowingStateLoss();
    }


    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        hideSoftInput();
    }

    private void hideSoftInput() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(content.getWindowToken(), 0);
        content.clearFocus();
        Window window = getActivity().getWindow();
        if (window != null) {
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        }

    }



}
