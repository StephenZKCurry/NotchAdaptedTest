package com.example.notchadaptedtest.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.notchadaptedtest.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_immersive).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转沉浸式页面
                startActivity(new Intent(MainActivity.this, ImmersiveActivity.class));
            }
        });
        findViewById(R.id.btn_fullscreen).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转全屏页面
                startActivity(new Intent(MainActivity.this, FullScreenActivity.class));
            }
        });
    }
}
