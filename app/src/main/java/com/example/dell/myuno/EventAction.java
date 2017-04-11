package com.example.dell.myuno;

import android.provider.Settings;
import android.view.MotionEvent;

public class EventAction {
	
	static final int CARDWIDTH = 72*2;
	static final int CARDHEIGHT = 118*2;
	
	MotionEvent event;
	UNOView unoView;
	
	public EventAction(MotionEvent event, UNOView unoView){
		this.event = event;
		this.unoView = unoView;
	}
	
	//点击的是哪张牌
	public Card getCard(){
		Card card = null;
		Card cardNull = null;
		float x = event.getX();
		float y = event.getY();
		float xOffset = CARDWIDTH / 4;
		float yOffset = CARDHEIGHT;
		
		if (y < unoView.screenHeight - 7 * CARDHEIGHT / 6)
			return null;
		
		for (int i = 0; i < unoView.gc.alp.get(0).cardNumber(); i++){
			//查找符合范围的卡牌
			card = unoView.gc.alp.get(0).cardGroup.get(i);
			if (card.isClicked){
				if ((x - card.x > 0) && (y - card.y > 0) &&
						(((x - card.x < xOffset) && (y - card.y < yOffset)) || 
						((x - card.x < CARDWIDTH/2) && (y - card.y < CARDHEIGHT/6)))){
//					System.out.println("i = " + i);
					return card;
				}
			}else{
				if ((x - card.x > 0) && (x - card.x < xOffset)
						&& (y - card.y > 0) && (y - card.y < yOffset)){
//					System.out.println("i = " + i);
					return card;
				}
			}
		}
		return cardNull;
	}
	
	//添加出牌按钮事件
	public Card buttonEvent(){
		float x = event.getX();
		float y = event.getY();
		
		if ((x > unoView.screenWidth/4 - unoView.buttonDraw.getWidth()/2) &&
				(x < unoView.screenWidth/4 + unoView.buttonDraw.getWidth()/2) &&
				(y > unoView.screenHeight/2) &&
				(y < unoView.screenHeight/2 + unoView.buttonDraw.getHeight())){
			for (int i = 0; i < unoView.gc.alp.get(0).cardNumber(); i++){
				Card card = unoView.gc.alp.get(0).cardGroup.get(i);
				if (card.isClicked == true){
					System.out.println("currentCard颜色" + unoView.gc.currentCard.color);
					System.out.println("currentCard数字" + unoView.gc.currentCard.number);
					unoView.outCards.add(card);
					unoView.gc.alp.get(0).playCard(card);
					unoView.gc.cardNow = card;
//					UNOView.isDraw = true;
					if(unoView.gc.cardNow != null){
						System.out.println("cardNow颜色" + unoView.gc.cardNow.color);
						System.out.println("cardNow数字" + unoView.gc.cardNow.number);
					}
					Common.rePosition(unoView, unoView.gc.alp.get(0), 0);
					unoView.update();
					card.isClicked = false;
					return card;
				}
			}
		}
		return null;
	}
	
	//添加抓牌按钮事件
	public void buttonEvent2(){
		float x = event.getX();
		float y = event.getY();
		
		if ((x > 4*unoView.screenWidth/5 - unoView.cardBackBitmap.getWidth()/2/2) &&
				(x < 4*unoView.screenWidth/5 + unoView.cardBackBitmap.getWidth()/2/2) &&
				(y > unoView.screenHeight/4) &&
				(y < unoView.screenHeight/4 + unoView.cardBackBitmap.getHeight()/2)){
			unoView.gc.alp.get(0).drawCard(unoView.gc.cg.draw());
			Common.rePosition(unoView, unoView.gc.alp.get(0), 0);
			unoView.update();
			unoView.gc.goOn();
		}
	}

	public Color getChooseColor(){
		float x = event.getX();
		float y = event.getY();

		if ((x > unoView.screenWidth / 3) && (x < unoView.screenWidth* 2 / 3) &&
				(y > unoView.screenHeight / 3) && (y < unoView.screenHeight * 2 / 3)){
			if (x < unoView.screenWidth / 2){
				if (y < unoView.screenHeight / 2){
					return Color.Red;
				}else{
					return Color.Blue;
				}
			}else{
				if (y < unoView.screenHeight / 2){
					return Color.Yellow;
				}else{
					return Color.Green;
				}
			}
		}else{
			return null;
		}
	}
}
