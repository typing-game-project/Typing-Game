package com.brunomarcos.typinggame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

public class Level implements Screen {
	final GameManager game; // referência necessária para a classe GameManager
	public String password;
	public int timer;
	public Frase frase;
	
	// BG
	private Texture bg;
	private int hudHeight;
	private int[] bgOffset;
	private int velocidadeAnimacaoBG;
	private boolean vaiVoltaBG;
	
	public Level(final GameManager game, String frase, String password, int timer) {
		this.game = game;
		this.frase = new Frase(frase);
		this.password = password;
		this.timer = timer;
		
		// BG
		this.bg = game.bg[0];
		hudHeight = (game.height/100) * 14; // Altura da hud em porcentagem
		bgOffset = new int[2];
		bgOffset[0] = 0;
		bgOffset[1] = 0;
		velocidadeAnimacaoBG = 1;
		vaiVoltaBG = true;
	}
	
	private void animarBG() {
		int x = bgOffset[0];
		int y = bgOffset[1];
		int w = game.width;
		int h = game.height;
		int v = velocidadeAnimacaoBG;
		
		if (x >= 200 && vaiVoltaBG)
			vaiVoltaBG = false;
		
		else if (x <= 0 && !vaiVoltaBG)
			vaiVoltaBG = true;
			
		if (!vaiVoltaBG)
			v *= -1;
			
		bgOffset[0] += v;
		bgOffset[1] += v;
			
		game.batch.draw(this.bg, x - w, y - h, w, h);
		game.batch.draw(this.bg, x, y - h, w, h);
		game.batch.draw(this.bg, x + w, y - h, w, h);		
		game.batch.draw(this.bg, x - w, y, w, h);
		game.batch.draw(this.bg, x, y, w, h);
		game.batch.draw(this.bg, x + w, y, w, h);
		game.batch.draw(this.bg, x - w, y + h, w, h);
		game.batch.draw(this.bg, x, y + h, w, h);	
		game.batch.draw(this.bg, x + w, y + h, w, h);
	}
	
	@Override
	public void render(float delta) {		
		// Aqui vai o game loop
		game.batch.begin();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		animarBG();
		
		game.batch.draw(game.hud, 0, game.height - hudHeight, game.width, hudHeight);
		
		// Loop para imprimir as letras presentes na frase
		frase.imprimeFrase(game);
		
		int topoTexto = game.height - 40;
		
		// Números para Debug
		game.fontP2white.draw(game.batch, "Pts:" + Integer.toString(game.player.pontos), game.width - 465, topoTexto);
		game.fontP2white.draw(game.batch, "x" + Integer.toString(game.player.multiplicador), game.width - 600, topoTexto);
		game.fontP2white.draw(game.batch, "Vida: " + Integer.toString(game.player.vida),  40, topoTexto);

		if (game.player.vida == 0) {
			//TODO Chamar a tela de DERROTA
			this.frase.frase.replace(0, frase.frase.length(), frase.fraseCompleta);
		}
		
		else {
			try {
				game.player.confereAcerto(this.frase.frase);
			}
			
			catch(Exception e) {
				//TODO Chamar a tela de SUCESSO
				this.frase.frase.replace(0, frase.frase.length(), frase.fraseCompleta);
			}
		}
		
		game.batch.end();
		dispose();
	}
	
	@Override
	public void dispose() {
	}
	
	// Esses métodos são obrigatórios, gerados pelo 'implements Screen'
	@Override public void show() {}
	@Override public void resize(int width, int height) {}
	@Override public void pause() {}
	@Override public void resume() {}
	@Override public void hide() {}
}
