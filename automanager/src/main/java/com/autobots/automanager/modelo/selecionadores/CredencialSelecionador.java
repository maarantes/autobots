package com.autobots.automanager.modelo.selecionadores;

import com.autobots.automanager.entidades.Credencial;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CredencialSelecionador {
	public Credencial selecionar(List<Credencial> credencial, long id){
		Credencial selecionado = null;
        for (Credencial c : credencial) {
            if (c.getId() == id) {
                selecionado = c;
                break;
            }
        }
        return selecionado;
	}
}
