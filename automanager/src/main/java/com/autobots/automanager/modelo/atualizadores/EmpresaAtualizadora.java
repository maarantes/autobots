package com.autobots.automanager.modelo.atualizadores;

import com.autobots.automanager.dto.EmpresaDto;
import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.modelo.StringVerificadorNulo;

import com.autobots.automanager.entidades.*;
import com.autobots.automanager.modelo.StringVerificadorNulo;
import java.util.Set;

public class EmpresaAtualizadora {
	private StringVerificadorNulo verificador = new StringVerificadorNulo();
	
	public void atualizar(Empresa empresa, EmpresaDto atualizacao,
	                      Set<Telefone> telefones, Endereco endereco,
	                      Set<Usuario> usuarios, Set<Mercadoria> mercadorias,
	                      Set<Servico> servicos, Set<Venda> vendas) {
		if (atualizacao != null) {
			if (!verificador.verificar(atualizacao.getRazaoSocial())) {
				empresa.setRazaoSocial(atualizacao.getRazaoSocial());
			}
			if (!verificador.verificar(atualizacao.getNomeFantasia())) {
				empresa.setNomeFantasia(atualizacao.getNomeFantasia());
			}
			if (!verificador.verificar(telefones.toString())) {
				empresa.setTelefones(telefones);
			}
			if (endereco != null) {
				empresa.setEndereco(endereco);
			}
			if (!verificador.verificar(usuarios.toString())) {
				empresa.setUsuarios(usuarios);
			}
			if (!verificador.verificar(mercadorias.toString())) {
				empresa.setMercadorias(mercadorias);
			}
			if (!verificador.verificar(servicos.toString())) {
				empresa.setServicos(servicos);
			}
			if (!verificador.verificar(vendas.toString())) {
				empresa.setVendas(vendas);
			}
			if (atualizacao.getCadastro() != null) {
				empresa.setCadastro(atualizacao.getCadastro());
			}
		}
	}
}