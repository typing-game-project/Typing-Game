package com.tu.streetescape;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class Enemy extends Calculos{

	private int randirectmov; 
	private float randtimemov = 0, contmov = 0, randstopmov = 0, contstopmov = 0;
	
	public int movx, movy;
	
	public Enemy(MainGame jogo) {
		super(jogo);
		// TODO Auto-generated constructor stub
	}

	public void movEnemy(Rectangle enemy){	
		if(contmov >= randtimemov){
			if(contstopmov < randstopmov){
				contstopmov += Gdx.graphics.getDeltaTime();
			}

			if(contstopmov >= randstopmov){
				contstopmov = 0;
				randstopmov = MathUtils.random(1);
				contmov = 0;
				randtimemov = MathUtils.random(3);
				//1 = up, 2 = down, 3 = left, 4 = right
				randirectmov = MathUtils.random(1, 4);
			}
		}
			
		if(contmov < randtimemov){
			contmov += Gdx.graphics.getDeltaTime();
			
			if(randirectmov == 1){
				enemy.y += 100 * Gdx.graphics.getDeltaTime();
			}
			if(randirectmov == 2){
				enemy.y -= 100 * Gdx.graphics.getDeltaTime();
			}
			if(randirectmov == 3){
				enemy.x -= 100 * Gdx.graphics.getDeltaTime();
			}
			if(randirectmov == 4){
				enemy.x += 100 * Gdx.graphics.getDeltaTime();
			}
		}
		
		boolean dist = calculaDistanciadaParede(enemy);
		
		if(dist == true){
			int lado = checaLado(enemy);
			if(lado == 1){
				randirectmov = 2;
			}
			if(lado == 2){
				randirectmov = 1;
			}
			if(lado == 3){
				randirectmov = 3;
			}
			if(lado == 4){
				randirectmov = 4;
			}
		}
	}

	
	public void ataqueEnemy(Rectangle enemy, Rectangle trans, Array<Rectangle>obj, int type){
		//1 = manifestantes, 2 = policiais, 3 = Nontendistas
		
		
		
		if(type == 1){
		
		}else if(type == 2){
			
		}else if(type == 3){
			
		}
	}
}
