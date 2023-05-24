package Medium.DeFam.app.view.recycle;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int horSpace=-1,verSpace=-1;

    public SpaceItemDecoration(int horSpace,int verSpace) {
        this.horSpace = (int) Math.round(horSpace/2.0);;
        this.verSpace = (int) Math.round(verSpace/2.0);;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        //设置左右的间隔如果想设置的话自行设置，我这用不到就注释掉了
        /*if(parent.getChildAdapterPosition(view)==0){
            outRect.left = 0;
        }else{
            outRect.left = horSpace;
        }*/
        outRect.left = horSpace;
        outRect.right = horSpace;
        outRect.top = verSpace;
        outRect.bottom = verSpace;
    }
}
