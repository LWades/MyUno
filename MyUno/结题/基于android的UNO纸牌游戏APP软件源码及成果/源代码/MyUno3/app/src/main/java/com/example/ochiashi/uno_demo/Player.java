package com.example.ochiashi.uno_demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Player {
	ArrayList<Card> cardGroup = new ArrayList<Card>();
	int aiLevel = 0;
	int score = 0;

	public Player(int aiLevel) {
		this.aiLevel = aiLevel;
	}

	public void drawCard(Card c){
		cardGroup.add(c);
	}

	public synchronized Card playCard(Card c){
		cardGroup.remove(c);
		return c;
	}

	//电脑随机选择出牌
	public Card computerPlayCard(Card lastCard, Color currentColor){
		ArrayList<Card> temp = new ArrayList<Card>();

		for (int i = 0; i < cardNumber(); i++){
			if (isAbledToPlay(lastCard, cardGroup.get(i), currentColor))
				temp.add(cardGroup.get(i));
		}

		if (temp.size() != 0){
			int n = ((int)((Math.random()*100)) % (temp.size()));	//在可以出牌的列表中随机选择一个牌打出
//			System.out.println("出牌随机数" + n);
			Card c = temp.get(n);
			cardGroup.remove(c);
			return c;
		}else
			return null;
	}

	public HashMap<String, Object> computerPlayCard2(UNOView unoView){
		ArrayList<Card> temp_list;
		HashMap<String, Object> temp_map;
		temp_map = Common.aiChoice(unoView, this);

		if (temp_map != null){
			temp_list = (ArrayList<Card>) temp_map.get("card");

			for (int i = 0; i < temp_list.size(); i++)
				cardGroup.remove(temp_list.get(i));
		}

		return temp_map;
	}

	/**
	 *
	 * @return手牌中有哪些颜色的牌（除了万能牌）
	 */
	public Color returnRandomColor(){
		Set<Color> temp = new HashSet<Color>();
		ArrayList<Color> temp_list = new ArrayList<Color>();

		for (int i = 0 ; i < cardGroup.size(); i++){
			if (cardGroup.get(i).color != Color.Black)
				temp.add(cardGroup.get(i).color);
		}
		Iterator<Color> it = temp.iterator();					//用迭代器将SET转换为ARRAYLIST

		while(it.hasNext()){
			Color c = it.next();
			temp_list.add(c);
//			System.out.println("颜色列表:" + c);
		}
		int t = ((int)(Math.random() * 100)) % (temp.size());
//		System.out.println("随机的数字" + t);
		return temp_list.get(t);
	}

	public synchronized boolean playerCanDraw(Card lastCard, Color currentColor){
		ArrayList<Card> temp = new ArrayList<Card>();

//		Iterator<Card> it = cardGroup.iterator();
//		while(it.hasNext()){
//
//		}
		for (int i = 0; i < cardNumber(); i++){
			if (isAbledToPlay(lastCard, cardGroup.get(i), currentColor))
				temp.add(cardGroup.get(i));
		}

		if (temp.size() != 0){
			return true;
		}else
			return false;
	}

	/*
	返回值：手牌数量
	 */
	public int cardNumber(){
		return cardGroup.size();
	}

	//判断一张牌是否可以出
	public boolean isAbledToPlay(Card lastCard, Card card ,Color currentColor){
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

	//判定手牌中是否有可以出的牌
	public boolean hasCardsToPlay(Card lastCard, Color currentColor){
		if (lastCard.type == Type.Number){
			for (int i = 0; i < cardGroup.size(); i++)
				if (lastCard.number == cardGroup.get(i).number ||
						lastCard.color == cardGroup.get(i).color ||
						cardGroup.get(i).color == Color.Black)
					return true;
			return false;
		}else if (lastCard.type == Type.Skip){
			for (int i = 0; i < cardGroup.size(); i++)
				if (cardGroup.get(i).color == Color.Black ||
						lastCard.color == cardGroup.get(i).color ||
						cardGroup.get(i).type == Type.Skip)
					return true;
			return false;
		}else if (lastCard.type == Type.Reverse){
			for (int i = 0; i < cardGroup.size(); i++)
				if (cardGroup.get(i).color == Color.Black ||
						lastCard.color == cardGroup.get(i).color ||
						cardGroup.get(i).type == Type.Reverse)
					return true;
			return false;
		}else if (lastCard.type == Type.Draw_Two){
			for (int i = 0; i < cardGroup.size(); i++)
				if (cardGroup.get(i).color == Color.Black ||
						lastCard.color == cardGroup.get(i).color ||
						cardGroup.get(i).type == Type.Draw_Two)
					return true;
			return false;
		}else if (lastCard.type == Type.Wild){
			for (int i = 0; i < cardGroup.size(); i++)
				if (cardGroup.get(i).color == Color.Black ||
						cardGroup.get(i).color == currentColor)
					return true;
			return false;
		}else if (lastCard.type == Type.Wild_Draw_Four){
			for (int i = 0; i < cardGroup.size(); i++)
				if (cardGroup.get(i).color == Color.Black ||
						cardGroup.get(i).color == currentColor)
					return true;
			return false;
		}
		return false;
	}

	public boolean Uno(){
		if(cardGroup.size()== 1)
			return true;
		else
			return false;
	}

	public boolean isEnd(){
		if (cardGroup.size() == 0)
			return true;
		else
			return false;
	}

	public Card chooseCard(){
		return null;
	}

	public Color chooseColor(){
		return Color.Blue;
	}

}