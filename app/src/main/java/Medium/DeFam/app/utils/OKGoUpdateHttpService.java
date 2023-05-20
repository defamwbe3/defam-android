package Medium.DeFam.app.utils;


import androidx.annotation.NonNull;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.xuexiang.xupdate.proxy.IUpdateHttpService;

import java.io.File;
import java.util.Map;
import java.util.TreeMap;

/**
 * 使用okgo
 *
 */
public class OKGoUpdateHttpService implements IUpdateHttpService {

    public OKGoUpdateHttpService() {
    }

    @Override
    public void asyncGet(@NonNull String url, @NonNull Map<String, Object> params, final @NonNull Callback callBack) {
        OkGo.<String>get(url)
                .params(transform(params))
                .tag(url)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callBack.onSuccess(response.body());
                    }

                    @Override
                    public void onError(Response<String> response) {
                        callBack.onError(response.getException());
                    }
                });
    }

    @Override
    public void asyncPost(@NonNull String url, @NonNull Map<String, Object> params, final @NonNull Callback callBack) {
        OkGo.<String>post(url)
                .params(transform(params))
                .tag(url)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        //LogUtil.d("OKGoUpdateHttpService-PostOnSuccess: " + response.body());
                        callBack.onSuccess(response.body());
                    }

                    @Override
                    public void onError(Response<String> response) {
                        //LogUtil.e("OKGoUpdateHttpService-PostOnSuccess: " + response.body());
                        callBack.onError(response.getException());
                    }
                });
    }

    @Override
    public void download(@NonNull String url, @NonNull String path, @NonNull String fileName, final @NonNull DownloadCallback callback) {
        OkGo.<File>get(url)
                .tag(url)
                .execute(new FileCallback(path, fileName) {
                    @Override
                    public void onSuccess(Response<File> response) {
                        callback.onSuccess(response.body());
                    }

                    @Override
                    public void onError(Response<File> response) {
                        callback.onError(response.getException());
                    }

                    @Override
                    public void onStart(Request<File, ? extends Request> request) {
                        callback.onStart();
                    }

                    @Override
                    public void downloadProgress(Progress progress) {
                        callback.onProgress(progress.fraction, progress.totalSize);
                    }
                });
    }

    @Override
    public void cancelDownload(@NonNull String url) {
        OkGo.getInstance().cancelTag(url);
    }

    private Map<String, String> transform(Map<String, Object> params) {
        Map<String, String> map = new TreeMap<>();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            map.put(entry.getKey(), entry.getValue().toString());
        }
        return map;
    }


}


