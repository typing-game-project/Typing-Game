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
		sfxOn = true; // Para usar nas op��es
		bgmOn = true; // Para usar nas op��es
		player = new Player();
		levels = new ArrayList<Level>();
		
		// Instanciando os levels
		levels.add(0,new Level(this));
		Level primeiro = levels.get(0);
		primeiro.frase.append("FRASE DE TESTE USANDO � E �.");
		
		// Indo para a primeira tela:
		this.setScreen(primeiro);
	}
	
    public void render() {
        super.render(); // Esse m�todo tem que existir, n�o sei direito o porqu�
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}
