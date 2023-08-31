package Medium.DeFam.app.adapter;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Medium.DeFam.app.R;
import Medium.DeFam.app.activity.Photo;
import Medium.DeFam.app.bean.JiangLiBean;
import Medium.DeFam.app.bean.WenZhangDetailBean;
import Medium.DeFam.app.common.Constants;
import Medium.DeFam.app.common.bean.MessageEvent;
import Medium.DeFam.app.common.http.HttpClient;
import Medium.DeFam.app.common.http.JsonBean;
import Medium.DeFam.app.common.http.TradeHttpCallback;
import Medium.DeFam.app.common.utils.AllUtils;
import Medium.DeFam.app.common.utils.GlideUtil;
import Medium.DeFam.app.common.utils.UserUtil;
import Medium.DeFam.app.dialog.AllDialog;
import Medium.DeFam.app.dialog.JiFenDialog;
import Medium.DeFam.app.utils.HttpUtil;
import Medium.DeFam.app.view.ExpandTextView;
import me.jessyan.autosize.utils.AutoSizeUtils;


public class ZhuYeAdapter extends RecyclerView.Adapter<ZhuYeAdapter.ViewHolder> {
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

    public ZhuYeAdapter(Context context) {
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
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_zhuye, parent, false));
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WenZhangDetailBean data = dataList.get(position);
        holder.itemview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(position, 0, data);
            }
        });
        if (TextUtils.isEmpty(data.getContent())) {
            holder.expandTextView.setVisibility(View.GONE);
        } else {
            holder.expandTextView.setVisibility(View.VISIBLE);
        }
        holder.expandTextView.setText(data);
        GlideUtil.showImg(context, data.getMember().getAvatar(), holder.avatar);
        holder.nickname.setText(data.getMember().getNickname());
        holder.created_at.setText(AllUtils.getTimeFormatText(data.getCreated_at()));
        if (UserUtil.getUserBean().getId().equals(data.getMember_id())) {
            holder.del.setVisibility(View.VISIBLE);
            holder.is_follow.setVisibility(View.GONE);
            holder.del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AllDialog payDialog = new AllDialog(context, "温馨提示", "确定删除当前记录？");
                    payDialog.setItemListener(new AllDialog.OnNoticeListener() {
                        @Override
                        public void setNoticeListener(int id, int itemposition, String itemdata) {
                            if (0 == id) {
                                Map<String, String> map = new HashMap<>();
                                map.put("id", data.getId());
                                HttpClient.getInstance().gets(HttpUtil.MEMBERARTICLEDEL, map, new TradeHttpCallback<JsonBean<WenZhangDetailBean>>() {
                                    @Override
                                    public void onSuccess(JsonBean<WenZhangDetailBean> urldata) {
                                        if (null == urldata || null == urldata.getData()) {
                                            return;
                                        }
                                        dataList.remove(position);
                                        notifyDataSetChanged();
                                        EventBus.getDefault().post(new MessageEvent<>(Constants.QUANZI));
                                    }

                                    @Override
                                    public boolean showLoadingDialog() {
                                        return true;
                                    }
                                });
                            }
                        }
                    });
                    payDialog.show();
                }
            });
        } else {
            holder.is_follow.setVisibility(View.VISIBLE);
            holder.del.setVisibility(View.GONE);
            holder.is_follow.setImageResource("0".equals(data.getIs_follow()) ? R.mipmap.img14 : R.mipmap.img15);
            holder.is_follow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Map<String, String> map = new HashMap<>();
                    map.put("to_member_id", data.getMember().getId());
                    map.put("type", "2");
                    map.put("member_id", UserUtil.getUserBean().getId());
                    HttpClient.getInstance().gets(HttpUtil.ISFOLLOWMEMBER, map, new TradeHttpCallback<JsonBean<String>>() {
                        @Override
                        public void onSuccess(JsonBean<String> urldata) {
                            if (null == urldata || null == urldata.getData()) {
                                return;
                            }
                            data.setIs_follow("0".equals(data.getIs_follow()) ? "1" : "0");
                            notifyDataSetChanged();
                        }

                        @Override
                        public boolean showLoadingDialog() {
                            return true;
                        }
                    });
                }
            });
        }

        holder.imgview.setVisibility(View.GONE);
        holder.img.setVisibility(View.INVISIBLE);
        holder.img1.setVisibility(View.INVISIBLE);
        holder.img2.setVisibility(View.INVISIBLE);
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null == data.getImage_text()) {
                    return;
                }
                Intent intent = new Intent(context,
                        Photo.class);
                Bundle bundle = new Bundle();
                bundle.putInt("position", 0);
                bundle.putSerializable("list", (Serializable) data.getImage_text());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        holder.img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null == data.getImage_text()) {
                    return;
                }
                Intent intent = new Intent(context,
                        Photo.class);
                Bundle bundle = new Bundle();
                bundle.putInt("position", 1);
                bundle.putSerializable("list", (Serializable) data.getImage_text());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        holder.img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null == data.getImage_text()) {
                    return;
                }
                Intent intent = new Intent(context,
                        Photo.class);
                Bundle bundle = new Bundle();
                bundle.putInt("position", 2);
                bundle.putSerializable("list", (Serializable) data.getImage_text());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
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
        holder.goodimg.setImageResource("0".equals(data.getIs_good()) ? R.mipmap.img12 : R.mipmap.img13);
        holder.goodview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> map = new HashMap<>();
                map.put("type", "2");
                map.put("id", data.getId());
                HttpClient.getInstance().gets(HttpUtil.THUMBSUPDOLIKE, map, new TradeHttpCallback<JsonBean<JiangLiBean>>() {
                    @Override
                    public void onSuccess(JsonBean<JiangLiBean> urldata) {
                        if (null == urldata || null == urldata.getData()) {
                            return;
                        }
                        if(!TextUtils.isEmpty(urldata.getData().getPoint())&&Integer.parseInt(urldata.getData().getPoint())>0){
                            JiFenDialog payDialog = new JiFenDialog(context,"点赞成功获得"+urldata.getData().getPoint()+"DD");
                            payDialog.show();
                        }
                        if ("0".equals(data.getIs_good())) {
                            data.setIs_good("1");
                            int num = Integer.valueOf(data.getGood());
                            num++;
                            data.setGood(String.valueOf(num));
                            notifyDataSetChanged();
                        } else {
                            data.setIs_good("0");
                            int num = Integer.valueOf(data.getGood());
                            num--;
                            data.setGood(String.valueOf(num));
                            notifyDataSetChanged();
                        }
                        notifyDataSetChanged();
                    }

                });

            }
        });
    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout imgview;
        LinearLayout itemview, goodview;
        ExpandTextView expandTextView;
        RoundedImageView avatar, img, img1, img2;
        TextView nickname, created_at, good, comments;
        ImageView is_follow, del, goodimg;

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
            del = view.findViewById(R.id.del);
            goodview = (LinearLayout) itemView.findViewById(R.id.goodview);
            goodimg = view.findViewById(R.id.goodimg);
        }
    }
}


