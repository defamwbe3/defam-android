package Medium.DeFam.app.common.utils;


import android.graphics.Bitmap;
import android.os.Environment;


import Medium.DeFam.app.common.CommonAppContext;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtilMy {
    private static FileUtilMy instance;
    public static String SDPATH = "";
    public FileUtilMy() {
    }

    public static FileUtilMy getInstance() {
        if (instance == null) {
            synchronized (FileUtilMy.class) {
                if (instance == null) {
                    instance = new FileUtilMy();
                }
            }
        }
        return instance;
    }

    /**
     * sd卡是否可用
     *
     * @return
     */
    private boolean isSdCardAvailable() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    /**
     * 创建根缓存目录
     *
     * @return
     */
    public void createRootPath() {
        if (isSdCardAvailable()) {
            // /sdcard/Android/data/<application package>/cache
            //getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath();
            SDPATH = CommonAppContext.sInstance.getApplicationContext().getExternalCacheDir()
                    .getPath();
        } else {
            // /data/data/<application package>/cache
            SDPATH = CommonAppContext.sInstance.getApplicationContext().getCacheDir().getPath();
        }
        CreateFile();
    }

    /**
     * 创建文件夹
     */
    private void CreateFile(){
        File dirFirstFolder = new File(FileUtilMy.SDPATH);
        if(!dirFirstFolder.exists())
        { //如果该文件夹不存在，则进行创建
            dirFirstFolder.mkdirs();//创建文件夹
        }
    }

    /**
     * 获取map 缓存和读取目录
     */
    public  String getSdCacheDir() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            File fExternalStorageDirectory = Environment
                    .getExternalStorageDirectory();
            File autonaviDir = new File(
                    SDPATH, "amapsdk");
            boolean result = false;
            if (!autonaviDir.exists()) {
                result = autonaviDir.mkdir();
            }
            File minimapDir = new File(autonaviDir,
                    "offlineMap");
            if (!minimapDir.exists()) {
                result = minimapDir.mkdir();
            }
            return minimapDir.toString() + "/";
        } else {
            return "";
        }
    }



    /**
     * 保存图片
     * @param bm
     * @param picName
     */
    public void saveBitmap(Bitmap bm, String picName) {
        try {
            File f = new File(SDPATH, picName + ".JPEG");
            if (f.exists()) {
                f.delete();
            }
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 无压缩保存图片 并返回保存位置
     * @return
     */
    public String saveBitmap(Bitmap bm) {
        String savePath = null;
        try {
            String fileName = String.valueOf(System.currentTimeMillis());
            File f = new File(SDPATH, fileName + ".JPEG");
            if (f.exists()) {
                f.delete();
            }
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            savePath = f.getAbsolutePath();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return savePath;
    }

    /**
     * 创建SD目录
     * @param dirName
     * @return
     * @throws IOException
     */
    public File createSDDir(String dirName) throws IOException {
        File dir = new File(SDPATH + dirName);
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {

            System.out.println("createSDDir:" + dir.getAbsolutePath());
            System.out.println("createSDDir:" + dir.mkdir());
        }
        return dir;
    }



    /**
     * 文件是存在的
     * @param path
     * @return
     */
    public boolean fileIsExists(String path) {
        try {
            File f = new File(path);
            if (!f.exists()) {
                return false;
            }
        } catch (Exception e) {

            return false;
        }
        return true;
    }
    /**
     * 删除文件
     * @param fileName
     */
    public void delFile(String fileName){
        File file = new File(SDPATH + fileName);
        if(file.isFile()){
            file.delete();
        }
        file.exists();
    }

    /**
     * 删除文件夹
     */
    public void deleteDir() {
        deleteAllFiles(new File(SDPATH));
    }



    /**
     * 清空文件夹内容
     * @param root
     */
    public static void deleteAllFiles(File root) {
        File files[] = root.listFiles();
        if (files != null)
            for (File f : files) {
                if (f.isDirectory()) { // 判断是否为文件夹
                    deleteAllFiles(f);
                    try {
                        f.delete();
                    } catch (Exception e) {
                    }
                } else {
                    if (f.exists()) { // 判断是否存在
                        deleteAllFiles(f);
                        try {
                            f.delete();
                        } catch (Exception e) {
                        }
                    }
                }
            }
    }


}

