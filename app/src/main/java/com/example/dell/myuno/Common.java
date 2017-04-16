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

	/*
	用于计算出牌动画的目标横纵坐标
	返回值为x,y组成的数组
	type为动画类型
	-1表示出牌列表
	0表示主玩家
	1表示电脑1
	2表示电脑2
	3表示电脑3
	 */
	public static int[] newCardPosition(UNOView unoView, int type){
		int[] result = new int[2];

		switch (type){
			case -1:
				if (unoView.OUTCARDS_MAX == unoView.outCards.size())
					result[0] = unoView.screenWidth/2 - unoView.cardWidth;
				else
					result[0] = unoView.screenWidth/2 - unoView.cardWidth +
						(unoView.outCards.size()-1)*unoView.cardWidth/8;
				result[1] = unoView.screenHeight/2 - unoView.cardHeight;
				break;
			case 0:
				result[0] = unoView.screenWidth/2-(4-unoView.gc.alp.get(0).cardNumber()-1)*unoView.cardWidth/4;
				result[1] = unoView.screenHeight-unoView.cardHeight;
				break;
			case 1:
				result[0] = unoView.screenWidth - unoView.cardWidth/2;
				result[1] = unoView.cardHeight/2+(4*(unoView.gc.alp.get(1).cardNumber())-2)*unoView.cardHeight/21;
				break;
			case 2:
				result[0] = unoView.screenWidth/2-(4-unoView.gc.alp.get(2).cardNumber()-1)*unoView.cardWidth*1/4;
				result[1] = 0;
				break;
			case 3:
				result[0] = 0;
				result[1] = unoView.cardHeight/2+(4*(unoView.gc.alp.get(3).cardNumber())-2)*unoView.cardHeight/21;
				break;
			default:
				break;
		}

		return result;
	}

	public static void moveAnimation(UNOView unoView, Card card, int target_x, int target_y){
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
			unoView.update();
			unoView.Sleep(30);
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
