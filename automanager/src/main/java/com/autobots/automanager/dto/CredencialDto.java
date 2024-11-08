package com.autobots.automanager.dto;

import com.autobots.automanager.entidades.Credencial;
import com.autobots.automanager.entidades.CredencialCodigoBarra;
import com.autobots.automanager.entidades.CredencialUsuarioSenha;
import lombok.Data;

import java.util.Date;

@Data
public class CredencialDto {
	private Long id;
	private Long codigo;
	private String senha;
	private String nomeUsuario;
	private Date criacao;
	private Date ultimoAcesso;
	private boolean inativo;
	
	public CredencialCodigoBarra toEntityCodigoBarra() {
		CredencialCodigoBarra credencial = new CredencialCodigoBarra();
		credencial.setCodigo(this.codigo);
		credencial.setCriacao(this.criacao != null ? this.criacao : new Date());
		credencial.setUltimoAcesso(this.ultimoAcesso);
		credencial.setInativo(this.inativo);
		return credencial;
	}
	
	public CredencialUsuarioSenha toEntityUsuarioSenha(){
		CredencialUsuarioSenha credencial = new CredencialUsuarioSenha();
        credencial.setNomeUsuario(this.nomeUsuario);
        credencial.setSenha(this.senha);
        credencial.setCriacao(this.criacao!= null? this.criacao : new Date());
        credencial.setUltimoAcesso(this.ultimoAcesso);
        credencial.setInativo(this.inativo);
        return credencial;
	}
	
	public void updateEntityCodigoBarra(CredencialCodigoBarra credencial) {
		credencial.setCodigo(this.codigo);
		credencial.setCriacao(this.criacao != null ? this.criacao : new Date());
		credencial.setUltimoAcesso(this.ultimoAcesso);
		credencial.setInativo(this.inativo);
	}
	
	public void updateEntityUsuarioSenha(CredencialUsuarioSenha credencial) {
		credencial.setNomeUsuario(this.nomeUsuario);
        credencial.setSenha(this.senha);
        credencial.setCriacao(this.criacao!= null? this.criacao : new Date());
        credencial.setUltimoAcesso(this.ultimoAcesso);
        credencial.setInativo(this.inativo);
	}
}