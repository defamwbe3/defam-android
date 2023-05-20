package Medium.DeFam.app.common.widget.flowlayout;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by zmh on 17/8/22.
 */

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int top,left,right,bottom;

    public SpaceItemDecoration(int left,int top,int right,int bottom) {
        this.top = top;
        this.left = left;
        this.right = right;
        this.bottom = bottom;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.top = top;
        outRect.left = left;
        outRect.right = right;
        outRect.bottom = bottom;
    }
}