package com.autobots.automanager.modelo.atualizadores;

import com.autobots.automanager.entidades.Venda;
import com.autobots.automanager.modelo.StringVerificadorNulo;

public class VendaAtualizadora {
	private StringVerificadorNulo verificador = new StringVerificadorNulo();
	
	public void atualizar(Venda venda, Venda atualizacao) {
		if (atualizacao!= null) {
            if (!verificador.verificar(atualizacao.getIdentificacao())) {
	            venda.setIdentificacao(atualizacao.getIdentificacao());
            }
			if (!verificador.verificar(String.valueOf(atualizacao.getMercadorias()))) {
                venda.setMercadorias(atualizacao.getMercadorias());
            }
			if (!verificador.verificar(String.valueOf(atualizacao.getServicos()))) {
                venda.setServicos(atualizacao.getServicos());
            }
			if (!verificador.verificar(String.valueOf(atualizacao.getCliente()))) {
                venda.setCliente(atualizacao.getCliente());
            }
			if (!verificador.verificar(String.valueOf(atualizacao.getVeiculo()))) {
                venda.setVeiculo(atualizacao.getVeiculo());
            }
			if (!verificador.verificar(String.valueOf(atualizacao.getMercadorias()))) {
                venda.setMercadorias(atualizacao.getMercadorias());
            }
			if (!verificador.verificar(String.valueOf(atualizacao.getFuncionario()))) {
                venda.setFuncionario(atualizacao.getFuncionario());
            }
			if (!verificador.verificar(String.valueOf(atualizacao.getCadastro()))) {
                venda.setCadastro(atualizacao.getCadastro());
            }
		}
	}
}
