package Medium.DeFam.app.common.utils;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;

import com.hjq.toast.Toaster;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.impl.LoadingPopupView;
import com.ypx.imagepicker.ImagePicker;
import com.ypx.imagepicker.bean.ImageItem;
import com.ypx.imagepicker.bean.MimeType;
import com.ypx.imagepicker.bean.SelectMode;
import com.ypx.imagepicker.bean.selectconfig.CropConfig;
import com.ypx.imagepicker.data.OnImagePickCompleteListener;

import java.io.File;
import java.util.ArrayList;

import Medium.DeFam.app.common.bean.UploadBean;
import Medium.DeFam.app.common.http.CommonHttpConsts;
import Medium.DeFam.app.common.http.HttpClient;
import Medium.DeFam.app.common.http.JsonBean;
import Medium.DeFam.app.common.http.TradeHttpCallback;
import Medium.DeFam.app.common.interfaces.OnUpdateImgListener;
import Medium.DeFam.app.common.widget.WeChatPresenter;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class UpdateImageUtil {

    private LoadingPopupView popupView;

    public static UpdateImageUtil getInstance() {
        return new UpdateImageUtil();
    }

    public void startSelectorWithCrop(Context mContext, String type, OnUpdateImgListener listener) {
        ImagePicker.withMulti(new WeChatPresenter())
                .setMaxCount(1)
                .mimeTypes(MimeType.ofImage())
                .filterMimeTypes(MimeType.GIF)
                .showCamera(true)//显示拍照
                .setPreview(false)//开启预览
                .setSelectMode(SelectMode.MODE_SINGLE)
                .setSinglePickWithAutoComplete(true)
                .cropSaveInDCIM(false)
                .setCropRatio(1, 1)
                .cropStyle(CropConfig.STYLE_FILL)
                .cropGapBackgroundColor(Color.TRANSPARENT)
                .crop((Activity) mContext, new OnImagePickCompleteListener() {
                    @Override
                    public void onImagePickComplete(ArrayList<ImageItem> items) {
                        compressImage(mContext, items.get(0).getCropUrl(), type, listener);
                    }
                });
    }
    public void startSelectorWithCrop_File(Context mContext, String type, OnUpdateImgListener listener) {
        ImagePicker.withMulti(new WeChatPresenter())
                .setMaxCount(1)
                .mimeTypes(MimeType.ofImage())
                .filterMimeTypes(MimeType.GIF)
                .showCamera(true)//显示拍照
                .setPreview(false)//开启预览
                .setSelectMode(SelectMode.MODE_SINGLE)
                .setSinglePickWithAutoComplete(true)
                .cropSaveInDCIM(false)
                .setCropRatio(1, 1)
                .cropStyle(CropConfig.STYLE_FILL)
                .cropGapBackgroundColor(Color.TRANSPARENT)
                .crop((Activity) mContext, new OnImagePickCompleteListener() {
                    @Override
                    public void onImagePickComplete(ArrayList<ImageItem> items) {
                        compressImage(mContext, items.get(0).getCropUrl(), type, listener);
                    }
                });
    }
    public void startSelector(Context mContext, String type, OnUpdateImgListener listener) {
        ImagePicker.withMulti(new WeChatPresenter())
                .setMaxCount(1)
                .mimeTypes(MimeType.ofImage())
                .filterMimeTypes(MimeType.GIF)
                .showCamera(true)//显示拍照
                .setPreview(false)//开启预览
                .setSelectMode(SelectMode.MODE_SINGLE)
                .setSinglePickWithAutoComplete(true)
                .pick((Activity) mContext, new OnImagePickCompleteListener() {
                    @Override
                    public void onImagePickComplete(ArrayList<ImageItem> items) {
                        compressImage(mContext, items.get(0).getPath(), type, listener);
                    }
                });
    }

    public void compressImage(Context mContext, String path, String type, OnUpdateImgListener listener) {
        if (popupView == null) {
            popupView = new XPopup.Builder(mContext)
                    .asLoading();
        }
        popupView.show();
        Luban.with(mContext)
                .load(path)
                .ignoreBy(100)
                .setTargetDir(FileUtilMy.SDPATH)
                .filter(new CompressionPredicate() {
                    @Override
                    public boolean apply(String path) {
                        return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                    }
                })
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                        // TODO 压缩开始前调用，可以在方法内启动 loading UI
                    }

                    @Override
                    public void onSuccess(File file) {
                        // TODO 压缩成功后调用，返回压缩后的图片文件
                        if("file".equals(type)){
                            dismiss();
                            listener.onSuccess("", file);
                        }else {
                            uploadPic(mContext, file, type, listener);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        // TODO 当压缩过程出现问题时调用
                        Toaster.show("压缩失败: " + e.getMessage());
                        dismiss();
                    }
                }).launch();
    }

    public void uploadPic(Context mContext, File file, String type, OnUpdateImgListener listener) {
        HttpClient.getInstance().upload(CommonHttpConsts.UPLOADS, "file", file, new TradeHttpCallback<JsonBean<UploadBean>>() {
            @Override
            public void onSuccess(JsonBean<UploadBean> bean) {
                listener.onSuccess(bean.getData().getFilePath(), file);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                dismiss();
            }

            @Override
            public boolean showLoadingDialog() {
                return popupView == null ? true : false;
            }
        });
    }

    private void dismiss() {
        if (popupView != null) {
            popupView.smartDismiss();
        }
    }
}

