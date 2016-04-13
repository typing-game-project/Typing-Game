package com.brunomarcos.typinggame;

import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameManager extends Game {
	public SpriteBatch batch;
	public BitmapFont fontP2white;
	public BitmapFont fontP2black;
	public boolean sfxOn;
	public boolean bgmOn;
	public Player player;
	public ArrayList<Level> levels;
	public Texture[] bg;
	public Texture hud;
	public Texture rect;
	public Texture canto;
	public Texture btnOpcoes;
	public Texture div;
	public static int width;
	public static int height;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		fontP2white = new BitmapFont(Gdx.files.internal("fonts/PressStart2P_white.fnt"));
		fontP2black = new BitmapFont(Gdx.files.internal("fonts/PressStart2P_black.fnt"));
		sfxOn = true; // Para usar nas opções
		bgmOn = true; // Para usar nas opções
		player = new Player();
		levels = new ArrayList<Level>();
		
		// Salvando tamanho da tela
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		
		// Redimensionando as fontes
		fontSize(fontP2white);
		fontSize(fontP2black);
		
		// Instânciando as imagens
		bg = new Texture[3];
		bg[0] = new Texture(Gdx.files.internal("bg_red.jpg"));
		bg[1] = new Texture(Gdx.files.internal("bg_green.jpg"));
		bg[2] = new Texture(Gdx.files.internal("bg_blue.jpg"));
		hud = new Texture(Gdx.files.internal("hud_border.png"));
		rect = new Texture(Gdx.files.internal("rect.jpg"));
		canto = new Texture(Gdx.files.internal("ornamento.png"));
		btnOpcoes = new Texture(Gdx.files.internal("gear.png"));
		div = new Texture(Gdx.files.internal("div.png"));
		
		// Instânciando os levels:
		levels.add(0, new Level(this,"teste de ç e á.","DEBUG1",0)); // refêrencia ao game, número do level, frase, password, timer
		levels.add(1, new Level(this,"Vou escrever uma frase bem longa para ver se está cortando certinho o código de quebrar linhas","DEBUG2",60));
		
		// Indo para a primeira tela:
		this.setScreen(levels.get(1));
	}
	
	public float porCentoW(float w) {
		return (GameManager.width/100)*((100.0f/1920)*w);
	}
	
	public float porCentoH(float h) {
		return (GameManager.height/100)*((100.0f/1080)*h);
	}
	
	public void fontSize(BitmapFont f) {
		float porCento = (porCentoH(28) * 1.0f) / 25.9f;
		f.getData().setScale(porCento, porCento);
	}
	
    public void render() {
        super.render(); // Esse método tem que existir, não sei direito o porquê
    }

    public void dispose() {
        batch.dispose();
        fontP2white.dispose();
        fontP2black.dispose();
        for (Texture i:bg)
        	i.dispose();
		hud.dispose();
		rect.dispose();
		canto.dispose();
		btnOpcoes.dispose();
		div.dispose();
    }
}
