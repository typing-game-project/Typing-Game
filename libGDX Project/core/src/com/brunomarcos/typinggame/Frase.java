package com.brunomarcos.typinggame;

import com.badlogic.gdx.math.Vector2;

public class Frase {
	
	// TODO Guardar linhas em um vetor[]
	// TODO Separar a frase com as palavras dividas corretamente
	
	public String fraseCompleta;
	public StringBuffer frase;
	public Vector2 frasePos;
	final int tamanhoChar = 10;
	final int limiteLinha = 60;
	
	public Frase(String frase) {
		this.fraseCompleta = frase;
		this.frase = new StringBuffer();
		this.frase.append(this.fraseCompleta);
		this.frasePos = new Vector2();
	}
	
	public void imprimeFrase(final GameManager game) {
		int indiceChar = 0;
		frasePos.x = 100;
		frasePos.y = 484;
		
		for (int i = 0; i < frase.length(); i++) {
			game.font.draw(game.batch, Character.toString(frase.charAt(i)),
					frasePos.x + tamanhoChar * indiceChar, frasePos.y);
			if (indiceChar < limiteLinha) {
				indiceChar++;
				System.out.println(indiceChar);
			}
			else {
				frasePos.y -= 30;
				indiceChar = 0;
			}
		}
	}
}
