package Medium.DeFam.app.adapter;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

import android.content.Context;
import android.text.TextUtils;
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
import java.util.List;

import Medium.DeFam.app.R;
import Medium.DeFam.app.bean.WenZhangDetailBean;
import Medium.DeFam.app.common.utils.AllUtils;
import Medium.DeFam.app.common.utils.GlideUtil;
import Medium.DeFam.app.view.ExpandTextView;
import me.jessyan.autosize.utils.AutoSizeUtils;

public class HomeItemKolAdapter extends RecyclerView.Adapter<HomeItemKolAdapter.ViewHolder> {
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

    public HomeItemKolAdapter(Context context) {
        this.context = context;
    }

    public void replaceAll(List<WenZhangDetailBean> list) {
        dataList.clear();
        if (list != null && list.size() > 0) {
            dataList.addAll(list);
        }
        notifyDataSetChanged();
        // recyclerView.scrollToPosition(0);
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
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_homeitemkol, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WenZhangDetailBean data = dataList.get(position);
        holder.itemview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(position, 0, data);
            }
        });
        if (TextUtils.isEmpty(data.getSubtitle())) {
            holder.expandTextView.setVisibility(View.GONE);
        } else {
            holder.expandTextView.setVisibility(View.VISIBLE);
        }
        holder.expandTextView.setText(data);
        GlideUtil.showImg(context, data.getMember().getAvatar(), holder.avatar);
        holder.nickname.setText(data.getMember().getNickname());
        holder.created_at.setText(AllUtils.getTimeFormatText(data.getCreated_at()));
        holder.is_follow.setImageResource("0".equals(data.getIs_follow()) ? R.mipmap.img14 : R.mipmap.img15);
        holder.imgview.setVisibility(View.GONE);
        holder.img.setVisibility(View.INVISIBLE);
        holder.img1.setVisibility(View.INVISIBLE);
        holder.img2.setVisibility(View.INVISIBLE);
        if (null != data.getImage_text() && data.getImage_text().size() > 2) {
            holder.imgview.setVisibility(View.VISIBLE);
            holder.img.setVisibility(View.VISIBLE);
            holder.img1.setVisibility(View.VISIBLE);
            holder.img2.setVisibility(View.VISIBLE);
            GlideUtil.showImg(context, data.getImage_text().get(0), holder.img);
            GlideUtil.showImg(context, data.getImage_text().get(1), holder.img1);
            GlideUtil.showImg(context, data.getImage_text().get(2), holder.img2);

        } else if (null != data.getImage_text() && data.getImage_text().size() > 1) {
            holder.imgview.setVisibility(View.VISIBLE);
            holder.img.setVisibility(View.VISIBLE);
            holder.img1.setVisibility(View.VISIBLE);
            holder.img2.setVisibility(View.INVISIBLE);
            GlideUtil.showImg(context, data.getImage_text().get(0), holder.img);
            GlideUtil.showImg(context, data.getImage_text().get(1), holder.img1);

        } else if (null != data.getImage_text() && data.getImage_text().size() > 0) {
            holder.imgview.setVisibility(View.VISIBLE);
            holder.img.setVisibility(View.VISIBLE);
            holder.img1.setVisibility(View.INVISIBLE);
            holder.img2.setVisibility(View.INVISIBLE);
            GlideUtil.showImg(context, data.getImage_text().get(0), holder.img);

        }
        holder.comments.setText(data.getComments());
        holder.good.setText(data.getGood());
    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout imgview;
        LinearLayout itemview;
        ExpandTextView expandTextView;
        RoundedImageView avatar, img, img1, img2;
        TextView nickname, created_at, good, comments;
        ImageView is_follow;

        public ViewHolder(View view) {
            super(view);
            imgview = (LinearLayout) itemView.findViewById(R.id.imgview);
            ViewGroup.LayoutParams lp = imgview.getLayoutParams();
            lp.width = MATCH_PARENT;
            lp.height = (int) (Double.valueOf(AutoSizeUtils.dp2px(context, 298)) / 3);
            imgview.setLayoutParams(lp);
            expandTextView = itemView.findViewById(R.id.expand_textView);
            itemview = view.findViewById(R.id.itemview);
            avatar = view.findViewById(R.id.avatar);
            nickname = view.findViewById(R.id.nickname);
            created_at = view.findViewById(R.id.created_at);
            is_follow = view.findViewById(R.id.is_follow);
            img = view.findViewById(R.id.img);
            img1 = view.findViewById(R.id.img1);
            img2 = view.findViewById(R.id.img2);
            comments = view.findViewById(R.id.comments);
            good = view.findViewById(R.id.good);
        }
    }
}

