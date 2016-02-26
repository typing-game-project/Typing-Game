package com.tu.streetescape;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class TelaJogo extends Calculos implements Screen{
	private final MainGame jogo;
	
	private Settings settings;
	private Arte artes;
	
	private boolean passou = false;
	private int idsala = 0;
	private Rectangle sup1, sup2, down1, down2, left1, left2, right1, right2;
	private Rectangle exitN, exitS, exitO, exitL;
	private Array<Rectangle> bordas;
	
	private int numEnemy, i = 1, j = 0;
	private Array<Rectangle> inimigo;
	private Array<Enemy> enemy;
	private Array<Rectangle> atk;
	private Rectangle temprect;
	private Enemy tempenemy;
	
	private Rectangle transeunte;
		
	public TelaJogo(final MainGame jogo){
		super(jogo);
		this.jogo = jogo;
		
		//Posicionamento da câmera
		jogo.camera.setToOrtho(false, jogo.WIDTH, jogo.HEIGHT);
				
		//Declaração de telas
		
		//Declaração de elementos
		artes = new Arte(jogo);
		settings = new Settings(jogo);
		
		enemy = new Array<Enemy>();
		inimigo = new Array<Rectangle>();
		atk = new Array<Rectangle>();
		temprect = new Rectangle();
		tempenemy = new Enemy(jogo);
		
		bordas = new Array<Rectangle>();
		
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
		
		transeunte = new Rectangle(380, 200, jogo.persowidth, jogo.persoheight);
		
		/*Essa variável terá que se alterar a cada mudança de sala. Terá que se integrar à
		 * lógica de mudança de salas. Está aqui só por provisório mesmo.*/
		numEnemy = MathUtils.random(1, 4);
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
		for(int i = 0; i < bordas.size; i++){
			if(transeunte.overlaps(bordas.get(i))){
				passou = true;
			}
		}
		if(passou == false){
			if(Gdx.input.isKeyPressed(Keys.W)){
				transeunte.y += 150 * Gdx.graphics.getDeltaTime();
			}
			if(Gdx.input.isKeyPressed(Keys.S)){
				transeunte.y -= 150 * Gdx.graphics.getDeltaTime();
			}
			if(Gdx.input.isKeyPressed(Keys.A)){
				transeunte.x -= 150 * Gdx.graphics.getDeltaTime();
			}
			if(Gdx.input.isKeyPressed(Keys.D)){
				transeunte.x += 150 * Gdx.graphics.getDeltaTime();
			}
		}else{
			if(Gdx.input.isKeyPressed(Keys.W)){
				transeunte.y -= 150 * Gdx.graphics.getDeltaTime();
			}
			if(Gdx.input.isKeyPressed(Keys.S)){
				transeunte.y += 150 * Gdx.graphics.getDeltaTime();
			}
			if(Gdx.input.isKeyPressed(Keys.A)){
				transeunte.x += 150 * Gdx.graphics.getDeltaTime();
			}
			if(Gdx.input.isKeyPressed(Keys.D)){
				transeunte.x -= 150 * Gdx.graphics.getDeltaTime();
			}
		}
		
		if(transeunte.overlaps(exitS)){
			transeunte.y = 400;
			if(idsala == 0){
				idsala = 1;
			}
			if(idsala == 3){
				idsala = 0;
			}
		}
		if(transeunte.overlaps(exitL)){
			transeunte.x = 10;
			if(idsala == 0){
				idsala = 2;
			}
			if(idsala == 4){
				idsala = 0;
			}
		}
		if(transeunte.overlaps(exitN)){
			transeunte.y = 10;
			if(idsala == 0){
				idsala = 3;
			}
			if(idsala == 1){
				idsala = 0;
			}
		}
		if(transeunte.overlaps(exitO)){
			transeunte.x = 700;
			if(idsala == 0){
				idsala = 4;
			}
			if(idsala == 2){
				idsala = 0;
			}
		}
		passou = false;
		
		
		//Atividade do inimigo
		GeraEnemy();
		if(settings.debug = true){
			for(Rectangle mau : inimigo){
				jogo.renderer.begin(ShapeType.FilledRectangle);
				jogo.renderer.setColor(Color.BLUE);
				jogo.renderer.filledRect(mau.getX(), mau.getY(), jogo.persowidth, jogo.persoheight);
				jogo.renderer.end();
			}
		}
		
		while(j < numEnemy){
			tempenemy = enemy.get(j);
			temprect = inimigo.get(j);
			
			tempenemy.movEnemy(temprect);
			j++;
		}
		if(j >= numEnemy){
			j = 0;
		}
	}
	
	private void GeraEnemy(){
		while(i <= numEnemy){
			Rectangle mau = new Rectangle(MathUtils.random(jogo.WIDTH - 750, jogo.WIDTH - 50),
					MathUtils.random(jogo.HEIGHT - 430, jogo.HEIGHT - 50), jogo.persowidth, jogo.persoheight);
			
			boolean proximidadetrans = checaProxRect(mau, transeunte);
			while(proximidadetrans == true){
				mau.x = MathUtils.random(jogo.WIDTH - 750, jogo.WIDTH - 50);
				mau.y = MathUtils.random(jogo.HEIGHT - 430, jogo.HEIGHT - 50);
				proximidadetrans = checaProxRect(mau, transeunte);
			}
			if(i >= 2){
				boolean proximidadeOtherEnemy = checaProxRect(mau, inimigo.get(i - 2));
				while(proximidadeOtherEnemy == true){
					mau.x = MathUtils.random(jogo.WIDTH - 750, jogo.WIDTH - 50);
					mau.y = MathUtils.random(jogo.HEIGHT - 430, jogo.HEIGHT - 50);
					proximidadeOtherEnemy = checaProxRect(mau, inimigo.get(i - 2));
				}
			}
			
			Enemy mal = new Enemy(jogo);
			inimigo.add(mau);
			enemy.add(mal);
			i++;
		}
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
