package Medium.DeFam.app.adapter;

import android.annotation.SuppressLint;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Medium.DeFam.app.R;
import Medium.DeFam.app.bean.JiangLiBean;
import Medium.DeFam.app.bean.KOLBean;
import Medium.DeFam.app.common.http.HttpClient;
import Medium.DeFam.app.common.http.JsonBean;
import Medium.DeFam.app.common.http.TradeHttpCallback;
import Medium.DeFam.app.common.utils.GlideUtil;
import Medium.DeFam.app.dialog.JiFenDialog;
import Medium.DeFam.app.utils.HttpUtil;
import Medium.DeFam.app.view.ExpandKOLView;
import Medium.DeFam.app.view.ExpandKOLView1;


public class KOLItemAdapter extends RecyclerView.Adapter<KOLItemAdapter.ViewHolder> {
    private final List<KOLBean.DataBean> dataList = new ArrayList<>();
    private Context context;
    private RecyclerView recyclerView;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(int pos, int type, KOLBean.DataBean data);
    }

    public void setRecyclerViewOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public KOLItemAdapter(Context context) {
        this.context = context;
    }

    public void replaceAll(List<KOLBean.DataBean> list) {
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
    public void addData(List<KOLBean.DataBean> list) {
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
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kolitem, parent, false));
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        KOLBean.DataBean data = dataList.get(position);
        holder.itemview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(position, 0, data);
            }
        });
        if (null != data.getSrc_tx_text() && !TextUtils.isEmpty(data.getSrc_tx_text().getImg())) {
            GlideUtil.showAllImg(context, data.getSrc_tx_text().getImg(), holder.avatar);
        }

        holder.nickname.setText(data.getName());
        holder.name.setText(data.getName());
        holder.screen_name.setText(data.getScreen_name());
        holder.created_at.setText(data.getCreated_at());
        if (TextUtils.isEmpty(data.getFull_text())) {
            holder.expand_textView.setVisibility(View.GONE);
        } else {
            holder.expand_textView.setVisibility(View.VISIBLE);

            holder.expand_textView.setText(data);
        }


        holder.href.setText(data.getHref());
        //Log.i("zmh","---------------------------"+data.getJSONObject("link_text"));
        if (null == data.getLink_text() || TextUtils.isEmpty(data.getLink_text().getImg())) {
            holder.img.setVisibility(View.GONE);
        } else {
            holder.img.setVisibility(View.VISIBLE);
            GlideUtil.showAllImg(context, data.getLink_text().getImg(), holder.img);
        }
        holder.fenxiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(position, 1, data);

            }
        });
        if (null != data.getMain_text()){
            holder.myview.setVisibility(View.VISIBLE);
            holder.main_name.setText(data.getMain_text().getName());
            holder.main_text.setText(data);
            holder.main_href.setText(data.getMain_text().getHref());

        }else if (null != data.getOther_text()){
            holder.myview.setVisibility(View.VISIBLE);
            holder.main_name.setText(data.getOther_text().getName());
            holder.main_text.setText(data);
            holder.main_href.setText(data.getOther_text().getHref());

        }else {
            holder.myview.setVisibility(View.GONE);
        }
       
        holder.good.setText(data.getGood());
        holder.goodimg.setImageResource("0".equals(data.getIs_good()) ? R.mipmap.img12 : R.mipmap.img13);
        holder.goodview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> map = new HashMap<>();
                map.put("type", "5");
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
        LinearLayout itemview,fenxiang,goodview,myview;
        TextView created_at, nickname, screen_name, href,name,good,main_name,main_href;
        ExpandKOLView expand_textView;
        ExpandKOLView1 main_text;
        RoundedImageView avatar, img;
        ImageView  goodimg;
        public ViewHolder(View view) {
            super(view);
            itemview = view.findViewById(R.id.itemview);
            created_at = view.findViewById(R.id.created_at);
            expand_textView = view.findViewById(R.id.expand_textView);
            avatar = view.findViewById(R.id.avatar);
            nickname = view.findViewById(R.id.nickname);
            screen_name = view.findViewById(R.id.screen_name);
            href = view.findViewById(R.id.href);
            img = view.findViewById(R.id.img);
            name = view.findViewById(R.id.name);
            fenxiang = view.findViewById(R.id.fenxiang);
            good = view.findViewById(R.id.good);
            goodview = (LinearLayout) itemView.findViewById(R.id.goodview);
            goodimg = view.findViewById(R.id.goodimg);
            main_text = view.findViewById(R.id.main_text);
            main_name = view.findViewById(R.id.main_name);
            main_href = view.findViewById(R.id.main_href);
            myview = view.findViewById(R.id.myview);
        }
    }
}

