package Medium.DeFam.app.view;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import Medium.DeFam.app.R;
import Medium.DeFam.app.common.utils.AllUtils;

@SuppressLint("AppCompatCustomView")
public class MyTextView extends TextView {
    private boolean mState;
    private Context mContext;
    public MyTextView(Context context) {
        super(context);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }


    private void initView(@NonNull Context context, @Nullable AttributeSet attrs) {
        mContext = context;
        if (null != attrs) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MyTextView);
            mState = a.getBoolean(R.styleable.MyTextView_state, true);
            a.recycle();

        }
        if(mState){
            setMinWidth(AllUtils.dip2px(mContext,20));
            setBackgroundResource(R.drawable.r_huitext);
        }

    }


    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
        if(mState) {
            setBackgroundColor(getResources().getColor(R.color.transparent));
        }
    }
}

