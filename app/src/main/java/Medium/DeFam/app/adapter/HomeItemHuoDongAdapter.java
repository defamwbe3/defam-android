package Medium.DeFam.app.adapter;

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
import Medium.DeFam.app.bean.HuoDongBean;
import Medium.DeFam.app.bean.HuoDongDetailBean;
import Medium.DeFam.app.common.utils.GlideUtil;


public class HomeItemHuoDongAdapter extends RecyclerView.Adapter<HomeItemHuoDongAdapter.ViewHolder> {
    private final List<HuoDongDetailBean> dataList = new ArrayList<>();
    private Context context;
    private RecyclerView recyclerView;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(int pos, int type, HuoDongDetailBean data);
    }

    public void setRecyclerViewOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public HomeItemHuoDongAdapter(Context context) {
        this.context = context;
    }

    public void replaceAll(List<HuoDongDetailBean> list) {
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
    public void addData( List<HuoDongDetailBean> list) {
        dataList.addAll( list);
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
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 创建空布局item
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_homeitemhuodong, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ViewHolder vh = (ViewHolder) holder;
        HuoDongDetailBean data = dataList.get(position);
        vh.itemview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(position,0,data);
            }
        });
        GlideUtil.showImg(context,data.getImage(),vh.image);
        vh.title.setText(data.getTitle());
        vh.start_time.setText(data.getStart_time()+" ~ "+data.getEnd_time());
        vh.number.setText("限"+data.getNumber()+"人报名参加");
        vh.registered_number.setText("已报名："+data.getRegistered_number());
        holder.status_text.setText(data.getStatus_text());
    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout itemview;
        RoundedImageView image;
        TextView title,start_time,number,registered_number,status_text;
        public ViewHolder(View view) {
            super(view);
            itemview = view.findViewById(R.id.itemview);
            image = view.findViewById(R.id.image);
            title = view.findViewById(R.id.title);
            start_time = view.findViewById(R.id.start_time);
            number = view.findViewById(R.id.number);
            registered_number = view.findViewById(R.id.registered_number);
            status_text = view.findViewById(R.id.status_text);
        }
    }
}

