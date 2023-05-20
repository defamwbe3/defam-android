package Medium.DeFam.app.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

import Medium.DeFam.app.R;
import Medium.DeFam.app.common.utils.GlideUtil;

public class PublishGridAdapter extends BaseAdapter {
    private int selectedPosition = -1;
    private Context context;
    private List<String> checkedItems;
    private int maxNum = 9;
    private int myimg = R.mipmap.icon_addpic_unfocused;

    public PublishGridAdapter(Context context, List<String> checkedItems) {
        this.checkedItems = checkedItems;
        this.context = context;
    }
    public void setMyImg(int newmyimg) {
        myimg = newmyimg;
    }
    public void setMaxNum(int mymaxNum) {
        maxNum = mymaxNum;
    }

    @Override
    public int getCount() {
        if (checkedItems.size() == maxNum) {
            return maxNum;
        }
        return (checkedItems.size() + 1);
    }

    @Override
    public Object getItem(int arg0) {
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        return 0;
    }

    public void setSelectedPosition(int position) {
        selectedPosition = position;
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_published_grida,
                    parent, false);
            holder = new ViewHolder();
            holder.image = (ImageView) convertView
                    .findViewById(R.id.item_grida_image);
            holder.guanbi = (ImageView) convertView
                    .findViewById(R.id.guanbi);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (position == checkedItems.size()) {
            holder.guanbi.setVisibility(View.GONE);
            GlideUtil.showImg(context, myimg, holder.image);
            if (position == maxNum) {
                holder.image.setVisibility(View.GONE);
            }
        } else {
            holder.guanbi.setVisibility(View.VISIBLE);
            GlideUtil.showImg(context, checkedItems.get(position), holder.image);
        }
        holder.guanbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkedItems.remove(position);
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    private class ViewHolder {
        private ImageView image;
        private ImageView guanbi;
    }


}


