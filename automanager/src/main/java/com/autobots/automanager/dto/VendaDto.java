package com.autobots.automanager.dto;

import com.autobots.automanager.entidades.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Data
public class VendaDto {
	private Long id;
	private Date cadastro;
	private String identificacao;
	private Long clienteId;
	private Long funcionarioId;
	private List<Long> mercadorias;
	private List<Long> servicos;
	private Long veiculoId;
	
	public VendaDto() {
		this.mercadorias = new ArrayList<>();
		this.servicos = new ArrayList<>();
	}
	
	public Venda cadastro(){
		Venda venda = new Venda();
        venda.setCadastro(this.cadastro);
        venda.setIdentificacao(this.identificacao);
        return venda;
	}
	
	public Venda toEntity(Usuario cliente, Usuario funcionario, List<Mercadoria> mercadorias, List<Servico> servicos, Veiculo veiculo) {
		Venda venda = new Venda();
		venda.setId(this.id);
		venda.setCadastro(this.cadastro);
		venda.setIdentificacao(this.identificacao);
		venda.setCliente(cliente);
		venda.setFuncionario(funcionario);
		venda.setMercadorias(new HashSet<>(mercadorias));
		venda.setServicos(new HashSet<>(servicos));
		venda.setVeiculo(veiculo);
		return venda;
	}
}
