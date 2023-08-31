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
import Medium.DeFam.app.bean.MapBean;
import Medium.DeFam.app.common.utils.GlideUtil;


public class DuiHuanTiaoJianAdapter extends BaseAdapter {
    private List<MapBean.UserMapBean> dataList = new ArrayList<>();
    private Context context;
    private OnNoticeListener onNoticeListener;

    public interface OnNoticeListener {
        void setNoticeListener(int id, int position, MapBean.UserMapBean data);
    }

    public void setOnNoticeListener(OnNoticeListener onNoticeListener) {
        this.onNoticeListener = onNoticeListener;
    }

    public DuiHuanTiaoJianAdapter(Context context) {
        this.context = context;
    }

    public void replaceAll(List<MapBean.UserMapBean> list) {
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
    public void addData(List<MapBean.UserMapBean> list) {
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
                    R.layout.duihuantiaojian_item, parent, false);
            holder.image = (RoundedImageView) convertView.findViewById(R.id.image);
            holder.num = (TextView) convertView.findViewById(R.id.num);
            holder.suipian_name = (TextView) convertView.findViewById(R.id.suipian_name);
            holder.need_num = (TextView) convertView.findViewById(R.id.need_num);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        MapBean.UserMapBean mEntity = dataList.get(position);
        GlideUtil.showImg(context,mEntity.getImage(),holder.image);
        holder.num.setText("x"+mEntity.getNumber());
        holder.suipian_name.setText(mEntity.getName());
        holder.need_num.setText("兑换需要 x"+mEntity.getValue());
        return convertView;
    }

    private class ViewHolder {
        private TextView num, suipian_name, need_num;
        LinearLayout select;
        RoundedImageView image;
    }
}
