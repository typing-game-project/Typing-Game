package com.brunomarcos.typinggame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

public class Player implements InputProcessor {
	public char letraDigitada;
	public int pontos;
	public int vida;
	public int acertosConsecutivos;
	public int multiplicador;
	public boolean digitou;
	final char[] aceitaveis = { 'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', 'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'ç', 'z', 'x', 'c', 'v', 'b', 'n', 'm',
			'á', 'é', 'í', 'ó', 'ú', 'à', 'è', 'ì', 'ò', 'ù', 'â', 'ê', 'î', 'ô', 'û', 'ä', 'ë', 'ï', 'ö', 'ü', 'ã', 'õ', 'ñ', 'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O',
			'P', 'A', 'S', 'D', 'F', 'G', 'H', 'J', 'K', 'L', 'Ç', 'Z', 'X', 'C', 'V', 'B', 'N', 'M', 'Á', 'É', 'Í', 'Ó', 'Ú', 'À', 'È', 'Ì', 'Ò', 'Ù', 'Â', 'Ê', 'Î', 'Ô',
			'Û', 'Ä', 'Ë', 'Ï', 'Ö', 'Ü', 'Ã', 'Õ', 'Ñ', '"', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '!', '@', '#', '$', '%', '¨', '&', '*', '(', ')', '_', '+',
			'-', '=', '/', '£', '³', '²', '¹', '¢', '¬', '§', '´', '`', '[', ']', '{', 'ª', '}', 'º', '~', '^', '<', '.', '>', ';', ':', '/', '?', '°', '|', ' '};
	
	public Player() {
		pontos = 0;
		vida = 50;
		acertosConsecutivos = 0;
		multiplicador = 1;
		digitou = false;
		Gdx.input.setInputProcessor(this);
	}
	
	public void confereAcerto(StringBuffer frase) {
		//TODO Conferir se o Caps Lock está ligado
	
		// Se acertou:
		if (letraDigitada == frase.charAt(0) && digitou) {
			multiplicaPontos();
			pontos += multiplicador * 1;
			acertosConsecutivos += 1;
			frase.deleteCharAt(0);
		}
		
		// Se errou:
		else if (letraDigitada != frase.charAt(0) && digitou) {
			vida -= 1;
			acertosConsecutivos = 0;
			multiplicador = 1;
		}
	
		digitou = false;
	}
	
	public void multiplicaPontos() {
		
		int[] acertos = {5, 10, 20};
		int[] multiplicar = {2, 4, 6};
		
		if (acertosConsecutivos >= acertos[0] && acertosConsecutivos < acertos[1])
			multiplicador = multiplicar[0];
		
		else if (acertosConsecutivos >= acertos[1] && acertosConsecutivos < acertos[2])
			multiplicador = multiplicar[1];
		
		else if (acertosConsecutivos >= acertos[2])
			multiplicador = multiplicar[2];
	}

	@Override
	public boolean keyTyped(char character) {
		
		for (int i = 0; i < aceitaveis.length; i++) {
			if (character == aceitaveis[i]) {
				letraDigitada = character;
				digitou = true;
				break;
			}
		}
		
		return false;
	}
	
	// Métodos obrigatórios por causa do implements InputProcessor:
	@Override public boolean keyDown(int keycode) { return false; }
	@Override public boolean keyUp(int keycode) { return false; }
	@Override public boolean touchDown(int screenX, int screenY, int pointer, int button) { return false; }
	@Override public boolean touchUp(int screenX, int screenY, int pointer, int button) { return false; }
	@Override public boolean touchDragged(int screenX, int screenY, int pointer) { return false; }
	@Override public boolean mouseMoved(int screenX, int screenY) { return false; }
	@Override public boolean scrolled(int amount) { return false; }
}
