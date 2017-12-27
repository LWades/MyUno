package com.example.ochiashi.uno_demo;

import android.view.MotionEvent;

import java.util.ArrayList;

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
	public ArrayList<Card> buttonEvent(){
		if (!UNOView.canDraw)
			return null;

		float x = event.getX();
		float y = event.getY();

		boolean isNull = true;

		ArrayList<Card> getCard = new ArrayList<Card>();

		if ((x > 2*unoView.screenWidth/5 - unoView.buttonDraw.getWidth()/2) &&
				(x < 2*unoView.screenWidth/5 + unoView.buttonDraw.getWidth()/2) &&
				(y > unoView.screenHeight/2) &&
				(y < unoView.screenHeight/2 + unoView.buttonDraw.getHeight())){
			for (int i = 0; i < unoView.gc.alp.get(0).cardNumber(); i++){
				Card card = unoView.gc.alp.get(0).cardGroup.get(i);
				if (card.isClicked == true){
					System.out.println("currentCard颜色" + unoView.gc.currentCard.color);
					System.out.println("currentCard数字" + unoView.gc.currentCard.number);
					unoView.outCards.add(card);
					unoView.card_record.add(card);
					unoView.gc.alp.get(0).playCard(card);
					unoView.gc.cardNow = card;
//					UNOView.isDraw = true;
//					if(unoView.gc.cardNow != null){
//						System.out.println("cardNow颜色" + unoView.gc.cardNow.color);
//						System.out.println("cardNow数字" + unoView.gc.cardNow.number);
//					}
					Common.rePosition(unoView, unoView.gc.alp.get(0), 0);
					unoView.update();
					card.isClicked = false;
					getCard.add(card);
//					return card;
					isNull = false;
				}
			}

		}
		if (isNull)
			return null;
		else {
			UNOView.canDraw = false;
			UNOView.cardSet.clear();
			return getCard;
		}
	}

	//添加抓牌按钮事件
	public Card buttonEvent2(){
		if (unoView.gc.currentPlayer != 0)
			return null;

		float x = event.getX();
		float y = event.getY();

		if ((x > 4*unoView.screenWidth/5 - unoView.cardBackBitmap.getWidth()/2/2) &&
				(x < 4*unoView.screenWidth/5 + unoView.cardBackBitmap.getWidth()/2/2) &&
				(y > unoView.screenHeight/4) &&
				(y < unoView.screenHeight/4 + unoView.cardBackBitmap.getHeight()/2)){
			/*
			如果刚抓到牌可以出就打出这张牌
		 	*/
			Card top_card = unoView.gc.cg.top();
			if (Common.isAbledToPlay(unoView.gc.currentCard, top_card, unoView.gc.currentColor)){
				Card temp_card = unoView.gc.cg.draw();			//这张牌从牌库中拿出直接打出去
				temp_card.x = UNOView.CARDGROUP_X;
				temp_card.y = UNOView.CARDGROUP_Y;
				unoView.outCards.add(temp_card);
				unoView.card_record.add(temp_card);
				unoView.gc.cardNow = temp_card;
				return temp_card;
			}

			Card temp_card = unoView.gc.cg.draw();
			temp_card.x = UNOView.CARDGROUP_X;
			temp_card.y = UNOView.CARDGROUP_Y;
			unoView.gc.alp.get(0).drawCard(temp_card);
			int[] temp = Common.newCardPosition(unoView, 0);
			Common.moveAnimation(unoView, temp_card, temp[0], temp[1]);
			Common.rePosition(unoView, unoView.gc.alp.get(0), 0);
			unoView.update();
			unoView.gc.goOn();
		}

		return null;
	}

	public Card buttonDontknow() {
		if (unoView.gc.currentPlayer != 0)
			return null;

		float x = event.getX();
		float y = event.getY();


		if ((x > 3 * unoView.screenWidth / 5 - unoView.buttonDraw.getWidth() / 2) &&
				(x < 3 * unoView.screenWidth / 5 + unoView.buttonDraw.getWidth() / 2) &&
				(y > unoView.screenHeight / 2) &&
				(y < unoView.screenHeight / 2 + unoView.buttonDraw.getHeight())) {
			ArrayList<Card> canDraw = new ArrayList<Card>();
			for (int i = 0; i < unoView.gc.alp.get(0).cardNumber(); i++) {
				Card temp = unoView.gc.alp.get(0).cardGroup.get(i);
				if (Common.isAbledToPlay(unoView.gc.currentCard, temp, unoView.gc.currentColor))
					canDraw.add(temp);
			}
			if (canDraw.size() > 0) {
				Card result = Common.randomCard(canDraw);
				unoView.outCards.add(result);
				unoView.card_record.add(result);
				unoView.gc.alp.get(0).playCard(result);
				unoView.gc.cardNow = result;
				Common.rePosition(unoView, unoView.gc.alp.get(0), 0);
				unoView.update();
				result.isClicked = false;
				return result;
			}
		}
		return null;
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
