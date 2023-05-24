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
import Medium.DeFam.app.bean.FenSiBean;
import Medium.DeFam.app.common.http.HttpClient;
import Medium.DeFam.app.common.http.JsonBean;
import Medium.DeFam.app.common.http.TradeHttpCallback;
import Medium.DeFam.app.common.utils.AllUtils;
import Medium.DeFam.app.common.utils.GlideUtil;
import Medium.DeFam.app.common.utils.UserUtil;
import Medium.DeFam.app.utils.HttpUtil;


public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    private final List<CommentBean.DataBean> dataList = new ArrayList<>();
    private Context context;
    private RecyclerView recyclerView;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(int pos, int type, CommentBean.DataBean data);
    }

    public void setRecyclerViewOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public CommentAdapter(Context context) {
        this.context = context;
    }

    public void replaceAll(List<CommentBean.DataBean> list) {
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
    public void addData(List<CommentBean.DataBean> list) {
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
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.zixuanping_item, parent, false));
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CommentBean.DataBean mEntity = dataList.get(position);
        if(mEntity.getMember()!=null){
            GlideUtil.showImg(context, mEntity.getMember().getAvatar(), holder.avatar);
            holder.nickname.setText(mEntity.getMember().getNickname());
        }
        holder.created_ats.setText(AllUtils.getTimeFormatText(mEntity.getCreated_at()));
        holder.good.setText(mEntity.getGood());
        holder.is_good.setImageResource("0".equals(mEntity.getIs_good()) ? R.mipmap.img29 : R.mipmap.img30);
        holder.contentmy.setText(mEntity.getContent());
        holder.comment.setText(mEntity.getComment() + "回复");
        if (null == mEntity.getChildren() || mEntity.getChildren().size() == 0) {
            holder.children.setVisibility(View.GONE);
        } else {
            holder.children.setVisibility(View.VISIBLE);
            holder.children.setText(mEntity.getChildren().get(0).getMember().getNickname() + "：" + mEntity.getChildren().get(0).getContent());
        }
        holder.is_good.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> map = new HashMap<>();
                map.put("type", "1");
                map.put("id", mEntity.getId());
                HttpClient.getInstance().gets(HttpUtil.APICOMMENTDOLIKE, map, new TradeHttpCallback<JsonBean<String>>() {
                    @Override
                    public void onSuccess(JsonBean<String> data) {
                        if (null == data || null == data.getData()) {
                            return;
                        }
                        if ("0".equals(mEntity.getIs_good())) {
                            mEntity.setIs_good("1");
                            int num = Integer.valueOf(mEntity.getGood());
                            num++;
                            mEntity.setGood(String.valueOf(num));
                            notifyDataSetChanged();
                        } else {
                            mEntity.setIs_good("0");
                            int num = Integer.valueOf(mEntity.getGood());
                            num--;
                            mEntity.setGood(String.valueOf(num));
                            notifyDataSetChanged();
                        }

                    }

                });
            }
        });
        holder.huifu.setOnClickListener(new View.OnClickListener() {
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
        private TextView nickname, created_ats, good, contentmy, comment, children;
        ImageView is_good;
        LinearLayout huifu;
        RoundedImageView avatar;

        public ViewHolder(View view) {
            super(view);

            avatar = (RoundedImageView) view.findViewById(R.id.avatar);
            nickname = (TextView) view.findViewById(R.id.nickname);
            created_ats = (TextView) view.findViewById(R.id.created_ats);
            good = (TextView) view.findViewById(R.id.good);
            is_good = (ImageView) view.findViewById(R.id.is_good);
            contentmy = (TextView) view.findViewById(R.id.contentmy);
            comment = (TextView) view.findViewById(R.id.comment);
            children = (TextView) view.findViewById(R.id.children);
            huifu = (LinearLayout) view.findViewById(R.id.huifu);
        }
    }
}

