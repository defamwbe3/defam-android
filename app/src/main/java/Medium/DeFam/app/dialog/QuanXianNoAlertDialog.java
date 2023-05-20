package Medium.DeFam.app.dialog;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import androidx.annotation.NonNull;

import Medium.DeFam.app.R;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QuanXianNoAlertDialog extends Dialog {
    private Context mContext;
    private OnNoticeListener mItemSelectListener;

    public interface OnNoticeListener {
        void setNoticeListener(int id, int position, String data);
    }

    public void setItemListener(OnNoticeListener listener) {
        mItemSelectListener = listener;
    }

    public QuanXianNoAlertDialog(@NonNull Context context) {
        super(context);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_quanxianno, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        setCancelable(false);
        setCanceledOnTouchOutside(false);

        Window dialogWindow = getWindow();
        dialogWindow.setWindowAnimations(R.style.dialogAnim);
        dialogWindow.setBackgroundDrawableResource(android.R.color.transparent);
        dialogWindow.setGravity(Gravity.CENTER);

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
}

