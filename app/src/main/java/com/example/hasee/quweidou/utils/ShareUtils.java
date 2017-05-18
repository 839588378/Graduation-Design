package com.example.hasee.quweidou.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by HASEE on 2017/4/17.
 */

public class ShareUtils {
    public static final String SHARE_NAME = "neihan";
    public static final String FIRST_RUN = "isFirstRun";


    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    /**
     *
     */

    public static void saveFirstRun(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARE_NAME,Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(FIRST_RUN,false).commit();
    }

    public static boolean isFirstRun(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARE_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(FIRST_RUN,true);
    }

    /**
     * 初始化共享参数
     * @param context
     */
    public static void init(Context context){
        sharedPreferences = context.getSharedPreferences("configer", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    /**
     * 写入int的数据
     */
    public static void setInt(String name, int value){
        editor.putInt(name, value);
        editor.commit();
    }

    /**
     * 读出int类型
     * @param name
     * @return
     */
    public static int getInt(String name){
        return sharedPreferences.getInt(name, -1);
    }

    /**
     * 写入String的数据
     */
    public static void setString(String name, String value){
        editor.putString(name, value);
        editor.commit();
    }

    /**
     * 读出String类型
     * @param name
     * @return
     */
    public static String getString(String name){
        return sharedPreferences.getString(name, null);
    }

}
