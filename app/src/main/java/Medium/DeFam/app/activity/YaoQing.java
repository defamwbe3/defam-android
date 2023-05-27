package Medium.DeFam.app.activity;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.XXPermissions;
import com.hjq.toast.Toaster;
import com.king.zxing.util.CodeUtils;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Medium.DeFam.app.R;
import Medium.DeFam.app.bean.ConfigBean;
import Medium.DeFam.app.common.base.BaseActivity;
import Medium.DeFam.app.common.http.HttpClient;
import Medium.DeFam.app.common.http.JsonBean;
import Medium.DeFam.app.common.http.TradeHttpCallback;
import Medium.DeFam.app.common.titlebar.CommonTitleBar;
import Medium.DeFam.app.common.utils.FileUtilMy;
import Medium.DeFam.app.common.utils.GlideUtil;
import Medium.DeFam.app.common.utils.UserUtil;
import Medium.DeFam.app.utils.HttpUtil;
import butterknife.BindView;
import butterknife.OnClick;


public class YaoQing extends BaseActivity {
    @BindView(R.id.poster)
    RoundedImageView poster;
    @BindView(R.id.img)
    RoundedImageView img;
    @BindView(R.id.myview)
    RelativeLayout myview;
    private Bitmap bitmap;

    @Override
    protected boolean isNeedLogin() {
        return false;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_yaoqing;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState, @Nullable Bundle bundle) {

    }

    @Override
    protected void initData() {
        getData();
    }

    @Override
    protected void onTitleListener(View v, int action, String extra) {
        if (action == CommonTitleBar.ACTION_LEFT_BUTTON) {
            finish();
        } else if (action == CommonTitleBar.ACTION_RIGHT_BUTTON) {
            Intent intent = new Intent(this, YaoQingList.class);
            startActivity(intent);
        }
    }

    private void getData() {
        Map<String, String> map = new HashMap<>();
        map.put("module", "poster");
        HttpClient.getInstance().gets(HttpUtil.CINFIG, map, new TradeHttpCallback<JsonBean<ConfigBean>>() {
            @Override
            public void onSuccess(JsonBean<ConfigBean> data) {
                if (null == data || null == data.getData()) {
                    return;
                }
                GlideUtil.showImg(YaoQing.this, data.getData().getPoster(), poster);
                img.setTag(data.getData().getUrl());
                bitmap = CodeUtils.createQRCode(data.getData().getUrl(), 600);
                img.setImageBitmap(bitmap);
            }


        });
    }

    @OnClick({R.id.fuzhi, R.id.yaoqing, R.id.baocun})
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.fuzhi) {
            //获取剪贴板管理器：
            ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            // 创建普通字符型ClipData
            ClipData mClipData = ClipData.newPlainText("Label", img.getTag().toString());
            // 将ClipData内容放到系统剪贴板里。
            cm.setPrimaryClip(mClipData);
            Toaster.show("复制成功");
        } else if (id == R.id.yaoqing) {
            //获取剪贴板管理器：
            ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            // 创建普通字符型ClipData
            ClipData mClipData = ClipData.newPlainText("Label", UserUtil.getUserBean().getInvite_code());
            // 将ClipData内容放到系统剪贴板里。
            cm.setPrimaryClip(mClipData);
            Toaster.show("复制成功");
        } else if (id == R.id.baocun) {
            XXPermissions.with(YaoQing.this)
                    // 不适配 Android 11 可以这样写
                    //.permission(Permission.Group.STORAGE)
                    // 适配 Android 11 需要这样写，这里无需再写 Permission.Group.STORAGE
                    .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .permission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    .request(new OnPermissionCallback() {

                        @Override
                        public void onGranted(List<String> permissions, boolean all) {
                            if (all) {
                                createBitmap();
                            }
                        }

                        @Override
                        public void onDenied(List<String> permissions, boolean never) {
                            if (never) {
                                Toaster.show("被永久拒绝授权，请手动授予存储权限");
                                // 如果是被永久拒绝就跳转到应用权限系统设置页面
                                XXPermissions.startPermissionActivity(YaoQing.this, permissions);
                            } else {
                                Toaster.show("获取存储权限失败");
                            }
                        }
                    });

        }
    }

    private void createBitmap() {
        String imageName = "JPEG_" + "down" + System.currentTimeMillis() + ".jpg";
        File erweimaImg = new File(FileUtilMy.SDPATH, imageName);
        if (erweimaImg == null) {
            Toaster.show("创建文件夹失败");
            return;
        }
        if (false == myview.isDrawingCacheEnabled()) {
            myview.setDrawingCacheEnabled(true);
        }
        myview.buildDrawingCache();
        Bitmap bitmap = myview.getDrawingCache();
        String path = erweimaImg.getAbsolutePath();
        try {
            FileOutputStream out = new FileOutputStream(path);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        myview.setDrawingCacheEnabled(false);
        // 下面的步骤必须有，不然在相册里找不到图片，若不需要让用户知道你保存了图片，可以不写下面的代码。
        // 把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(getContentResolver(),
                    erweimaImg.getAbsolutePath(), imageName, null);
            Toaster.show("保存成功，请您到 相册/图库 中查看");
        } catch (FileNotFoundException e) {
            Toaster.show("保存失败");
            e.printStackTrace();
        }
        // 最后通知图库更新
        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                Uri.fromFile(new File(erweimaImg.getPath()))));

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != bitmap && !bitmap.isRecycled()) {
            bitmap.recycle();
            bitmap = null;
        }
    }
}
