package Medium.DeFam.app.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import Medium.DeFam.app.R;
import Medium.DeFam.app.bean.KOLBean;



public class ExpandKOLView1 extends LinearLayout {
    private final int DEFAULT_MAX_LINES = 3;//最大的行数
    private final String STATE_UNKNOW = "";//未知状态
    private final String STATE_NOT_OVERFLOW = "1";//文本行数小于最大可显示行数
    private final String STATE_COLLAPSED = "2";//折叠状态
    private final String STATE_EXPANDED = "3";//展开状态
    private TextView contentText;
    private TextView textState;
    private KOLBean.DataBean alldata;
    private int showLines;


    public ExpandKOLView1(Context context) {
        super(context);
        initView();
    }

    public ExpandKOLView1(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
        initView();
    }

    public ExpandKOLView1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
        initView();
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.ExpandTextView, 0, 0);
        try {
            showLines = typedArray.getInt(R.styleable.ExpandTextView_showLines, DEFAULT_MAX_LINES);
        } finally {
            typedArray.recycle();
        }
    }

    private void initView() {
        setOrientation(LinearLayout.VERTICAL);
        LayoutInflater.from(getContext()).inflate(R.layout.layout_expand_text, this);
        contentText = (TextView) findViewById(R.id.contentText);
        textState = (TextView) findViewById(R.id.textState);
        textState.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (alldata.getMaintextstate().equals(STATE_COLLAPSED)) {
                    contentText.setMaxLines(Integer.MAX_VALUE);
                    textState.setText("收起");
                    alldata.setMaintextstate(STATE_EXPANDED);
                } else if (alldata.getMaintextstate().equals(STATE_EXPANDED)) {
                    contentText.setMaxLines(showLines);
                    textState.setText("全文");
                    alldata.setMaintextstate(STATE_COLLAPSED);
                }

            }
        });
    }


    public void setText(KOLBean.DataBean data) {
        alldata = data;
        if (TextUtils.isEmpty(alldata.getMaintextstate())) {
            contentText.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    // 避免重复监听
                    contentText.getViewTreeObserver().removeOnPreDrawListener(this);
                    if (contentText.getLineCount() > showLines) {
                        contentText.setMaxLines(showLines);
                        textState.setText("全文");
                        textState.setVisibility(View.VISIBLE);
                        alldata.setMaintextstate(STATE_COLLAPSED);
                    } else {
                        textState.setVisibility(View.GONE);
                        alldata.setMaintextstate(STATE_NOT_OVERFLOW);
                    }
                    return true;
                }

            });
            contentText.setMaxLines(Integer.MAX_VALUE);

            if(alldata.getMain_text()!=null){
                contentText.setText(alldata.getMain_text().getFull_text());
            }else{
                contentText.setText("");
            }

        } else {
            //如果之前已经初始化过了，则使用保存的状态。
            switch (alldata.getMaintextstate()) {
                case STATE_NOT_OVERFLOW:
                    textState.setVisibility(View.GONE);
                    break;
                case STATE_COLLAPSED:
                    contentText.setMaxLines(showLines);
                    textState.setVisibility(View.VISIBLE);
                    textState.setText("全文");
                    break;
                case STATE_EXPANDED:
                    contentText.setMaxLines(Integer.MAX_VALUE);
                    textState.setVisibility(View.VISIBLE);
                    textState.setText("收起");
                    break;
            }

            contentText.setText(alldata.getMain_text().getFull_text());

        }

    }

}
