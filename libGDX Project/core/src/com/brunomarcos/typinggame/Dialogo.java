package com.brunomarcos.typinggame;

import java.util.ArrayList;
import java.util.Map;

public class Dialogo {
	Map<Integer,Spritesheet> personagens;
	ArrayList<String> falas;
	
	public Dialogo(Map<Integer,Spritesheet> personagens, ArrayList<String> falas) {
		this.personagens = personagens;
		this.falas = falas;
	}

	// TODO: Anima��es de fala: idle, falando, mostrar ou n�o letras
}
