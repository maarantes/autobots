package com.autobots.automanager.modelo.atualizadores;

import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.modelo.StringVerificadorNulo;

public class UsuarioAtualizador {
	private StringVerificadorNulo verificador = new StringVerificadorNulo();
	
	public void atualizar(Usuario usuario, Usuario atualizacao) {
		if (atualizacao != null) {
			if (!verificador.verificar(atualizacao.getPerfis().toString())){
				usuario.setPerfis(atualizacao.getPerfis());
			}
			if (!verificador.verificar(atualizacao.getNome())){
                usuario.setNome(atualizacao.getNome());
            }
			if (!verificador.verificar(atualizacao.getNomeSocial())){
                usuario.setNomeSocial(atualizacao.getNomeSocial());
            }
			if (!verificador.verificar(atualizacao.getCredenciais().toString())){
                usuario.setNome(atualizacao.getNome());
            }
			if (!verificador.verificar(atualizacao.getTelefones().toString())){
                usuario.setNomeSocial(atualizacao.getNomeSocial());
            }
			if (!verificador.verificar(String.valueOf(atualizacao.getEndereco()))){
                usuario.setEndereco(atualizacao.getEndereco());
            }
			if (!verificador.verificar(String.valueOf(atualizacao.getEmails()))){
                usuario.setEmails(atualizacao.getEmails());
            }
			if (!verificador.verificar(String.valueOf(atualizacao.getDocumentos()))){
                usuario.setDocumentos(atualizacao.getDocumentos());
            }
			if (!verificador.verificar(String.valueOf(atualizacao.getVeiculos()))){
                usuario.setVeiculos(atualizacao.getVeiculos());
            }
			if (!verificador.verificar(String.valueOf(atualizacao.getVendas()))){
                usuario.setVendas(atualizacao.getVendas());
            }
		}
	}
}
