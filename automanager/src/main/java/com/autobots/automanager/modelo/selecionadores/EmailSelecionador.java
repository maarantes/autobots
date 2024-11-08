package com.autobots.automanager.modelo.selecionadores;

import com.autobots.automanager.entidades.Email;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmailSelecionador {
	public Email selecionar(List<Email> email, long id){
		Email selecionado = null;
        for (Email emailSelecionado : email) {
            if (emailSelecionado.getId() == id) {
                selecionado = emailSelecionado;
            }
        }
        return selecionado;
	}
}
