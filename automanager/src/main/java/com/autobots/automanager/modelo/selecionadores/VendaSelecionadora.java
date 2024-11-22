package com.autobots.automanager.modelo.selecionadores;

import com.autobots.automanager.entidades.Venda;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VendaSelecionadora {
	public Venda selecionar(List<Venda> vendas, long id){
		Venda selecionado = null;
        for (Venda v : vendas) {
            if (v.getId() == id) {
                selecionado = v;
                break;
            }
        }
        return selecionado;
	}
}
