package com.example.dell.myuno;

public enum Color {
	Black(0),
	Red(1),
	Yellow(2),
	Blue(3),
	Green(4);
	private final int Color;
	private Color(int i) {
		this.Color=i;
	}
	public int getValue(){
		return Color;
	}
}
