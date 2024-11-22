package com.autobots.automanager.modelo.selecionadores;

import com.autobots.automanager.entidades.Servico;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ServicoSelecionador {
	public Servico selecionar(List<Servico> servicos, long id){
		Servico selecionado = null;
        for (Servico s : servicos) {
            if (s.getId() == id) {
                selecionado = s;
            }
        }
        return selecionado;
	}
}
