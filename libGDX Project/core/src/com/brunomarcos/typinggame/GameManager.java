package com.brunomarcos.typinggame;

import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Json;

public class GameManager extends Game {
	public SpriteBatch batch;
	public BitmapFont fontP2white;
	public BitmapFont fontP2black;
	public BitmapFont fontP2grey;
	public boolean sfxOn;
	public boolean bgmOn;
	public Player player;
	public Texture[] bg;
	public Texture hud;
	public Texture rect;
	public Texture canto;
	public Texture btnOpcoes;
	public Texture div;
	public Texture heart;
	private Texture texAnimAcerto;
	public Spritesheet animAcerto;
	public Music bgm;
	public static int width;
	public static int height;
	public Sound[] erroSnd;
	public Sound[] acertoSnd;
	private Json json;
	private ArrayList<Level> levels;
	public Resources res;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		
		// Salvando tamanho da tela
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		
		// Lendo o JSON
		json = new Json();
		res = new Resources();
		Resources res = json.fromJson(Resources.class, Gdx.files.internal("data/levels.json").readString("ISO-8859-1"));
		
		// Populando variáveis
		fontP2white = new BitmapFont(Gdx.files.internal("fonts/PressStart2P_white.fnt"));
		fontP2black = new BitmapFont(Gdx.files.internal("fonts/PressStart2P_black.fnt"));
		fontP2grey = new BitmapFont(Gdx.files.internal("fonts/PressStart2P_grey.fnt"));
		sfxOn = true; // Para usar nas opções
		bgmOn = true; // Para usar nas opções
		player = new Player();
		erroSnd = new Sound[3];
		acertoSnd = new Sound[4];
		
		// Redimensionando as fontes
		fontSize(fontP2white);
		fontSize(fontP2black);
		fontSize(fontP2grey);
		
		// Instânciando as imagens
		bg = new Texture[3];
		bg[0] = new Texture(Gdx.files.internal("img/bg_red.jpg"));
		bg[1] = new Texture(Gdx.files.internal("img/bg_green.jpg"));
		bg[2] = new Texture(Gdx.files.internal("img/bg_blue.jpg"));
		hud = new Texture(Gdx.files.internal("img/hud_border.png"));
		rect = new Texture(Gdx.files.internal("img/rect.jpg"));
		canto = new Texture(Gdx.files.internal("img/ornamento.png"));
		btnOpcoes = new Texture(Gdx.files.internal("img/gear.png"));
		div = new Texture(Gdx.files.internal("img/div.png"));
		texAnimAcerto = new Texture(Gdx.files.internal("img/anim_acerto.png"));
		heart = new Texture(Gdx.files.internal("img/Heart.png"));
		
		// Animações
		animAcerto = new Spritesheet(texAnimAcerto, 6, 336, 352, 2, 3);
		animAcerto.velocidade = 0.025f;
		
		// Sons
		for (int i = 0; i < 3;i++)
			erroSnd[i] = Gdx.audio.newSound(Gdx.files.internal("sfx/error" + (i + 1) + ".mp3"));
		for (int i = 0; i < 4;i++)
			acertoSnd[i] = Gdx.audio.newSound(Gdx.files.internal("sfx/acerto" + (i + 1) + ".mp3"));
		bgm = Gdx.audio.newMusic(Gdx.files.internal("bgm/LOOP4.wav"));
		
		// Deserializando os levels:
		levels = new ArrayList<Level>();
		for (int i = 0; i < res.frase.size(); i++)
			levels.add(i, new Level(this,res.frase.get(i),res.password.get(i),res.timer.get(i),res.maxVida.get(i)));
		
		// Indo para a primeira tela:
		this.setScreen(levels.get(0));
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
        fontP2grey.dispose();
        for (Texture i:bg)
        	i.dispose();
		hud.dispose();
		rect.dispose();
		canto.dispose();
		btnOpcoes.dispose();
		div.dispose();
		texAnimAcerto.dispose();
    }
}
