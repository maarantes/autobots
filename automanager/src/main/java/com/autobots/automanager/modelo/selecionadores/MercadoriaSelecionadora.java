package com.autobots.automanager.modelo.selecionadores;

import com.autobots.automanager.entidades.Mercadoria;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MercadoriaSelecionadora {
	public Mercadoria selecionar(List<Mercadoria> mercadoria, long id) {
        Mercadoria selecionado = null;
		for(Mercadoria m: mercadoria){
			if(m.getId() == id){
                selecionado = m;
                break;
            }
		}
        return selecionado;
    }
}
