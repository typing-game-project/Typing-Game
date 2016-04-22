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

	// TODO: Animações de fala: idle, falando, mostrar ou não letras
}
