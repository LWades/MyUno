package com.example.dell.myuno;

import java.util.ArrayList;

public class Common {
	public static UNOView unoView;
	
	public static void rePosition(UNOView unoView, Player player, int playerNumber){
		if (playerNumber == 0){
			for (int i = 0; i < player.cardNumber(); i++){
				Card card = player.cardGroup.get(i);
				int y = unoView.screenHeight-unoView.cardHeight;
				if (card.isClicked)
					y -= unoView.cardHeight/6;
				card.setLocation(unoView.screenWidth/2-(4-i)*card.width/4, y);
//				card.setLocation(unoView.screenWidth/2-(4-i)*UNOView.CARDWIDTH*1/4, y);
			}
		}else if (playerNumber == 1){
			for (int i = 0; i < player.cardNumber(); i++){
				Card card = player.cardGroup.get(i);
				int x = unoView.screenWidth - unoView.cardWidth/2;
				card.setLocation(x, unoView.cardHeight/2+(4*(i+1)-2)*unoView.cardHeight/21);
			}
		}else if (playerNumber == 2){
			for (int i = 0; i < player.cardNumber(); i++){
				Card card = player.cardGroup.get(i);
				int y = 0;
				card.setLocation(unoView.screenWidth/2-(4-i)*unoView.cardWidth*1/4, y);
			}
		}else if (playerNumber == 3){
			for (int i = 0; i < player.cardNumber(); i++){
				Card card = player.cardGroup.get(i);
				int x = 0;
				card.setLocation(x, unoView.cardHeight/2+(4*(i+1)-2)*unoView.cardHeight/21);
			}
		}
	}

//	public static void moveCardAnimation(UNOView unoView, Card card, int target_x, int target_y){
//		int start_x = card.x;
//		int start_y = card.y;
//		int x = (target_x - start_x) / 5;
//		int y = (target_y - start_y) / 5;
//
//		for (int i = 0; i < 5; i++){
//			card.setLocation(start_x + x, start_y + y);
//			unoView.update();
//			unoView.Sleep(1000);
//		}
//
//	}

	//输出基本信息
	public static void outputInformation(UNOView unoView){
		System.out.println("方向:" + unoView.gc.currentDirection + "当前玩家：" + unoView.gc.currentPlayer);
	}
}
