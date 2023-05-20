package Medium.DeFam.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import Medium.DeFam.app.R;
import Medium.DeFam.app.activity.Web;
import Medium.DeFam.app.bean.MyMapBean;
import Medium.DeFam.app.common.utils.GlideUtil;

public class BaoZangAdapter extends BaseAdapter {
    private List<MyMapBean.DataBean> dataList = new ArrayList<>();
    private Context context;
    private OnNoticeListener onNoticeListener;

    public interface OnNoticeListener {
        void setNoticeListener(int position, MyMapBean.DataBean data);
    }

    public void setOnNoticeListener(OnNoticeListener onNoticeListener) {
        this.onNoticeListener = onNoticeListener;
    }

    public BaoZangAdapter(Context context) {
        this.context = context;
    }

    public void replaceAll(List<MyMapBean.DataBean> list) {
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
    public void addData(List<MyMapBean.DataBean> list) {
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
                    R.layout.baozang_item, parent, false);
            holder.image = (RoundedImageView) convertView.findViewById(R.id.image);
            holder.map_name = (TextView) convertView.findViewById(R.id.map_name);
            holder.itemview = (LinearLayout) convertView.findViewById(R.id.itemview);
            holder.status_text = (TextView) convertView.findViewById(R.id.status_text);
            holder.huizhiview = (RelativeLayout) convertView.findViewById(R.id.huizhiview);
            holder.huizhi = (TextView) convertView.findViewById(R.id.huizhi);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        MyMapBean.DataBean mEntity = dataList.get(position);
        GlideUtil.showImg(context, mEntity.getImage(), holder.image);
        holder.map_name.setText(mEntity.getMap_name());
        holder.itemview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNoticeListener.setNoticeListener(0,mEntity);
            }
        });
        holder.status_text.setVisibility(View.GONE);
        holder.huizhiview.setVisibility(View.GONE);
        if("0".equals(mEntity.getStatus())){
            holder.status_text.setVisibility(View.VISIBLE);
            holder.status_text.setText("藏宝图提现");
            holder.status_text.setBackgroundResource(R.drawable.r_lan);
            holder.status_text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onNoticeListener.setNoticeListener(1,mEntity);
                }
            });
        } else if("1".equals(mEntity.getStatus())){
            holder.status_text.setVisibility(View.VISIBLE);
            holder.status_text.setText("正在申请中");
            holder.status_text.setBackgroundResource(R.drawable.r_hui40);
            holder.status_text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onNoticeListener.setNoticeListener(0,mEntity);
                }
            });
        } else if("2".equals(mEntity.getStatus())){
            holder.huizhiview.setVisibility(View.VISIBLE);
            holder.huizhi.setText(mEntity.getReceipt());
            holder.huizhiview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Web.startWebActivity(context, "",  mEntity.getReceipt(), "", true);

                }
            });
        }
        return convertView;
    }

    private class ViewHolder {
        private TextView map_name;
        RoundedImageView image;
        LinearLayout itemview;
        TextView status_text,huizhi;
        RelativeLayout huizhiview;
    }
}
