package Medium.DeFam.app.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import Medium.DeFam.app.R;
import Medium.DeFam.app.bean.ZiXunBean;
import Medium.DeFam.app.bean.ZiXunDetailBean;
import Medium.DeFam.app.common.utils.AllUtils;
import Medium.DeFam.app.common.utils.GlideUtil;

public class HomeItemAdapter extends RecyclerView.Adapter {
    private final List<ZiXunDetailBean> dataList = new ArrayList<>();
    private Context context;
    private RecyclerView recyclerView;
    private final int TYPE_ITEM = 0;
    private final int TYPE_ITEM1 = 1;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(int pos, int type, ZiXunDetailBean data);
    }

    public void setRecyclerViewOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public HomeItemAdapter(Context context) {
        this.context = context;
    }

    public void replaceAll(List<ZiXunDetailBean> list) {
        dataList.clear();
        if (list != null && list.size() > 0) {
            dataList.addAll(list);
        }
        notifyDataSetChanged();
        //recyclerView.scrollToPosition(0);
    }

    /**
     * 插入数据使用notifyItemInserted，如果要使用插入动画，必须使用notifyItemInserted
     * 才会有效果。即便不需要使用插入动画，也建议使用notifyItemInserted方式添加数据，
     * 不然容易出现闪动和间距错乱的问题
     */
    public void addData(List<ZiXunDetailBean> list) {
        dataList.addAll(list);
        notifyDataSetChanged();
    }

    //移除数据使用notifyItemRemoved
    public void removeData(int position) {
        dataList.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                //如果快速滑动， 不加载图片
                if (newState == 2) {
                    Glide.with(context).pauseRequests();
                } else {
                    Glide.with(context).resumeRequests();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            }
        });
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            // 创建空布局item
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_homeitem, parent, false));
        } else {
            return new ViewHolder1(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_homeitem1, parent, false));
        }
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ZiXunDetailBean data = dataList.get(position);
        switch (getItemViewType(position)) {
            case TYPE_ITEM:
                ViewHolder vh = (ViewHolder) holder;
                vh.itemview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickListener.onItemClick(position, 0, data);
                    }
                });
                vh.title.setText(data.getTitle());
                vh.is_top.setVisibility("1".equals(data.getIs_top()) ? View.VISIBLE : View.GONE);
                vh.comments.setText(data.getComments() + "评论");
                vh.created_at.setText(AllUtils.getTimeFormatText(data.getCreated_at()));
                if (null != data.getImage_text() && data.getImage_text().size() > 0) {
                    vh.img.setVisibility(View.VISIBLE);
                    GlideUtil.showImg(context, data.getImage_text().get(0), vh.img);
                } else {
                    vh.img.setVisibility(View.GONE);
                }

                break;
            case TYPE_ITEM1:
                ViewHolder1 vh1 = (ViewHolder1) holder;
                vh1.itemview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickListener.onItemClick(position, 0, data);
                    }
                });
                vh1.title.setText(data.getTitle());
                vh1.is_top.setVisibility("1".equals(data.getIs_top()) ? View.VISIBLE : View.GONE);
                vh1.comments.setText(data.getComments() + "评论");
                vh1.created_at.setText(AllUtils.getTimeFormatText(data.getCreated_at()));
                vh1.img.setVisibility(View.INVISIBLE);
                vh1.img1.setVisibility(View.INVISIBLE);
                vh1.img2.setVisibility(View.INVISIBLE);
                if (null != data.getImage_text() && data.getImage_text().size() > 0) {
                    vh1.img.setVisibility(View.VISIBLE);
                    GlideUtil.showImg(context, data.getImage_text().get(0), vh1.img);
                }
                if (null != data.getImage_text() && data.getImage_text().size() > 1) {
                    vh1.img1.setVisibility(View.VISIBLE);
                    GlideUtil.showImg(context, data.getImage_text().get(1), vh1.img1);
                }
                if (null != data.getImage_text() && data.getImage_text().size() > 2) {
                    vh1.img2.setVisibility(View.VISIBLE);
                    GlideUtil.showImg(context, data.getImage_text().get(2), vh1.img2);
                }
                break;
        }
    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (null == dataList.get(position).getImage_text() || dataList.get(position).getImage_text().size() <= 1) {
            return TYPE_ITEM;
        } else {
            return TYPE_ITEM1;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout itemview;
        TextView title, is_top, comments, created_at;
        RoundedImageView img;

        public ViewHolder(View view) {
            super(view);
            itemview = view.findViewById(R.id.itemview);
            title = view.findViewById(R.id.title);
            is_top = view.findViewById(R.id.is_top);
            comments = view.findViewById(R.id.comments);
            created_at = view.findViewById(R.id.created_at);
            img = view.findViewById(R.id.img);
        }
    }

    public class ViewHolder1 extends RecyclerView.ViewHolder {
        LinearLayout itemview;
        TextView title, is_top, comments, created_at;
        RoundedImageView img, img1, img2;

        public ViewHolder1(View view) {
            super(view);
            itemview = view.findViewById(R.id.itemview);
            title = view.findViewById(R.id.title);
            is_top = view.findViewById(R.id.is_top);
            comments = view.findViewById(R.id.comments);
            created_at = view.findViewById(R.id.created_at);
            img = view.findViewById(R.id.img);
            img1 = view.findViewById(R.id.img1);
            img2 = view.findViewById(R.id.img2);
        }
    }
}

