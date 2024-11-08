package com.autobots.automanager.modelo.atualizadores;

import com.autobots.automanager.entidades.Servico;
import com.autobots.automanager.modelo.StringVerificadorNulo;

public class ServicoAtualizador {
	private StringVerificadorNulo verificador = new StringVerificadorNulo();
	
	public void atualizar(Servico servico, Servico atualizacao) {
		if (atualizacao!= null) {
			if (!verificador.verificar(atualizacao.getDescricao())) {
				servico.setDescricao(atualizacao.getDescricao());
			}
			if (!verificador.verificar(String.valueOf(atualizacao.getValor()))) {
                servico.setValor(atualizacao.getValor());
            }
			if (!verificador.verificar(atualizacao.getNome())) {
                servico.setNome(atualizacao.getNome());
            }
		}
	}
}
