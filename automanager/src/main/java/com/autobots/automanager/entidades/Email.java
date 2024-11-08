package com.autobots.automanager.entidades;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;

@Data
@Entity
public class Email extends RepresentationModel<Email> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String endereco;
}
