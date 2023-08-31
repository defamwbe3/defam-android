package Medium.DeFam.app.adapter;

import android.content.Context;
import android.content.Intent;
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
import Medium.DeFam.app.activity.DuiHuanDetail;
import Medium.DeFam.app.bean.SuiPianBean;
import Medium.DeFam.app.common.utils.GlideUtil;


public class JiangLiSuiPianAdapter extends BaseAdapter {
    private List<SuiPianBean.DataBean> dataList = new ArrayList<>();
    private Context context;
    private OnNoticeListener onNoticeListener;

    public interface OnNoticeListener {
        void setNoticeListener(int id, int position, SuiPianBean.DataBean data);
    }

    public void setOnNoticeListener(OnNoticeListener onNoticeListener) {
        this.onNoticeListener = onNoticeListener;
    }

    public JiangLiSuiPianAdapter(Context context) {
        this.context = context;
    }

    public void replaceAll(List<SuiPianBean.DataBean> list) {
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
    public void addData(List<SuiPianBean.DataBean> list) {
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
                    R.layout.jianglisuipian_item, parent, false);
            holder.image = (RoundedImageView) convertView.findViewById(R.id.image);
            holder.need_num = (TextView) convertView.findViewById(R.id.need_num);
            holder.map_name = (TextView) convertView.findViewById(R.id.map_name);
            holder.itemview = (LinearLayout) convertView.findViewById(R.id.itemview);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        SuiPianBean.DataBean mEntity = dataList.get(position);
        GlideUtil.showImg(context, mEntity.getImage(), holder.image);
        holder.need_num.setText("x" + mEntity.getNumber());
        holder.map_name.setText(mEntity.getName());
        holder.itemview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DuiHuanDetail.class);
                intent.putExtra("map_id",mEntity.getFragment_id());
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    private class ViewHolder {
        private TextView need_num, map_name;
        LinearLayout itemview;
        RoundedImageView image;
    }
}
