package com.tu.streetescape;

public class MovPerso extends MainGame{
	
	public int transeuntex, transeuntey;
	
	public int goup(){
		transeuntey = transeuntey + 5;
		return transeuntey;
	}
	
	public int godown(){
		transeuntey = transeuntey - 5;
		return transeuntey;
	}
	
	public int goleft(){
		transeuntex = transeuntex - 5;
		return transeuntex;
	}
	
	public int goright(){
		transeuntex = transeuntex + 5;
		return transeuntex;
	}
}
