package com.autobots.automanager.modelo.selecionadores;

import com.autobots.automanager.entidades.Veiculo;
import com.autobots.automanager.modelo.atualizadores.VeiculoAtualizador;
import com.autobots.automanager.repositorios.VeiculoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
