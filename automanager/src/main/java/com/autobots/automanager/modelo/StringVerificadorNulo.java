package com.autobots.automanager.modelo;

public class StringVerificadorNulo {
	
	public boolean verificar(String informacao) {
		boolean nulo = true;
		if (!(informacao == null)) {
			if (!informacao.isBlank()) {
				nulo = false;
			}
		}
		return nulo;
	}
}