package com.autobots.automanager.entidades;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
@Data
@Entity
public class Credencial extends RepresentationModel<Credencial> implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String nomeUsuario;
	
	@Column(nullable = false)
	private String senha;
	
	private Date criacao;
	
	@Column
	private Date ultimoAcesso;
	
	@Column(nullable = false)
	private boolean inativo;
}