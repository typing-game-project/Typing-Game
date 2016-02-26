package com.tu.streetescape;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class MainGame extends Game {
	//Declaração de telas do jogo
	public Inicio telainicio;
	public TelaJogo telajogo;
	
	//Declaração de elementos globais, utilizados em todas as classes do jogo
	public SpriteBatch batch;
	public OrthographicCamera camera;
	public ShapeRenderer renderer;
	public boolean debug;
	public boolean music;
	public boolean sound;
	public final int WIDTH = 800;
	public final int HEIGHT = 480;	
	public final int persowidth = 70;
	public final int persoheight = 80;
	
	@Override
	public void create() { //Método aonde tudo é instanciado
		//Elementos globais
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		renderer = new ShapeRenderer();
		
		//Telas
		telainicio = new Inicio(this);
		telajogo = new TelaJogo(this);
		
		//Mudança de tela (direciona o jogo para ir à tela seguinte)
		this.setScreen(telainicio);			
	}
	
	public void setDebug(boolean debug){
		debug = this.debug;
	}
	
	public void setSound(boolean sound){
		sound = this.sound;
	}
	
	public void setMusic(boolean music){
		music = this.music;
	}

	@Override
	public void dispose() { //Tudo o que pesa na memória precisa ser jogado fora aqui
		//Elementos globais
		batch.dispose();
		renderer.dispose();
		
		//Telas
		telainicio.dispose();
	}

	@Override
	public void render() {	//"loop" principal do jogo
		super.render(); //O jogo é renderizado na classe superior (Game)
	}

	@Override
	public void resize(int width, int height) { //Acho que não faremos uso desse método
	}

	@Override
	public void pause() { //Tudo o que é feito quando o botão HOME do Android é pressionado
	}

	@Override
	public void resume() { //Tudo o que é feito quando o jogo é retomado
	}
}
