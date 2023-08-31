package Medium.DeFam.app.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import Medium.DeFam.app.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class AllDialog extends Dialog {
    private Context mContext;
    private OnNoticeListener mItemSelectListener;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.desc)
    TextView desc;
    @BindView(R.id.ok)
    Button okBtn;

    public interface OnNoticeListener {
        void setNoticeListener(int id, int position, String data);
    }

    private String mydesc, mytitle;
    private String okStr;

    public void setItemListener(OnNoticeListener listener) {
        mItemSelectListener = listener;
    }

    public AllDialog(@NonNull Context context, String title, String desc) {
        super(context);
        mContext = context;
        this.mytitle = title;
        this.mydesc = desc;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_all, null);
        setContentView(view);
        setCancelable(false);
        setCanceledOnTouchOutside(true);

        Window dialogWindow = getWindow();
        dialogWindow.setWindowAnimations(R.style.dialogAnim);
        dialogWindow.setBackgroundDrawableResource(android.R.color.transparent);
        dialogWindow.setGravity(Gravity.CENTER);

        ButterKnife.bind(this, view);
        initData();
    }

    private void initData() {
        title.setText(mytitle);
        desc.setText(mydesc);
        if(!TextUtils.isEmpty(okStr)){
            okBtn.setText(okStr);
        }
    }

    public void seOkBtnText(String str){
        this.okStr = str;
        if(okBtn!=null && !TextUtils.isEmpty(okStr)){
            okBtn.setText(okStr);
        }
    }

    @OnClick({R.id.quxiao, R.id.ok})
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.quxiao) {
            dismiss();
        } else if (id == R.id.ok) {
            dismiss();
            mItemSelectListener.setNoticeListener(0,0,"");
        }
    }
}
