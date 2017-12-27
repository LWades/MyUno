package com.example.dell.myuno;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.*;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

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

    RelativeLayout layout;
    ArrayList<ImageView> imageViews = new ArrayList<ImageView>();
    CardGroup cardGroup = new CardGroup();		//卡组
    GameController gc;				//游戏控制器
    int screenWidth;				//屏幕宽度
    int screenHeight;				//屏幕高度

    public void initCard(){
        for (int i = 0; i < 108; i++)
            cardGroup.card[i].imageView = (ImageView)findViewById(Ids.card_ids[i]);
    }

    public void init(){
        //获取屏幕信息
        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);

        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;

        System.out.println("屏幕宽度" + screenWidth);
        System.out.println("屏幕高度" + screenHeight);
    }

    public void change(){
        cardGroup.card[5].imageView.setVisibility(View.VISIBLE);
        cardGroup.card[5].imageView.setX(0);
        cardGroup.card[5].imageView.setY(0);

//        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
//        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
//        cardGroup.card[5].imageView.measure(w, h);
//
//        int a = cardGroup.card[5].imageView.getMeasuredWidth();
//        int b = cardGroup.card[5].imageView.getMeasuredHeight();
//
//        cardGroup.card[5].imageView.setMaxWidth(screenWidth);
//        cardGroup.card[5].imageView.setMaxHeight(screenHeight*10);
//
//        LayoutParams para = cardGroup.card[5].imageView.getLayoutParams();
//        para.width = screenWidth;
//        para.height = screenHeight;


//        cardGroup.card[5].imageView.setScaleX((float)0.5);
//        cardGroup.card[5].imageView.setScaleY((float)0.5);

//        cardGroup.card[5].imageView.setLayoutParams(para);

        cardGroup.card[15].imageView.setVisibility(View.VISIBLE);
        cardGroup.card[15].imageView.setX(200);
        cardGroup.card[15].imageView.setY(200);
    }

    public void setLayout(){
        layout = new RelativeLayout(this);

        RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp1.addRule(RelativeLayout.ALIGN_BOTTOM);
        lp1.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        // btn1 位于父 View 的顶部，在父 View 中水平居中
        ImageView ii = cardGroup.card[5].imageView;
//        lp1.removeRule(ii);
//        layout.addView(ii, lp1 );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        //强制设置界面为横屏
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//
//        init();
//        initCard();
////        initPicture();
//
//        Log.d("大小是：",String.valueOf(imageViews.size()));
//
//        change();

        //之前版本
        getWindow().setFormat(PixelFormat.RGBA_8888);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //设置界面为横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        unoView = new UNOView(this, handler);
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
