package Medium.DeFam.app.view;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by apple on 2017/10/25.
 */

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    int mSpace;

    /**
     * Retrieve any offsets for the given item. Each field of <code>outRect</code> specifies
     * the number of pixels that the item view should be inset by, similar to padding or margin.
     * The default compile sets the bounds of outRect to 0 and returns.
     * <p>
     * <p>
     * If this ItemDecoration does not affect the positioning of item views, it should set
     * all four fields of <code>outRect</code> (left, top, right, bottom) to zero
     * before returning.
     * <p>
     * <p>
     * If you need to access Adapter for additional data, you can call
     * {@link RecyclerView#getChildAdapterPosition(View)} to get the adapter position of the
     * View.
     *
     * @param outRect Rect to receive the output.
     * @param view    The child view to decorate
     * @param parent  RecyclerView this ItemDecoration is decorating
     * @param state   The current state of RecyclerView.
     */
    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.left = mSpace / 2;
        outRect.right = mSpace / 2;
        outRect.bottom = mSpace / 2;
//        if (parent.getChildAdapterPosition(view) == 0) {
        outRect.top = mSpace / 2;
//        }

    }

    public SpaceItemDecoration(int space) {
        this.mSpace = space;
    }
}
