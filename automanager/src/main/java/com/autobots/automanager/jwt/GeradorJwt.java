package com.autobots.automanager.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class GeradorJwt {
	@Value("${jwt.secret}")
	private String assinatura;
	@Value("${jwt.expiration}")
	private Long duracao;

	public String gerarJwt(String nomeUsuario) {
		Date expiracao = new Date(System.currentTimeMillis() + duracao);
		String jwt = Jwts.builder()
				.setSubject(nomeUsuario)
				.setExpiration(expiracao)
				.signWith(SignatureAlgorithm.HS512, assinatura.getBytes())
				.compact();
		return jwt;
	}
}