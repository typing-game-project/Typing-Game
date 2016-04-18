package com.brunomarcos.typinggame;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.math.Vector2;

public class Frase {	
	public String fraseCompleta;
	public StringBuffer frase;
	public Vector2 frasePos;
	private Vector2 treme;
	public boolean tremendo;
	Sequencia seqErro;
	public int limiteLinha;
	public int indiceLinha;
	public ArrayList<StringBuffer> linha;
	private int[][] letrasY;
	private final int tamanhoChar = 70;
	private Random random;
	public boolean acertou = false;
	
	public Frase(String frase) {
		this.fraseCompleta = frase;
		this.frase = new StringBuffer();
		this.frase.append(this.fraseCompleta);
		this.frasePos = new Vector2();
		this.treme = new Vector2();
		this.tremendo = false;
		this.seqErro = new Sequencia(4);
		this.linha = new ArrayList<StringBuffer>();
		limiteLinha = (GameManager.width - (3*63)) / 63;
		criandoLinhas();
		random = new Random();
	}
	
	public void criandoLinhas() {
		int indiceChar = 0;
		indiceLinha = 0;
		criarLinha(indiceChar);
		
		letrasY = new int[linha.size()][];
		for (int i = 0; i < letrasY.length; i++)
			letrasY[i] = new int[linha.get(i).length()];
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
	
	public void treme(float velocidade) {
		if (tremendo) {
			if (seqErro.ir(0)) {
				treme.x += velocidade;
				if (treme.x >= 60)
					seqErro.next();
			}
			if (seqErro.ir(1)) {
				treme.x -= velocidade/2;
				if (treme.x <= -30)
					seqErro.next();
			}
			if (seqErro.ir(2)) {
				treme.x += velocidade/3;
				if (treme.x >= 15)
					seqErro.next();
			}
			if (seqErro.ir(3)) {
				treme.x -= velocidade/4;
				if (treme.x <= 0) {
					treme.x = 0;
					treme.y = 0;
					tremendo = false;
					seqErro.reset();
				}
			}
		}
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
				float x = frasePos.x + tamanhoChar * indiceChar + treme.x;
				float y;
				
				// Fazer as letras tremerem :
				try {	
					letrasY[i][j] = random.nextInt(2);
					y = frasePos.y + letrasY[i][j] + treme.y;
					// TODO: algumas letras não tremem depois de completar a primeira linha
				} catch (Exception e) {
					y = frasePos.y + treme.y;
				}
				
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
			treme(30);
		}
	}
}
