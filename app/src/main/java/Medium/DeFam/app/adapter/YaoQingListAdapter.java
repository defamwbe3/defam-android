package Medium.DeFam.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import Medium.DeFam.app.R;
import Medium.DeFam.app.bean.YaoQingListBean;
import Medium.DeFam.app.common.utils.GlideUtil;


public class YaoQingListAdapter extends BaseAdapter {
    private List<YaoQingListBean.DataBean> dataList = new ArrayList<>();
    private Context context;
    private OnNoticeListener onNoticeListener;

    public interface OnNoticeListener {
        void setNoticeListener(int id, int position, YaoQingListBean.DataBean data);
    }

    public void setOnNoticeListener(OnNoticeListener onNoticeListener) {
        this.onNoticeListener = onNoticeListener;
    }

    public YaoQingListAdapter(Context context) {
        this.context = context;
    }

    public void replaceAll(List<YaoQingListBean.DataBean> list) {
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
    public void addData(List<YaoQingListBean.DataBean> list) {
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
                    R.layout.yaoqinglist_item, parent, false);
            holder.avatar = (RoundedImageView) convertView.findViewById(R.id.avatar);
            holder.nickname = (TextView) convertView.findViewById(R.id.nickname);
            holder.created_at = (TextView) convertView.findViewById(R.id.created_at);
            holder.itemview = (LinearLayout) convertView.findViewById(R.id.itemview);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        YaoQingListBean.DataBean mEntity = dataList.get(position);
        GlideUtil.showImg(context,mEntity.getAvatar(),holder.avatar);
        holder.nickname.setText(mEntity.getNickname());
        holder.created_at.setText(mEntity.getCreated_at());
        holder.itemview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNoticeListener.setNoticeListener(0,position,mEntity);
            }
        });
        return convertView;
    }

    private class ViewHolder {
        LinearLayout itemview;
        private TextView nickname, created_at;
        RoundedImageView avatar;
    }
}
