package com.autobots.automanager.controles;

import com.autobots.automanager.adaptadores.UserDetailsServiceImpl;
import com.autobots.automanager.entidades.Credencial;
import com.autobots.automanager.filtros.Autenticador;
import com.autobots.automanager.jwt.ProvedorJwt;
import com.autobots.automanager.repositorios.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AutenticadorControle {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	public ProvedorJwt provedorJwt;
	
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	@Autowired
	private UsuarioRepositorio usuarioRepositorio;
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody Credencial credencial) {
		try {
			Autenticador authentication = (Autenticador) authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(credencial.getNomeUsuario(), credencial.getSenha()));
			return ResponseEntity.ok("Autenticação bem-sucedida");
		} catch (AuthenticationException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"error\": \"Falha na autenticação: " + e.getMessage() + "\"}");
		}
	}
}
