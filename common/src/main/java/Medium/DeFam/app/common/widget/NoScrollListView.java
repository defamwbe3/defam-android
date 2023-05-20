package Medium.DeFam.app.common.widget;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by MacBook on 17/3/10.
 */

public class NoScrollListView extends ListView {
    public NoScrollListView(Context context) {
        super(context);

    }
    public NoScrollListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
