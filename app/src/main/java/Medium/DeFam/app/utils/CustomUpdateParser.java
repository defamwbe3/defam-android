package Medium.DeFam.app.utils;


import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xuexiang.xupdate.entity.UpdateEntity;
import com.xuexiang.xupdate.listener.IUpdateParseCallback;
import com.xuexiang.xupdate.proxy.IUpdateParser;
import com.xuexiang.xupdate.utils.UpdateUtils;

import Medium.DeFam.app.bean.AppVerBean;
import Medium.DeFam.app.common.http.JsonBean;

public class CustomUpdateParser implements IUpdateParser {
    private Context context;

    public CustomUpdateParser(Context context) {
        this.context = context;
    }

    @Override
    public UpdateEntity parseJson(String json) throws Exception {
        Log.i("mylog1", json);
        JsonBean<AppVerBean> responseBean = new Gson().fromJson(json, new TypeToken<JsonBean<AppVerBean>>() {
        }.getType());
        AppVerBean result = responseBean.getData();
        if (result != null) {
            return new UpdateEntity()
                    .setHasUpdate(true)//!version.equals(UpdateUtils.getVersionName(context))
                    .setIsIgnorable(false)
                    .setForce("2".equals(result.getForceUpdate()))
                    .setVersionCode(Integer.parseInt(result.getVersionCode()))
                    .setVersionName(result.getVersionName())
                    .setUpdateContent(result.getVersionInfo())
                    .setDownloadUrl(result.getDownloadUrl())
                    .setSize(0);

        }
        return null;
    }

    @Override
    public void parseJson(String json, IUpdateParseCallback callback) throws Exception {

    }

    @Override
    public boolean isAsyncParser() {
        return false;
    }
}




