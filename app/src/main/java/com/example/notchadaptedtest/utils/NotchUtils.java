package com.example.notchadaptedtest.utils;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @description: 刘海屏适配工具类（全）
 * @author: zhukai
 * @date: 2019/3/14 13:44
 */
public class NotchUtils {

    /*华为刘海屏全屏显示FLAG*/
    public static final int FLAG_NOTCH_SUPPORT_HW = 0x00010000;
    public static final String DISPLAY_NOTCH_STATUS = "display_notch_status";
    /*小米刘海屏全屏显示FLAG*/
    public static final int FLAG_NOTCH_SUPPORT_MI = 0x00000100; // 开启配置
    public static final int FLAG_NOTCH_PORTRAIT_MI = 0x00000200; // 竖屏配置
    public static final int FLAG_NOTCH_HORIZONTAL_MI = 0x00000400; // 横屏配置
    /*Vivo手机屏幕属性参数*/
    public static final int VIVO_NOTCH = 0x00000020; // 是否有刘海
    public static final int VIVO_FILLET = 0x00000008; // 是否有圆角

    /**
     * 华为手机判断是否有刘海屏
     *
     * @param context
     * @return true：有刘海屏；false：没有刘海屏
     */
    public static boolean hasNotchAtHuawei(Context context) {
        boolean ret = false;
        try {
            ClassLoader cl = context.getClassLoader();
            Class HwNotchSizeUtil = cl.loadClass("com.huawei.android.util.HwNotchSizeUtil");
            Method get = HwNotchSizeUtil.getMethod("hasNotchInScreen");
            ret = (boolean) get.invoke(HwNotchSizeUtil);
        } catch (ClassNotFoundException e) {
            Log.e("test", "hasNotchInScreen ClassNotFoundException");
        } catch (NoSuchMethodException e) {
            Log.e("test", "hasNotchInScreen NoSuchMethodException");
        } catch (Exception e) {
            Log.e("test", "hasNotchInScreen Exception");
        } finally {
            return ret;
        }
    }

    /**
     * 华为手机获取刘海尺寸
     *
     * @param context
     * @return int[0]值为刘海宽度 int[1]值为刘海高度
     */
    public static int[] getNotchSizeAtHuawei(Context context) {
        int[] ret = new int[]{0, 0};
        try {
            ClassLoader cl = context.getClassLoader();
            Class HwNotchSizeUtil = cl.loadClass("com.huawei.android.util.HwNotchSizeUtil");
            Method get = HwNotchSizeUtil.getMethod("getNotchSize");
            ret = (int[]) get.invoke(HwNotchSizeUtil);
        } catch (ClassNotFoundException e) {
            Log.e("test", "getNotchSize ClassNotFoundException");
        } catch (NoSuchMethodException e) {
            Log.e("test", "getNotchSize NoSuchMethodException");
        } catch (Exception e) {
            Log.e("test", "getNotchSize Exception");
        } finally {
            return ret;
        }
    }

    /**
     * 华为手机设置应用窗口在刘海屏手机使用刘海区
     *
     * @param window 应用页面window对象
     */
    public static void setFullScreenAtHuawei(Window window) {
        if (window == null) {
            return;
        }
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        try {
            Class layoutParamsExCls = Class.forName("com.huawei.android.view.LayoutParamsEx");
            Constructor con = layoutParamsExCls.getConstructor(WindowManager.LayoutParams.class);
            Object layoutParamsExObj = con.newInstance(layoutParams);
            Method method = layoutParamsExCls.getMethod("addHwFlags", int.class);
            method.invoke(layoutParamsExObj, FLAG_NOTCH_SUPPORT_HW);
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException
                | InvocationTargetException e) {
            Log.e("test", "hw add notch screen flag api error");
        } catch (Exception e) {
            Log.e("test", "other Exception");
        }
    }

    /**
     * 华为手机恢复应用不使用刘海区显示
     *
     * @param window 应用页面window对象
     */
    public static void setNotFullScreenAtHuawei(Window window) {
        if (window == null) {
            return;
        }
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        try {
            Class layoutParamsExCls = Class.forName("com.huawei.android.view.LayoutParamsEx");
            Constructor con = layoutParamsExCls.getConstructor(WindowManager.LayoutParams.class);
            Object layoutParamsExObj = con.newInstance(layoutParams);
            Method method = layoutParamsExCls.getMethod("clearHwFlags", int.class);
            method.invoke(layoutParamsExObj, FLAG_NOTCH_SUPPORT_HW);
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException
                | InvocationTargetException e) {
            Log.e("test", "hw clear notch screen flag api error");
        } catch (Exception e) {
            Log.e("test", "other Exception");
        }
    }

    /**
     * 华为手机获取默认和隐藏刘海区开关值
     *
     * @param context
     * @return 0表示“默认”，1表示“隐藏显示区域”
     */
    public static int getIsNotchSwitchOpenAtHuawei(Context context) {
        return Settings.Secure.getInt(context.getContentResolver(), DISPLAY_NOTCH_STATUS, 0);
    }

    /**
     * 小米手机判断是否有刘海屏
     *
     * @param context
     * @return true：有刘海屏；false：没有刘海屏
     */
    public static boolean hasNotchAtXiaomi(Context context) {
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
     * 小米手机获取刘海屏高度
     *
     * @param context
     * @return
     */
    public static int getNotHeightAtXiaomi(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("notch_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 小米手机获取刘海屏宽度
     *
     * @param context
     * @return
     */
    public static int getNotWidthAtXiaomi(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("notch_width", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 小米手机设置应用窗口在刘海屏手机使用刘海区
     *
     * @param window 应用页面window对象
     */
    public static void setFullScreenAtXiaomi(Window window) {
        // 竖屏绘制到耳朵区
        int flag = FLAG_NOTCH_SUPPORT_MI | FLAG_NOTCH_PORTRAIT_MI;
        try {
            Method method = Window.class.getMethod("addExtraFlags",
                    int.class);
            method.invoke(window, flag);
        } catch (Exception e) {
            Log.e("test", "addExtraFlags not found.");
        }
    }

    /**
     * 小米手机判断是否隐藏屏幕刘海
     *
     * @param context
     * @return false：未隐藏刘海区域 true：隐藏了刘海区域
     */
    public static boolean getIsNotchHideOpenAtXiaomi(Context context) {
        return Settings.Global.getInt(context.getContentResolver(), "force_black", 0) == 1;
    }

    /**
     * Vivo手机判断是否有刘海屏
     *
     * @param context
     * @return true：有刘海屏；false：没有刘海屏
     */
    public static boolean hasNotchAtVivo(Context context) {
        boolean ret = false;
        try {
            ClassLoader classLoader = context.getClassLoader();
            Class FtFeature = classLoader.loadClass("android.util.FtFeature");
            Method method = FtFeature.getMethod("isFeatureSupport", int.class);
            ret = (boolean) method.invoke(FtFeature, VIVO_NOTCH);
        } catch (ClassNotFoundException e) {
            Log.e("Notch", "hasNotchAtVivo ClassNotFoundException");
        } catch (NoSuchMethodException e) {
            Log.e("Notch", "hasNotchAtVivo NoSuchMethodException");
        } catch (Exception e) {
            Log.e("Notch", "hasNotchAtVivo Exception");
        } finally {
            return ret;
        }
    }

    /**
     * Oppo手机判断是否有刘海屏
     *
     * @param context
     * @return true：有刘海屏；false：没有刘海屏
     */
    public static boolean hasNotchAtOppo(Context context) {
        return context.getPackageManager().hasSystemFeature("com.oppo.feature.screen.heteromorphism");
    }

    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }
}
