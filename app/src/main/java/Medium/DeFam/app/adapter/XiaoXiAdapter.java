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
import Medium.DeFam.app.bean.NotifyBean;
import Medium.DeFam.app.common.utils.AllUtils;
import Medium.DeFam.app.common.utils.GlideUtil;


public class XiaoXiAdapter extends RecyclerView.Adapter<XiaoXiAdapter.ViewHolder> {
    private final List<NotifyBean.DataBean> dataList = new ArrayList<>();
    private Context context;
    private RecyclerView recyclerView;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(int pos, int type, NotifyBean.DataBean data);
    }

    public void setRecyclerViewOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public XiaoXiAdapter(Context context) {
        this.context = context;
    }

    public void replaceAll(List<NotifyBean.DataBean> list) {
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
    public void addData(List<NotifyBean.DataBean> list) {
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
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 创建空布局item
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_xiaoxi, parent, false));
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NotifyBean.DataBean data = dataList.get(position);
        holder.itemview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(position, 0, data);
            }
        });
        GlideUtil.showImg(context, data.getTo_member_info().getAvatar(), holder.avatar);
        holder.nickname.setText(data.getTo_member_info().getNickname());
        holder.created_at.setText(AllUtils.getTimeFormatText(data.getTo_member_info().getCreated_at()));
        holder.content.setText(data.getContent());
    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout itemview;
        RoundedImageView avatar;
        TextView nickname, created_at, content;

        public ViewHolder(View view) {
            super(view);
            itemview = view.findViewById(R.id.itemview);
            avatar = view.findViewById(R.id.avatar);
            nickname = view.findViewById(R.id.nickname);
            created_at = view.findViewById(R.id.created_at);
            content = view.findViewById(R.id.content);
        }
    }
}

