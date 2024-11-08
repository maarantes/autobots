package com.autobots.automanager.modelo.selecionadores;

import com.autobots.automanager.entidades.Usuario;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UsuarioSelecionador {
	public Usuario selecionar(List<Usuario> usuarios, long id) {
		Usuario selecionado = null;
        for (Usuario u : usuarios) {
            if (u.getId() == id) {
                selecionado = u;
                break;
            }
        }
        return selecionado;
	}
}
