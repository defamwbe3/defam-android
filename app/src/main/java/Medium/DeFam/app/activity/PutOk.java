package Medium.DeFam.app.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import Medium.DeFam.app.R;
import Medium.DeFam.app.common.base.BaseActivity;
import butterknife.OnClick;


public class PutOk extends BaseActivity {
    @Override
    protected boolean isNeedLogin() {
        return false;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_putok;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState, @Nullable Bundle bundle) {

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.chakan})
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.chakan) {
            finish();
        }
    }

}
