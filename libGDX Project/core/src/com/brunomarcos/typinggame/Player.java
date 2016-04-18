package com.brunomarcos.typinggame;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

public class Player implements InputProcessor {
	public char letraDigitada;
	public char maiscula;
	public char minuscula;
	public int pontos;
	public int vida;
	public int acertosConsecutivos;
	public int multiplicador;
	public boolean digitou;
	private Random random;
	final char[] aceitaveis = { 'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', 'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'ç', 'z', 'x', 'c', 'v', 'b', 'n', 'm', 'á',
			'é', 'í', 'ó', 'ú', 'à', 'è', 'ì', 'ò', 'ù', 'â', 'ê', 'î', 'ô', 'û', 'ä', 'ë', 'ï', 'ö', 'ü', 'ã', 'õ', 'ñ', 'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P',
			'A', 'S', 'D', 'F', 'G', 'H', 'J', 'K', 'L', 'Ç', 'Z', 'X', 'C', 'V', 'B', 'N', 'M', 'Á', 'É', 'Í', 'Ó', 'Ú', 'À', 'È', 'Ì', 'Ò', 'Ù', 'Â', 'Ê', 'Î', 'Ô', 'Û',
			'Ä', 'Ë', 'Ï', 'Ö', 'Ü', 'Ã', 'Õ', 'Ñ', '"', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '!', '@', '#', '$', '%', '¨', '&', '*', '(', ')', '_', '+', '-',
			'=', '/', '£', '³', '²', '¹', '¢', '¬', '§', '´', '`', '[', ']', '{', 'ª', '}', 'º', '~', '^', '<', '.', '>', ';', ':', '/', '?', '°', '|', ' ', ','};
	
	public Player() {
		pontos = 0;
		vida = 50;
		acertosConsecutivos = 0;
		multiplicador = 1;
		digitou = false;
		Gdx.input.setInputProcessor(this);
		random = new Random();
	}
	
	public void confereAcerto(GameManager game, Frase Frase) {
		StringBuffer frase = Frase.linha.get(0);
		
		if (digitou) {	
			char letra = frase.charAt(0); // Primeira letra da frase
			
			// Se acertou:
			if (letraDigitada == letra || maiscula == letra || minuscula == letra) {
				multiplicaPontos();
				pontos += multiplicador * 1;
				acertosConsecutivos += 1;
				frase.deleteCharAt(0);
				
				game.batch.setColor(1,1,1,0.3f);
				game.batch.draw(game.rect, 0,0,GameManager.width,GameManager.height);
				game.animAcerto.reset();
				Frase.acertou = true;
			}
			
			// Se errou:
			else {			
				vida -= 1;
				acertosConsecutivos = 0;
				multiplicador = 1;
				
				game.erro[random.nextInt(2)].play();
				game.batch.setColor(1,0,0,0.3f);
				game.batch.draw(game.rect, 0,0,GameManager.width,GameManager.height);
			}	
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
				if (i < 50)
					maiscula = aceitaveis[i + 50];
				
				else if (i < 100)
					minuscula = aceitaveis[i - 50];
				
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
