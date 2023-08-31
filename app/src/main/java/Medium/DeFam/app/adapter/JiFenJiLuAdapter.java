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
import Medium.DeFam.app.bean.JiFenJiLuDetailBean;
import Medium.DeFam.app.common.utils.GlideUtil;

public class JiFenJiLuAdapter extends BaseAdapter {
    private List<JiFenJiLuDetailBean> dataList = new ArrayList<>();
    private Context context;
    private OnNoticeListener onNoticeListener;

    public interface OnNoticeListener {
        void setNoticeListener(int id, int position, JiFenJiLuDetailBean data);
    }

    public void setOnNoticeListener(OnNoticeListener onNoticeListener) {
        this.onNoticeListener = onNoticeListener;
    }

    public JiFenJiLuAdapter(Context context) {
        this.context = context;
    }

    public void replaceAll(List<JiFenJiLuDetailBean> list) {
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
    public void addData(List<JiFenJiLuDetailBean> list) {
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
                    R.layout.jifenjilu_item, parent, false);
            holder.created_at = (TextView) convertView.findViewById(R.id.created_at);
            holder.status = (TextView) convertView.findViewById(R.id.status);
            holder.goods_image = (RoundedImageView) convertView.findViewById(R.id.goods_image);
            holder.goods_name = (TextView) convertView.findViewById(R.id.goods_name);
            holder.integral = (TextView) convertView.findViewById(R.id.integral);
            holder.itemview = (LinearLayout) convertView.findViewById(R.id.itemview);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        JiFenJiLuDetailBean mEntity = dataList.get(position);
        holder.itemview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNoticeListener.setNoticeListener(0,position,mEntity);
            }
        });
        holder.created_at.setText(mEntity.getCreated_at());
       /* if("unpaid".equals(mEntity.getStatus())){
            holder.status.setText("未支付");
        }else  if("paying".equals(mEntity.getStatus())){
            holder.status.setText("支付中");
        }else  if("paid".equals(mEntity.getStatus())){
            holder.status.setText("已支付");
        }else  if("delivered".equals(mEntity.getStatus())){
            holder.status.setText("已发货");
        }else  if("received".equals(mEntity.getStatus())){
            holder.status.setText("已收货");
        }else  if("finished".equals(mEntity.getStatus())){
            holder.status.setText("已完成");
        }else  if("closed".equals(mEntity.getStatus())){
            holder.status.setText("已关闭");
        }*/
        holder.status.setText(mEntity.getStatus_name());
        GlideUtil.showImg(context,mEntity.getGoods().get(0).getGoods_image(),holder.goods_image);
        holder.goods_name.setText(mEntity.getGoods().get(0).getGoods_name());
        holder.integral.setText(mEntity.getGoods().get(0).getPrice()+"DD");
        return convertView;
    }

    private class ViewHolder {
        private TextView created_at, status, goods_name, integral;
        RoundedImageView goods_image;
        LinearLayout itemview;
    }
}
