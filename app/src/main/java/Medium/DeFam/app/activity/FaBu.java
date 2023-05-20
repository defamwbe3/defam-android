package Medium.DeFam.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import Medium.DeFam.app.R;
import Medium.DeFam.app.common.ActivityRouter;
import Medium.DeFam.app.common.base.BaseActivity;
import Medium.DeFam.app.common.utils.UserUtil;
import butterknife.OnClick;

public class FaBu extends BaseActivity {
    @Override
    protected boolean isNeedLogin() {
        return false;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_fabu;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState, @Nullable Bundle bundle) {

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.guanbi, R.id.guandian, R.id.changwen})
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.guanbi) {
            finish();
        } else if (id == R.id.guandian) {
            Intent intent = new Intent(this, GuanDianPut.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.changwen) {
            if (!isLogined()) {
                ActivityRouter.toPoint(this, ActivityRouter.Mall.MALL_LOGIN);
                return;
            }
            Web.startWebActivity(this, "", "https://h5.cjlbzx.szyqa.com#/pages/release/postarticle?token=" + UserUtil.getToken() + "&type=Android", "", false);
            finish();
        }
    }

}
