package com.example.hasee.quweidou.utils;

import android.content.Context;

/**
 * Created by ken on 2016/12/26.
 */

public class ScreenUtil {

    /**
     * 获取状态栏的高度
     * int resid = context.getResources().getIdentifier("icon_logo", "drawable", context.getPackageName())//基于资源名称的字符串找到资源所对应的ID
       iv.setImageResourece(resid);
    * @return
     */
    public static int getStatusHeight(Context context){
        int resid = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if(resid > 0){
            return context.getResources().getDimensionPixelSize(resid);//通过资源id获得资源所对应的值
        }
        return -1;
    }

    /**
     * 获得屏幕的宽度
     * @return
     */
    public static int getScreenWidth(Context context){
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获得屏幕的高度
     * @return
     */
    public static int getScreenHeight(Context context){
        return context.getResources().getDisplayMetrics().heightPixels;
    }
}
