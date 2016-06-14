package com.brunomarcos.typinggame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class MainMenu implements Screen {
	public final GameManager game;
	private float bgLetrasPosX;
	private float escalaLogo;
	private boolean escalaLogoIndoVindo;
	private Vector2 boxSize;
	private float originX;
	private float boxY;
	private float[] boxX;
	private float margemBox;
	private float textY;
	private float[] textX;
	private Rectangle[] button;
	private boolean[] buttonHover;
	private boolean animandoPassword;
	public StringBuffer passwordDigitado;
	private Music bgm;
	private boolean started;
	
	public MainMenu(final GameManager game) {
		this.game = game;
		bgLetrasPosX = 0;
		escalaLogo = 1f;
		escalaLogoIndoVindo = false;
		boxSize = new Vector2(game.porCentoW(584),game.porCentoW(280));
		originX = 0;
		boxY = game.porCentoH(100);
		margemBox = game.porCentoW(100);
		textY = (boxY + boxSize.y) - game.porCentoH(100);
		boxX = new float[5];
		textX = new float[6];
		button = new Rectangle[5];
		buttonHover = new boolean[5];
		passwordDigitado = new StringBuffer();
		bgm = Gdx.audio.newMusic(Gdx.files.internal("bgm/LOOP6.mp3"));
		bgm.setLooping(true);
		if (game.bgmOn)
			bgm.play();
		for (int i = 0; i < 5; i++) {
			if (i == 4)
				button[i] = new Rectangle(boxX[i],boxY,game.porCentoW(1144),boxSize.y);
			else
				button[i] = new Rectangle(boxX[i],boxY,boxSize.x,boxSize.y);
			buttonHover[i] = false;
		}
		recalcularBoxPos();
	}
	
	public void recalcularBoxPos() {
		boxX[0] = originX + margemBox;
		boxX[1] = originX + ((GameManager.width/2) - (boxSize.x/2));
		boxX[2] = originX + (GameManager.width - boxSize.x - margemBox);
		boxX[3] = originX + GameManager.width + margemBox;
		boxX[4] = originX + GameManager.width + ((GameManager.width/2) - (boxSize.x/2));
		
		for (int i = 0; i < 5; i++) {
			textX[i] = boxX[i] + game.porCentoW(80);
			button[i].x = boxX[i];
		}
		textX[5] = textX[4] + game.porCentoW(450);
	}
	
	public void renderBoxes() {
		for (int i = 0; i < 5; i++) {
			if (buttonHover[i])
				game.batch.setColor(1,1,1,0.5f);
			else
				game.batch.setColor(1,1,1,1);
			if (i == 4)
				game.batch.draw(game.boxGd,boxX[i],boxY,game.porCentoW(1144),boxSize.y);
			else
				game.batch.draw(game.boxPq,boxX[i],boxY,boxSize.x,boxSize.y);
			game.batch.setColor(1,1,1,1);
		}
		
		game.fontP2black.draw(game.batch, "Jogar", textX[0], textY);
		game.fontP2black.draw(game.batch, "Password", textX[1], textY);
		game.fontP2black.draw(game.batch, "Sair", textX[2], textY);
		game.fontP2black.draw(game.batch, "Voltar", textX[3], textY);
		game.fontP2black.draw(game.batch, "Password: ", textX[4], textY);
		game.fontP2black.draw(game.batch, passwordDigitado.toString(), textX[5], textY);
	}
	
	private void onStart() {
		if (!started) {
			game.resetMouse();
			if (game.bgmOn)
				bgm.play();
			started = true;
			originX = 0;
			animandoPassword = false;
			recalcularBoxPos();
		}
	}
	
	@Override public void render(float delta) {
		game.batch.begin();
		
		if (GameManager.creditos) {
			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			
			Gdx.input.setCursorCatched(true);
			game.fontP2white.draw(game.batch, "Um jogo de Bruno Araujo e Marcos Antonio", game.porCentoW(150), GameManager.height / 2);
			
			GameManager.creditosTimer--;
			if (GameManager.creditosTimer == 0)
				GameManager.creditos = false;
		}
		
		else {
			Gdx.gl.glClearColor(0.9f, 0.9f, 0.9f, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			
			onStart();
			
			// Animação letras do fundo
			bgLetrasPosX += 0.5;
			
			// Animação de escala do logo
			if (escalaLogo < 1.04f && !escalaLogoIndoVindo)
				escalaLogo += 0.003f;
			
			else if (escalaLogo >= 1.04f)
				escalaLogoIndoVindo = true;
			
			if (escalaLogo > 1 && escalaLogoIndoVindo)
				escalaLogo -= 0.006f;
			
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
			
			if (animandoPassword && originX > -GameManager.width) {
				originX -= 40;
				recalcularBoxPos();
			}
			
			else if (!animandoPassword && originX < 0) {
				originX += 40;
				recalcularBoxPos();
			}
			
			renderBoxes();
			game.trocaCursor();
			
			for (int i = 0; i < 5; i++) {
				if (game.mouseColide(button[i])) {
					buttonHover[i] = true;
					if(Gdx.input.isTouched()) {
						switch(i) {
						
							// Jogar 
							case 0:
								bgm.stop();
								this.started = false;
								if (passwordDigitado.length() > 0)
									passwordDigitado.delete(0, passwordDigitado.length());
								GameManager.levelAtual = 0;
								game.setScreen(game.levels.get(0));
					            break;
					        
					        // Ir para password   
							case 1:
					            animandoPassword = true;
					            break;
					        
					        // Sair 
							case 2:
								if (originX <= -GameManager.width || originX >= 0) {
									bgm.stop();
						            Gdx.app.exit();
								}
					            break;
					            
					        // Voltar
							case 3:
					            animandoPassword = false;
					            break;
					        
					        // Digitar password
							case 4:
					            game.hideCursor = true;
					            break;
					        
					        default:
					        	continue;
						}
					}
				}
				else
					buttonHover[i] = false;
			}
			
			if (game.hideCursor) {
				buttonHover[4] = true;
				
				if (passwordDigitado.length() < 10) {
					for (int i = 7; i <= 16; i++) {
						checarTecla(i,41);
					}
					
					for (int i = 29; i <= 54; i++) {
						checarTecla(i,36);
					}
				}
				
				if (Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE))
					if (passwordDigitado.length() > 0)
						passwordDigitado.deleteCharAt(passwordDigitado.length() - 1);
				
				if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
					game.hideCursor = false;
					if (passwordDigitado.length() > 0)
						passwordDigitado.delete(0, passwordDigitado.length());
				}
				
				if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER))
					for (int i = 0; i < game.levels.size(); i++) {
						if (passwordDigitado.toString().equals(game.levels.get(i).password)) {
							if (game.sfxOn)
								game.acertoSnd[game.random.nextInt(3)].play();
							bgm.stop();
							this.started = false;
							game.hideCursor = false;
							if (passwordDigitado.length() > 0)
								passwordDigitado.delete(0, passwordDigitado.length());
							GameManager.levelAtual = i;
							game.setScreen(game.levels.get(i));
						}
						else {
							if (game.sfxOn)
								game.erroSnd[game.random.nextInt(2)].play();
							game.batch.setColor(1,0,0,1);
							game.batch.draw(game.rect, 0,0,GameManager.width,GameManager.height);
						}
					}
			}
		}
			
		game.batch.end();
	}
	
	public void checarTecla(int libgdxChar, int asciiCharDiferenca) {
		if (Gdx.input.isKeyJustPressed(libgdxChar)) {
			int i = libgdxChar + asciiCharDiferenca;
			passwordDigitado.append((char)i);
		}
	}
	
	@Override
	public void dispose() {
		bgm.dispose();
	}
	
	@Override public void show() {}
	@Override public void resize(int width, int height) {}
	@Override public void pause() {}
	@Override public void resume() {}
	@Override public void hide() {}
}
