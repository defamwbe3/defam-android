package Medium.DeFam.app.common.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.view.WindowManager;

import Medium.DeFam.app.common.utils.ScreenUtils;

public abstract class BaseDialog extends Dialog {

	protected OnKeyClickListener onKeyClickListener;
	public void setOnKeyClickListener(OnKeyClickListener onKeyClickListener) {
		this.onKeyClickListener = onKeyClickListener;
	}

	public BaseDialog(Context context) {
		super(context);
	}

	public BaseDialog(Context context, int theme) {
		super(context, theme);
	}

	protected BaseDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(onKeyClickListener!=null){
			onKeyClickListener.onKeyDown(keyCode, event);
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if(onKeyClickListener!=null){
			onKeyClickListener.onKeyUp(keyCode, event);
		}
		return super.onKeyUp(keyCode, event);
	}

	@Override
	public boolean onKeyLongPress(int keyCode, KeyEvent event) {
		if(onKeyClickListener!=null){
			onKeyClickListener.onKeyLongPress(keyCode, event);
		}
		return super.onKeyLongPress(keyCode, event);
	}

	protected void setWindowWidth(){
		int maxW = ScreenUtils.dp2PxInt(getContext(),350);
		WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
		int w = ScreenUtils.getScreenWidth(getContext());
		int h = ScreenUtils.getScreenHeight(getContext());
		w = Math.min(w,h);
		w = Math.min(w,maxW);
		layoutParams.width = w;
		layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
		getWindow().setAttributes(layoutParams);
	}

}
