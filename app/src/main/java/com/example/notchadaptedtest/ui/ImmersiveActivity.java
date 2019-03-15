package com.example.notchadaptedtest.ui;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.example.notchadaptedtest.R;

/**
 * @description: 沉浸式状态栏页面
 * @author: zhukai
 * @date: 2019/3/9 15:59
 */
public class ImmersiveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_immersive);
        // 透明状态栏
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            getWindow().addFlags(
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        }
        // 解决部分手机设置透明状态栏后显示的蒙层问题
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            // 设置状态栏背景时不能同时设置FLAG_TRANSLUCENT_STATUS
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
//        }

//        LinearLayout llRoot = findViewById(R.id.ll_root);
            // 设置根布局的paddingTop
//        llRoot.setPadding(0, getStatusBarHeight(this), 0, 0);
//        View statusBarView = new View(this);
//        statusBarView.setBackgroundColor(Color.TRANSPARENT);
//        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                getStatusBarHeight(this));
            // 在根布局中添加一个状态栏高度的View
//        llRoot.addView(statusBarView, 0, lp);
        }
    }

    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */
    public int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }
}
