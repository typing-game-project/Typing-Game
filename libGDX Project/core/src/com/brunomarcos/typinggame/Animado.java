package com.brunomarcos.typinggame;

public class Animado {
	private float varF;
	private float varFinit;
	private boolean loop;
	private boolean play;
	private boolean[] sequencia;
	
	public Animado(float varF, boolean loop, int sequencias) {
		this.varF = varF;
		this.varFinit = varF;
		this.loop = loop;
		this.play = false;
		sequencia = new boolean[sequencias];
		sequencia[0] = true;
		if (sequencia.length > 1)
			for (int i = 1; i < sequencia.length; i++)
				sequencia[i] = false;
	}
	
	public float intervalo(float start, float end, float step, int indiceSequencia) {
		if (sequencia[indiceSequencia] && play) {
			if (varF >= start && varF < end)
				varF += step;
			else if (varF < start)
				varF = start;
			else if (varF >= end) {
				varF = end;
				if (sequencia.length > 1) {
					if (indiceSequencia < sequencia.length - 1) {
						sequencia[indiceSequencia] = false;
						sequencia[indiceSequencia + 1] = true;
					}
					else {
						if (loop) {
							sequencia[indiceSequencia] = false;
							sequencia[0] = true;
							varF = varFinit;
						}
						else
							sequencia[indiceSequencia] = false;
					}
				}
				else {
					if (loop)
						varF = varFinit;
					else
						sequencia[indiceSequencia] = false;
				}
			}
		}
		return varF;
	}
	
	public void play() {
		play = true;
	}
	
	public void reset() {
		this.varF = this.varFinit;
		sequencia[0] = true;
		if (sequencia.length > 1)
			for (int i = 1; i < sequencia.length; i++)
				sequencia[i] = false;
	}
}