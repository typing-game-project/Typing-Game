package com.tu.streetescape;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class Inicio implements Screen{
	private final MainGame jogo;
	
	//Classes utilizadas
	private Arte artes;
	
	//Elementos da tela
	
	public Inicio(final MainGame jogo){ //Construtor. Serve para instanciar os elementos das telas
		this.jogo = jogo;
		
		//Posicionamento da c�mera
		jogo.camera.setToOrtho(false, jogo.WIDTH, jogo.HEIGHT);
		
		//Declara��o de telas
		artes = new Arte(jogo);
		
		//Declara��o de elementos
		
	}

	@Override
	public void render(float delta) { //"loop" principal da tela
		//Desenho do forninho
		jogo.batch.begin();
		jogo.batch.draw(artes.forninho, 0, 0, jogo.WIDTH, jogo.HEIGHT);
		jogo.batch.end();
		
		//Atualiza��o da c�mera e renderer (para debug)
		jogo.camera.update();
		jogo.renderer.begin(ShapeType.Filled);
		jogo.renderer.setColor(Color.RED);
		jogo.renderer.end();
		
		//Tempor�rio (serve enquanto montamos os menus)
		jogo.setScreen(jogo.telajogo);
	}

	@Override
	public void resize(int width, int height) { //Acho que n�o faremos uso desse m�todo
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() { //Tudo que � feito quando a tela � aberta (antes do loop principal)
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() { //Tudo o que � feito quando a tela � fechada (depois de fechar o loop principal)
		
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() { //Tudo o que � feito quando o bot�o HOME do Android � pressionado
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() { //Tudo o que � feito quando o jogo � retomado
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() { //Tudo o que pesa na mem�ria precisa ser jogado fora aqui
		
	}

}
