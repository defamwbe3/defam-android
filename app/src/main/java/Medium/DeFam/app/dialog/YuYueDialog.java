package Medium.DeFam.app.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import Medium.DeFam.app.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class YuYueDialog extends Dialog {
    private Context mContext;
    private OnNoticeListener mItemSelectListener;
    @BindView(R.id.desc)
    TextView desc;

    public interface OnNoticeListener {
        void setNoticeListener(int id, int position, String data);
    }

    private String after_content;

    public void setItemListener(OnNoticeListener listener) {
        mItemSelectListener = listener;
    }

    public YuYueDialog(@NonNull Context context, String after_content) {
        super(context);
        mContext = context;
        this.after_content = after_content;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_yuyue, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        setCancelable(false);
        setCanceledOnTouchOutside(true);

        Window dialogWindow = getWindow();
        dialogWindow.setWindowAnimations(R.style.dialogAnim);
        dialogWindow.setBackgroundDrawableResource(android.R.color.transparent);
        dialogWindow.setGravity(Gravity.CENTER);
        desc.setText(after_content);


    }

    @OnClick({R.id.quxiao})
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.quxiao) {
            dismiss();
        }
    }

}
