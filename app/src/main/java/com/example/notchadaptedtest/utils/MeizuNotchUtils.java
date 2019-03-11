package com.example.notchadaptedtest.utils;

import android.content.Context;
import android.provider.Settings;
import android.view.Window;

import java.lang.reflect.Field;

/**
 * @description: 魅族刘海屏适配工具类
 * @author: zhukai
 * @date: 2019/3/8 16:44
 */
public class MeizuNotchUtils {


    /**
     * 判断是否有刘海屏
     *
     * @param context
     * @return true：有刘海屏；false：没有刘海屏
     */
    public static boolean hasNotch(Context context) {
        boolean ret = false;
        try {
            Class clazz = Class.forName("flyme.config.FlymeFeature");
            Field field = clazz.getDeclaredField("IS_FRINGE_DEVICE");
            ret = (boolean) field.get(null);
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
        int fhid = context.getResources().getIdentifier("fringe_height", "dimen", "android");
        if (fhid > 0) {
            result = context.getResources().getDimensionPixelSize(fhid);
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
        int fwid = context.getResources().getIdentifier("fringe_width", "dimen", "android");
        if (fwid > 0) {
            result = context.getResources().getDimensionPixelSize(fwid);
        }
        return result;
    }

    /**
     * 获取默认和隐藏刘海区开关值
     *
     * @param context
     * @return false表示“默认”，true表示“隐藏显示区域”
     */
    public static boolean getIsNotchSwitchOpen(Context context) {
        return Settings.Global.getInt(context.getContentResolver(), "mz_fringe_hide", 0) == 1;
    }

    /**
     * 设置应用窗口在刘海屏手机使用刘海区
     * <p>
     * 通过添加窗口FLAG的方式设置页面使用刘海区显示
     *
     * @param window 应用页面window对象
     */
    public static void setFullScreenWindowLayoutInDisplayCutout(Window window) {

    }
}
