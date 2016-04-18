package com.brunomarcos.typinggame;

public class Sequencia {
	boolean[] sequencia;
	
	public Sequencia(int n) {
		sequencia = new boolean[n];
		reset();
	}
	
	public boolean ir(int i) {
		return sequencia[i];
	}
	
	public void next() {
		for (int i = 0; i < sequencia.length; i++) {
			if (sequencia[i] == true) {
				this.sequencia[i] = false;
				this.sequencia[i + 1] = true;
				break;
			}
		}
	}

	public void end() {
		for (int i = 1; i < sequencia.length; i++)
			sequencia[i] = false;	
	}
	
	public void reset() {
		sequencia[0] = true;
		end();
	}
}
