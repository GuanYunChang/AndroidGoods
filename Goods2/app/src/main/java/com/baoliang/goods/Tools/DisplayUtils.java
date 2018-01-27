package com.baoliang.goods.Tools;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * 获取屏幕的信息
 */
public class DisplayUtils {
    /**
     * 获取屏幕宽度
     * @param ctx
     * @return
     */
    public static int getSreenWidth(Context ctx)
    {

        WindowManager wm=(WindowManager)ctx.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm=new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return  dm.widthPixels;
    }

    /**
     * 获取屏幕高度
     * @param ctx
     * @return
     */
    public static int getSreenHeight(Context ctx)
    {

        WindowManager wm=(WindowManager)ctx.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm=new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return  dm.heightPixels;
    }
} 