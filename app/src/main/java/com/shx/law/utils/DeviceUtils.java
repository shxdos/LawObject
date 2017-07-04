package com.shx.law.utils;

import android.content.Context;

/**
 * Created by 邵鸿轩 on 2016/11/24.
 */

public class DeviceUtils {
    /**
     * dp转px
     * @param context
     * @param dp
     * @return
     */
    public static int dp2Px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    /**
     * px转dp
     * @param context
     * @param px
     * @return
     */
    public int px2Dp(Context context, float px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }
}
