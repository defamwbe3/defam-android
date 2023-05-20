package Medium.DeFam.app.adapter;


import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class WelcomeGuideAdapter extends PagerAdapter {
    private List<ImageView> imgs;

    public WelcomeGuideAdapter(List<ImageView> imgs) {
        this.imgs = imgs;
    }

    // item的个数
    @Override
    public int getCount() {
        return imgs.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    // 初始化item布局
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView view = imgs.get(position);
        container.addView(view);
        return view;
    }

    // 销毁item
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(imgs.get(position));
    }


}

