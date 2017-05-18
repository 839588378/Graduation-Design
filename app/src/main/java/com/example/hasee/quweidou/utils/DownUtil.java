package com.example.hasee.quweidou.utils;

import android.os.Handler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ken on 2016/12/26.
 * 下载工具类
 */
public class DownUtil {

    private static Handler handler = new Handler();

    /**
     * 下载方法
     */
    public static void down(final String url, final OnDownListener onDownListener){
        new Thread(){
            @Override
            public void run() {
                final String json = requestJson(url);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //主线程执行
                        if(onDownListener != null){
                            onDownListener.downSucc(json);
                        }
                    }
                });
            }
        }.start();
    }

    /**
     * 通过url下载json
     * @return
     */
    private static String requestJson(String urlstr){
        InputStream in = null;
        ByteArrayOutputStream out = null;
        try {
            URL url = new URL(urlstr);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(3000);
            in = httpURLConnection.getInputStream();
            out = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024 * 8];
            int len;
            while((len = in.read(buffer)) != -1){
                out.write(buffer, 0, len);
            }

            byte[] buffers = out.toByteArray();
            return new String(buffers);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public interface OnDownListener{
        void downSucc(String json);
    }
}
