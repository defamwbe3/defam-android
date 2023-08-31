package Medium.DeFam.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import Medium.DeFam.app.R;
import Medium.DeFam.app.bean.UserMapLogBean;
import Medium.DeFam.app.common.utils.GlideUtil;


public class DuiHuanJiLuAdapter extends BaseAdapter {
    private List<UserMapLogBean.DataBean> dataList = new ArrayList<>();
    private Context context;
    private OnNoticeListener onNoticeListener;

    public interface OnNoticeListener {
        void setNoticeListener(int position, String DataBean);
    }

    public void setOnNoticeListener(OnNoticeListener onNoticeListener) {
        this.onNoticeListener = onNoticeListener;
    }

    public DuiHuanJiLuAdapter(Context context) {
        this.context = context;
    }

    public void replaceAll(List<UserMapLogBean.DataBean> list) {
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
    public void addData(List<UserMapLogBean.DataBean> list) {
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
                    R.layout.duihuanjilu_item, parent, false);
            holder.created_at = (TextView) convertView.findViewById(R.id.created_at);
            holder.image = (RoundedImageView) convertView.findViewById(R.id.image);
            holder.map_name = (TextView) convertView.findViewById(R.id.map_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        UserMapLogBean.DataBean mEntity = dataList.get(position);
        holder.created_at.setText(mEntity.getCreated_at());
        GlideUtil.showImg(context, mEntity.getImage(), holder.image);
        holder.map_name.setText(mEntity.getMap_name());
        return convertView;
    }

    private class ViewHolder {
        private TextView created_at, map_name;
        RoundedImageView image;
    }
}
