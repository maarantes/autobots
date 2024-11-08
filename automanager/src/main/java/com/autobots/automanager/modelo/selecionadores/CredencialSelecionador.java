package com.autobots.automanager.modelo.selecionadores;

import com.autobots.automanager.entidades.CredencialUsuarioSenha;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CredencialSelecionador {
	public CredencialUsuarioSenha selecionar(List<CredencialUsuarioSenha> credencial, long id){
		CredencialUsuarioSenha selecionado = null;
        for (CredencialUsuarioSenha c : credencial) {
            if (c.getId() == id) {
                selecionado = c;
                break;
            }
        }
        return selecionado;
	}
}
