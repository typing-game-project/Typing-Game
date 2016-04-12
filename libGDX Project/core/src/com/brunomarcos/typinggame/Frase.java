package com.brunomarcos.typinggame;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;

public class Frase {	
	public String fraseCompleta;
	public StringBuffer frase;
	public Vector2 frasePos;
	public int limiteLinha;
	public int indiceLinha;
	public ArrayList<StringBuffer> linha;
	final int tamanhoChar = 70;
	
	public Frase(String frase) {
		this.fraseCompleta = frase;
		this.frase = new StringBuffer();
		this.frase.append(this.fraseCompleta);
		this.frasePos = new Vector2();
		this.linha = new ArrayList<StringBuffer>();
		limiteLinha = (GameManager.width - (3*63)) / 63;
		criandoLinhas();
	}
	
	public void criandoLinhas() {
		int indiceChar = 0;
		indiceLinha = 0;
		criarLinha(indiceChar);
	}
	
	public void criarLinha(int indice) {
		this.linha.add(new StringBuffer());
		boolean terminou = false;
		int i = indice;
		while (i < frase.length()) {
			if (i <= limiteLinha + indice)
				this.linha.get(indiceLinha).append(frase.charAt(i));
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
				if (this.linha.get(indiceLinha).charAt(linha.get(indiceLinha).length()-1) == ' ')
					this.linha.get(indiceLinha).deleteCharAt(linha.get(indiceLinha).length()-1);
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
	
	public void imprimeFrase(final GameManager game) {
		int indiceChar = 0;
		frasePos.x = 30;
		frasePos.y = GameManager.height - 165;
		
		for (int i = 0; i < linha.size(); i++) {
			for (int j = 0; j < linha.get(i).length();j++) {
				float x = frasePos.x + tamanhoChar * indiceChar;
				float y = frasePos.y;
				
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
	}
}
