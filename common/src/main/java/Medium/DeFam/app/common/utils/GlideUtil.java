package Medium.DeFam.app.common.utils;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;

import java.io.File;

import Medium.DeFam.app.common.Constants;
import Medium.DeFam.app.common.R;

public class GlideUtil {
    /**
     * 图片重新加载
     *
     * @param context
     */
    public static void resumeShowImg(Context context) {
        Glide.with(context).pauseRequests();
    }

    /**
     * 图片暂停加载
     *
     * @param context
     */
    public static void pauseShowImg(Context context) {
        Glide.with(context).resumeRequests();
    }

    /**
     * 从url 加载图片
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void showImg(Context context, String url, ImageView imageView) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        if (!url.startsWith("http")) {
            url = Constants.HOST + url;
        } /*else if (url.endsWith(".avif")) {
           // Log.i("zmh", "-" + url);
            // 实例化 CloudInfinite，用来构建请求图片请求连接；
            CloudInfinite cloudInfinite = new CloudInfinite();
// 根据用户所选万象基础功能 options 进行 Transformation；
            CITransformation transform = new CITransformation();
            transform.format(CIImageFormat.AVIF, CIImageLoadOptions.LoadTypeUrlFooter);
// 构建图片 CIImageLoadRequest

            try {
                CIImageLoadRequest request = cloudInfinite.requestWithBaseUrlSync(new URL(url), transform);
                Glide.with(context).load(request.getUrl().toString()).error(R.mipmap.mycenter_3).into(imageView);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }


            return;
        }*/
        /*.placeholder(R.mipmap.shibai)*/

        if (null == imageView) {
            return;
        }


        Glide.with(context).load(url).priority(Priority.HIGH)
                .dontAnimate()
                .placeholder(R.mipmap.icon_banner_def)
                .error(R.mipmap.icon_banner_def)
                .into(imageView);
//                . skipMemoryCache(true)
//                .diskCacheStrategy(DiskCacheStrategy.SOURCE)


    }

    public static void showBiImg(Context context, String url, ImageView imageView) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        if (!url.startsWith("http")) {
            url = Constants.HOST + url;
        }
        if (null == imageView) {
            return;
        }
        Glide.with(context)
                .load(url)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        imageView.post(new Runnable() {
                            @Override
                            public void run() {
                                showImg(context, Constants.HOST + "/coin-image/default.png", imageView);
                            }
                        });
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                }).into(imageView);
    }

    public interface CbGetImg {
        public void onGet(Bitmap img);
    }

    public static void showImg(Context context, String url, ImageView imageView, CbGetImg cbGetImg) {
        if (!TextUtils.isEmpty(url) && !url.startsWith("http")) {
            url = Constants.HOST + url;
        }
        if (isValidContext(context)) {
            Glide.with(context).asBitmap().load(url).placeholder(R.mipmap.icon_banner_def).error(R.mipmap.icon_banner_def).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                    if (null != imageView) {
                        imageView.setImageBitmap(resource);
                    }
                    cbGetImg.onGet(resource);
                }

            });
        } else {
            Log.i("zmh", "Picture loading failed,context is null");
        }
    }

    /**
     * 从资源id加载图片
     *
     * @param context
     * @param res
     * @param imageView
     */
    public static void showImg(Context context, int res, ImageView imageView) {
        if (isValidContext(context)) {
            Glide.with(context).load(res).into(imageView);
        } else {
            Log.i("zmh", "Picture loading failed,context is null");
        }
    }

    /**
     * 从资源id加载图片
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void showSDImg(Context context, String url, ImageView imageView) {
        if (null == imageView) {
            return;
        }
        Glide.with(context).load(url).placeholder(R.mipmap.icon_banner_def).error(R.mipmap.icon_banner_def).into(imageView);
    }

    public static void showSDImg(Context context, int url, ImageView imageView) {
        if (null == imageView) {
            return;
        }
        Glide.with(context).load(url).into(imageView);
    }

    public static void showFileImg(Context context, File url, ImageView imageView) {
        if (null == imageView) {
            return;
        }
        Glide.with(context).load(url).into(imageView);
    }

    /**
     * 从localpath/url 加载图片
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void showAllImg(Context context, String url, ImageView imageView) {
        if (isValidContext(context)) {
            Glide.with(context).load(url).placeholder(R.mipmap.icon_banner_def).error(R.mipmap.icon_banner_def).into(imageView);
        } else {
            Log.i("zmh", "Picture loading failed,context is null");
        }
    }

    public static boolean isValidContext(final Context context) {
        if (context == null) {
            return false;
        }
        if (context instanceof Activity) {
            final Activity activity = (Activity) context;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                if (activity.isDestroyed() || activity.isFinishing()) {
                    return false;
                }
            }
        }
        return true;
    }

    public static String getHttpUrl(String url) {
        if (!TextUtils.isEmpty(url) && !url.startsWith("http")) {
            url = Constants.HOST + url;
        }
        return url;
    }
}
