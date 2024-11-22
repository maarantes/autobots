package com.autobots.automanager.modelo.selecionadores;

import com.autobots.automanager.entidades.Veiculo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VeiculoSelecionador {
	public Veiculo selecionar(List<Veiculo> veiculos, long id){
		Veiculo selecionado = null;
        for (Veiculo v : veiculos) {
            if (v.getId() == id) {
                selecionado = v;
            }
        }
        return selecionado;
	}
}
