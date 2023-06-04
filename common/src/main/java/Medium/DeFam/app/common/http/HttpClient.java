package Medium.DeFam.app.common.http;

import android.text.TextUtils;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.Callback;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.MemoryCookieStore;
import com.lzy.okgo.request.GetRequest;

import Medium.DeFam.app.common.CommonAppContext;
import Medium.DeFam.app.common.Constants;
import Medium.DeFam.app.common.utils.UserUtil;

import org.json.JSONObject;

import java.io.File;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Dispatcher;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

/**
 * Created by zmh on 2018/9/17.
 * 数据请求
 */

public class HttpClient {

    private static final int TIMEOUT = 6000;
    private static HttpClient sInstance;
    private OkHttpClient mOkHttpClient;
    private String mUrl;

    private HttpClient() {
        mUrl = Constants.HOST;
    }

    public static HttpClient getInstance() {
        if (sInstance == null) {
            synchronized (HttpClient.class) {
                if (sInstance == null) {
                    sInstance = new HttpClient();
                }
            }
        }
        return sInstance;
    }

    public void init() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS);
        builder.readTimeout(TIMEOUT, TimeUnit.MILLISECONDS);
        builder.writeTimeout(TIMEOUT, TimeUnit.MILLISECONDS);
        builder.cookieJar(new CookieJarImpl(new MemoryCookieStore()));
        builder.retryOnConnectionFailure(true);
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequests(20000);
        dispatcher.setMaxRequestsPerHost(10000);
        builder.dispatcher(dispatcher);

        //输出HTTP请求 响应信息
       HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("http");
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BASIC);
        builder.addInterceptor(loggingInterceptor);
        mOkHttpClient = builder.build();

        OkGo.getInstance().init(CommonAppContext.sInstance)
                .setOkHttpClient(mOkHttpClient)
                .setCacheMode(CacheMode.NO_CACHE)
                .setRetryCount(1);

    }

    public <T> void gets(String serviceName, Map<String, String> map, TradeHttpCallback<T> callback) {
        GetRequest request = OkGo.<T>get(mUrl + serviceName);
        if(!TextUtils.isEmpty(UserUtil.getToken())){
            request.headers("Authorization", "Bearer " + UserUtil.getToken());
        }
        request.tag(serviceName)
                .params(map)
                .execute(callback);
    }

    public <T> void posts(String serviceName, Map<String, String> map, TradeHttpCallback<T> callback) {
        OkGo.<T>post(mUrl + serviceName)
                .headers("Authorization", "Bearer " + UserUtil.getToken())
                .tag(serviceName)
                .params(map)
                .execute(callback);
    }

    public <T> void put(String serviceName, JSONObject json, TradeHttpCallback<T> callback) {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, null == json ? "" : json.toString());
        OkGo.<T>put(mUrl + serviceName)
                .headers("Authorization", "Bearer " + UserUtil.getToken())
                .upRequestBody(body)
                .tag(serviceName)
                .execute(callback);
    }

    public <T> void deletes(String serviceName, Map<String, String> map, TradeHttpCallback<T> callback) {
        OkGo.<T>delete(mUrl + serviceName)
                .headers("Authorization", "Bearer " + UserUtil.getToken())
                .tag(serviceName)
                .params(map)
                .execute(callback);
    }

    public <T> void putFile(String serviceName, File file, TradeHttpCallback<T> callback) {
        OkGo.<T>put(mUrl + serviceName)
                .headers("Authorization", "Bearer " + UserUtil.getToken())
                .tag(serviceName)
                .params("file", file)
                .execute(callback);
    }

    public <T> void upload(String url, String key, File file, Callback<T> callback) {
        // TODO: 2017/10/13  加密 时间戳等 请求日志打印
        OkGo.<T>post(mUrl + url)
                .tag(url)
                .headers("Authorization", "Bearer " + UserUtil.getToken())
                .params(key, file)
                .execute(callback);
    }

    public void cancel(String tag) {
        OkGo.cancelTag(mOkHttpClient, tag);
    }


}
