package com.tu.streetescape;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Arte {
	final MainGame jogo;
	
	//Declaração das imagens e artes utilizadas no jogo
	public Texture forninho;
	public Texture cenario1, esq, cima, baixo, dir;
	public ArrayList<Texture> salas;
	
	public Arte(final MainGame jogo){
		this.jogo = jogo;
		
		salas = new ArrayList<Texture>();
		
		forninho = new Texture(Gdx.files.internal("forninho.jpg"));
		
		salas.add(cenario1 = new Texture(Gdx.files.internal("paulista1.jpg")));
		salas.add(cima = new Texture(Gdx.files.internal("saidacima.jpg")));
		salas.add(esq = new Texture(Gdx.files.internal("saidaesq.jpg")));
		salas.add(baixo = new Texture(Gdx.files.internal("saidabaixo.jpg")));
		salas.add(dir = new Texture(Gdx.files.internal("saidadir.jpg")));
	}
	
	public void dispose(){
		cenario1.dispose();
		esq.dispose();
		dir.dispose();
		cima.dispose();
		baixo.dispose();
	}
}
