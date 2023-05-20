package Medium.DeFam.app.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Medium.DeFam.app.R;
import Medium.DeFam.app.bean.CommentBean;
import Medium.DeFam.app.bean.WenZhangDetailBean;
import Medium.DeFam.app.common.http.HttpClient;
import Medium.DeFam.app.common.http.JsonBean;
import Medium.DeFam.app.common.http.TradeHttpCallback;
import Medium.DeFam.app.common.utils.AllUtils;
import Medium.DeFam.app.common.utils.GlideUtil;
import Medium.DeFam.app.utils.HttpUtil;

public class TuiJianAdapter extends RecyclerView.Adapter<TuiJianAdapter.ViewHolder> {
    private final List<WenZhangDetailBean> dataList = new ArrayList<>();
    private Context context;
    private RecyclerView recyclerView;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(int pos, int type, WenZhangDetailBean data);
    }

    public void setRecyclerViewOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public TuiJianAdapter(Context context) {
        this.context = context;
    }

    public void replaceAll(List<WenZhangDetailBean> list) {
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
    public void addData(List<WenZhangDetailBean> list) {
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
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.tuijian_item, parent, false));
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WenZhangDetailBean mEntity = dataList.get(position);
        if(null!=mEntity.getImage()&&mEntity.getImage().size()>0){
            holder.img.setVisibility(View.VISIBLE);
            GlideUtil.showImg(context, mEntity.getImage().get(0), holder.img);
        }else {
            holder.img.setVisibility(View.GONE);
        }

        holder.title.setText(mEntity.getTitle());
        holder.created_ats.setText(mEntity.getComments()+"评论 "+AllUtils.getTimeFormatText(mEntity.getCreated_at()));
        holder.tiemview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(0, position, mEntity);
            }
        });
    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title, created_ats;
        LinearLayout tiemview;
        RoundedImageView img;

        public ViewHolder(View view) {
            super(view);
            tiemview = (LinearLayout) view.findViewById(R.id.tiemview);
            img = (RoundedImageView) view.findViewById(R.id.img);

            title = (TextView) view.findViewById(R.id.title);
            created_ats = (TextView) view.findViewById(R.id.created_ats);

        }
    }
}

