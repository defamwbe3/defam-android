package Medium.DeFam.app.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import Medium.DeFam.app.R;
import Medium.DeFam.app.bean.JiFenBean;


public class JiangLiAdapter extends BaseAdapter {
    private List<JiFenBean.DataBean> dataList = new ArrayList<>();
    private Context context;
    private OnNoticeListener onNoticeListener;

    public interface OnNoticeListener {
        void setNoticeListener(int id, int position, JiFenBean.DataBean data);
    }

    public void setOnNoticeListener(OnNoticeListener onNoticeListener) {
        this.onNoticeListener = onNoticeListener;
    }

    public JiangLiAdapter(Context context) {
        this.context = context;
    }

    public void replaceAll(List<JiFenBean.DataBean> list) {
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
    public void addData(List<JiFenBean.DataBean> list) {
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
                    R.layout.jiangli_item, parent, false);
            holder.type_name = (TextView) convertView.findViewById(R.id.type_name);
            holder.created_at = (TextView) convertView.findViewById(R.id.created_at);
            holder.amount = (TextView) convertView.findViewById(R.id.amount);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        JiFenBean.DataBean mEntity = dataList.get(position);
        holder.type_name.setText(mEntity.getType_name());
        holder.created_at.setText(mEntity.getCreated_at());

        if(mEntity.getAmount().indexOf("-")==-1){//不包含
            holder.amount.setTextColor(context.getResources().getColor(R.color.color_01C8E5));
            holder.amount.setText("+"+mEntity.getAmount());
        }else {
            holder.amount.setTextColor(context.getResources().getColor(R.color.color_FF1C41));
            holder.amount.setText(mEntity.getAmount());
        }

        return convertView;
    }

    private class ViewHolder {
        private TextView type_name, created_at, amount;
    }
}
