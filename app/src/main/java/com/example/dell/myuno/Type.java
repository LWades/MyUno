package com.example.dell.myuno;

public enum Type {
	Number(0),
	Skip(1),
	Reverse(2),
	Draw_Two(3),
	Wild(4),
	Wild_Draw_Four(5);
	private final int Type;
	private Type(int i) {
		this.Type=i;
	}
	public int getValue(){
		return Type;
	}
}
