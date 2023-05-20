package Medium.DeFam.app.common.widget;


import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class NoAutoSlideScrollView extends ScrollView {
    public NoAutoSlideScrollView(Context context) {
        super(context);
    }

    public NoAutoSlideScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoAutoSlideScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int computeScrollDeltaToGetChildRectOnScreen(Rect rect) {
        return 0;
    }

}
