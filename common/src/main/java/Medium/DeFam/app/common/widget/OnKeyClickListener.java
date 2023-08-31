package Medium.DeFam.app.common.widget;

import android.view.KeyEvent;

public interface OnKeyClickListener {
	
	public boolean onKeyDown(int keyCode, KeyEvent event);
	
	public boolean onKeyUp(int keyCode, KeyEvent event);
	
	public boolean onKeyLongPress(int keyCode, KeyEvent event);
}
