package Medium.DeFam.app.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;

import Medium.DeFam.app.R;

public class JiFenDialog extends Dialog {
    private Context mContext;
    private OnNoticeListener mItemSelectListener;
    private String point;
    public interface OnNoticeListener {
        void setNoticeListener(int id, int position, String data);
    }

    public void setItemListener(OnNoticeListener listener) {
        mItemSelectListener = listener;
    }

    public JiFenDialog(@NonNull Context context,String point) {
        super(context);
        mContext = context;
        this.point = point;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_jifen);
        setCancelable(false);
        setCanceledOnTouchOutside(true);

        Window dialogWindow = getWindow();
        dialogWindow.setWindowAnimations(R.style.dialogAnim);
        dialogWindow.setBackgroundDrawableResource(android.R.color.transparent);
        dialogWindow.setGravity(Gravity.CENTER);
        findViewById(R.id.quxiao).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        TextView jifennum = findViewById(R.id.jifennum);
        jifennum.setText(point);
        findViewById(R.id.renzheng).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }


}
