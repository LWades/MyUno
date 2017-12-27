package com.example.ochiashi.uno_demo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {

    UNOView unoView;
    String messageString;
    Handler handler = new Handler(){
        public void handleMessage(Message msg){
            if (msg.what == 0){
                messageString = msg.getData().getString("data");
                showDialog();
            }
        }
    };

    int[] level = new int[3];
    String model;
    int model_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        Intent intent_receive = getIntent();

        level = intent_receive.getIntArrayExtra("level");
        model = intent_receive.getStringExtra("model");

        if (model.equals("common"))
            model_id = 1;               //模式为一般
        else if (model.equals("practice"))
            model_id = 2;               //模式为训练

        Log.v("哈", " " + level[1]);
        Log.v("嘿", " " + level[2]);
        Log.v("嗨", " " + level[3]);
        Log.v("模式", model);

        //之前版本
        getWindow().setFormat(PixelFormat.RGBA_8888);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //设置界面为横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        unoView = new UNOView(this, handler, model_id, level);
        setContentView(unoView);
        //之前版本结束*/
    }

    public void showDialog(){
        new AlertDialog.Builder(this).setMessage(messageString)
                .setNegativeButton("退出游戏", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        finish();
                    }
                }).setTitle("游戏结束").create().show();
    }
}
