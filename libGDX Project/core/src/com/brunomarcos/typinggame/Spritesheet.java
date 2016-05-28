package com.brunomarcos.typinggame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Spritesheet {
	private TextureRegion[] texArray;
	private Animation animacao;
	private int frames;
	private float stateTime;
	public float width;
	public float height;
	public float velocidade;

	public Spritesheet(Texture textura, int frames, int width, int height, int linhas, int colunas){
		this.frames = frames;
		this.width = width;
		this.height = height;
		this.texArray = new TextureRegion[frames];
		this.velocidade = 0.025f;
		this.stateTime = 0f;
		
		for (int i = 0; i < linhas; i++) {
			for (int j = 0; j < colunas; j++) {
				int k = (i * colunas) + j;
				if (k < frames)
					texArray[k] = new TextureRegion(textura,j*width,i*height,width,height);
				else
					break;
			}
		}
		
		this.animacao = new Animation(this.velocidade,this.texArray);
	}
	
	public void play(SpriteBatch batch, float x, float y, boolean loop){
		this.stateTime += Gdx.graphics.getDeltaTime();
		if (loop) {
			batch.draw(this.animacao.getKeyFrame(this.stateTime, loop),x,y);
		}
		else if (this.stateTime < this.frames * this.velocidade) {
			batch.draw(this.animacao.getKeyFrame(this.stateTime, loop),x,y);
		}
	}

	public void play(SpriteBatch batch, float x, float y, boolean loop, boolean flag){
		this.stateTime += Gdx.graphics.getDeltaTime();
		if (loop) {
			batch.draw(this.animacao.getKeyFrame(this.stateTime, loop),x,y);
		}
		else if (this.stateTime < this.frames * this.velocidade) {
			batch.draw(this.animacao.getKeyFrame(this.stateTime, loop),x,y);
		}
		else
			flag = false;
	}
	
	public void getFrame(SpriteBatch batch, int n, float x, float y, float w, float h) {
		batch.draw(this.animacao.getKeyFrame(n, false), x, y, w, h);
	}
	
	public void reset() {
		this.stateTime = 0f;
	}
}
