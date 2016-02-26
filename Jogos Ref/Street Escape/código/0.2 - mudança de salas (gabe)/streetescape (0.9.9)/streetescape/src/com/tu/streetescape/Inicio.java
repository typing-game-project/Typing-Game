package com.tu.streetescape;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class Inicio implements Screen{
	private final MainGame jogo;
	
	//Classes utilizadas
	private Arte artes;
	
	//Elementos da tela
	
	public Inicio(final MainGame jogo){ //Construtor. Serve como instanciação dos elementos das telas
		this.jogo = jogo;
		
		//Posicionamento da câmera
		jogo.camera.setToOrtho(false, jogo.WIDTH, jogo.HEIGHT);
		
		//Declaração de telas
		artes = new Arte(jogo);
		
		//Declaração de elementos
		
	}

	@Override
	public void render(float delta) { //"loop" principal da tela
		//Desenho do forninho
		jogo.batch.begin();
		jogo.batch.draw(artes.forninho, 0, 0, jogo.WIDTH, jogo.HEIGHT);
		jogo.batch.end();
		
		//Atualização da câmera e renderer (para debug)
		jogo.camera.update();
		jogo.renderer.begin(ShapeType.Rectangle);
		jogo.renderer.setColor(Color.RED);
		jogo.renderer.end();
		
		//Temporário (serve enquanto montamos os menus)
		jogo.setScreen(jogo.telajogo);
	}

	@Override
	public void resize(int width, int height) { //Acho que não faremos uso desse método
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() { //Tudo que é feito quando a tela é aberta (antes do loop principal)
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() { //Tudo o que é feito quando a tela é fechada (depois de fechar o loop principal)
		
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() { //Tudo o que é feito quando o botão HOME do Android é pressionado
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() { //Tudo o que é feito quando o jogo é retomado
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() { //Tudo o que pesa na memória precisa ser jogado fora aqui
		
	}

}
