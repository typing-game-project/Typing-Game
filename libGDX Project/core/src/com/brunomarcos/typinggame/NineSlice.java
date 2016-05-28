package com.brunomarcos.typinggame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class NineSlice {
	private float[] x;
	private float[] y;
	private TextureRegion[] texArray;
	private Animation animacao;

	public NineSlice(Texture textura, float cantoWidth, float cantoHeight) {
		this.texArray = new TextureRegion[9];
		this.x = new float[4];
		this.y = new float[4];
		
		x[0] = 0;
		x[1] = cantoWidth;
		x[2] = textura.getWidth() - cantoWidth;
		x[3] = textura.getWidth();

		y[0] = 0;
		y[1] = cantoHeight;
		y[2] = textura.getHeight() - cantoHeight;
		y[3] = textura.getHeight();
		
		for (int i = 0; i < 9; i++) {
			for(int j = 0; j < 3; j++) {
				for(int k = 0; k < 3; k++) {
					this.texArray[i] = new TextureRegion(
							textura, x[k], y[j], x[k + 1] - x[k], y[j + 1] - y[j]);
				}
			}
		}
		
		this.animacao = new Animation(1, this.texArray);
	}
	
	public void draw(SpriteBatch batch, float x, float y, float width, float height) {
		float w;
		float h;
		
		for (int i = 0; i < 9; i++) {
			for(int j = 0; j < 3; j++) {
				for(int k = 0; k < 3; k++) {
					if (k == 1)
						w = width - (2 * this.x[1]);
					else
						w = this.x[1];
					if (j == 1)
						h = height - (2 * this.y[1]);
					else
						h = this.y[1];
					batch.draw(this.animacao.getKeyFrame(i, false), x + this.x[k], y + this.y[j], w, h);
				}
			}
		}
	}
}
