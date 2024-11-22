package com.autobots.automanager.modelo.selecionadores;

import com.autobots.automanager.entidades.Empresa;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmpresaSelecionadora {
	public Empresa selecionar(List<Empresa> empresa, long id) {
        Empresa selecionado = null;
		for(Empresa e : empresa) {
			if(e.getId() == id) {
                selecionado = e;
                break;
            }
		}
		return selecionado;
    }
}
