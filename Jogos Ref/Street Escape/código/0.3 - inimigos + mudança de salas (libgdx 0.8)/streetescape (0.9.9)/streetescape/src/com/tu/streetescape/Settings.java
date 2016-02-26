package com.tu.streetescape;

//Essa classe serve apenas para ativar e desativar as coisas. NÃO ESQUECER DE ADICIONAR CONDIÇÕES
public class Settings extends MainGame{
	private final MainGame jogo;
	
	public Settings(final MainGame jogo){
		this.jogo = jogo;
		jogo.setDebug(true);
		jogo.setMusic(true);
		jogo.setSound(true);
	}	
}