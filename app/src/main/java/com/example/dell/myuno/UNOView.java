package com.example.dell.myuno;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class UNOView extends SurfaceView implements SurfaceHolder.Callback, Runnable{

//	static final int CARDWIDTH = 72*2;
//	static final int CARDHEIGHT = 118*2;
	static final int OUTCARDS_MAX = 20;

	SurfaceHolder surfaceHolder;	//控制Surface的接口
	Canvas canvas;					//画布
	boolean repaint = true;			//是否要重绘
	boolean going;					//游戏是否在进行
	Thread gameThread;				//游戏运行线程
	Thread drawThread;				//画图线程
	int screenWidth;				//屏幕宽度
	int screenHeight;				//屏幕高度
	Bitmap [] cardBitmap = new Bitmap[108];		//卡片图片
	Bitmap cardBackBitmap;			//卡片背面图片
	Bitmap backgroundBitmap;		//背景图片
	Bitmap [] fingerBitmap = new Bitmap[4];
	Bitmap buttonDraw;				//出牌按钮
	Bitmap buttonZhuaPai;			//抓牌按钮
	int cardWidth;					//卡片宽度
	int cardHeight;					//卡片高度
	Paint paint;					//画笔
	Paint change_paint;				//控制抓牌闪烁的画笔
	Paint card_blink;				//控制纸牌闪烁
	Paint[] color_paint = new Paint[4];
	String message[] = new String[3];	//计时器信息
	CardGroup cardGroup = new CardGroup();		//卡组
	Handler handler;				//消息处理器
	GameController gc;				//游戏控制器
	ArrayList<Card> outCards = new ArrayList<Card> ();		//出牌卡表
//	Card[] card_change = new Card[8];
	Bitmap[] card_change = new Bitmap[8];
	int outCardsIndice = 0;
	static boolean is_chooseColor = false;

	//控制界面切换的变量
	public static boolean s_nextPlayer = false;
	public static boolean s_skipPlayer = false;
	public static boolean s_reverse = false;
	public static boolean s_chooseColor = false;
	public static boolean s_drawTwo = false;
	public static boolean s_drawFour = false;

	//构造函数
	public UNOView(Context context, Handler handler){
		super(context);
		this.handler = handler;
		surfaceHolder = this.getHolder();
		surfaceHolder.addCallback(this);
		for (int i = 0; i < 3; i++){
			message[i] = "";
		}
	}

	//初始化卡片图片
	public void initCardBitmap(){
		cardGroup.card[0].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_red_0);
		cardGroup.card[1].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_yellow_0);
		cardGroup.card[2].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_blue_0);
		cardGroup.card[3].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_green_0);
		cardGroup.card[4].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_red_1);
		cardGroup.card[5].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_yellow_1);
		cardGroup.card[6].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_blue_1);
		cardGroup.card[7].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_green_1);
		cardGroup.card[8].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_red_1);
		cardGroup.card[9].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_yellow_1);
		cardGroup.card[10].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_blue_1);
		cardGroup.card[11].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_green_1);
		cardGroup.card[12].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_red_2);
		cardGroup.card[13].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_yellow_2);
		cardGroup.card[14].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_blue_2);
		cardGroup.card[15].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_green_2);
		cardGroup.card[16].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_red_2);
		cardGroup.card[17].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_yellow_2);
		cardGroup.card[18].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_blue_2);
		cardGroup.card[19].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_green_2);
		cardGroup.card[20].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_red_3);
		cardGroup.card[21].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_yellow_3);
		cardGroup.card[22].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_blue_3);
		cardGroup.card[23].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_green_3);
		cardGroup.card[24].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_red_3);
		cardGroup.card[25].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_yellow_3);
		cardGroup.card[26].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_blue_3);
		cardGroup.card[27].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_green_3);
		cardGroup.card[28].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_red_4);
		cardGroup.card[29].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_yellow_4);
		cardGroup.card[30].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_blue_4);
		cardGroup.card[31].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_green_4);
		cardGroup.card[32].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_red_4);
		cardGroup.card[33].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_yellow_4);
		cardGroup.card[34].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_blue_4);
		cardGroup.card[35].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_green_4);
		cardGroup.card[36].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_red_5);
		cardGroup.card[37].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_yellow_5);
		cardGroup.card[38].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_blue_5);
		cardGroup.card[39].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_green_5);
		cardGroup.card[40].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_red_5);
		cardGroup.card[41].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_yellow_5);
		cardGroup.card[42].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_blue_5);
		cardGroup.card[43].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_green_5);
		cardGroup.card[44].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_red_6);
		cardGroup.card[45].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_yellow_6);
		cardGroup.card[46].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_blue_6);
		cardGroup.card[47].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_green_6);
		cardGroup.card[48].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_red_6);
		cardGroup.card[49].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_yellow_6);
		cardGroup.card[50].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_blue_6);
		cardGroup.card[51].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_green_6);
		cardGroup.card[52].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_red_7);
		cardGroup.card[53].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_yellow_7);
		cardGroup.card[54].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_blue_7);
		cardGroup.card[55].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_green_7);
		cardGroup.card[56].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_red_7);
		cardGroup.card[57].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_yellow_7);
		cardGroup.card[58].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_blue_7);
		cardGroup.card[59].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_green_7);
		cardGroup.card[60].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_red_8);
		cardGroup.card[61].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_yellow_8);
		cardGroup.card[62].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_blue_8);
		cardGroup.card[63].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_green_8);
		cardGroup.card[64].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_red_8);
		cardGroup.card[65].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_yellow_8);
		cardGroup.card[66].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_blue_8);
		cardGroup.card[67].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_green_8);
		cardGroup.card[68].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_red_9);
		cardGroup.card[69].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_yellow_9);
		cardGroup.card[70].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_blue_9);
		cardGroup.card[71].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_green_9);
		cardGroup.card[72].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_red_9);
		cardGroup.card[73].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_yellow_9);
		cardGroup.card[74].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_blue_9);
		cardGroup.card[75].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_green_9);
		cardGroup.card[76].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_red_skip);
		cardGroup.card[77].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_yellow_skip);
		cardGroup.card[78].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_blue_skip);
		cardGroup.card[79].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_green_skip);
		cardGroup.card[80].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_red_skip);
		cardGroup.card[81].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_yellow_skip);
		cardGroup.card[82].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_blue_skip);
		cardGroup.card[83].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_green_skip);
		cardGroup.card[84].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_red_reverse);
		cardGroup.card[85].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_yellow_reverse);
		cardGroup.card[86].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_blue_reverse);
		cardGroup.card[87].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_green_reverse);
		cardGroup.card[88].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_red_reverse);
		cardGroup.card[89].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_yellow_reverse);
		cardGroup.card[90].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_blue_reverse);
		cardGroup.card[91].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_green_reverse);
		cardGroup.card[92].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_red_draw2);
		cardGroup.card[93].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_yellow_draw2);
		cardGroup.card[94].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_blue_draw2);
		cardGroup.card[95].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_green_draw2);
		cardGroup.card[96].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_red_draw2);
		cardGroup.card[97].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_yellow_draw2);
		cardGroup.card[98].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_blue_draw2);
		cardGroup.card[99].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_green_draw2);
		cardGroup.card[100].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_wild);
		cardGroup.card[101].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_wild);
		cardGroup.card[102].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_wild);
		cardGroup.card[103].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_wild);
		cardGroup.card[104].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_wild_draw4);
		cardGroup.card[105].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_wild_draw4);
		cardGroup.card[106].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_wild_draw4);
		cardGroup.card[107].bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_wild_draw4);

		card_change[0] = BitmapFactory.decodeResource(getResources(), R.drawable.image_red_wild);
		card_change[1] = BitmapFactory.decodeResource(getResources(), R.drawable.image_yellow_wild);
		card_change[2] = BitmapFactory.decodeResource(getResources(), R.drawable.image_blue_wild);
		card_change[3] = BitmapFactory.decodeResource(getResources(), R.drawable.image_green_wild);
		card_change[4] = BitmapFactory.decodeResource(getResources(), R.drawable.image_red_wild_draw4);
		card_change[5] = BitmapFactory.decodeResource(getResources(), R.drawable.image_yellow_wild_draw4);
		card_change[6] = BitmapFactory.decodeResource(getResources(), R.drawable.image_blue_wild_draw4);
		card_change[7] = BitmapFactory.decodeResource(getResources(), R.drawable.image_green_wild_draw4);
	}

	//初始化图片
	public void initBitmap(){
		this.initCardBitmap();
		//背景图片
		backgroundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.simple1);
		cardBackBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_back);

		//手指图片
		fingerBitmap[0] = BitmapFactory.decodeResource(getResources(), R.drawable.finger_down);
		fingerBitmap[1] = BitmapFactory.decodeResource(getResources(), R.drawable.finger_right);
		fingerBitmap[2] = BitmapFactory.decodeResource(getResources(), R.drawable.finger_up);
		fingerBitmap[3] = BitmapFactory.decodeResource(getResources(), R.drawable.finger_left);

		//出牌图片
		buttonDraw = BitmapFactory.decodeResource(getResources(), R.drawable.drawcard);
		//抓牌图片
		buttonZhuaPai = BitmapFactory.decodeResource(getResources(), R.drawable.zhuapai);

		for (int i = 0; i < 107; i++){
			cardGroup.card[i].width = screenWidth/8;
			cardGroup.card[i].height = screenWidth/8/48*77;
		}

//		for (int i = 0; i < 107; i++){
//			Bitmap.createScaledBitmap(cardGroup.card[i].bitmap, 10, 10, true);
//		}

//		for (int i = 0; i < 107; i++){
//			cardGroup.card[i].width = cardBackBitmap.getWidth();
//			cardGroup.card[i].height = cardBackBitmap.getHeight();
//		}

//		cardWidth = cardBackBitmap.getWidth();
//		cardHeight = cardBackBitmap.getHeight();

		cardWidth = screenWidth / 8;
		cardHeight = screenWidth/8/48*77;

		System.out.println("卡片宽度" + cardWidth);
		System.out.println("卡片高度" + cardHeight);

		//计时器画笔
		paint = new Paint();
		paint.setColor(Color.BLACK);
		paint.setTextSize(cardWidth*1/3);
		paint.setAntiAlias(true);
		paint.setStyle(Style.STROKE);	//设置空心
		paint.setStrokeWidth(1.0f);
		paint.setTextAlign(Align.CENTER);

		//闪烁动画画笔
		change_paint = new Paint();
		change_paint.setStyle(Style.STROKE);
		change_paint.setAlpha(255);

		card_blink = new Paint();
		card_blink.setStyle(Style.STROKE);
		card_blink.setAlpha(255);

		//画选择颜色矩形的画笔
		for (int i = 0; i < 4; i++){
			color_paint[i] = new Paint();
			color_paint[i].setStyle(Style.FILL);
			color_paint[i].setAntiAlias(true);
		}
		color_paint[0].setColor(Color.RED);
		color_paint[1].setColor(Color.YELLOW);
		color_paint[2].setColor(Color.BLUE);
		color_paint[3].setColor(Color.GREEN);
	}

	private int ss = 180;
	private int tt = 200;

	public void mmove(){
		for(int i = 0; i < 5; i++){
			ss += 10;
			update();
			Sleep(50);
		}
	}

	public void ddraw(){
		Rect r = new Rect(ss, tt, ss + 40, tt + 80);
		canvas.drawBitmap(cardBackBitmap, null, r, change_paint);
	}

	//绘图线程
	@Override
	public void run() {
		while(going){
			if (repaint){
				OnDraw();
				repaint = false;
				Sleep(33);
			}
		}
	}

	public void OnDraw() {
		synchronized(surfaceHolder){
			try{
				canvas = surfaceHolder.lockCanvas();
				//画背景
				drawBackground();
//				canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.image_blue_2), 0, 0, null);
				//画玩家牌
				for (int i = 0; i < 4; i++)
					drawPlayerCards(i);

				drawFinger();

				drawButtonDraw();

				drawButtonZhuaPai();

				drawOutCards();

				drawMessage();

				ddraw();

				if (is_chooseColor)
					drawChooseColor();
			}catch (Exception e){
				e.printStackTrace();
			}finally{
				if (canvas != null)
					surfaceHolder.unlockCanvasAndPost(canvas);
			}
		}

	}

	//刷新界面
	public void update(){
		repaint = true;
	}

	//画背景
	public void drawBackground(){
		Rect src = new Rect(0, 0, backgroundBitmap.getWidth(),
				backgroundBitmap.getHeight());
		Rect dst = new Rect(0, 0, screenWidth, screenHeight);
		canvas.drawBitmap(backgroundBitmap, src, dst, null);
	}

	//画玩家的牌
	public void drawPlayerCards(int currentPlayer){
		if (!gc.alp.get(currentPlayer).isEnd()){
			for (int i = 0; i < gc.alp.get(currentPlayer).cardNumber(); i++){
				if (currentPlayer == 0)
					drawCard(gc.alp.get(currentPlayer).cardGroup.get(i));
				else
					drawCardLit(gc.alp.get(currentPlayer).cardGroup.get(i));
			}
		}
	}

	//画卡片
	public void drawCard(Card card){
		Bitmap tempBitmap;
		if (card.rear)
			tempBitmap = cardBackBitmap;
		else
			tempBitmap = card.bitmap;
//		System.out.println(card.getSRC());
//		System.out.println(card.getDST());
//		System.out.println("位图高度" + card.bitmap.getHeight());
//		System.out.println("位图宽度" + card.bitmap.getWidth());
		canvas.drawBitmap(tempBitmap, null, card.getDST(), null);
//		canvas.drawBitmap(tempBitmap, card.getSRC(), card.getDST(), null);
	}

	//画小卡片
	public void drawCardLit(Card card){
		Bitmap tempBitmap;
		if (card.rear)
			tempBitmap = cardBackBitmap;
		else
			tempBitmap = card.bitmap;

		Rect rect= card.getDST();
		rect.right = (rect.left + rect.right) / 2;
		rect.bottom = (rect.top + rect.bottom) / 2;

		canvas.drawBitmap(tempBitmap, null, rect, null);
	}

	//画手指
	public void drawFinger(){
		if (gc.currentPlayer == 0){
			Rect src = new Rect(0, 0, fingerBitmap[0].getWidth(), fingerBitmap[0].getHeight());
			Rect dst = new Rect(screenWidth/4 - fingerBitmap[0].getWidth()/2, 3*screenHeight/4 - fingerBitmap[0].getHeight()/2,
					screenWidth/4 + fingerBitmap[0].getWidth()/2, 3*screenHeight/4 + fingerBitmap[0].getHeight()/2);
			canvas.drawBitmap(fingerBitmap[0], src, dst, null);
		}else if (gc.currentPlayer == 1){
			Rect src2 = new Rect(0, 0, fingerBitmap[1].getWidth(), fingerBitmap[1].getHeight());
			Rect dst2 = new Rect(3*screenWidth/4 + fingerBitmap[1].getWidth()/2, screenHeight/2 - fingerBitmap[1].getHeight()/2,
					3*screenWidth/4 + 3*fingerBitmap[1].getWidth()/2, screenHeight/2 + fingerBitmap[1].getHeight()/2);
			canvas.drawBitmap(fingerBitmap[1], src2, dst2, null);
		}else if (gc.currentPlayer == 2){
			Rect src3 = new Rect(0, 0, fingerBitmap[2].getWidth(), fingerBitmap[2].getHeight());
			Rect dst3 = new Rect(3*screenWidth/4 - fingerBitmap[2].getWidth()/2, screenHeight/4 - fingerBitmap[2].getHeight()/2,
					3*screenWidth/4 + fingerBitmap[2].getWidth()/2, screenHeight/4 + fingerBitmap[2].getHeight()/2);
			canvas.drawBitmap(fingerBitmap[2], src3, dst3, null);
		}else if (gc.currentPlayer == 3){
			Rect src4 = new Rect(0, 0, fingerBitmap[3].getWidth(), fingerBitmap[3].getHeight());
			Rect dst4 = new Rect(screenWidth/4 - 3*fingerBitmap[3].getWidth()/2, screenHeight/2 - fingerBitmap[3].getHeight()/2,
					screenWidth/4 - fingerBitmap[3].getWidth()/2, screenHeight/2 + fingerBitmap[3].getHeight()/2);
			canvas.drawBitmap(fingerBitmap[3], src4, dst4, null);
		}
	}

	//画出牌按钮
	public void drawButtonDraw(){
		Rect src = new Rect(0, 0, buttonDraw.getWidth(), buttonDraw.getHeight());
		Rect dst = new Rect(screenWidth/4 - buttonDraw.getWidth()/2, screenHeight/2,
				screenWidth/4 + buttonDraw.getWidth()/2, screenHeight/2 + buttonDraw.getHeight());
		canvas.drawBitmap(buttonDraw, null, dst, null);
	}

	//画抓牌按钮
	public void drawButtonZhuaPai(){
//		Rect src = new Rect(0, 0, buttonZhuaPai.getWidth(), buttonZhuaPai.getHeight());
//		Rect dst = new Rect(4*screenWidth/5 - buttonZhuaPai.getWidth()/2, screenHeight/2,
//				4*screenWidth/5 + buttonZhuaPai.getWidth()/2, screenHeight/2 + buttonZhuaPai.getHeight());
//		canvas.drawBitmap(buttonZhuaPai, src, dst, null);
		Rect dst = new Rect(4*screenWidth/5 - cardBackBitmap.getWidth()/2/2, screenHeight/4,
				4*screenWidth/5 + cardBackBitmap.getWidth()/2/2, screenHeight/4 + cardBackBitmap.getHeight()/2);
		canvas.drawBitmap(cardBackBitmap, null, dst, change_paint);
	}

	//画出牌列表
	public void drawOutCards(){
		if (outCards.size() >= OUTCARDS_MAX){
			Card card = outCards.get(outCards.size()-1);
			outCards.clear();
			outCards.add(card);
		}
		if (outCards.size() > 0){
//			for (int i = 0; i < outCards.size(); i++){
////				moveAnimation(outCards.get(i), screenWidth/2 - cardWidth + i*cardWidth/8, screenHeight/2 - cardHeight);
////				outCards.get(i).setLocation(screenWidth/2 - cardWidth + i*cardWidth/8, screenHeight/2 - cardHeight);
//			}

			for (int i = 0; i< outCards.size(); i++){
				if (i == outCards.size() - 1){
					Rect rect= outCards.get(i).getDST();
					rect.right = (rect.left + rect.right) / 2;
					rect.bottom = (rect.top + rect.bottom) / 2;

					canvas.drawBitmap(outCards.get(i).bitmap, null, rect, card_blink);
				}else
					drawCardLit(outCards.get(i));
			}
		}
	}

	//画出选择颜色模块
	public void drawChooseColor(){
		int left;
		int right;
		int top;
		int bottom;
		for(int i = 0; i < 4; i++){
			if (i % 2 == 0){
				left = screenWidth / 3;
				right = screenWidth / 2;
			}else{
				left = screenWidth / 2;
				right = 2 * screenWidth / 3;
			}

			if (i <= 1){
				top = screenHeight / 3;
				bottom = screenHeight / 2;
			}else{
				top = screenHeight / 2;
				bottom = 2 * screenHeight / 3;
			}

			canvas.drawRect(left, top, right, bottom, color_paint[i]);
//			canvas.drawRect(screenWidth/2, screenHeight/2, 2*screenWidth/3, 2*screenHeight/3, color_paint[0]);
		}
	}

	//画计时器信息
	public void drawMessage(){
		canvas.drawText(message[0], 3*screenWidth/4 + 140, screenHeight/2, paint);
		canvas.drawText(message[1], screenWidth/2, screenHeight/4, paint);
		canvas.drawText(message[2], screenWidth/7, screenHeight/2, paint);
	}

	//设定计时器
	public void setTimer(int time,int c)
	{
//		System.out.println("这是计时器" + c);
		while(time-->0){
			Sleep(1000);
			message[c]=time+"";
			System.out.println("message=" + message[c]);
			update();
		}
		message[c]="";
	}

	//时间延后
	public void Sleep(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	//发牌
	public void handCards() {
		int turn = 0;
		System.out.println("screenWidth  " + screenWidth);
		System.out.println("screenHeight  " + screenHeight);
		System.out.println("cardWidth  " + cardWidth);
		System.out.println("cardHeight  " + cardHeight);
		for (int i = 0; i < 28; i++){
			switch((turn++) % 4){
				case 0:
					//玩家
//				gc.cg.CardGroup[107-i].setLocation(screenWidth/2-(9-i/3)*cardWidth*2/3, 
//						screenHeight-cardHeight);
					gc.cg.CardGroup[107-i].setLocation(screenWidth/2-(4-i/4)*cardWidth*1/4,
							screenHeight-cardHeight);
					gc.cg.CardGroup[107-i].rear = false;
					gc.alp.get(0).drawCard(gc.cg.draw());
					break;
				case 1:
					//右边电脑
//				gc.cg.CardGroup[107-i].setLocation(screenWidth-3*cardWidth/2, 
//						cardHeight/2+i*cardHeight/21);
					gc.cg.CardGroup[107-i].setLocation(screenWidth-cardWidth/2,
							cardHeight/2+i*cardHeight/21);
					gc.cg.CardGroup[107-i].rear = false;
					gc.alp.get(1).drawCard(gc.cg.draw());
					break;
				case 2:
					//上边电脑
//				gc.cg.CardGroup[107-i].setLocation(screenWidth/2-(3*i-155)*cardWidth/2, 0);
					gc.cg.CardGroup[107-i].setLocation(screenWidth/2-(4-i/4)*cardWidth*1/4, 0);
					gc.cg.CardGroup[107-i].rear = false;
					gc.alp.get(2).drawCard(gc.cg.draw());
					break;
				case 3:
					//左边电脑
//				gc.cg.CardGroup[107-i].setLocation(cardWidth/2, 
//						cardHeight/2+i*cardHeight/21);
					gc.cg.CardGroup[107-i].setLocation(0,
							cardHeight/2+i*cardHeight/21);
					gc.cg.CardGroup[107-i].rear = false;
					gc.alp.get(3).drawCard(gc.cg.draw());
			}
			update();
			Sleep(100);
		}
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		going = true;
		screenWidth = this.getWidth();
		screenHeight = this.getHeight();
		//初始化图片
		initBitmap();
		//初始化卡组
		gc = new GameController(cardGroup, this);
		//洗牌
		gc.cg.Shuffle();
		//初始化玩家
		for (int i = 0; i < 4; i++){
			Player player = new Player();
			gc.alp.add(player);
		}

		final UNOView unoView = this;

		//开启游戏进程
		gameThread = new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				//发牌
				handCards();

				gc.gameStart();
				update();

				gc.currentCard = gc.cg.draw();
				gc.currentColor = gc.currentCard.color;
				outCards.add(gc.currentCard);
				gc.currentCard.setLocation(screenWidth/2 - cardWidth + (outCards.size()-1)*cardWidth/8,
						screenHeight/2 - cardHeight);


				while(!gc.isGameEnd){
//					blinkAnimation();
					if (gc.currentPlayer == 0) {
						//当玩家手中没有UNO牌可以出时，制造卡组的闪烁效果提醒玩家抓牌
						if (!gc.alp.get(0).playerCanDraw(gc.currentCard, gc.currentColor))
							blinkAnimation();
						gc.play(gc.currentCard, gc.cardNow);
						gc.cardNow = null;
						update();
					}else if (gc.currentPlayer == 1){
						setTimer(4, 0);
						gc.cardNow = gc.alp.get(1).computerPlayCard(gc.currentCard, gc.currentColor);
						if (gc.cardNow == null){
							gc.alp.get(1).drawCard(gc.cg.draw());
							Common.rePosition(unoView, gc.alp.get(1), 1);
							System.out.println("玩家" + 1 + gc.alp.get(1).cardNumber() + "张手牌");
							for (int i = 0; i < gc.alp.get(1).cardNumber(); i++)
								System.out.println("reP" + gc.alp.get(1).cardGroup.get(i).getDST());
							gc.goOn();
							update();
						}else{
							System.out.println("玩家" + gc.currentPlayer + "出牌");
							outCards.add(gc.cardNow);

							//处理万能牌
							if (gc.cardNow.type == Type.Wild){
								gc.currentColor = gc.alp.get(gc.currentPlayer).returnRandomColor();
								blinkAnimationColor();
							}

							//设置出牌列表的坐标
							int temp_x = screenWidth/2 - cardWidth + (outCards.size()-1)*cardWidth/8;
							int temp_y = screenHeight/2 - cardHeight;
							if(outCards.size() == OUTCARDS_MAX){
								temp_x = screenWidth/2 - cardWidth;
							}
							moveAnimation(gc.cardNow, temp_x, temp_y);

							gc.play(gc.currentCard, gc.cardNow);
							Common.rePosition(unoView, gc.alp.get(1), 1);
							gc.cardNow = null;
							update();
						}
					}else if (gc.currentPlayer == 2){
						setTimer(4, 1);
						gc.cardNow = gc.alp.get(2).computerPlayCard(gc.currentCard, gc.currentColor);
						if (gc.cardNow == null){
							gc.alp.get(2).drawCard(gc.cg.draw());
							Common.rePosition(unoView, gc.alp.get(2), 2);
							System.out.println("玩家" + 2 + gc.alp.get(2).cardNumber() + "张手牌");
							for (int i = 0; i < gc.alp.get(2).cardNumber(); i++)
								System.out.println("reP" + gc.alp.get(2).cardGroup.get(i).getDST());
							gc.goOn();
							update();
						}else{
							System.out.println("玩家" + gc.currentPlayer + "出牌");
							outCards.add(gc.cardNow);

							//处理万能牌
							if (gc.cardNow.type == Type.Wild){
								gc.currentColor = gc.alp.get(gc.currentPlayer).returnRandomColor();
								blinkAnimationColor();
							}

							//设置出牌列表的坐标
							update();
							int temp_x = screenWidth/2 - cardWidth + (outCards.size()-1)*cardWidth/8;
							int temp_y = screenHeight/2 - cardHeight;
							if(outCards.size() == OUTCARDS_MAX){
								temp_x = screenWidth/2 - cardWidth;
							}
							moveAnimation(gc.cardNow, temp_x, temp_y);



							gc.play(gc.currentCard, gc.cardNow);
							Common.rePosition(unoView, gc.alp.get(2), 2);
							gc.cardNow = null;
							update();
						}
					}else if (gc.currentPlayer == 3){
						setTimer(4, 2);
						gc.cardNow = gc.alp.get(3).computerPlayCard(gc.currentCard, gc.currentColor);
						if (gc.cardNow == null){
							gc.alp.get(3).drawCard(gc.cg.draw());
							Common.rePosition(unoView, gc.alp.get(3), 3);
							System.out.println("玩家" + 3 + gc.alp.get(3).cardNumber() + "张手牌");
							for (int i = 0; i < gc.alp.get(3).cardNumber(); i++)
								System.out.println("reP" + gc.alp.get(3).cardGroup.get(i).getDST());
							gc.goOn();
							update();
						}else{
							System.out.println("玩家" + gc.currentPlayer + "出牌");
							outCards.add(gc.cardNow);

							//处理万能牌
							if (gc.cardNow.type == Type.Wild){
								gc.currentColor = gc.alp.get(gc.currentPlayer).returnRandomColor();
								blinkAnimationColor();
							}

							//设置出牌列表的坐标
							update();
							int temp_x = screenWidth/2 - cardWidth + (outCards.size()-1)*cardWidth/8;
							int temp_y = screenHeight/2 - cardHeight;
							if(outCards.size() == OUTCARDS_MAX){
								temp_x = screenWidth/2 - cardWidth;
							}
							moveAnimation(gc.cardNow, temp_x, temp_y);

							gc.play(gc.currentCard, gc.cardNow);
							Common.rePosition(unoView, gc.alp.get(3), 3);
							gc.cardNow = null;
							update();
						}
					}
					gameWin();
				}//结束while
			}

		});
		gameThread.start();

		//开启绘图进程进程
		drawThread = new Thread(this);
		drawThread.start();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		// TODO Auto-generated method stub

	}
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub

	}


	@Override
	public boolean onTouchEvent(MotionEvent event) {
		//只接受按下事件
		if (event.getAction() != MotionEvent.ACTION_UP)
			return true;
		System.out.println("x: " + event.getX() + " y: " + event.getY());

		EventAction eventAction = new EventAction(event, this);
		Card card = eventAction.getCard();

		if (card != null){
			if (card.isClicked)
				card.y += cardHeight / 6;
			else
				card.y -= cardHeight / 6;

			card.isClicked = !card.isClicked;
			update();
		}

		com.example.dell.myuno.Color temp_color;
		if (is_chooseColor){
			temp_color = eventAction.getChooseColor();
			gc.currentColor = temp_color;
			System.out.println("系统的当前颜色变了，变成了" + gc.currentColor);
			is_chooseColor = false;
			blinkAnimationColor();
			gc.goOn();
		}

		Card temp_card = eventAction.buttonEvent();
		eventAction.buttonEvent2();
		mmove();
		if(temp_card != null){
			//设置出牌列表的坐标
			int temp_x = screenWidth/2 - cardWidth + (outCards.size()-1)*cardWidth/8;
			int temp_y = screenHeight/2 - cardHeight;
			moveAnimation(temp_card, temp_x, temp_y);
		}
		return super.onTouchEvent(event);
	}

	//出牌动画
	public void moveAnimation(Card card, int target_x, int target_y){
		int start_x = card.x;
		int start_y = card.y;
		int x = (target_x - start_x) / 10;
		int y = (target_y - start_y) / 10;

		for (int i = 0; i < 10; i++){
			start_x += x;
			start_y += y;
			if (i == 9)
				card.setLocation(target_x, target_y);
			else
				card.setLocation(start_x, start_y);
			update();
			Sleep(30);
		}

	}

	//闪烁动画:牌库
	public void blinkAnimation(){
		for(int i = 5; i < 18; i++){
			change_paint.setAlpha(i * 15);
			Sleep(50);
			update();
		}
	}

	//闪烁动画:换颜色
	public void blinkAnimationColor(){
		for(int i = 17; i > 4; i--){
			card_blink.setAlpha(i * 15);
			Sleep(50);
			update();
		}

		switch (gc.currentColor){
			case Red:
				outCards.get(outCards.size()-1).bitmap = card_change[0];
				break;
			case Yellow:
				outCards.get(outCards.size()-1).bitmap = card_change[1];
				break;
			case Blue:
				outCards.get(outCards.size()-1).bitmap = card_change[2];
				break;
			case Green:
				outCards.get(outCards.size()-1).bitmap = card_change[3];
				break;
			default:
				break;
		}

		for(int i = 5; i < 18; i++){
			card_blink.setAlpha(i * 15);
			Sleep(50);
			update();
		}
	}

	public void gameWin(){
		int flag = -1;
		boolean temp = false;
		for (int i = 0; i < 4; i++){
			if (gc.alp.get(i).cardNumber() == 0) {
				temp = true;
				flag = i;
			}
		}

		if (temp == true){
			gc.isGameEnd = true;
			Message msg = new Message();
			msg.what = 0;

			Bundle builder = new Bundle();

			if (flag == 0)
				builder.putString("data", "你赢了");
			else
				builder.putString("data", "电脑" + flag + "赢了");

			msg.setData(builder);

			handler.sendMessage(msg);
		}
	}
}
