package com.autobots.automanager.service;

import com.autobots.automanager.dto.UsuarioDto;
import com.autobots.automanager.entidades.Usuario;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;

@Service
public class UsuarioService {
	public void atualizarUsuario(Usuario usuario, UsuarioDto usuarioDto, Usuario usuarioLogado) throws AccessDeniedException {
		if (!usuarioLogado.getPerfis().contains("ADMIN") && !usuarioLogado.getPerfis().contains("GERENTE")) {
			throw new AccessDeniedException("Usuário não autorizado.");
		}
		if (usuarioLogado.getId() != usuario.getId()) {
			throw new AccessDeniedException("Usuário não autorizado a modificar este registro.");
		}
	}
}
