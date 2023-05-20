package Medium.DeFam.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.youth.banner.adapter.BannerAdapter;

import java.util.List;

import Medium.DeFam.app.R;
import Medium.DeFam.app.activity.Web;
import Medium.DeFam.app.bean.BannerBean;
import Medium.DeFam.app.common.utils.GlideUtil;

public class ImageRadiusAdapter extends BannerAdapter<BannerBean.DataBean, ImageRadiusAdapter.ImageHolder> {
    private Context mContext;
    public ImageRadiusAdapter(Context context, List<BannerBean.DataBean> mDatas) {
        //设置数据，也可以调用banner提供的方法,或者自己在adapter中实现
        super(mDatas);
        mContext = context;
    }

    //更新数据
    public void updateData(List<BannerBean.DataBean> data) {
        //这里的代码自己发挥，比如如下的写法等等
        mDatas.clear();
        mDatas.addAll(data);
        notifyDataSetChanged();
    }


    //创建ViewHolder，可以用viewType这个字段来区分不同的ViewHolder
    @Override
    public ImageHolder onCreateHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.banner_imageradius, parent, false);
        return new ImageHolder(view);
    }

    @Override
    public void onBindView(ImageHolder holder, BannerBean.DataBean data, int position, int size) {
        GlideUtil.showImg(mContext,data.getImage(),holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("1".equals(data.getIs_jump())) {
                    Web.startWebActivity(mContext, data.getTitle(), data.getJump_url(), "");
                }
            }
        });
    }
    public class ImageHolder extends RecyclerView.ViewHolder {
        public RoundedImageView imageView;

        public ImageHolder(@NonNull View view) {
            super(view);
            this.imageView = (RoundedImageView) view.findViewById(R.id.img);
        }
    }
}

