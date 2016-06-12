package com.brunomarcos.typinggame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Level implements Screen {
	public final GameManager game;
	public String password;
	public Frase frase;
	public Music bgm;
	public int maxVida;
	public static boolean pausado;
	private String titulo;
	private int cantoOffsetYL;
	private int cantoOffsetYR;
	private int velocidadeAnimacaoBG;
	private int pausaTimer;
	private int btnCooldown;
	private int introTimer;
	private int[] timer;
	private int[] bgOffset;
	private boolean piscaVida;
	private boolean started;
	private boolean gameOverScreen;
	private boolean sucessoScreen;
	private boolean acabouIntro;
	private float hudHeight;
	private float[] boxX;
	private float[] boxY;
	private float[] boxW;
	private float[] boxH;
	private long milisegundos;
	private long segundos;
	private long segundoAnterior;
	private StringBuilder placar;
	private Rectangle btnOpcoesRect;
	private Texture bg;
	private Texture arteCantos;
	private Rectangle[] boxBtn;
	
	public Level(final GameManager game, LevelJSONData LJD, int i) {
		this.game = game;
		this.titulo = LJD.titulo.get(i);
		this.frase = new Frase(LJD.frase.get(i));
		this.password = LJD.password.get(i);
		this.timer = new int[2]; 
		this.timer[1] = LJD.timer.get(i);
		this.bgm = LJD.bgmFile.get(LJD.bgm.get(i));
		int maxVida = LJD.maxVida.get(i);
		if (maxVida > 34)
			maxVida = 34;
		this.maxVida = maxVida;
		this.piscaVida = false;
		float x = GameManager.width - game.porCentoW(155);
		float y = GameManager.height - game.porCentoH(150);
		btnOpcoesRect = new Rectangle(x, y, game.porCentoW(120), game.porCentoH(100));
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
		pausado = false;
		started = false;
		bgm.setLooping(true);
		boxX = new float[7];
		boxY = new float[7];
		boxW = new float[7];
		boxH = new float[7];
		boxBtn = new Rectangle[5];
		setBoxes();
		boxBtn[0] = new Rectangle(boxX[3], boxY[3] - boxH[3], boxW[3], boxH[3]);
		boxBtn[1] = new Rectangle(boxX[4], boxY[4] - boxH[4], boxW[4], boxH[4]);
		boxBtn[2] = new Rectangle(boxX[5], boxY[5] - boxH[5], boxW[5], boxH[5]);
		boxBtn[3] = new Rectangle(boxX[6], boxY[6] - boxH[6], boxW[6], boxH[6]);
		boxBtn[4] = new Rectangle(boxX[0], boxY[0], boxW[0], boxH[0]);
	}
	
	private void onStart() {
		if (!started) {
			game.resetMouse();
			if (game.bgmOn)
				bgm.play();
			game.player.vida = this.maxVida;
			game.player.pontos = 0;
			game.player.multiplicador = 1;
			timer[0] = timer[1];
			started = true;
			gameOverScreen = false;
			sucessoScreen = false;
			segundos = 0;
			milisegundos = 0;
			segundoAnterior = System.currentTimeMillis();
			introTimer = 200;
			acabouIntro = false;
		}
	}
	
	@Override
	public void render(float delta) {
		int w = GameManager.width;
		int h = GameManager.height;
		
		// Aqui vai o game loop
		game.batch.begin();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		onStart();
		animarBG();
		
		if (!acabouIntro) {
			String line1 = "Level: " + Integer.toString(GameManager.levelAtual + 1);
			game.fontP2white.draw(game.batch, line1, w/2 - game.porCentoW(148), h/2);
			game.fontP2white.draw(game.batch, this.titulo, w/2 - game.porCentoW(407), h/2 - game.porCentoH(50));
					
			introTimer--;
			if (introTimer <= 0)
				acabouIntro = true;
		}
		
		else {
			game.batch.draw(game.hud, 0, h - hudHeight, w, hudHeight);
			
			// Loop para imprimir as letras presentes na frase
			frase.imprimeFrase(game);
			
			float topoTexto = h - game.porCentoH(80);
			
			// Opções aqui
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
			
			if (game.mouseColide(btnOpcoesRect) && Gdx.input.isTouched()) {
				if (!pausado && this.pausaTimer == 0) {
					pausado = true;
					pausaTimer = 30;
				}
			}
			
			if (game.player.vida == 0)
				gameOverScreen = true;
			
			else if (timer[1] > 0 && this.timer[0] == 0)
				gameOverScreen = true;
			
			else if (!Level.pausado) {
				try {
					game.player.confereAcerto(game, this.frase);
					if(this.frase.linha.get(0).length() == 0) {
						this.frase.linha.remove(0);
						this.frase.linhaAtual++;
					}
				}
				
				catch(Exception e) {
					sucessoScreen = true;
				}
			}
			
			if (timer[1] > 0) {
				game.batch.draw(game.timerBG, w - game.porCentoW(148 + 150), game.porCentoH(50), 148, 152);
				game.fontP2black.draw(game.batch, Long.toString(timer[0]),
						w - game.porCentoW(148 + 90), game.porCentoH(165));
			}
			
			if (gameOverScreen)
				gameOver();
			
			if (sucessoScreen)
				fimDaTela();
			
			if (pausado) {
				menuDePausa();
			}
			
			else {
				bgm.setVolume(1.0f);
			}
			
			if (pausaTimer > 0)
				pausaTimer--;
			
			if (btnCooldown > 0)
				btnCooldown--;
			
			game.trocaCursor();
			
			if (!sucessoScreen && !pausado && !gameOverScreen) {
				milisegundos += System.currentTimeMillis() - segundoAnterior;
				if (milisegundos / 1000 > segundos) {
					timer[0]--;
				}
				segundos = milisegundos / 1000;
				segundoAnterior = System.currentTimeMillis();
			}
		}			
		
		game.batch.end();
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
		
		if (!pausado) {
			bgOffset[0] -= v * 2;
			bgOffset[1] -= v * 2;
			cantoOffsetYL -= v;
			cantoOffsetYR += v;
		}
		
		game.batch.draw(this.bg, x, y + h, w, h);
		game.batch.draw(this.bg, x + w, y + h, w, h);
		game.batch.draw(this.bg, x, y, w, h);
		game.batch.draw(this.bg, x + w, y, w, h);
		
		game.batch.draw(arteCantos, 0, cantoOffsetYL, game.porCentoW(144), h);
		game.batch.draw(arteCantos, 0, cantoOffsetYL + h, game.porCentoW(144), h);
		game.batch.draw(arteCantos, w, cantoOffsetYR, -game.porCentoW(144), h);
		game.batch.draw(arteCantos, w, cantoOffsetYR - h, -game.porCentoW(144), h);
	}
	
	public void setBoxes() {
		float w = GameManager.width;
		float h = GameManager.height;

		boxX[0] = w/2 - game.porCentoW(510);
		boxY[0] = h - game.porCentoH(883);
		boxW[0] = game.porCentoW(1020);
		boxH[0] = game.porCentoH(490);
		
		boxX[1] = w/2 - game.porCentoW(510);
		boxY[1] = h - game.porCentoH(199);
		boxW[1] = 0;
		boxH[1] = 0;
		
		boxX[2] = w/2 - game.porCentoW(510);
		boxY[2] = h - game.porCentoH(293);
		boxW[2] = 0;
		boxH[2] = 0;
		
		boxX[3] = boxX[0] + game.porCentoW(70);
		boxY[3] = boxY[0] + game.porCentoH(400);
		boxW[3] = game.porCentoW(750);
		boxH[3] = game.porCentoH(30);
		
		boxX[4] = boxX[0] + game.porCentoW(70);
		boxY[4] = boxY[0] + game.porCentoH(320);
		boxW[4] = game.porCentoW(850);
		boxH[4] = game.porCentoH(30);
		
		boxX[5] = boxX[0] + game.porCentoW(70);
		boxY[5] = boxY[0] + game.porCentoH(220);
		boxW[5] = game.porCentoW(700);
		boxH[5] = game.porCentoH(30);
		
		boxX[6] = boxX[0] + game.porCentoW(70);
		boxY[6] = boxY[0] + game.porCentoH(120);
		boxW[6] = game.porCentoW(700);
		boxH[6] = game.porCentoH(30);
	}
	
	private void menuDePausa() {
		game.batch.setColor(0,0,0,0.8f);
		game.batch.draw(game.rect,0,0,GameManager.width,GameManager.height);
		game.batch.setColor(1,1,1,1);
		bgm.setVolume(0.5f);
		String on;
		
		game.batch.draw(game.boxPausa, boxX[0], boxY[0], boxW[0], boxH[0]);
		game.fontP2white.draw(game.batch,"PAUSA", boxX[1], boxY[1]);
		game.fontP2white.draw(game.batch,"Password: " + this.password, boxX[2], boxY[2]);
		
		if (game.sfxOn)
			on = "Ligado";
		else
			on = "Desligado";
		
		if (game.mouseColide(this.boxBtn[0]))
			game.fontP2yellow.draw(game.batch,"Sons: " +  on, boxX[3], boxY[3]);
		else
			game.fontP2white.draw(game.batch,"Sons: " +  on, boxX[3], boxY[3]);
		
		if (game.bgmOn)
			on = "Ligado";
		else
			on = "Desligado";
		
		if (game.mouseColide(this.boxBtn[1]))
			game.fontP2yellow.draw(game.batch,"Música: " +  on, boxX[4], boxY[4]);
		else
			game.fontP2white.draw(game.batch,"Música: " +  on, boxX[4], boxY[4]);
		
		if (game.mouseColide(this.boxBtn[2]))
			game.fontP2yellow.draw(game.batch,"Menu Principal", boxX[5], boxY[5]);
		else
			game.fontP2white.draw(game.batch,"Menu Principal", boxX[5], boxY[5]);
		
		if (game.mouseColide(this.boxBtn[3]))
			game.fontP2yellow.draw(game.batch,"Voltar ao Jogo", boxX[6], boxY[6]);
		else
			game.fontP2white.draw(game.batch,"Voltar ao Jogo", boxX[6], boxY[6]);
		
		if (Gdx.input.isTouched() && pausaTimer == 0) {
			if (game.mouseColide(this.boxBtn[0])) {
				if (btnCooldown == 0) {
					if (game.sfxOn)
						game.sfxOn = false;
					else
						game.sfxOn = true;
					btnCooldown = 30;
				}
			}
			
			else if (game.mouseColide(this.boxBtn[1])) {
				if (btnCooldown == 0) {
					if (game.bgmOn) {
						game.bgmOn = false;
						bgm.pause();
					}
					else {
						game.bgmOn = true;
						bgm.play();
					}
					btnCooldown = 30;
				}
			}
			
			else if (game.mouseColide(this.boxBtn[2])) {
				bgm.stop();
				this.started = false;
				pausado = false;
				pausaTimer = 30;
				this.frase.linhaAtual = 0;
				this.frase.apagarLinhas();
				this.frase.criandoLinhas();
				game.mainMenu.passwordDigitado.append(this.password);
				game.setScreen(game.mainMenu);
			}
			
			else if (game.mouseColide(this.boxBtn[3])) {
				pausado = false;
				pausaTimer = 30;
			}
			
			else if (!game.mouseColide(this.boxBtn[4])) {
				pausado = false;
				pausaTimer = 30;
			}
		}
	}
	
	private void gameOver() {
		game.batch.setColor(1,0,0,0.8f);
		game.batch.draw(game.rect,0,0,GameManager.width,GameManager.height);
		game.batch.setColor(1,1,1,1);
		
		game.batch.draw(game.boxPausa, boxX[0], boxY[0], boxW[0], boxH[0]);
		game.fontP2white.draw(game.batch,"GAME OVER", boxX[1], boxY[1]);
		game.fontP2white.draw(game.batch,"Password: " + this.password, boxX[2], boxY[2]);
		
		if (game.mouseColide(this.boxBtn[1]))
			game.fontP2yellow.draw(game.batch,"Tentar Novamente", boxX[4], boxY[4]);
		else
			game.fontP2white.draw(game.batch,"Tentar Novamente", boxX[4], boxY[4]);
		
		if (game.mouseColide(this.boxBtn[2]))
			game.fontP2yellow.draw(game.batch,"Menu Principal", boxX[5], boxY[5]);
		else
			game.fontP2white.draw(game.batch,"Menu Principal", boxX[5], boxY[5]);
		
		if (Gdx.input.isTouched()) {		
			if (game.mouseColide(this.boxBtn[1])) {
				bgm.stop();
				this.started = false;
				this.frase.linhaAtual = 0;
				this.frase.apagarLinhas();
				this.frase.criandoLinhas();
				gameOverScreen = false;
			}
			
			else if (game.mouseColide(this.boxBtn[2])) {
				bgm.stop();
				this.started = false;
				this.frase.linhaAtual = 0;
				gameOverScreen = false;
				this.frase.apagarLinhas();
				this.frase.criandoLinhas();
				game.mainMenu.passwordDigitado.append(this.password);
				game.setScreen(game.mainMenu);
			}
		}
	}
	
	private void fimDaTela() {
		game.batch.setColor(0,0.3f,0,0.8f);
		game.batch.draw(game.rect,0,0,GameManager.width,GameManager.height);
		game.batch.setColor(1,1,1,1);
		
		game.batch.draw(game.boxPausa, boxX[0], boxY[0], boxW[0], boxH[0]);
		game.fontP2white.draw(game.batch,"Level concluído!", boxX[1], boxY[1]);
		if (GameManager.levelAtual < game.levels.size() - 1)
			game.fontP2white.draw(game.batch,
					"Password: " + game.levels.get(GameManager.levelAtual + 1).password, boxX[2], boxY[2]);
		game.fontP2white.draw(game.batch,"Tempo: " +  segundos + " seg.", boxX[3], boxY[3]);
		game.fontP2white.draw(game.batch,"Pontos: " +  game.player.pontos, boxX[4], boxY[4]);
		
		if (game.mouseColide(this.boxBtn[2]))
			game.fontP2yellow.draw(game.batch,"Menu Principal", boxX[5], boxY[5]);
		else
			game.fontP2white.draw(game.batch,"Menu Principal", boxX[5], boxY[5]);
		
		if (game.mouseColide(this.boxBtn[3]))
			game.fontP2yellow.draw(game.batch,"Continuar", boxX[6], boxY[6]);
		else
			game.fontP2white.draw(game.batch,"Continuar", boxX[6], boxY[6]);
		
		if (Gdx.input.isTouched()) {	
			if (game.mouseColide(this.boxBtn[2])) {
				bgm.stop();
				this.started = false;
				this.frase.linhaAtual = 0;
				this.frase.apagarLinhas();
				this.frase.criandoLinhas();
				if (GameManager.levelAtual < game.levels.size() - 1)
					game.mainMenu.passwordDigitado.append(game.levels.get(GameManager.levelAtual + 1).password);
				else
					game.mainMenu.passwordDigitado.append(this.password);
				game.setScreen(game.mainMenu);
			}
			
			else if (game.mouseColide(this.boxBtn[3])) {
				
				// Próximo level:
				if (GameManager.levelAtual < game.levels.size() - 1) {
					GameManager.levelAtual++;
					bgm.stop();
					this.started = false;
					this.frase.linhaAtual = 0;
					this.frase.criandoLinhas();
					game.setScreen(game.levels.get(GameManager.levelAtual));
				}
				
				// Fim do jogo:
				else { 
					bgm.stop();
					this.started = false;
					this.frase.linhaAtual = 0;
					this.frase.criandoLinhas();
					game.setScreen(game.mainMenu);
				}
			}
		}
	}
	
	// Esses métodos são obrigatórios, gerados pelo 'implements Screen'
	@Override public void pause() {}
	@Override public void dispose() {}
	@Override public void show() {}
	@Override public void resize(int width, int height) {}
	@Override public void resume() {}
	@Override public void hide() {}
}
