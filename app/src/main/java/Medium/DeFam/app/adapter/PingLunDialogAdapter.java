package Medium.DeFam.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Medium.DeFam.app.R;
import Medium.DeFam.app.bean.CommentBean;
import Medium.DeFam.app.common.http.HttpClient;
import Medium.DeFam.app.common.http.JsonBean;
import Medium.DeFam.app.common.http.TradeHttpCallback;
import Medium.DeFam.app.common.utils.AllUtils;
import Medium.DeFam.app.common.utils.GlideUtil;
import Medium.DeFam.app.utils.HttpUtil;

public class PingLunDialogAdapter extends BaseAdapter {
    private List<CommentBean.ChildrenBean> dataList = new ArrayList<>();
    private Context context;
    private OnNoticeListener onNoticeListener;

    public interface OnNoticeListener {
        void setNoticeListener(int id, int position, CommentBean.ChildrenBean data);
    }

    public void setOnNoticeListener(OnNoticeListener onNoticeListener) {
        this.onNoticeListener = onNoticeListener;
    }

    public PingLunDialogAdapter(Context context) {
        this.context = context;
    }

    public void replaceAll(List<CommentBean.ChildrenBean> list) {
        dataList.clear();
        if (list != null && list.size() > 0) {
            dataList.addAll(list);
        }
        notifyDataSetChanged();
    }

    /**
     * 插入数据使用notifyItemInserted，如果要使用插入动画，必须使用notifyItemInserted
     * 才会有效果。即便不需要使用插入动画，也建议使用notifyItemInserted方式添加数据，
     * 不然容易出现闪动和间距错乱的问题
     */
    public void addData(List<CommentBean.ChildrenBean> list) {
        dataList.addAll(list);
        notifyDataSetChanged();
    }

    //移除数据使用notifyItemRemoved
    public void removeData(int position) {
        dataList.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.pinglundialog_item, parent, false);
            holder.avatar = (RoundedImageView) convertView.findViewById(R.id.avatar);
            holder.nickname = (TextView) convertView.findViewById(R.id.nickname);
            holder.created_ats = (TextView) convertView.findViewById(R.id.created_ats);
            holder.good = (TextView) convertView.findViewById(R.id.good);
            holder.is_good = (ImageView) convertView.findViewById(R.id.is_good);
            holder.contentmy = (TextView) convertView.findViewById(R.id.contentmy);
            holder.comment = (TextView) convertView.findViewById(R.id.comment);
            holder.children = (TextView) convertView.findViewById(R.id.children);
            holder.huifu = (LinearLayout) convertView.findViewById(R.id.huifu);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        CommentBean.ChildrenBean mEntity = dataList.get(position);
        GlideUtil.showImg(context, mEntity.getMember().getAvatar(), holder.avatar);
        holder.nickname.setText(mEntity.getMember().getNickname());
        holder.created_ats.setText(AllUtils.getTimeFormatText(mEntity.getCreated_at()));
        holder.good.setText(mEntity.getGood());
        holder.is_good.setImageResource("0".equals(mEntity.getIs_good()) ? R.mipmap.img29 : R.mipmap.img30);
        holder.contentmy.setText(mEntity.getContent());
        holder.comment.setText(mEntity.getComment() + "回复");
        if (null == mEntity.getPl()) {
            holder.children.setVisibility(View.GONE);
        } else {
            holder.children.setVisibility(View.VISIBLE);
            holder.children.setText(mEntity.getTomember().getNickname() + "：" + mEntity.getPl().getContent());
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
                onNoticeListener.setNoticeListener(0, position, mEntity);
            }
        });

        return convertView;
    }

    private class ViewHolder {
        private TextView nickname, created_ats, good, contentmy, comment, children;
        ImageView is_good;
        LinearLayout huifu;
        RoundedImageView avatar;
    }
}
