package com.autobots.automanager.modelo.atualizadores;

import com.autobots.automanager.entidades.Veiculo;
import com.autobots.automanager.modelo.StringVerificadorNulo;

public class VeiculoAtualizador {
	private StringVerificadorNulo verificador = new StringVerificadorNulo();
	
	public void atualizar(Veiculo veiculo, Veiculo atualizacao) {
		if (atualizacao != null){
			if (!verificador.verificar(atualizacao.getModelo())){
				veiculo.setModelo(atualizacao.getModelo());
			}
			if (!verificador.verificar(String.valueOf(atualizacao.getTipo()))){
                veiculo.setTipo(atualizacao.getTipo());
            }
			if (!verificador.verificar(atualizacao.getPlaca())){
                veiculo.setPlaca(atualizacao.getPlaca());
            }
			if (!verificador.verificar(atualizacao.getVendas().toString())){
                veiculo.setVendas(atualizacao.getVendas());
            }
			if (!verificador.verificar(String.valueOf(atualizacao.getProprietario()))){
                veiculo.setProprietario(atualizacao.getProprietario());
            }
		}
	}
}
