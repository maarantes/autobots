package com.autobots.automanager.dto;

import com.autobots.automanager.entidades.Veiculo;
import com.autobots.automanager.enumeracoes.TipoVeiculo;
import lombok.Data;

import java.util.Set;

@Data
public class VeiculoDto {
	private Long id;
	private TipoVeiculo tipo;
	private String modelo;
	private String placa;
	private Set<Long> vendas;
	private Long proprietario;
	public Veiculo toEntity() {
		Veiculo veiculo = new Veiculo();
		veiculo.setTipo(this.tipo);
		veiculo.setModelo(this.modelo);
		veiculo.setPlaca(this.placa);
		return veiculo;
	}
}
