package com.brunomarcos.typinggame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

public class Level implements Screen {
	public final GameManager game; // referência necessária para a classe GameManager
	public String password;
	public int timer;
	public Frase frase;
	public Music bgm;
	public int maxVida;
	private boolean piscaVida;
	private StringBuilder placar;
	
	// BG
	private Texture bg;
	private Texture arteCantos;
	private float hudHeight;
	private int[] bgOffset;
	private int cantoOffsetYL;
	private int cantoOffsetYR;
	private int velocidadeAnimacaoBG;
	
	public Level(final GameManager game, LevelJSONData LJD, int i) {
		this.game = game;
		this.frase = new Frase(LJD.frase.get(i));
		this.password = LJD.password.get(i);
		this.timer = LJD.timer.get(i);
		this.bgm = LJD.bgmFile.get(LJD.bgm.get(i));
		int maxVida = LJD.maxVida.get(i);
		if (maxVida > 34)
			maxVida = 34;
		this.maxVida = maxVida;
		game.player.vida = this.maxVida;
		this.piscaVida = false;
		
		// BG
		this.bg = LJD.bgTex.get(LJD.bg.get(i));
		this.arteCantos = LJD.arteCantosTex.get(LJD.arteCantos.get(i));
		hudHeight = game.porCentoH(200);
		bgOffset = new int[2];
		bgOffset[0] = 0;
		bgOffset[1] = 0;
		cantoOffsetYL = 0;
		cantoOffsetYR = 0;
		velocidadeAnimacaoBG = 1;
		placar = new StringBuilder();
	}
	
	private void animarBG() {
		int x = bgOffset[0];
		int y = bgOffset[1];
		int w = GameManager.width;
		int h = GameManager.height;
		int v = velocidadeAnimacaoBG;
		
		if (bgOffset[0] <= -w)
			bgOffset[0] = 0;

		if (bgOffset[1] <= -h)
			bgOffset[1] = 0;
		
		if (cantoOffsetYL <= -h)
			cantoOffsetYL = 0;

		if (cantoOffsetYR >= h)
			cantoOffsetYR = 0;
		
		bgOffset[0] -= v * 2;
		bgOffset[1] -= v * 2;
		cantoOffsetYL -= v;
		cantoOffsetYR += v;
			
		game.batch.draw(this.bg, x, y + h, w, h);
		game.batch.draw(this.bg, x + w, y + h, w, h);
		game.batch.draw(this.bg, x, y, w, h);
		game.batch.draw(this.bg, x + w, y, w, h);
		
		game.batch.draw(arteCantos, 0, cantoOffsetYL, game.porCentoW(144), h);
		game.batch.draw(arteCantos, 0, cantoOffsetYL + h, game.porCentoW(144), h);
		game.batch.draw(arteCantos, w, cantoOffsetYR, -game.porCentoW(144), h);
		game.batch.draw(arteCantos, w, cantoOffsetYR - h, -game.porCentoW(144), h);
	}
	
	@Override
	public void render(float delta) {
		int w = GameManager.width;
		int h = GameManager.height;
		
		bgm.setLooping(true);
		bgm.play();
		
		// Aqui vai o game loop
		game.batch.begin();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		animarBG();
		
		game.batch.draw(game.hud, 0, h - hudHeight, w, hudHeight);
		
		// Loop para imprimir as letras presentes na frase
		frase.imprimeFrase(game);
		
		float topoTexto = h - game.porCentoH(80);
		
		// Números para Debug
		
		game.batch.draw(game.btnOpcoes, w - game.porCentoW(155), h - game.porCentoH(150), game.porCentoW(120), game.porCentoH(100));
		game.batch.draw(game.div, w - game.porCentoW(200), h - game.porCentoH(176), game.porCentoW(35), game.porCentoH(152));
		
		// Colocando os zeros do placar
		int digitos = Integer.toString(game.player.pontos).length();
		for (int i = 12; i > digitos; i--)
			placar.append("0");
		
		// Imprimindo o placar
		game.fontP2white.draw(game.batch, placar + Integer.toString(game.player.pontos), w/2 + game.porCentoW(100), topoTexto);
		
		// Limpando o placar
		for (int i = 0; i < 12; i++) {
			try {
				placar.deleteCharAt(0);
			}
			catch (Exception e) {
				break;
			}
		}
		
		// Imprime o Multiplicador
		game.batch.draw(game.div, w/2 + game.porCentoW(35), h - game.porCentoH(176), game.porCentoW(35), game.porCentoH(152));
		game.fontP2white.draw(game.batch, "x" + Integer.toString(game.player.multiplicador), w/2 - game.porCentoW(90), topoTexto);
		
		// Vida piscando
		// TODO: Deixar animação suave
		if (game.player.vida < 7){
			if (piscaVida) {
				game.batch.setColor(1,0,0,1);
				piscaVida = false;
			}
			else {
				game.batch.setColor(1,1,1,1);
				piscaVida = true;
			}
		}
		
		// Imprime a vida
		for (int i = 0; i < game.player.vida; i++) {
			if (i < 17)
				game.batch.draw(game.heart, game.porCentoW(34 + (47 * i)), h - game.porCentoH(96),game.porCentoW(40), game.porCentoH(56));
			else
				game.batch.draw(game.heart, game.porCentoW(34 + (47 * (i - 17))), h - game.porCentoH(160),game.porCentoW(40), game.porCentoH(56));	
		}
		
		game.batch.setColor(1,1,1,1);
		game.batch.draw(game.div, game.porCentoW(848), h - game.porCentoH(176), game.porCentoW(35), game.porCentoH(152));
			
		if (game.player.vida == 0) {
			//TODO Chamar a tela de DERROTA
			this.frase.criandoLinhas();
		}
		
		else {
			try {
				game.player.confereAcerto(game, this.frase);
				if(this.frase.linha.get(0).length() == 0) {
					this.frase.linha.remove(0);
					this.frase.linhaAtual++;
				}
			}
			
			catch(Exception e) {
				//TODO Chamar a tela de SUCESSO
				this.frase.criandoLinhas();
			}
		}
		
		game.batch.end();
		dispose();
	}
	
	// Esses métodos são obrigatórios, gerados pelo 'implements Screen'
	@Override public void dispose() {}
	@Override public void show() {}
	@Override public void resize(int width, int height) {}
	@Override public void pause() {}
	@Override public void resume() {}
	@Override public void hide() {}
}
