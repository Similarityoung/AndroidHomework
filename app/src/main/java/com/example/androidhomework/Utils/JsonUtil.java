package com.example.androidhomework.Utils;

import android.os.Environment;

import com.alibaba.fastjson.JSON;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.List;

public class JsonUtil {

    //写出json文件
    public static void writeJson(Object obj, String filename) throws Exception {
        File sdcard = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        String path = sdcard.getPath();
        if (obj == null) {
            return;
        }
        String jsonStr = JSON.toJSONString(obj);
        File file = new File(path+File.separator, filename);
        if(!file.exists()){
            file.createNewFile();
        }
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(jsonStr.getBytes());
        fileOutputStream.flush();
        fileOutputStream.close();
    }

    //读入json文件
    public static <T> List<T> readJsonToArray(String filename, Class<T> clazz) throws Exception {
        File sdcard = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        String path = sdcard.getPath();
        File file = new File(path+File.separator, filename);
        //        File file = new File(filename);
        if (!file.exists() || file.length() == 0) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        FileReader reader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(reader);
        String str;
        while ((str = bufferedReader.readLine()) != null) {
            sb.append(str);
        }
        bufferedReader.close();
        return JSON.parseArray(sb.toString(), clazz);
    }


}


