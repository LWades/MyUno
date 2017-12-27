package com.example.ochiashi.uno_demo;

/**
 * Created by ochiashi on 2017/5/26.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;


public class MainActivity extends Activity {

    private String model;


    private RadioButton right_easy;
    private RadioButton right_hard;
    private RadioButton top_easy;
    private RadioButton top_hard;
    private RadioButton left_easy;
    private RadioButton left_hard;


    private Button sure;
    private String range = "";
    private int[] level = new int[4];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent_receive = getIntent();
        model = intent_receive.getStringExtra("model");
        Log.v("", model);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  WindowManager.LayoutParams.FLAG_FULLSCREEN);

        sure =  (Button)findViewById(R.id.sure);

        right_easy = (RadioButton)findViewById(R.id.right_easy);
        right_hard = (RadioButton)findViewById(R.id.right_hard);
        top_easy = (RadioButton)findViewById(R.id.top_easy);
        top_hard = (RadioButton)findViewById(R.id.top_hard);
        left_easy = (RadioButton)findViewById(R.id.left_easy);
        left_hard = (RadioButton)findViewById(R.id.left_hard);


        Log.v("",range);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(right_easy.isChecked()){
                    level[1] = 0;
                }
                if(right_hard.isChecked()){
                    level[1] = 1;
                }

                if(top_easy.isChecked()){
                    level[2] = 0;
                }

                if(top_hard.isChecked()){
                    level[2] = 1;
                }
                if(left_easy.isChecked()){
                    level[3] = 0;
                }
                if(left_hard.isChecked()){
                    level[3] = 1;
                }


                Intent intent_range = new Intent(MainActivity.this,GameActivity.class);
                intent_range.putExtra("level", level);
                intent_range.putExtra("model", model);
                intent_range.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent_range);
            }
        });

   }

}