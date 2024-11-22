package com.autobots.automanager.entidades;

import com.autobots.automanager.enumeracoes.PerfilUsuario;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class PerfilUsuarioDeserializer extends StdDeserializer<PerfilUsuario> {
	public PerfilUsuarioDeserializer() {
		super(PerfilUsuario.class);
	}
	
	@Override
	public PerfilUsuario deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
		String value = p.getText();
		try {
			return PerfilUsuario.valueOf(value.toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new RuntimeException("PerfilUsuario inválido: " + value);
		}
	}
}
