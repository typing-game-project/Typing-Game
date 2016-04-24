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
	public boolean errou;
	public int limiteLinha;
	public int indiceLinha;
	public ArrayList<StringBuffer> linha;
	private final int tamanhoChar = 70;
	public boolean acertou = false;
	private Animado tremendo;
	
	public Frase(String frase) {
		this.fraseCompleta = frase;
		this.frase = new StringBuffer();
		this.frase.append(this.fraseCompleta);
		this.frasePos = new Vector2();
		this.errou = false;
		tremendo = new Animado(offsetX, false, 4);
		this.linha = new ArrayList<StringBuffer>();
		limiteLinha = (GameManager.width - (3*63)) / 63;
		criandoLinhas();
	}
	
	public void criandoLinhas() {
		int indiceChar = 0;
		indiceLinha = 0;
		criarLinha(indiceChar);
		
		offsetY = new float[linha.size()][];
		for (int i = 0; i < offsetY.length; i++)
			offsetY[i] = new float[linha.get(i).length()];
	}
	
	public void criarLinha(int indice) {
		this.linha.add(new StringBuffer());
		
		boolean terminou = false;
		int i = indice;
		while (i < frase.length()) {
			if (i <= limiteLinha + indice) {
				this.linha.get(indiceLinha).append(frase.charAt(i)); // Problema de serialização
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
				}
			}
			if (terminou) {
				if (this.linha.get(indiceLinha).charAt(0) == ' ')
					this.linha.get(indiceLinha).deleteCharAt(0);
				//if (this.linha.get(indiceLinha).charAt(linha.get(indiceLinha).length()-1) == ' ')
					//this.linha.get(indiceLinha).deleteCharAt(linha.get(indiceLinha).length()-1);
				//TODO: Colocar excessão caso tenha uma palavra maior que os caracters de uma linha
				break;
			}
			i++;
		}
		if (this.linha.get(indiceLinha).charAt(0) == ' ')
			this.linha.get(indiceLinha).deleteCharAt(0);
		indiceLinha += 1;
		if (i < frase.length()) {
			criarLinha(indice);
		}
	}
	
	public void treme(float velocidade) {
		if (errou) {
			tremendo.reset();
			tremendo.play();
			errou = false;
		}

		offsetX = tremendo.intervalo(0, 60, velocidade, 0);
		offsetX = tremendo.intervalo(60, -30, -velocidade/2, 1);
		offsetX = tremendo.intervalo(-30, 15, velocidade/3, 2);
		offsetX = tremendo.intervalo(15, 0, -velocidade/4, 3);
	}
	
	public void animaLinha() {
		// TODO
	}
	
	public void imprimeFrase(final GameManager game) {
		int indiceChar = 0;
		
		frasePos.x = game.porCentoW(60);
		frasePos.y = GameManager.height - game.porCentoH(260);
		
		for (int i = 0; i < linha.size(); i++) {
			for (int j = 0; j < linha.get(i).length();j++) {		
				float x = frasePos.x + tamanhoChar * indiceChar + offsetX;
				float y = frasePos.y + offsetY[i][j];
				
				/*
				// Fazer as letras tremerem :
				try {	
					letrasY[i][j] = random.nextInt(4);
					y = frasePos.y + letrasY[i][j] + treme.y;
					// TODO: algumas letras não tremem depois de completar a primeira linha
				} catch (Exception e) {
					y = frasePos.y + treme.y;
				}
				*/
				
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
