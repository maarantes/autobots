package com.autobots.automanager.entidades;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class CredencialCodigoBarra extends Credencial{
	@Column(unique = true)
	private long codigo;
}
