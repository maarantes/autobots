package com.autobots.automanager.dto;

import com.autobots.automanager.entidades.*;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class EmpresaDto {
	private Long id;
	private String razaoSocial;
	private String nomeFantasia;
	private Set<Long> telefones;
	private Long endereco;
	private Date cadastro;
	private Set<Long> usuarios;
	private Set<Long> mercadorias;
	private Set<Long> servicos;
	private Set<Long> vendas;
	
	public Empresa cadastro(){
		Empresa empresa = new Empresa();
		empresa.setRazaoSocial(this.razaoSocial);
		empresa.setNomeFantasia(this.nomeFantasia);
		empresa.setCadastro(this.cadastro);
		return empresa;
	}
	
	public void updateEntity(Empresa empresa, Set<Telefone> telefones, Endereco endereco, Set<Usuario> usuarios, Set<Mercadoria> mercadorias, Set<Servico> servicos, Set<Venda> vendas) {
		empresa.setRazaoSocial(this.razaoSocial);
		empresa.setNomeFantasia(this.nomeFantasia);
		empresa.setCadastro(this.cadastro);
		
		empresa.getTelefones().clear();
		empresa.getTelefones().addAll(telefones);
		
		empresa.setEndereco(endereco);
		
		empresa.getUsuarios().clear();
		empresa.getUsuarios().addAll(usuarios);
		
		empresa.getMercadorias().clear();
		empresa.getMercadorias().addAll(mercadorias);
		
		empresa.getServicos().clear();
		empresa.getServicos().addAll(servicos);
		
		empresa.getVendas().clear();
		empresa.getVendas().addAll(vendas);
	}
	
	public Empresa toEntity(Set<Telefone> telefones, Endereco endereco, Set<Usuario> usuarios, Set<Mercadoria> mercadorias, Set<Servico> servicos, Set<Venda> vendas) {
		Empresa empresa = new Empresa();
		empresa.setRazaoSocial(this.razaoSocial);
		empresa.setNomeFantasia(this.nomeFantasia);
		empresa.setTelefones(telefones);
		empresa.setEndereco(endereco);
		empresa.setCadastro(this.cadastro);
		empresa.setUsuarios(usuarios);
		empresa.setMercadorias(mercadorias);
		empresa.setServicos(servicos);
		empresa.setVendas(vendas);
		return empresa;
	}
}
