package Medium.DeFam.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import Medium.DeFam.app.R;
import Medium.DeFam.app.bean.WalletAddressBean;
import Medium.DeFam.app.common.http.HttpClient;
import Medium.DeFam.app.common.http.JsonBean;
import Medium.DeFam.app.common.http.TradeHttpCallback;
import Medium.DeFam.app.dialog.AllDialog;
import Medium.DeFam.app.utils.HttpUtil;


public class WalletAddressAdapter extends BaseAdapter {
    private List<WalletAddressBean.DataBean> list;
    private Context context;
    private OnNoticeListener onNoticeListener;

    public interface OnNoticeListener {
        void setNoticeListener(int position, WalletAddressBean.DataBean data);
    }

    public void setOnNoticeListener(OnNoticeListener onNoticeListener) {
        this.onNoticeListener = onNoticeListener;
    }

    public WalletAddressAdapter(Context context, List<WalletAddressBean.DataBean> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
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
                    R.layout.walletaddress_item, parent, false);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.address = (TextView) convertView.findViewById(R.id.address);
            holder.itemview = (LinearLayout) convertView.findViewById(R.id.itemview);
            holder.shanchu = (ImageView) convertView.findViewById(R.id.shanchu);
            holder.bianji = (ImageView) convertView.findViewById(R.id.bianji);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        WalletAddressBean.DataBean mEntity = list.get(position);
        holder.title.setText(mEntity.getTitle());
        holder.address.setText(mEntity.getAddress());
        holder.itemview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNoticeListener.setNoticeListener(0, mEntity);
            }
        });
        holder.bianji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNoticeListener.setNoticeListener(1, mEntity);
            }
        });

        holder.shanchu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllDialog payDialog = new AllDialog(context, "温馨提示", "确定删除当前钱包地址？");
                payDialog.setItemListener(new AllDialog.OnNoticeListener() {
                    @Override
                    public void setNoticeListener(int id, int positionDialog, String data) {
                        if (0 == id) {
                            HttpClient.getInstance().deletes(HttpUtil.APIWALLETADDRESSDEL + mEntity.getId(), null, new TradeHttpCallback<JsonBean<WalletAddressBean.DataBean>>() {
                                @Override
                                public void onSuccess(JsonBean<WalletAddressBean.DataBean> data) {
                                    if (null == data || null == data.getData()) {
                                        return;
                                    }
                                    list.remove(position);
                                    notifyDataSetChanged();
                                }

                                @Override
                                public boolean showLoadingDialog() {
                                    return true;
                                }
                            });
                        }
                    }
                });
                payDialog.show();


            }
        });
        return convertView;
    }

    private class ViewHolder {
        TextView title, address;
        ImageView shanchu,bianji;
        LinearLayout itemview;
    }
}
