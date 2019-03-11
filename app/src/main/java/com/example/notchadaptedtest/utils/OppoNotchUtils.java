package com.example.notchadaptedtest.utils;

import android.content.Context;

/**
 * @description: Oppo刘海屏适配工具类
 * @author: zhukai
 * @date: 2019/2/26 9:43
 */
public class OppoNotchUtils {

    /**
     * 判断是否有刘海屏
     *
     * @param context
     * @return true：有刘海屏；false：没有刘海屏
     */
    public static boolean hasNotch(Context context) {
        return context.getPackageManager().hasSystemFeature("com.oppo.feature.screen.heteromorphism");
    }
}
