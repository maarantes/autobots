package com.autobots.automanager.modelo.atualizadores;

import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.modelo.StringVerificadorNulo;

public class MercadoriaAtualizadora {
	private StringVerificadorNulo verificador = new StringVerificadorNulo();
	
	public void atualizar(Mercadoria mercadoria, Mercadoria atualizacao) {
		if (atualizacao!= null) {
            if (!verificador.verificar(atualizacao.getDescricao())) {
                mercadoria.setDescricao(atualizacao.getDescricao());
            }
            if (!verificador.verificar(atualizacao.getNome())) {
                mercadoria.setNome(atualizacao.getNome());
            }
            if (!verificador.verificar(String.valueOf(atualizacao.getValor()))) {
                mercadoria.setValor(atualizacao.getValor());
            }
			if (!verificador.verificar(String.valueOf(atualizacao.getQuantidade()))) {
                mercadoria.setQuantidade(atualizacao.getQuantidade());
            }
			if (!verificador.verificar(String.valueOf(atualizacao.getCadastro()))) {
                mercadoria.setCadastro(atualizacao.getCadastro());
            }
			if (!verificador.verificar(String.valueOf(atualizacao.getFabricacao()))) {
                mercadoria.setFabricacao(atualizacao.getFabricacao());
            }
		}
	}
}
