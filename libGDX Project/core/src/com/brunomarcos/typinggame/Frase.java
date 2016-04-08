package com.brunomarcos.typinggame;

import com.badlogic.gdx.math.Vector2;

public class Frase {
	
	// TODO Guardar linhas em um vetor[]
	// TODO Separar a frase com as palavras dividas corretamente
	
	public String fraseCompleta;
	public StringBuffer frase;
	public Vector2 frasePos;
	final int tamanhoChar = 70;
	final int limiteLinha = 16;
	
	public Frase(String frase) {
		this.fraseCompleta = frase;
		this.frase = new StringBuffer();
		this.frase.append(this.fraseCompleta);
		this.frasePos = new Vector2();
	}
	
	public void imprimeFrase(final GameManager game) {
		int indiceChar = 0;
		frasePos.x = 30;
		frasePos.y = game.height - 165;
		
		for (int i = 0; i < frase.length(); i++) {
			float x = frasePos.x + tamanhoChar * indiceChar;
			float y = frasePos.y;
			
			game.batch.setColor(0,0,0,0.5f);
			game.batch.draw(game.rect, x - 7, y + 7, tamanhoChar - 10, -50);
			
			game.batch.setColor(1,1,1,1);
			game.batch.draw(game.rect, x - 10, y + 10, tamanhoChar - 10, -50);
			
			game.fontP2black.draw(game.batch, Character.toString(frase.charAt(i)), x, y);
			
			if (indiceChar < limiteLinha) {
				indiceChar++;
				System.out.println(indiceChar);
			}
			else {
				frasePos.y -= 60;
				indiceChar = 0;
			}
		}
	}
}
