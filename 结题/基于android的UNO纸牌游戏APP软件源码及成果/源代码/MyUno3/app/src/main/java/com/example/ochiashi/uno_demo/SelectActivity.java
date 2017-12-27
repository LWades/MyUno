package com.example.ochiashi.uno_demo;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageButton;

public class SelectActivity extends Activity {

    private ImageButton button_single; //单机模式按钮
    private ImageButton button_back;   //退出游戏按钮
    private ImageButton button_practice; //训练模式按钮


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        setContentView(R.layout.activity_selection);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  WindowManager.LayoutParams.FLAG_FULLSCREEN);

        button_single = (ImageButton)findViewById(R.id.btn1);
        button_back = (ImageButton)findViewById(R.id.btn3);
        button_practice = (ImageButton)findViewById(R.id.btn2);

        button_single.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent_single = new Intent(SelectActivity.this,MainActivity.class);
                intent_single.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent_single.putExtra("model", "common");
                startActivity(intent_single);
            }
        });


        button_practice.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_practice = new Intent(SelectActivity.this,MainActivity.class);
                intent_practice.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent_practice.putExtra("model", "practice");
                startActivity(intent_practice);
            }
        });

        button_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}


