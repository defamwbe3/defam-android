package Medium.DeFam.app.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import Medium.DeFam.app.R;
import Medium.DeFam.app.bean.JiFenDetailBean;
import Medium.DeFam.app.common.utils.AllUtils;
import Medium.DeFam.app.common.widget.flowlayout.FlowLayoutManager;
import Medium.DeFam.app.common.widget.flowlayout.SpaceItemDecoration;

public class ProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public interface OnNoticeListener {
        void setNoticeListener(int id, int position, String data);
    }

    private List<JiFenDetailBean.SkuBean> classifies;
    private Context context;
    private OnNoticeListener onNoticeListener;

    public ProductAdapter(Context context, List<JiFenDetailBean.SkuBean> classifies, OnNoticeListener onNoticeListener) {
        this.context = context;
        this.classifies = classifies;
        this.onNoticeListener = onNoticeListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ProductHolder(View.inflate(context, R.layout.product_item, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ProductHolder productHolder = (ProductHolder) holder;
        JiFenDetailBean.SkuBean classify = classifies.get(position);
        FlowLayoutManager flowLayoutManager = new FlowLayoutManager();
        productHolder.title.setText(classify.getName());
        if (null == productHolder.itemView.getTag()) {
            productHolder.des.addItemDecoration(new SpaceItemDecoration(0, AllUtils.dip2px(context, 8), AllUtils.dip2px(context, 12), AllUtils.dip2px(context, 0)));
            productHolder.itemView.setTag("item");
        }
        productHolder.des.setLayoutManager(flowLayoutManager);
        productHolder.des.setAdapter(new FlowAdapter(classify.getChildren()));
    }

    public String getTitle(int position) {
        return classifies.get(position).getName();
    }

    @Override
    public int getItemCount() {
        return classifies.size();
    }

    class ProductHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private RecyclerView des;

        public ProductHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            des = (RecyclerView) itemView.findViewById(R.id.des);
        }
    }

    class FlowAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private List<JiFenDetailBean.ChildrenBean> list;

        public FlowAdapter(List<JiFenDetailBean.ChildrenBean> list) {
            this.list = list;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyHolder(View.inflate(context, R.layout.jifenitem_item, null));
        }

        @SuppressLint("RecyclerView")
        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            MyHolder myHolder = ((MyHolder) holder);
            JiFenDetailBean.ChildrenBean des = list.get(position);
            myHolder.text.setBackgroundResource(des.isCheck() ? R.drawable.r_lanxian4 : R.drawable.r_xian);
            myHolder.text.setText(des.getName());
            myHolder.text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0, a = list.size(); i < a; i++) {
                        list.get(i).setCheck(false);
                    }
                    list.get(position).setCheck(true);
                    notifyDataSetChanged();
                    onNoticeListener.setNoticeListener(0, position, des.getName());
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class MyHolder extends RecyclerView.ViewHolder {
            private TextView text;

            public MyHolder(View itemView) {
                super(itemView);
                text = (TextView) itemView.findViewById(R.id.flow_text);
            }
        }
    }


}



