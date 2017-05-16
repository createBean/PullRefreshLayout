package com.yu.pullrefresh;

import android.app.Activity;
import android.util.DisplayMetrics;

/**
 * Created by yu on 2017/5/16.
 */

public class DisplayUtil {
    // 屏幕宽度（像素）
    public static int getWindowWidth(Activity context) {
        DisplayMetrics metric = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric.widthPixels;
    }
}
