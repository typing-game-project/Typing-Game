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
	final char[] aceitaveis = {'1','2','3','4','5','6','7','8','9','0',' ',',','.','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o',
	'p','q','r', 's','t','u','v','w', 'x','y','z','ç','á','é','í','ó','ú','à','è','ì','ò','ù','â','ê','î','ô','û','ã','õ','ä','ë','ï','ö','ü'};
	
	public Player() {
		pontos = 0;
		vida = 50;
		acertosConsecutivos = 0;
		multiplicador = 1;
		digitou = false;
		Gdx.input.setInputProcessor(this);
	}
	
	public void confereAcerto(StringBuffer frase) {
	
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
		
		if (acertosConsecutivos >= acertos[0])
			multiplicador = multiplicar[0];
		
		else if (acertosConsecutivos >= acertos[1])
			multiplicador = multiplicar[1];
		
		else if (acertosConsecutivos >= acertos[2])
			multiplicador = multiplicar[2];
	}

	@Override
	public boolean keyTyped(char character) {
		for (int i = 0;i < aceitaveis.length; i++) {
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
