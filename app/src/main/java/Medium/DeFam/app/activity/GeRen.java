package Medium.DeFam.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.makeramen.roundedimageview.RoundedImageView;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import Medium.DeFam.app.R;
import Medium.DeFam.app.bean.InfoBean;
import Medium.DeFam.app.common.Constants;
import Medium.DeFam.app.common.base.BaseActivity;
import Medium.DeFam.app.common.bean.MessageEvent;
import Medium.DeFam.app.common.bean.UserBean;
import Medium.DeFam.app.common.http.HttpClient;
import Medium.DeFam.app.common.http.JsonBean;
import Medium.DeFam.app.common.http.TradeHttpCallback;
import Medium.DeFam.app.common.interfaces.OnUpdateImgListener;
import Medium.DeFam.app.common.utils.GlideUtil;
import Medium.DeFam.app.common.utils.UpdateImageUtil;
import Medium.DeFam.app.common.utils.UserUtil;
import Medium.DeFam.app.utils.HttpUtil;
import butterknife.BindView;
import butterknife.OnClick;


public class GeRen extends BaseActivity {
    @BindView(R.id.touxiang)
    RoundedImageView touxiang;
    @BindView(R.id.nackname)
    TextView nackname;

    @Override
    protected boolean useEventBus() {
        return true;
    }

    @Override
    protected void onEventComing(MessageEvent event) {
        super.onEventComing(event);
        if (event.getCode() == Constants.ACTION_LOGIN && event.getData() != null) {
            nackname.setText(UserUtil.getUserBean().getNickname());
        }
    }

    @Override
    protected boolean isNeedLogin() {
        return false;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_geren;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState, @Nullable Bundle bundle) {

    }

    @Override
    protected void initData() {
        GlideUtil.showImg(this, UserUtil.getUserBean().getAvatar(), touxiang);
        nackname.setText(UserUtil.getUserBean().getNickname());
    }

    @OnClick({R.id.nicheng, R.id.touxiang})
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.nicheng) {
            Intent intent = new Intent(this, NickNameSet.class);
            startActivity(intent);
        } else if (id == R.id.touxiang) {
            UpdateImageUtil.getInstance().startSelectorWithCrop(GeRen.this, "face", new OnUpdateImgListener() {
                @Override
                public void onSuccess(String url, File file) {
                    Map<String, String> map = new HashMap<>();
                    map.put("avatar", url);
                    HttpClient.getInstance().posts(HttpUtil.MEMBERUPDATE, map, new TradeHttpCallback<JsonBean<InfoBean>>() {
                        @Override
                        public void onSuccess(JsonBean<InfoBean> data) {
                            if (null == data || null == data.getData()) {
                                return;
                            }
                            GlideUtil.showImg(GeRen.this, data.getData().getAvatar(), touxiang);
                            UserBean myuserBean = UserUtil.getUserBean();
                            myuserBean.setAvatar(data.getData().getAvatar());
                            UserUtil.setUserBean(myuserBean);
                        }

                        @Override
                        public boolean showLoadingDialog() {
                            return true;
                        }
                    });

                }
            });
        }
    }

}
