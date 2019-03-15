package com.example.notchadaptedtest.utils;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;
import android.view.Window;

import java.lang.reflect.Method;

/**
 * @description: 小米刘海屏适配工具类
 * @author: zhukai
 * @date: 2019/2/26 9:42
 */
public class XiaomiNotchUtils {

    /*刘海屏全屏显示FLAG*/
    public static final int FLAG_NOTCH_SUPPORT = 0x00000100; // 开启配置
    public static final int FLAG_NOTCH_PORTRAIT = 0x00000200; // 竖屏配置
    public static final int FLAG_NOTCH_HORIZONTAL = 0x00000400; // 横屏配置

    /**
     * 判断是否有刘海屏
     *
     * @param context
     * @return true：有刘海屏；false：没有刘海屏
     */
    public static boolean hasNotch(Context context) {
        boolean ret = false;
        try {
            ClassLoader cl = context.getClassLoader();
            Class SystemProperties = cl.loadClass("android.os.SystemProperties");
            Method get = SystemProperties.getMethod("getInt", String.class, int.class);
            ret = (Integer) get.invoke(SystemProperties, "ro.miui.notch", 0) == 1;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return ret;
        }
    }

    /**
     * 获取刘海屏高度
     *
     * @param context
     * @return
     */
    public static int getNotHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("notch_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 获取刘海屏宽度
     *
     * @param context
     * @return
     */
    public static int getNotWidth(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("notch_width", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 设置应用窗口在刘海屏手机使用刘海区
     * <p>
     * 通过添加窗口FLAG的方式设置页面使用刘海区显示
     *
     * @param window 应用页面window对象
     */
    public static void setFullScreenWindowLayoutInDisplayCutout(Window window) {
        // 竖屏绘制到耳朵区
        int flag = FLAG_NOTCH_SUPPORT | FLAG_NOTCH_PORTRAIT;
        try {
            Method method = Window.class.getMethod("addExtraFlags",
                    int.class);
            method.invoke(window, flag);
        } catch (Exception e) {
            Log.e("test", "addExtraFlags not found.");
        }
    }

    /**
     * 判断是否隐藏屏幕刘海
     *
     * @param context
     * @return false：未隐藏刘海区域 true：隐藏了刘海区域
     */
    public static boolean getIsNotchHideOpen(Context context) {
        return Settings.Global.getInt(context.getContentResolver(), "force_black", 0) == 1;
    }
}
