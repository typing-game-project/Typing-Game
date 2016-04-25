package com.brunomarcos.typinggame;

import java.util.ArrayList;
//import java.util.Random;

import com.badlogic.gdx.math.Vector2;

public class Frase {	
	public String fraseCompleta;
	public StringBuffer frase;
	public Vector2 frasePos;
	private float offsetX;
	private float[][] offsetY;
	private Animado animadoX;
	private ArrayList<ArrayList<Animado>> animadoY;
	public ArrayList<StringBuffer> linha;
	public boolean errou;
	public int limiteLinha;
	public int indiceLinha;
	private final int tamanhoChar = 70;
	public boolean acertou = false;
	private StringBuffer linhaSemEspaco;
	public int linhaAtual;
	
	public Frase(String frase) {
		this.fraseCompleta = frase;
		this.frase = new StringBuffer();
		this.linhaSemEspaco = new StringBuffer();
		this.frase.append(this.fraseCompleta);
		this.frasePos = new Vector2();
		this.errou = false;
		animadoX = new Animado(offsetX, false, 4);
		this.linha = new ArrayList<StringBuffer>();
		this.animadoY = new ArrayList<ArrayList<Animado>>();
		limiteLinha = (GameManager.width - (3*63)) / 63;
		linhaAtual = 0;
		criandoLinhas();
	}
	
	public void criandoLinhas() {
		int indiceChar = 0;
		indiceLinha = 0;
		criarLinha(indiceChar);
		
		offsetY = new float[linha.size()][];
		for (int i = 0; i < linha.size(); i++) {
			offsetY[i] = new float[linha.get(i).length()];
			for (int j = 0; j < linha.get(i).length(); j++) {
				offsetY[i][j] = 0.0f;
				this.animadoY.get(i).add(j,new Animado(offsetY[i][j],true,4));
				this.animadoY.get(i).get(j).play();
			}
		}
	}
	
	public void criarLinha(int indice) {
		this.linha.add(new StringBuffer());
		this.animadoY.add(new ArrayList<Animado>());
		
		boolean terminou = false;
		int i = indice;
		while (i < frase.length()) {
			if (i <= limiteLinha + indice) {
				this.linha.get(indiceLinha).append(frase.charAt(i));
				this.linhaSemEspaco.append(frase.charAt(i));
			}
			else {
				for (int j = this.linha.get(indiceLinha).length() - 1; j > 0; j--) {
					if (this.linha.get(indiceLinha).charAt(j) == ' ') {
						indice += j;
						terminou = true;
						break;
					}
					else
						this.linha.get(indiceLinha).deleteCharAt(j);
					//TODO: Criar linha para caso a palavra seja tão grande que não dê para cortá-la
					/*
					if(linha.get(indiceLinha).length() == 1) {
						this.linha.get(indiceLinha).deleteCharAt(0);
						this.linha.get(indiceLinha).append(linhaSemEspaco.toString());
						
					}
					*/
				}
			}
			if (terminou) {
				if (this.linha.get(indiceLinha).charAt(0) == ' ')
					this.linha.get(indiceLinha).deleteCharAt(0);
				break;
			}
			i++;
		}
		if (this.linha.get(indiceLinha).charAt(0) == ' ')
			this.linha.get(indiceLinha).deleteCharAt(0);
		indiceLinha += 1;
		if (i < frase.length()) {
			//linhaSemEspaco.delete(0, linhaSemEspaco.length());
			criarLinha(indice);
		}
	}
	
	public void treme(float velocidade) {
		if (errou) {
			animadoX.reset();
			animadoX.play();
			errou = false;
		}

		offsetX = animadoX.intervalo(0, 60, velocidade, 0);
		offsetX = animadoX.intervalo(60, -30, -velocidade/2, 1);
		offsetX = animadoX.intervalo(-30, 15, velocidade/3, 2);
		offsetX = animadoX.intervalo(15, 0, -velocidade/4, 3);
	}
	
	public void animaLinha() {
		float y = 4f;
		float vel = 1f;
		//TODO: Animado não funciona do jeito certo
	    for (int i = 0; i < offsetY.length; i++) {
	        for (int j = 0; j < offsetY[i].length; j++) {
	            if (j%2==0) {
	            	offsetY[i][j] = animadoY.get(i).get(j).intervalo(0,y,vel,0);
	            	offsetY[i][j] = animadoY.get(i).get(j).intervalo(y,0,-vel,1);
	            	offsetY[i][j] = animadoY.get(i).get(j).intervalo(0,-y,-vel,2);
	            	offsetY[i][j] = animadoY.get(i).get(j).intervalo(-y,0,vel,3);
	            }
	            else if (j%2==1) {
	            	offsetY[i][j] = animadoY.get(i).get(j).intervalo(0,-y,-vel,0);
	            	offsetY[i][j] = animadoY.get(i).get(j).intervalo(-y,0,vel,1);
	            	offsetY[i][j] = animadoY.get(i).get(j).intervalo(0,y,vel,2);
	            	offsetY[i][j] = animadoY.get(i).get(j).intervalo(y,0,-vel,3);
	            }
	        }
	    }
	}
	
	public void imprimeFrase(final GameManager game) {
		int indiceChar = 0;
		
		frasePos.x = game.porCentoW(60);
		frasePos.y = GameManager.height - game.porCentoH(260);
		
		animaLinha();
		
		for (int i = 0; i < linha.size(); i++) {
			for (int j = 0; j < linha.get(i).length();j++) {		
				float x = frasePos.x + tamanhoChar * indiceChar + offsetX;
				float y = frasePos.y + offsetY[i + linhaAtual][j];
				
				game.batch.setColor(0,0,0,0.5f);
				game.batch.draw(game.rect, x - 7, y + 7, tamanhoChar - 10, -50);
				
				game.batch.setColor(1,1,1,1);
				game.batch.draw(game.rect, x - 10, y + 10, tamanhoChar - 10, -50);
				
				game.fontP2black.draw(game.batch, Character.toString(linha.get(i).charAt(j)), x, y);
				
				indiceChar++;
			}
			frasePos.y -= 60;
			indiceChar = 0;
		}
		if (acertou) {
			game.animAcerto.play(game.batch,
					game.porCentoW(60) - (game.animAcerto.width/2) + 5,
					(GameManager.height - game.porCentoH(260)) - (game.animAcerto.height/2),
					false, acertou);
		}
		treme(30);
	}
}
