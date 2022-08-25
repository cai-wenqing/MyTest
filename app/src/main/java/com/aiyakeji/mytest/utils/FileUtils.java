package com.aiyakeji.mytest.utils;

import android.content.Context;
import android.os.Environment;


import com.aiyakeji.mytest.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2017/2/16 0016.
 */

public class FileUtils {

    static public boolean mkdir(String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            return dir.mkdirs();
        }
        return true;
    }

    // 拍照文件临时存储路径
    public static String imageCatchDir(Context context) {
        return basePath(context) + "images" + File.separator + "拍照" + File.separator;
    }

    // 图片缓存目录
    public static String imageCacheDir(Context context) {
        return basePath(context) + "cache" + File.separator + "image" + File.separator;
    }

    public static String basePath(Context context) {
        String path;
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            path = Environment.getExternalStorageDirectory().toString();
        } else {
            path = context.getFilesDir().getPath();
        }
        return path + File.separator + context.getResources().getString(R.string.app_name) + File.separator;
    }


    public static byte[] short2byte(short[] sData) {
        int shortArrsize = sData.length;
        byte[] bytes = new byte[shortArrsize * 2];
        for (int i = 0; i < shortArrsize; i++) {
            bytes[i * 2] = (byte) (sData[i] & 0x00FF);
            bytes[(i * 2) + 1] = (byte) (sData[i] >> 8);
            sData[i] = 0;
        }
        return bytes;

    }


    public static byte[] readBytesFromFile(String path) throws IOException {
        try (InputStream inputStream = new FileInputStream(path)) {
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes);
            inputStream.close();
            return bytes;
        }
    }
}
