package Medium.DeFam.app.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.hjq.toast.Toaster;
import com.makeramen.roundedimageview.RoundedImageView;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.HashMap;
import java.util.Map;

import Medium.DeFam.app.R;
import Medium.DeFam.app.adapter.PingLunDialogAdapter;
import Medium.DeFam.app.bean.CommentBean;
import Medium.DeFam.app.bean.CommentChildrenBean;
import Medium.DeFam.app.bean.JiangLiBean;
import Medium.DeFam.app.common.base.BaseDialogFragment;
import Medium.DeFam.app.common.http.HttpClient;
import Medium.DeFam.app.common.http.JsonBean;
import Medium.DeFam.app.common.http.TradeHttpCallback;
import Medium.DeFam.app.common.utils.AllUtils;
import Medium.DeFam.app.common.utils.GlideUtil;
import Medium.DeFam.app.utils.HttpUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class PingLunDialogFragment extends BaseDialogFragment {
    private Context mContext;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.listview)
    ListView listview;
    @BindView(R.id.not)
    View notview;
    @BindView(R.id.comment)
    TextView comment;
    @BindView(R.id.avatar)
    RoundedImageView avatar;
    @BindView(R.id.nickname)
    TextView nickname;
    @BindView(R.id.created_ats)
    TextView created_ats;
    @BindView(R.id.good)
    TextView good;
    @BindView(R.id.is_good)
    ImageView is_good;
    @BindView(R.id.contentmy)
    TextView contentmy;
    @BindView(R.id.content)
    EditText content;
    private PingLunDialogAdapter adapter;
    private int page = 1;
    CommentBean.DataBean alldata;
    private String myid,ziID,type;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mContext = getContext();
        int screenWidth = mContext.getResources().getDisplayMetrics().widthPixels;
        Dialog dialog = new Dialog(mContext, R.style.BottomViewTheme_Transparent);//dialog_no_background
        dialog.setCanceledOnTouchOutside(true);
        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_dialog_pinglun, null);
        dialog.setContentView(view);
        window.setWindowAnimations(R.style.BottomToTopAnim);
        window.setGravity(Gravity.BOTTOM);

        window.setLayout(screenWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
        ButterKnife.bind(this, view);
        initData();
        return dialog;
    }

    private void initData() {
        listview.setEmptyView(notview);
        if (getArguments() != null) {
            alldata = (CommentBean.DataBean) getArguments().getSerializable("data");
            myid = getArguments().getString("id");
            type = getArguments().getString("type");
            ziID= alldata.getId();
            comment.setText(alldata.getComment() + "回复");
            GlideUtil.showImg(mContext, alldata.getMember().getAvatar(), avatar);
            nickname.setText(alldata.getMember().getNickname());
            created_ats.setText(AllUtils.getTimeFormatText(alldata.getCreated_at()));
            good.setText(alldata.getGood());
            is_good.setImageResource("0".equals(alldata.getIs_good()) ? R.mipmap.img29 : R.mipmap.img30);
            contentmy.setText(alldata.getContent());
        }
        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                getData();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                getData();
            }
        });
        adapter = new PingLunDialogAdapter(getActivity());
        adapter.setOnNoticeListener(new PingLunDialogAdapter.OnNoticeListener() {
            @Override
            public void setNoticeListener(int id, int position, CommentBean.ChildrenBean data) {
                ziID = data.getId();
                content.setHint("回复 "+data.getMember().getNickname()+"：");
            }
        });
        listview.setAdapter(adapter);
        getData();
    }


    @OnClick({R.id.back,R.id.is_good,R.id.fabu})
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.back) {
            dismiss();
        }else  if (id == R.id.is_good) {
            Map<String, String> map = new HashMap<>();
            map.put("type",type);
            map.put("id",alldata.getId());
            HttpClient.getInstance().gets(HttpUtil.APICOMMENTDOLIKE, map, new TradeHttpCallback<JsonBean<String>>() {
                @Override
                public void onSuccess(JsonBean<String> data) {
                    if (null == data || null == data.getData()) {
                        return;
                    }
                    if("0".equals(alldata.getIs_good())){
                        int num = Integer.valueOf(alldata.getGood());
                        num++;
                        alldata.setGood(String.valueOf(num));
                        alldata.setIs_good("1");
                    }else {
                        alldata.setIs_good("0");
                        int num = Integer.valueOf(alldata.getGood());
                        num--;
                        alldata.setGood(String.valueOf(num));
                        alldata.setIs_good("0");
                    }
                    good.setText(alldata.getGood());
                    is_good.setImageResource("0".equals(alldata.getIs_good()) ? R.mipmap.img29 : R.mipmap.img30);
                }

            });
        }else  if (id == R.id.fabu) {
            if(TextUtils.isEmpty(content.getText().toString())){
                Toaster.show("说点什么吧~");
                return;
            }

            Map<String, String> map = new HashMap<>();
            map.put("type",type);
            map.put("id",ziID);
            map.put("article_id",myid);
            map.put("content",content.getText().toString());
            HttpClient.getInstance().posts(HttpUtil.COMMENTSAVE, map, new TradeHttpCallback<JsonBean<JiangLiBean>>() {
                @Override
                public void onSuccess(JsonBean<JiangLiBean> data) {
                    if (null == data || null == data.getData()) {
                        return;
                    }
                    content.setText("");
                    if(!TextUtils.isEmpty(data.getData().getPoint())&&Integer.parseInt(data.getData().getPoint())>0){
                        JiFenDialog payDialog = new JiFenDialog(getActivity(),"评论成功获得"+data.getData().getPoint()+"DD");
                        payDialog.show();
                    }
                    page = 1;
                    getData();
                }

            });
        }
    }

    private void getData() {
        Map<String, String> map = new HashMap<>();
        map.put("page", page + "");
        map.put("id", alldata.getId());
        HttpClient.getInstance().gets(HttpUtil.COMMENTCHILDREN, map, new TradeHttpCallback<JsonBean<CommentChildrenBean>>() {
            @Override
            public void onSuccess(JsonBean<CommentChildrenBean> data) {
                if (null == data || null == data.getData()) {
                    refreshLayout.finishRefresh();
                    refreshLayout.finishLoadMore();
                    return;
                }
                if (page > 1) {
                    refreshLayout.finishLoadMore();
                    if (data.getData().getData().size() == 0) {
                        Toaster.show("暂无更多数据");
                        return;
                    }
                    adapter.addData(data.getData().getData());
                } else {
                    comment.setText(data.getData().getTotal() + "回复");
                    refreshLayout.finishRefresh();
                    adapter.replaceAll(data.getData().getData());
                }
                page++;
            }

        });
    }
}