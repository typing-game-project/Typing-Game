package com.brunomarcos.typinggame;

import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameManager extends Game {
	public SpriteBatch batch;
	public BitmapFont font;
	public boolean sfxOn;
	public boolean bgmOn;
	public Player player;
	public ArrayList<Level> levels;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont();
		sfxOn = true; // Para usar nas opções
		bgmOn = true; // Para usar nas opções
		player = new Player();
		levels = new ArrayList<Level>();
		
		// Instânciando os levels:
		levels.add(0, new Level(this,"teste de ç e á","DEBUG1",0)); // refêrencia ao game, número do level, frase, password, timer
		levels.add(1, new Level(this,"FRASE GRANDE DE EXEMPLO PARA A SEGUNDA TELA, FRASE GRANDE DE EXEMPLO PARA A SEGUNDA TELA","DEBUG2",60));
		
		// Indo para a primeira tela:
		this.setScreen(levels.get(0));
	}
	
    public void render() {
        super.render(); // Esse método tem que existir, não sei direito o porquê
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}
