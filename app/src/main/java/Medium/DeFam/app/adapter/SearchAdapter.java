package Medium.DeFam.app.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import Medium.DeFam.app.R;
import Medium.DeFam.app.bean.SearchListBean;

public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<SearchListBean> list;
    private OnNoticeListener onNoticeListener;

    public interface OnNoticeListener {
        void setNoticeListener(int id, int position, SearchListBean data);
    }

    public SearchAdapter(Context context, List<SearchListBean> list, OnNoticeListener onNoticeListener) {
        this.context = context;
        this.list = list;
        this.onNoticeListener = onNoticeListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyHolder(View.inflate(context, R.layout.search_item, null));
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MyHolder myHolder = ((MyHolder) holder);

        myHolder.text.setText(list.get(position).getKey_word());
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