package Medium.DeFam.app.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebView;

import androidx.core.view.ViewCompat;

import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.MaterialHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshFooter;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.constant.RefreshState;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshFooterCreator;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshHeaderCreator;

public class SmartRefreshView extends SmartRefreshLayout {
    public SmartRefreshView(Context context) {
        this(context,null);
    }

    public SmartRefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setEnableAutoLoadMore(false);
        setEnableLoadMoreWhenContentNotFull(false);//取消内容不满一页时开启上拉加载功能
    }

    public void complete(){
        if(mState== RefreshState.Loading){
            finishLoadMore();
        }else{
            finishRefresh();
        }
    }

    public boolean isRefreshEnable(){
        return mEnableRefresh;
    }

    public boolean canScrollVertically(int direction){
        View mTargetView = getChildAt(0);
        if (mTargetView instanceof WebView) {
            return direction < 0 ? mTargetView.getScrollY() > 0
                    : mTargetView.getScrollY() < mTargetView
                    .getMeasuredHeight();
        } else {
            return ViewCompat.canScrollVertically(mTargetView, direction);
        }
    }

}
