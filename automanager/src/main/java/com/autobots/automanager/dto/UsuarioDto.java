package com.autobots.automanager.dto;

import com.autobots.automanager.entidades.*;
import com.autobots.automanager.enumeracoes.PerfilUsuario;
import lombok.Data;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class UsuarioDto {
	private Long id;
	private String nome;
	private String nomeSocial;
	private Set<Long> documentos;
	private Set<Long> telefones;
	private Long endereco;
	private Set<Long> emails;
	private Set<Long> credenciais;
	private Set<Long> mercadorias;
	private Set<Long> vendas;
	private Set<Long> veiculos;
	private List<PerfilUsuario> perfis;
	
	public Usuario toEntity(List<PerfilUsuario> perfis) {
		Usuario usuario = new Usuario();
		usuario.setId(this.id);
		usuario.setNome(this.nome);
		usuario.setNomeSocial(this.nomeSocial);
		usuario.setPerfis(perfis.stream().collect(Collectors.toSet()));
		return usuario;
	}
	
	public void updateEntity(Usuario usuario, List<Documento> documentos, Endereco endereco, List<Telefone> telefones, List<Email> emails, List<PerfilUsuario> perfis, List<Credencial> credenciais, List<Mercadoria> mercadorias, List<Venda> vendas, List<Veiculo> veiculos) {
		usuario.setNome(this.nome);
		usuario.setNomeSocial(this.nomeSocial);
		updateCollection(usuario.getDocumentos(), documentos);
		usuario.setEndereco(endereco);
		updateCollection(usuario.getTelefones(), telefones);
		updateCollection(usuario.getEmails(), emails);
		usuario.setPerfis(perfis.stream().collect(Collectors.toSet()));
		updateCollection(usuario.getCredenciais(), credenciais);
		updateCollection(usuario.getMercadorias(), mercadorias);
		updateCollection(usuario.getVendas(), vendas);
		updateCollection(usuario.getVeiculos(), veiculos);
	}
	
	private <T> void updateCollection(Set<T> target, List<? extends T> source) {
		target.clear();
		target.addAll(source);
	}
}
