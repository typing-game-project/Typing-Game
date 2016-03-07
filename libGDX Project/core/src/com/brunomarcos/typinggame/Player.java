package com.brunomarcos.typinggame;

public class Player {
	public char letraDigitada;
	public int pontos;
	public int vida;
	public int acertosConsecutivos;
	public int indiceAtual;
	public int multiplicador;
	public boolean digitou;
	
	public Player() {
		pontos = 0;
		vida = 50;
		acertosConsecutivos = 0;
		indiceAtual = 0;
		multiplicador = 1;
		digitou = false;
	}
	
	public void confereAcerto(StringBuffer frase) {
		
		//TODO Pegar char digitado pelo usuário
		
		// Se acertou:
		if (letraDigitada == frase.charAt(indiceAtual) && digitou) {
			multiplicaPontos();
			pontos += multiplicador * 1;
			acertosConsecutivos += 1;
			frase.deleteCharAt(indiceAtual);
			indiceAtual += 1;
		}
		
		// Se errou:
		else if (letraDigitada != frase.charAt(indiceAtual) && digitou) {
			vida -= 1;
			acertosConsecutivos = 0;
			multiplicador = 1;
		}
		
		digitou = false;
	}
	
	public void multiplicaPontos() {
		
		int[] acertos = {5, 10, 20};
		int[] multiplicar = {2, 4, 6};
		
		if (acertosConsecutivos >= acertos[0])
			multiplicador = multiplicar[0];
		
		else if (acertosConsecutivos >= acertos[1])
			multiplicador = multiplicar[1];
		
		else if (acertosConsecutivos >= acertos[2])
			multiplicador = multiplicar[2];
	}
}
