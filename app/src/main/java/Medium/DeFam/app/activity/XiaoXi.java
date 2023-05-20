package Medium.DeFam.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import Medium.DeFam.app.R;
import Medium.DeFam.app.common.base.BaseActivity;
import butterknife.OnClick;

public class XiaoXi extends BaseActivity {
    @Override
    protected boolean isNeedLogin() {
        return false;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_xiaoxi;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState, @Nullable Bundle bundle) {

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.zan, R.id.huifu, R.id.guanzhu})
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.zan) {
            Intent intent = new Intent(this, XiaoXiList.class);
            intent.putExtra("mold", "good");
            startActivity(intent);
        } else if (id == R.id.huifu) {
            Intent intent = new Intent(this, XiaoXiList.class);
            intent.putExtra("mold", "reply");
            startActivity(intent);
        } else if (id == R.id.guanzhu) {
            Intent intent = new Intent(this, XiaoXiList.class);
            intent.putExtra("mold", "follow");
            startActivity(intent);
        }
    }
}
