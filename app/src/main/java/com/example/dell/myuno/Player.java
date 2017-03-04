package com.example.dell.myuno;

import java.util.ArrayList;

public class Player {
	ArrayList<Card> cardGroup = new ArrayList<Card>();
	
	public Player() {
		
	}
	
	public void drawCard(Card c){
		cardGroup.add(c);
	}
	
	public Card playCard(Card c){
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
//		System.out.println("currentColor:::" + currentColor);
//		System.out.println("temp" + temp);
		if (temp.size() != 0){
			int n = (int)(Math.random()*100 % (temp.size()));
			Card c = temp.get(n);
			cardGroup.remove(c);
			return c;
		}else
			return null;
	}

	public boolean playerCanDraw(Card lastCard, Color currentColor){
		ArrayList<Card> temp = new ArrayList<Card>();

		for (int i = 0; i < cardNumber(); i++){
			if (isAbledToPlay(lastCard, cardGroup.get(i), currentColor))
				temp.add(cardGroup.get(i));
		}

		if (temp.size() != 0){
			return true;
		}else
			return false;
	}
	
	//手牌数量
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