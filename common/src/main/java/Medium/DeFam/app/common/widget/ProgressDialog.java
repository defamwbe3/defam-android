package Medium.DeFam.app.common.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import Medium.DeFam.app.common.R;

/**
 * 进度等待框
 */
public class ProgressDialog extends BaseDialog {
	
	private Context context;
	private TextView msgTv;
	private ProgressBar pb;
	
	public ProgressDialog(Context context) {
		this(context,"");
	}
	
	public ProgressDialog(Context context, String msg) {
		this(context,"", R.style.libdroid_dialog_theme);
	}
	
	public ProgressDialog(Context context, String msg, int style) {
		super(context, style);
		this.context = context;
		init(msg);
	}
	
	void init(String msg){
		this.setCancelable(false);
		LayoutInflater inflater = (LayoutInflater)context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.xind_dialog_progress, null);
		msgTv = view.findViewById(R.id.ui_progress_tv);
		setMsg(msg);
		pb = view.findViewById(R.id.ui_progress_pb);
		this.setContentView(view);
	}
	
	/**
	 * 设置展示信息
	 * @param msg
	 */
	public void setMsg(String msg){
		if(TextUtils.isEmpty(msg)){
			msgTv.setVisibility(View.GONE);
		}else{
			msgTv.setVisibility(View.VISIBLE);
			msgTv.setText(msg);
		}
	}
	
	/**
	 * 设置文字大小
	 * @param textSize
	 */
	public void setTextSize(int textSize){
		msgTv.setTextSize(TypedValue.COMPLEX_UNIT_SP,textSize);
	}
	
	/**
	 * 设置圆圈的样式
	 * @param indeterminateDrawable
	 */
	public void setIndeterminateDrawable(Drawable indeterminateDrawable){
		pb.setIndeterminateDrawable(indeterminateDrawable);
	}
	
}
