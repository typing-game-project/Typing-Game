package com.brunomarcos.typinggame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

public class MainMenu implements Screen {
	public final GameManager game;
	private float bgLetrasPosX;
	private float escalaLogo;
	private boolean escalaLogoIndoVindo;
	
	public MainMenu(final GameManager game) {
		this.game = game;
		bgLetrasPosX = 0;
		escalaLogo = 1f;
		escalaLogoIndoVindo = false;
	}
	
	@Override public void render(float delta) {
		game.batch.begin();
		Gdx.gl.glClearColor(0.9f, 0.9f, 0.9f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// Animação letras do fundo
		bgLetrasPosX += 0.5;
		
		// Animação de escala do logo
		if (escalaLogo < 1.07f && !escalaLogoIndoVindo)
			escalaLogo += 0.001f;
		
		else if (escalaLogo >= 1.07f)
			escalaLogoIndoVindo = true;
		
		if (escalaLogo > 1 && escalaLogoIndoVindo)
			escalaLogo -= 0.002f;
		
		else if (escalaLogo <= 1)
			escalaLogoIndoVindo = false;
		
		float w = game.porCentoW(2512);
		float h = game.porCentoH(1088);
		
		if (bgLetrasPosX >= w + game.porCentoW(100))
			bgLetrasPosX = 0;
		
		game.batch.draw(game.bgLetras,bgLetrasPosX,0, w, h);
		game.batch.draw(game.bgLetras,bgLetrasPosX - w - game.porCentoW(100),0, w, h);
		
		w = game.porCentoW(1104/escalaLogo);
		h = game.porCentoH(472 * escalaLogo);
		float x = GameManager.width;
		float y = GameManager.height;
		
		game.batch.draw(game.logo, x/2 - (w/2), y - game.porCentoH(600), w, h);
		
		game.trocaCursor();
			
		game.batch.end();
		dispose();
	}
	
	@Override public void show() {}
	@Override public void resize(int width, int height) {}
	@Override public void pause() {}
	@Override public void resume() {}
	@Override public void hide() {}
	@Override public void dispose() {}
}
