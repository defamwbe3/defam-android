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

import java.util.ArrayList;
import java.util.List;

import Medium.DeFam.app.R;

public class DuiHuanAdapter extends BaseAdapter {
    private List<String> dataList = new ArrayList<>();
    private Context context;
    private OnNoticeListener onNoticeListener;
    public interface OnNoticeListener {
        void setNoticeListener(int id, int position, String data);
    }
    public void setOnNoticeListener(OnNoticeListener onNoticeListener) {
        this.onNoticeListener = onNoticeListener;
    }
    public DuiHuanAdapter(Context context) {
        this.context = context;
    }
    public void replaceAll(List<String> list) {
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
    public void addData(int position, List<String> list) {
        dataList.addAll(position, list);
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
                    R.layout.duihuan_item, parent, false);
            //holder.name = (TextView) convertView.findViewById(R.id.name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //ExpressAddressBean.DataBean mEntity = list.get(position);


        return convertView;
    }

    private class ViewHolder {
        private TextView name, tel,status_default,desc;
        ImageView xiugai;
        LinearLayout select;
        CheckBox xuanze;
    }
}
