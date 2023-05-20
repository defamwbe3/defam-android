package Medium.DeFam.app.common.utils;

import static android.content.Context.INPUT_METHOD_SERVICE;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import Medium.DeFam.app.common.CommonAppContext;

public class KeyboardUtils {

    /**
     * Hide the soft input.
     *
     * @param activity The activity.
     */
    public static void hideSoftInput(final Activity activity) {
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        hideSoftInput(view);
    }
    /**
     * Hide the soft input.
     *
     * @param view The view.
     */
    public static void hideSoftInput(final View view) {
        InputMethodManager imm =
                (InputMethodManager) CommonAppContext.getContext().getSystemService(INPUT_METHOD_SERVICE);
        if (imm == null) return;
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0, new ResultReceiver(new Handler()) {
            @Override
            protected void onReceiveResult(int resultCode, Bundle resultData) {
                if (resultCode == InputMethodManager.RESULT_UNCHANGED_SHOWN
                        || resultCode == InputMethodManager.RESULT_SHOWN) {
                    toggleSoftInput();
                }
            }
        });
    }
    /**
     * Toggle the soft input display or not.
     */
    public static void toggleSoftInput() {
        InputMethodManager imm =
                (InputMethodManager) CommonAppContext.getContext().getSystemService(INPUT_METHOD_SERVICE);
        if (imm == null) return;
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }
}
