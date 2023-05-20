package Medium.DeFam.app.common.utils;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.widget.TextView;

import Medium.DeFam.app.common.R;


public class DialogUitl {
    public static Dialog loadingDialog(Context context) {
        return loadingDialog(context, "");
    }

    /**
     * 用于网络请求等耗时操作的LoadingDialog
     */
    public static Dialog loadingDialog(Context context, String text) {
        Dialog dialog = new Dialog(context, R.style.dialog);
        dialog.setContentView(R.layout.dialog_loading);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        if (!TextUtils.isEmpty(text)) {
            TextView titleView = (TextView) dialog.findViewById(R.id.text);
            if (titleView != null) {
                titleView.setText(text);
            }
        }
        return dialog;
    }
}
