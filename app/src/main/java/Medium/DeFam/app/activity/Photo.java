package Medium.DeFam.app.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;
import java.util.List;

import Medium.DeFam.app.R;
import Medium.DeFam.app.common.base.BaseActivity;
import Medium.DeFam.app.common.them.Eyes;
import Medium.DeFam.app.common.utils.GlideUtil;
import Medium.DeFam.app.common.widget.zoom.PhotoView;
import Medium.DeFam.app.common.widget.zoom.PhotoViewAttacher;
import Medium.DeFam.app.common.widget.zoom.ViewPagerFixed;
import butterknife.BindView;

public class Photo extends BaseActivity {

    private ArrayList<View> listViews = new ArrayList<View>();
    @BindView(R.id.gallery)
    ViewPagerFixed pager;
    private MyPageAdapter adapter;
    private List<String> tempSelectBitmap = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_photo;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState, @Nullable Bundle bundle) {
        findViewById(R.id.topview).setPadding(0, Eyes.getStatusBarHeight(this), 0, 0);
        findViewById(R.id.bancTextView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        tempSelectBitmap = (List<String>) intent.getSerializableExtra("list");
        if (null != tempSelectBitmap && tempSelectBitmap.size() > 0) {
            for (int i = 0, a = tempSelectBitmap.size(); i < a; i++) {
                PhotoView img = new PhotoView(this);
                GlideUtil.showImg(this, tempSelectBitmap.get(i), img);
                img.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
                img.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
                    @Override
                    public void onViewTap(View view, float x, float y) {
                        finish();
                    }
                });
                listViews.add(img);
            }
        } else {
            finish();
        }
        adapter = new MyPageAdapter(listViews);
        pager.setAdapter(adapter);
        pager.setPageMargin((int) getResources().getDimensionPixelOffset(R.dimen.dp_10));
        pager.setCurrentItem(intent.getIntExtra("position", 0));
    }


    private class MyPageAdapter extends PagerAdapter {
        private ArrayList<View> listViews;
        private int size;

        public MyPageAdapter(ArrayList<View> listViews) {
            this.listViews = listViews;
            size = listViews == null ? 0 : listViews.size();
        }

        public void setListViews(ArrayList<View> listViews) {
            this.listViews = listViews;
            size = listViews == null ? 0 : listViews.size();
        }

        @Override
        public int getCount() {
            return size;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPagerFixed) arg0).removeView(listViews.get(arg1 % size));
        }

        @Override
        public void finishUpdate(View arg0) {
        }

        @Override
        public Object instantiateItem(View arg0, int arg1) {
            try {
                ((ViewPagerFixed) arg0).addView(listViews.get(arg1 % size), 0);
            } catch (Exception e) {
            }
            return listViews.get(arg1 % size);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }
    }
}




