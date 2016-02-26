package com.tu.streetescape;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

public class TelaJogo implements Screen{
	private final MainGame jogo;
	
	private Settings settings;
	private Arte artes;
	private MovPerso movimento;
	
	private boolean passou = false;
	
	private int idsala = 0;
	
	private Rectangle transeunte;
	private Rectangle sup1, sup2, down1, down2, left1, left2, right1, right2;
	private Rectangle exitN, exitS, exitO, exitL;
	private ArrayList<Rectangle> bordas;
		
	public TelaJogo(final MainGame jogo){
		this.jogo = jogo;
		
		//Posicionamento da câmera
		jogo.camera.setToOrtho(false, jogo.WIDTH, jogo.HEIGHT);
				
		//Declaração de telas
		artes = new Arte(jogo);
				
		//Declaração de elementos
		
		bordas = new ArrayList<Rectangle>();
		
		sup1 = new Rectangle(-1, 479, 267, 1);
		sup2 = new Rectangle(536, 479, 267, 1);
		down1 = new Rectangle(0, 1, 267, 1);
		down2 = new Rectangle(536, 1, 267, 1);
		left1 = new Rectangle(1, 370, 1, 110);
		left2 = new Rectangle(1, 0, 1, 110);
		right1 = new Rectangle(799, 0, 1, 110);
		right2 = new Rectangle(799, 370, 1, 110);

		exitN = new Rectangle(270, jogo.HEIGHT + jogo.persoheight, jogo.WIDTH, 1);
		exitS = new Rectangle(270, 0 - jogo.persoheight, jogo.WIDTH, 1);
		exitL = new Rectangle(jogo.WIDTH + jogo.persowidth, 189, 1, jogo.HEIGHT);
		exitO = new Rectangle(0 - jogo.persowidth, 189, 1, jogo.HEIGHT);
		
		bordas.add(sup1);
		bordas.add(sup2);
		bordas.add(down1);
		bordas.add(down2);
		bordas.add(left1);
		bordas.add(left2);
		bordas.add(right1);
		bordas.add(right2);
		
		settings = new Settings(jogo);
		movimento = new MovPerso();
		movimento.transeuntex = 380;
		movimento.transeuntey = 200;
		transeunte = new Rectangle(movimento.transeuntex, movimento.transeuntey, jogo.persowidth, jogo.persoheight);
	}

	@Override
	public void render(float delta) {
		
		//Desenhos
		jogo.batch.begin();
		jogo.batch.draw(artes.salas.get(idsala), 0, 0, jogo.WIDTH, jogo.HEIGHT);
		jogo.batch.end();
		
		//Atualização da câmera e renderer (para debug)
		jogo.camera.update();
		jogo.renderer.setColor(Color.RED);
		
		//Atividade do personagem
		if(settings.debug = true){
			jogo.renderer.begin(ShapeType.FilledRectangle);
			jogo.renderer.filledRect(transeunte.getX(), transeunte.getY(), jogo.persowidth, jogo.persoheight);
			jogo.renderer.end();
		}
		for(int i = 0; i < bordas.size(); i++){
			if(transeunte.overlaps(bordas.get(i))){
				passou = true;
			}
		}
		
		if(passou == false){	
			if(Gdx.input.isKeyPressed(Keys.W)){
				transeunte.setY(movimento.goup());
			}
			
			if(Gdx.input.isKeyPressed(Keys.S)){
				transeunte.setY(movimento.godown());
			}
			
			if(Gdx.input.isKeyPressed(Keys.A)){
				transeunte.setX(movimento.goleft());
			}
			
			if(Gdx.input.isKeyPressed(Keys.D)){
				transeunte.setX(movimento.goright());
			}
		}else{
			if(Gdx.input.isKeyPressed(Keys.W)){
				transeunte.setY(movimento.godown());
			}
			
			if(Gdx.input.isKeyPressed(Keys.S)){
				transeunte.setY(movimento.goup());
			}
			
			if(Gdx.input.isKeyPressed(Keys.A)){
				transeunte.setX(movimento.goright());
			}
			
			if(Gdx.input.isKeyPressed(Keys.D)){
				transeunte.setX(movimento.goleft());
			}
		}
		
		if(transeunte.overlaps(exitS)){
			movimento.transeuntey = 400;
			if(idsala == 0){
				idsala = 1;
			}
			if(idsala == 3){
				idsala = 0;
			}
		}
		if(transeunte.overlaps(exitL)){
			movimento.transeuntex = 10;
			if(idsala == 0){
				idsala = 2;
			}
			if(idsala == 4){
				idsala = 0;
			}
		}
		if(transeunte.overlaps(exitN)){
			movimento.transeuntey = 10;
			if(idsala == 0){
				idsala = 3;
			}
			if(idsala == 1){
				idsala = 0;
			}
		}
		if(transeunte.overlaps(exitO)){
			movimento.transeuntex = 700;
			if(idsala == 0){
				idsala = 4;
			}
			if(idsala == 2){
				idsala = 0;
			}
		}
		passou = false;
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
