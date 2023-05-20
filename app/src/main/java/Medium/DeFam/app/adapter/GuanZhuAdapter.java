package Medium.DeFam.app.adapter;

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
import Medium.DeFam.app.bean.FenSiBean;
import Medium.DeFam.app.bean.GuanZhuBean;
import Medium.DeFam.app.common.http.HttpClient;
import Medium.DeFam.app.common.http.JsonBean;
import Medium.DeFam.app.common.http.TradeHttpCallback;
import Medium.DeFam.app.common.utils.GlideUtil;
import Medium.DeFam.app.common.utils.UserUtil;
import Medium.DeFam.app.utils.HttpUtil;


public class GuanZhuAdapter extends RecyclerView.Adapter<GuanZhuAdapter.ViewHolder> {
    private final List<GuanZhuBean.DataBean> dataList = new ArrayList<>();
    private Context context;
    private RecyclerView recyclerView;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(int pos, int type, GuanZhuBean.DataBean data);
    }

    public void setRecyclerViewOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public GuanZhuAdapter(Context context) {
        this.context = context;
    }

    public void replaceAll(List<GuanZhuBean.DataBean> list) {
        dataList.clear();
        if (list != null && list.size() > 0) {
            dataList.addAll(list);
        }
        notifyDataSetChanged();
        recyclerView.scrollToPosition(0);
    }

    /**
     * 插入数据使用notifyItemInserted，如果要使用插入动画，必须使用notifyItemInserted
     * 才会有效果。即便不需要使用插入动画，也建议使用notifyItemInserted方式添加数据，
     * 不然容易出现闪动和间距错乱的问题
     */
    public void addData(List<GuanZhuBean.DataBean> list) {
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
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_guanzhu, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GuanZhuBean.DataBean data = dataList.get(position);
        holder.itemview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(position, 0, data);
            }
        });
        holder.is_gz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null == data.getTomember()) {
                    return;
                }
                Map<String, String> map = new HashMap<>();
                map.put("to_member_id", data.getTomember().getId());
                map.put("type", "1");
                map.put("member_id", UserUtil.getUserBean().getId());
                HttpClient.getInstance().gets(HttpUtil.ISFOLLOWMEMBER, map, new TradeHttpCallback<JsonBean<String>>() {
                    @Override
                    public void onSuccess(JsonBean<String> urldata) {
                        if (null == urldata || null == urldata.getData()) {
                            return;
                        }
                        //data.setIs_gz("0".equals(data.getIs_gz()) ? "1" : "0");
                        dataList.remove(position);
                        notifyDataSetChanged();
                    }

                    @Override
                    public boolean showLoadingDialog() {
                        return true;
                    }
                });
            }
        });
        if (null != data.getTomember()) {
            GlideUtil.showImg(context, data.getTomember().getAvatar(), holder.avatar);
            holder.nickname.setText(data.getTomember().getNickname());
            holder.article_num.setText("作品："+data.getTomember().getArticle_num());
        }

        //holder.is_gz.setImageResource("0".equals(data.getIs_gz()) ? R.mipmap.img15 : R.mipmap.img14);
    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout itemview;
        RoundedImageView avatar;
        TextView nickname, article_num;
        ImageView is_gz;

        public ViewHolder(View view) {
            super(view);
            itemview = view.findViewById(R.id.itemview);
            avatar = view.findViewById(R.id.avatar);
            nickname = view.findViewById(R.id.nickname);
            article_num = view.findViewById(R.id.article_num);
            is_gz = view.findViewById(R.id.is_gz);
        }
    }
}

