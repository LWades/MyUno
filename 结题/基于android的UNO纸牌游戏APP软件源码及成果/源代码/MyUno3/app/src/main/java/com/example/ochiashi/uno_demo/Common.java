package com.example.ochiashi.uno_demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class Common {
	public static UNOView unoView;

	public static void rePosition(UNOView unoView, Player player, int playerNumber){
		if (playerNumber == 0){
			for (int i = 0; i < player.cardNumber(); i++){
				Card card = player.cardGroup.get(i);
				int y = unoView.screenHeight-unoView.cardHeight;
				if (card.isClicked)
					y -= unoView.cardHeight/6;
				card.setLocation(unoView.screenWidth/2-(4-i)*unoView.cardWidth/4, y);
//				card.setLocation(unoView.screenWidth/2-(4-i)*card.width/4, y);
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

	/*
	被点击的牌是否可以一起出
	 */
	public static boolean isCanDrawTogether(Set<Card> cardSet, Card card, Color color){
		if (0 == cardSet.size())
			return false;

		Iterator<Card> it = cardSet.iterator();

		while (it.hasNext()){
			Card temp = it.next();
//			System.out.println("????" + temp.number + temp.type);

			if (!isAbledToPlay(card, temp, color))
				return false;

		}
		if (cardSet.size() > 1){
			Iterator<Card> it2 = cardSet.iterator();
			while (it2.hasNext()){
				Card temp2 = it2.next();
				if (10 == temp2.number)
					return false;

				if (temp2.number != card.number)
					return false;
			}
		}
		return true;
	}

	/*
	一张牌是否可以出
	 */
	public static  boolean isAbledToPlay(Card lastCard, Card card ,Color currentColor){
		if (lastCard.type == Type.Number){
			if (lastCard.number == card.number ||
					lastCard.color == card.color ||
					card.color == Color.Black)
				return true;
			else
				return false;
		}else if (lastCard.type == Type.Skip){
			if (card.color == Color.Black ||
					lastCard.color == card.color ||
					card.type == Type.Skip)
				return true;
			else
				return false;
		}else if (lastCard.type == Type.Reverse){
			if (card.color == Color.Black ||
					lastCard.color == card.color ||
					card.type == Type.Reverse)
				return true;
			else
				return false;
		}else if (lastCard.type == Type.Draw_Two){
			if (card.color == Color.Black ||
					lastCard.color == card.color ||
					card.type == Type.Draw_Two)
				return true;
			else
				return false;
		}else if (lastCard.type == Type.Wild){
			if (card.color == Color.Black ||
					card.color == currentColor)
				return true;
			else
				return false;
		}else if (lastCard.type == Type.Wild_Draw_Four){
			if (card.color == Color.Black ||
					card.color == currentColor)
				return true;
			else
				return false;
		}
		return false;
	}

	/*
	程序AI的选择
	 */
	public static HashMap<String, Object> aiChoice(UNOView unoView, Player player){
		Color current_color = unoView.gc.currentColor;
		Card current_card = unoView.gc.currentCard;
		int current_num = current_card.number;
		Type current_type = current_card.type;
		GameController gc = unoView.gc;
		Player current_player = unoView.gc.alp.get(unoView.gc.currentPlayer);
		ArrayList<Card> canOut = new ArrayList<Card>();
		Set<Card> canSet = new HashSet<Card>();
		Set<Color> colorSet = new HashSet<Color>();
		HashMap<String, Object> result = new HashMap<String, Object>();
		ArrayList<Card> result_card = new ArrayList<Card>();
		Iterator<Card> it_cardSet = canSet.iterator();
		boolean isCondition = false; 							//用于判断是否满足条件

		result.put("color", null);

		for (int i = 0; i < current_player.cardNumber(); i++){
			if (isAbledToPlay(current_card, current_player.cardGroup.get(i), current_color)) {
				canOut.add(current_player.cardGroup.get(i));
				canSet.add(current_player.cardGroup.get(i));
				if (current_player.cardGroup.get(i).color != Color.Black)
					colorSet.add(current_player.cardGroup.get(i).color);
			}
		}

		if (canSet.size() == 0)
			return null;

		if ( 0 == player.aiLevel){
			result_card.add(randomCard(canOut));
			result.put("card", result_card);
			return result;
		}

		//下一个玩家只剩一张，快要打完时
		if (gc.alp.get(gc.nextPlayer()).cardNumber() == 1){
			it_cardSet = canSet.iterator();
			while(it_cardSet.hasNext()){
				Card temp = it_cardSet.next();
				if (temp.type != Type.Number) {				//功能牌就打出，给对方制造麻烦
					if (temp.type == Type.Wild || temp.type == Type.Wild_Draw_Four){
						result.remove("color");
						result.put("color", returnColorExcept(colorSet, current_color));	//颜色尽量避开现在的颜色
					}
					result_card.add(temp);
					result.put("card", result_card);
					return result;
				}

				if (temp.color != current_color){
					result_card.add(temp);
					result.put("card", result_card);
					return result;
				}

				//看看最后三张是否颜色相同
				Set<Color> last3color = new HashSet<Color>();
				for (int i = 0; i < 3; i++)
					last3color.add(unoView.card_record.get(unoView.card_record.size() - 1 - i).color);

				if (last3color.size() == 1)			//相同则抓牌，说不定能改变一下，博一下
					return null;
				else{
					result_card.add(randomCard(canOut));
					result.put("card", result_card);
					return result;
				}
			}
		}

		/*
		剩两张都可以出，选择留哪一张
		 */
		if (player.cardNumber() <= 3 && canOut.size() == 2){
			for (int i = 0; i < canOut.size(); i++){
				Card temp = canOut.get(i);
				//如果有万能牌，选择留万能牌，不遇到加2和加4的话，必胜
				if (temp.type == Type.Wild_Draw_Four || temp.type == Type.Wild) {
					if (i == 0) {
						result_card.add(canOut.get(1));
						result.put("card", result_card);
						return result;
					} else {
						result_card.add(canOut.get(0));
						result.put("card", result_card);
						return result;
					}
				}
			}
		}

		/*
		剩三张，如果有万能牌且可出牌大于2张时
		 */
		if (player.cardNumber() == 3 && canSet.size() == 3){
			it_cardSet = canSet.iterator();
			while(it_cardSet.hasNext()) {
				Card temp = it_cardSet.next();
				if (temp.type == Type.Wild_Draw_Four || temp.type == Type.Wild) {
					canSet.remove(temp);
				}
			}
			if (canSet.size() > 0){
				result_card.add(maxScoreCard(canSet));
				result.put("card", result_card);
				return result;
			}
		}

		/*
		除了万能牌没有能出的牌时
		 */
		it_cardSet = canSet.iterator();
		boolean isMustWild = true;
		while (it_cardSet.hasNext()){
			Card card = it_cardSet.next();
			if (card.type != Type.Wild_Draw_Four && card.type != Type.Wild)
				isMustWild = false;
		}
		if (isMustWild){
			//这时候如果其他人牌数较多可以选择抓牌，万能牌留到关键的时候用
			if (gc.alp.get(gc.nextPlayer()).cardNumber() >=5 && gc.alp.get(gc.facePlayer()).cardNumber() >= 5 &&
					gc.alp.get(gc.beforePlayer()).cardNumber() >= 5){
				return null;
			}else{
				result_card.add(randomCard(canOut));
				result.put("card", result_card);
				return result;
			}
		}



		result_card.add(maxScoreCard(canSet));
		result.put("card", result_card);
		return result;

//		return null;
	}

	/*
	随机选一个除了给定颜色的颜色
	 */
	public static Color returnColorExcept(Set<Color> colorSet, Color except_color){
		ArrayList<Color> temp_list = new ArrayList<Color>();

		Iterator<Color> it = colorSet.iterator();					//用迭代器将SET转换为ARRAYLIST

		while(it.hasNext()){
			Color c = it.next();
			if (c != except_color)
				temp_list.add(c);
		}
		int t = ((int)(Math.random() * 100)) % (colorSet.size());
		return temp_list.get(t);
	}

	/*
	从牌组里随机选一张
	 */
	public static Card randomCard(ArrayList<Card> cards){
		int n = ((int)((Math.random()*100)) % (cards.size()));
		Card c = cards.get(n);
		return c;
	}

	/*
	获得罚分最高的一张卡
	 */
	public static Card maxScoreCard(Set<Card> cardSet){
		Iterator<Card> it = cardSet.iterator();
		TreeSet<Integer> score_set = new TreeSet<>();
		Card temp;
		while(it.hasNext()){
			temp = it.next();
			score_set.add(getCardScore(temp));
		}
		it = cardSet.iterator();
		while (it.hasNext()){
			temp = it.next();
			if (getCardScore(temp) == score_set.last())
				return temp;
		}
		return null;
	}

//	public static Card maxScoreCard(ArrayList<Card> cardSet){
//		Iterator<Card> it = cardSet.iterator();
//		TreeSet<Integer> score_set = new TreeSet<>();
//		Card temp;
//		while(it.hasNext()){
//			temp = it.next();
//			score_set.add(getCardScore(temp));
//		}
//		it = cardSet.iterator();
//		while (it.hasNext()){
//			temp = it.next();
//			if (getCardScore(temp) == score_set.last())
//				return temp;
//		}
//		return null;
//	}

	/*
	除了黑色，有几种颜色
	 */
	public static int numberOfColor(Set<Card> cardSet){
		Iterator<Card> it = cardSet.iterator();
		Set<Color> colorSet = new HashSet<>();
		while(it.hasNext()){
			Card temp = it.next();
			if (temp.color != Color.Black)
				colorSet.add(temp.color);
		}
		return colorSet.size();
	}

	public static int getCardScore(Card card){
		if (card.type == Type.Number)
			return card.number;
		else if (card.type == Type.Wild || card.type == Type.Wild_Draw_Four)
			return 50;
		else
			return 20;
	}

	/*
	计算给定牌集的分数
	 */
	public static int getScore(ArrayList<Card> cardSet){
		Iterator<Card> it = cardSet.iterator();
		int score_sum = 0;
		while(it.hasNext()){
			Card temp = it.next();
			if (temp.type == Type.Number)
				score_sum += temp.number;
			else if (temp.type == Type.Wild || temp.type == Type.Wild_Draw_Four)
				score_sum += 50;
			else
				score_sum += 20;
		}
		return score_sum;
	}

	//输出基本信息
	public static void outputInformation(UNOView unoView){
		System.out.println("方向:" + unoView.gc.currentDirection + "当前玩家：" + unoView.gc.currentPlayer);
	}
}