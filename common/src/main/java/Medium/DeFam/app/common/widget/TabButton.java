package Medium.DeFam.app.common.widget;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import Medium.DeFam.app.common.R;


/**
 * Created by zmh on 2018/9/21.
 */

public class TabButton extends RelativeLayout {

    private Context mContext;
    private float mScale;
    private int mSelectedIcon;
    private int mUnSelectedIcon;
    private String mTip;
    private int mIconSize;
    private int mTextSize;
    private int mTextColor;
    private int mTextColors;
    private boolean mChecked;
    private ImageView mImg;
    private TextView mText;
    private TextView numText;

    public TabButton(Context context) {
        this(context, null);
    }

    public TabButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mScale = context.getResources().getDisplayMetrics().density;
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TabButton);
        mSelectedIcon = ta.getResourceId(R.styleable.TabButton_tbn_selected_icon, 0);
        mUnSelectedIcon = ta.getResourceId(R.styleable.TabButton_tbn_unselected_icon, 0);
        mTip = ta.getString(R.styleable.TabButton_tbn_tip);
        mIconSize = (int) ta.getDimension(R.styleable.TabButton_tbn_icon_size, 0);
        mTextSize = (int) ta.getDimension(R.styleable.TabButton_tbn_text_size, 0);
        mTextColor = (int) ta.getColor(R.styleable.TabButton_tbn_text_color, 0xff000000);
        mTextColors = (int) ta.getColor(R.styleable.TabButton_tbn_text_colors, 0xff000000);
        mChecked = ta.getBoolean(R.styleable.TabButton_tbn_checked, false);
        ta.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        LinearLayout linearLayout = new LinearLayout(mContext);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setGravity(Gravity.CENTER);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        linearLayout.setLayoutParams(params);

        mImg = new ImageView(mContext);
        LayoutParams params1 = new LayoutParams(mIconSize, mIconSize);
        mImg.setLayoutParams(params1);
        mText = new TextView(mContext);
        LayoutParams params2 = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params2.setMargins(0, dp2px(2), 0, 0);
        mText.setLayoutParams(params2);
        mText.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
        if(TextUtils.isEmpty(mTip)){
            mText.setVisibility(GONE);
        }else {
            mText.setText(mTip);
        }

        if (mChecked) {
            mImg.setImageResource(mSelectedIcon);
            mText.setTextColor(mTextColors);
            mText.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);//加粗
        } else {
            mImg.setImageResource(mUnSelectedIcon);
            mText.setTextColor(mTextColor);
            mText.setTypeface(Typeface.SANS_SERIF, Typeface.NORMAL);//加粗
        }

        numText = new TextView(mContext);
        LayoutParams params3 = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params3.setMargins(0, dp2px(3), dp2px(16), 0);
        params3.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        numText.setTextColor(getResources().getColor(R.color.white));
        numText.setLayoutParams(params3);
     /*   numText.setFilters(new InputFilter[] { new InputFilter.LengthFilter(3) });
        numText.setEllipsize(TextUtils.TruncateAt.END);*/
        numText.setPadding(dp2px(5), 0, dp2px(5), 0);
        numText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
        numText.setVisibility(GONE);
        numText.setBackgroundResource(R.drawable.r_hong20);
        numText.setGravity(Gravity.CENTER);

        addView(numText);
        linearLayout.addView(mImg);
        linearLayout.addView(mText);

        addView(linearLayout);
    }

    public void setNum(String num) {
        if (TextUtils.isEmpty(num)) {
            numText.setVisibility(GONE);
        } else {
            numText.setVisibility(VISIBLE);
            numText.setText(num);
        }

    }

    public void setChecked(boolean checked) {
        mChecked = checked;
        if (checked) {
            mImg.setImageResource(mSelectedIcon);
            mText.setTextColor(mTextColors);
            mText.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);//加粗
        } else {
            mImg.setImageResource(mUnSelectedIcon);
            mText.setTextColor(mTextColor);
            mText.setTypeface(Typeface.SANS_SERIF, Typeface.NORMAL);//加粗
        }
    }

    private int dp2px(int dpVal) {
        return (int) (mScale * dpVal + 0.5f);
    }

}

