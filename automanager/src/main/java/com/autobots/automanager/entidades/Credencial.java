package com.autobots.automanager.entidades;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Inheritance(strategy= InheritanceType.JOINED)
public class Credencial extends RepresentationModel<Credencial> {
	@Id()
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Date criacao;

	@Column()
	private Date ultimoAcesso;

	@Column(nullable = false)
	private boolean inativo;
}
