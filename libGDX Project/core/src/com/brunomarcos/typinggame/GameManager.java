package com.brunomarcos.typinggame;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;

public class GameManager extends Game {
	public SpriteBatch batch;
	public BitmapFont fontP2white;
	public BitmapFont fontP2black;
	public BitmapFont fontP2grey;
	public BitmapFont fontP2yellow;
	public BitmapFont fontArmaFiveBlack;
	public BitmapFont fontArmaFiveWhite;
	public boolean sfxOn;
	public boolean bgmOn;
	public Player player;
	public Texture hud;
	public Texture rect;
	public Texture btnOpcoes;
	public Texture div;
	public Texture heart;
	private Texture texAnimAcerto;
	private Texture cursor;
	public Texture bgLetras;
	public Texture boxPq;
	public Texture boxGd;
	public Texture boxPausa;
	public Texture logo;
	public Texture timerBG;
	public Spritesheet cursorState;
	public Spritesheet animAcerto;
	public static int width;
	public static int height;
	public Sound[] erroSnd;
	public Sound[] acertoSnd;
	private Json json;
	private LevelJSONData levelJD;
	public MainMenu mainMenu;
	public ArrayList<Level> levels;
	private Vector2 mousePos;
	public boolean hideCursor;
	public Random random;
	public static int levelAtual;
	public NineSlice btn;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		
		// Salvando tamanho da tela
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		
		// Populando variáveis
		fontP2white = new BitmapFont(Gdx.files.internal("fonts/PressStart2P_white.fnt"));
		fontP2black = new BitmapFont(Gdx.files.internal("fonts/PressStart2P_black.fnt"));
		fontP2grey = new BitmapFont(Gdx.files.internal("fonts/PressStart2P_grey.fnt"));
		fontP2yellow = new BitmapFont(Gdx.files.internal("fonts/PressStart2P_yellow.fnt"));
		fontArmaFiveBlack = new BitmapFont(Gdx.files.internal("fonts/armaFiveBlack.fnt"));
		fontArmaFiveWhite = new BitmapFont(Gdx.files.internal("fonts/armaFiveWhite.fnt"));
		sfxOn = true; // Para usar nas opções
		bgmOn = true; // Para usar nas opções
		player = new Player();
		erroSnd = new Sound[3];
		acertoSnd = new Sound[4];
		mousePos = new Vector2(width/2,height/2);
		hideCursor = false;
		random = new Random();
		
		// Redimensionando as fontes
		fontSize(fontP2white);
		fontSize(fontP2black);
		fontSize(fontP2grey);
		fontSize(fontP2yellow);
		fontSize(fontArmaFiveBlack);
		fontSize(fontArmaFiveWhite);
		
		// Instânciando as imagens
		hud = new Texture(Gdx.files.internal("img/hud_border.png"));
		rect = new Texture(Gdx.files.internal("img/rect.jpg"));
		btnOpcoes = new Texture(Gdx.files.internal("img/gear.png"));
		div = new Texture(Gdx.files.internal("img/div.png"));
		texAnimAcerto = new Texture(Gdx.files.internal("img/anim_acerto.png"));
		heart = new Texture(Gdx.files.internal("img/Heart.png"));
		cursor = new Texture(Gdx.files.internal("img/cursor.png"));
		bgLetras = new Texture(Gdx.files.internal("img/bg-letras.png"));
		boxPq = new Texture(Gdx.files.internal("img/box-pq.png"));
		boxGd = new Texture(Gdx.files.internal("img/box-gd.png"));
		boxPausa = new Texture(Gdx.files.internal("img/box-pause.png"));
		logo = new Texture(Gdx.files.internal("img/logo.png"));
		timerBG = new Texture(Gdx.files.internal("img/timerBG.png"));
		btn = new NineSlice(boxPq, 66, 74);
		
		// Animações
		animAcerto = new Spritesheet(texAnimAcerto, 6, 336, 352, 2, 3);
		cursorState = new Spritesheet(cursor, 2, 56, 80, 1, 2);
		animAcerto.velocidade = 0.025f;
		
		// Sons
		for (int i = 0; i < 3;i++)
			erroSnd[i] = Gdx.audio.newSound(Gdx.files.internal("sfx/error" + (i + 1) + ".mp3"));
		for (int i = 0; i < 4;i++)
			acertoSnd[i] = Gdx.audio.newSound(Gdx.files.internal("sfx/acerto" + (i + 1) + ".mp3"));
		
		// Lendo o JSON
		json = new Json();
		levelJD = new LevelJSONData();
		levelJD = json.fromJson(LevelJSONData.class, Gdx.files.internal("data/levels.json").readString("ISO-8859-1"));
		
		// Deserializando os levels:
		levels = new ArrayList<Level>();
		for (int i = 0; i < levelJD.frase.size(); i++)
			levels.add(i, new Level(this,levelJD,i));
		
		// Indo para o main menu:
		this.mainMenu = new MainMenu(this);
		this.setScreen(mainMenu);
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
	
	public void trocaCursor() {
		Gdx.input.setCursorCatched(true);
		
		float w = porCentoW(56);
		float h = porCentoH(80);
		
		if (!hideCursor)
			if(Gdx.input.isTouched())
				cursorState.getFrame(batch, 1, mousePos.x, mousePos.y - h, w, h);
			else
				cursorState.getFrame(batch, 0, mousePos.x, mousePos.y - h, w, h);
		
		float x = mousePos.x + Gdx.input.getDeltaX();
		float y = mousePos.y - Gdx.input.getDeltaY();

		if (x <= (width + w) && x >= 0 && y <= (height + h) && y >= 0)
			mousePos.set(x, y);
	}
	
	public boolean mouseColide(Rectangle rect) {
		float x = mousePos.x + Gdx.input.getDeltaX();
		float y = mousePos.y - Gdx.input.getDeltaY();
		
		if (x >= rect.x && x <= (rect.x + rect.width) &&
				y >= rect.y && y <= (rect.y + rect.height) && !hideCursor)
			return true;
		
		else
			return false;
	}
	
	public void resetMouse() {
		this.mousePos.set(width/2,height/2);
	}
	
    public void render() {
        super.render(); // Todos os render() dependem desse
    }

    public void dispose() {
        batch.dispose();
        fontP2white.dispose();
        fontP2black.dispose();
        fontP2grey.dispose();
        fontP2yellow.dispose();
        fontArmaFiveBlack.dispose();
        fontArmaFiveWhite.dispose();
        levelJD.dispose();
		hud.dispose();
		rect.dispose();
		btnOpcoes.dispose();
		div.dispose();
		texAnimAcerto.dispose();
		cursor.dispose();
		bgLetras.dispose();
		boxPq.dispose();
		boxGd.dispose();
		boxPausa.dispose();
		logo.dispose();
		timerBG.dispose();
		mainMenu.dispose();
    }
}
