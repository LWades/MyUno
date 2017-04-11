package com.example.dell.myuno;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.widget.ImageView;

public class Card {
	
	static final int CARDWIDTH = 72*2;
	static final int CARDHEIGHT = 118*2;
	
	Color color;    			//纸牌颜色
	Type type;					//纸牌类型
	int number;     			//纸牌数字
	int id;		 				//代号（因为纸牌有重复）
	int x = 0;					//横坐标
	int y = 0; 					//纵坐标
	int width;					//宽度
	int height;					//高度
	Bitmap bitmap;				//图片
	boolean rear = false;		//是否为背面
	boolean isClicked = false;  //是否被点击
	ImageView imageView;
	
	public Card(Color c,Type t,int n,int i){
		color = c;
		type = t;
		number = n;
		id = i;
	}
	
	//设置纸牌的位置
	public void setLocation(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public Rect getSRC(){
		return new Rect(0, 0, width, height);
	}
	
	public Rect getDST(){
		return new Rect(x, y, x + width, y + height);
	}
//	public Rect getDST(){
//		return new Rect(x, y, x + CARDWIDTH, y + CARDHEIGHT);
//	}
	
	public Card(){
		
	}

	public void output(){
		System.out.println(color+" " + type + " " + number + " " + id);
	}
}
