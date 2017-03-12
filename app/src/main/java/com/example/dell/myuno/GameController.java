package com.example.dell.myuno;

import java.util.ArrayList;

public class GameController {

	static final int clockwise = 1;
	static final int anticlockwise = 0;
	
	public ArrayList<Player> alp;
	public Color currentColor;
	public int currentPlayer;
	public int currentDirection;
	public Card currentCard;
	public Card cardNow = null;
	public int playerNumber;
	
	public CardGroup cg;
	UNOView unoView;
	public boolean isGameEnd = false;
	
	public GameController(CardGroup cg, UNOView unoView){
		currentColor = Color.Black;
		currentPlayer = 5;
		currentDirection = anticlockwise;
		currentCard = new Card();
		playerNumber = 4;
		this.cg = cg;
		this.unoView = unoView;
		alp = new ArrayList<Player>();
	}

	//游戏开始时的洗牌，发牌与初始化等
	public void gameStart(){
		
		//界面初始化
		System.out.println("界面初始化成功");
		
//		System.out.println("ϴ���뷢�Ƴɹ�");
		
		//系统选出牌库中随机的一张
		currentPlayer = (int)(Math.random()*100 % 4);
		System.out.println("currentPlayer" + currentPlayer);
		System.out.println("已随机选择了一名玩家先出牌");
		
	}
	
	public boolean isPlayerEnd(ArrayList<Player> x){
		for (int i = 0; i < playerNumber; i++){
			if (x.get(i).isEnd())
				return true;
		}
		return false;
	}
	
	//游戏进行时
	public void gameGoing(){
		if (!alp.get(currentPlayer).hasCardsToPlay(currentCard, currentColor)){
			alp.get(currentPlayer).drawCard(cg.draw());
			currentPlayer = (currentPlayer + 1) % playerNumber;
		}else
			this.play(currentCard, alp.get(currentPlayer).playCard(currentCard));	
		
		if (isPlayerEnd(alp))
			isGameEnd = true;
	}

//	public void gameWin(){
//		if (isPlayerEnd(alp))
//			isGameEnd = true;
//	}
	
	//游戏结束时的界面与释放资源
	public void end(){
		System.out.println("游戏结束");
	}
	
	public void goOn() {
		// TODO Auto-generated method stub
		if (currentDirection == anticlockwise){
			currentPlayer = (currentPlayer + 1) % playerNumber;
			//界面转换
			System.out.println("界面按逆时针转换");
		}else{
			System.out.println("方向:" + currentDirection + "当前玩家：" + currentPlayer);
			currentPlayer = (currentPlayer + 7) % playerNumber;
			System.out.println("方向:" + currentDirection + "当前玩家：" + currentPlayer);
			//界面转换
			System.out.println("界面按顺时针转换");
		}
	}

	//打出阻挡牌时
	public void skip() {
		// TODO Auto-generated method stub
			currentPlayer = (currentPlayer + 2) % playerNumber;
			//界面转换
			System.out.println("界面跳过一人转换");
	}
	
	//打出反转牌时
	public void reverse() {
		// TODO Auto-generated method stub
		if (currentDirection == clockwise)
			currentDirection = anticlockwise;
		else
			currentDirection = clockwise;
		System.out.println("方向变化");
	}

	//打出加二牌时
	public void drawTwo() {
		// TODO Auto-generated method stub
		if (currentDirection == anticlockwise){
			alp.get((currentPlayer + 1) % playerNumber).drawCard(cg.draw());
			alp.get((currentPlayer + 1) % playerNumber).drawCard(cg.draw());
			Common.rePosition(unoView, alp.get((currentPlayer + 1) % playerNumber), (currentPlayer + 1) % playerNumber);
			System.out.println("玩家手牌" + alp.get((currentPlayer + 1) % playerNumber).cardNumber() + "��");
			for (int i = 0; i < alp.get((currentPlayer + 1) % playerNumber).cardNumber(); i++)
				System.out.println("卡牌" + i + "为" + alp.get((currentPlayer + 1) % playerNumber).cardGroup.get(i));
			currentPlayer = (currentPlayer + 2) % playerNumber;
			System.out.println("drawtwo后颜色:" + currentColor);
			System.out.println("界面因drawtwo跳过一人转换");
		}else{
			alp.get((currentPlayer + 3) % playerNumber).drawCard(cg.draw());
			alp.get((currentPlayer + 3) % playerNumber).drawCard(cg.draw());
			Common.rePosition(unoView, alp.get((currentPlayer + 3) % playerNumber), (currentPlayer + 3) % playerNumber);
			System.out.println("玩家手牌" + alp.get((currentPlayer + 3) % playerNumber).cardNumber() + "��");
			currentPlayer = (currentPlayer + 2) % playerNumber;
			//界面转换
			System.out.println("drawtwo后颜色:" + currentColor);
			System.out.println("界面因drawtwo跳过一人转换");
		}

	}

	public Color chooseColor() {
		// TODO Auto-generated method stub
//		unoView.canvas.drawBitmap();
		return alp.get(currentPlayer).chooseColor();
	}

	public void changeCurrentColor(Color chooseColor) {
		// TODO Auto-generated method stub
		//界面转换
		System.out.println("生成选择颜色界面");
		currentColor = chooseColor;
	}
	
	//打出加四牌
	public void drawFour() {
		// TODO Auto-generated method stub
		if (currentDirection == anticlockwise){
			for (int i = 0; i < 4; i++)
				alp.get((currentPlayer + 1) % playerNumber).drawCard(cg.draw());
			Common.rePosition(unoView, alp.get((currentPlayer + 1) % playerNumber), (currentPlayer + 1) % playerNumber);
			System.out.println("玩家手牌" + alp.get((currentPlayer + 1) % playerNumber).cardNumber() + "��");
			currentPlayer = (currentPlayer + 2) % playerNumber;

			System.out.println("drawfour后颜色:" + currentColor);
			System.out.println("界面因drawfour跳过一人转换");
		}else{
			for (int i = 0; i < 4; i++)
				alp.get((currentPlayer + 3) % playerNumber).drawCard(cg.draw());
			Common.rePosition(unoView, alp.get((currentPlayer + 3) % playerNumber), (currentPlayer + 3) % playerNumber);
			System.out.println("玩家手牌" + alp.get((currentPlayer + 3) % playerNumber).cardNumber() + "��");
			currentPlayer = (currentPlayer + 2) % playerNumber;

			System.out.println("drawfour后颜色:" + currentColor);
			System.out.println("界面因drawfour跳过一人转换");
		}
	}
	
	//根据出牌判断该怎样进行
	public void play(Card lastCard, Card card){
		if (card == null)
			return;
		currentCard = card;
		if (card.type == Type.Number){
			if (lastCard.color == card.color){
				goOn();
			}
			else{
				currentColor = card.color;
				goOn();
			}
		}else if (card.type == Type.Skip){
			currentColor = card.color;
			skip();
		}else if (card.type == Type.Reverse){
			currentColor = card.color;
			reverse();
			goOn();
		}else if (card.type == Type.Draw_Two){
			currentColor = card.color;
			drawTwo();
		}else if (card.type == Type.Wild){
			if(currentPlayer == 0){
				unoView.is_chooseColor = true;
			}else{
				goOn();
			}
//			changeCurrentColor(chooseColor());
//			goOn();
		}else if (card.type == Type.Wild_Draw_Four){
//			changeCurrentColor(chooseColor());
			drawFour();
		}
	}
	
	public static void main(String[] args){
//		GameController gc = new GameController();
//		gc.gameStart();
//		
//		while(!gc.isGameEnd)
//			gc.gameGoing();
//		
//		gc.end();
	}
}
