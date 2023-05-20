package Medium.DeFam.app.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Random;

import Medium.DeFam.app.R;


public class DesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<String> list;
    private OnNoticeListener onNoticeListener;

    public interface OnNoticeListener {
        void setNoticeListener(int id, int position, String data);
    }

    public DesAdapter(Context context, List<String> list, OnNoticeListener onNoticeListener) {
        this.context = context;
        this.list = list;
        this.onNoticeListener = onNoticeListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyHolder(View.inflate(context, R.layout.faxian_item, null));
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MyHolder myHolder = ((MyHolder) holder);
        int random = new Random().nextInt(4);
        if (0 == random) {
            myHolder.text.setTextColor(context.getResources().getColor(R.color.color_F177C1));
        } else if (1 == random) {
            myHolder.text.setTextColor(context.getResources().getColor(R.color.color_FFAF39));
        } else if (2 == random) {
            myHolder.text.setTextColor(context.getResources().getColor(R.color.color_01C8E5));
        } else if (3 == random) {
            myHolder.text.setTextColor(context.getResources().getColor(R.color.color_1B1BDD));
        } else if (4 == random) {
            myHolder.text.setTextColor(context.getResources().getColor(R.color.color_FF1C41));
        }
        myHolder.text.setText(list.get(position));
        myHolder.text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNoticeListener.setNoticeListener(0, position, list.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        private TextView text;

        public MyHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.flow_text);
        }
    }
}