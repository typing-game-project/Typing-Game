package com.brunomarcos.typinggame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

public class Level implements Screen {
	final GameManager game; // referência necessária para a classe GameManager
	public String password;
	public int timer;
	public Frase frase;
	
	public Level(final GameManager game, String frase, String password, int timer) {
		this.game = game;
		this.frase = new Frase(frase);
		this.password = password;
		this.timer = timer;
	}
	
	@Override
	public void render(float delta) {		
		// Aqui vai o game loop
		game.batch.begin();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// Loop para imprimir as letras presentes na frase
		frase.imprimeFrase(game);
		
		// Números para Debug
		game.font.draw(game.batch, "Pontos: " + Integer.toString(game.player.pontos), 500, 284);
		game.font.draw(game.batch, "x" + Integer.toString(game.player.multiplicador), 600, 284);
		game.font.draw(game.batch, "Vida: " + Integer.toString(game.player.vida), 700, 284);
		game.font.draw(game.batch, "Acertos Consecutivos: " + 
				Integer.toString(game.player.acertosConsecutivos), 500, 234);
		
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
