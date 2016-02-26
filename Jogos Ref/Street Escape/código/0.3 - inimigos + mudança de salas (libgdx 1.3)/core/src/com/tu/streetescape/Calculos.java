package com.tu.streetescape;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class Calculos {
	private final MainGame jogo;
	
	public Calculos(final MainGame jogo){
		this.jogo = jogo;
	}
	
	protected boolean calculaDistanciadaParede(Rectangle enemy){
		
		if(jogo.HEIGHT - enemy.y <= 50){
			return true;
		}
		if(jogo.HEIGHT - enemy.y >= 430){
			return true;
		}
		if(jogo.WIDTH - enemy.x <= 50){
			return true;
		}
		if(jogo.WIDTH - enemy.x >= 750){
			return true;
		}
		return false;
	}
	
	protected int checaLado(Rectangle enemy){
		//1 = cima, 2 = baixo, 3 = direita, 4 = esquerda
		if(jogo.HEIGHT - enemy.y <= 50){
			return 1;
		}
		if(jogo.HEIGHT - enemy.y >= 430){
			return 2;
		}
		if(jogo.WIDTH - enemy.x <= 50){
			return 3;
		}
		if(jogo.WIDTH - enemy.x >= 750){
			return 4;
		}
		return 0;
	}
	
	protected boolean checaProxRect(Rectangle enemy, Rectangle trans){
		if(((enemy.x + (jogo.persowidth/2 + 50) <= trans.x) && (enemy.x >= trans.x - (jogo.persowidth/2 + 50)))
				&& ((enemy.y + (jogo.persowidth/2 + 50) <= trans.y) && (enemy.y >= trans.y - (jogo.persowidth/2 + 50)))){
			return true;
		}
		return false;
	}
	
	protected int checaProxRectDir(Rectangle enemy, Rectangle trans){
		//1 = Próximo esq, 2 = próximo dir, 3 = próximo cima, 4 = próximo baixo
		if((enemy.x <= trans.x) && ((enemy.y + (jogo.persoheight/2) >= trans.y) && (enemy.y - (jogo.persoheight/2) <= trans.y))){
			return 1;
		}
		if((enemy.x >= trans.x) && ((enemy.y + (jogo.persoheight/2) >= trans.y) && (enemy.y - (jogo.persoheight/2) <= trans.y))){
			return 2;
		}
		if((enemy.y >= trans.y) && ((enemy.x + (jogo.persoheight/2) >= trans.x) && (enemy.x - (jogo.persoheight/2) <= trans.x))){
			return 3;
		}
		if((enemy.y <= trans.y) && ((enemy.x + (jogo.persoheight/2) >= trans.x) && (enemy.x - (jogo.persoheight/2) <= trans.x))){
			return 4;
		}
		return 0;
	}
	
	protected void geraTiro(Array<Rectangle>tiros, Rectangle enemy){
		Rectangle tiro = new Rectangle(10, 10, enemy.x, enemy.y);
		tiros.add(tiro);
	}
	
	protected void Colisao(Rectangle trans, Rectangle parede){
		if(trans.overlaps(parede)){
			if(trans.x >= (jogo.HEIGHT - jogo.persoheight)){
				if(Gdx.input.isKeyPressed(Keys.W)){
					trans.x = trans.x;
				}
			}
			if(trans.x <= jogo.persoheight){
				if(Gdx.input.isKeyPressed(Keys.S)){
					trans.x = trans.x;
				}
			}
			if(trans.y >= (jogo.WIDTH - jogo.persowidth)){
				if(Gdx.input.isKeyPressed(Keys.A)){
					trans.y = trans.y;
				}
			}
			if(trans.y <= jogo.persowidth){
				if(Gdx.input.isKeyPressed(Keys.D)){
					trans.y = trans.y;
				}
			}
		}
	}
}
