package com.autobots.automanager.controles;

import com.autobots.automanager.entidades.Email;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.modelo.adicionadorLink.AdicionadorLinkEmail;
import com.autobots.automanager.modelo.adicionadorLink.AdicionadorLinkUsuario;
import com.autobots.automanager.modelo.selecionadores.EmailSelecionador;
import com.autobots.automanager.repositorios.EmailRepositorio;
import com.autobots.automanager.repositorios.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/emails")
public class EmailControle {
	
	@Autowired
	public EmailRepositorio repositorio;
	
	@Autowired
	public EmailSelecionador selecionador;
	
	@Autowired
	public AdicionadorLinkEmail adicionadorLink;
	
	@Autowired
	public UsuarioRepositorio usuarioRepositorio;
	
	@Autowired
	private AdicionadorLinkUsuario adicionadorLinkUsuario;
	
	@PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR', 'CLIENTE')")
	@GetMapping("/{id}")
	public ResponseEntity<Email> obterEmail(@PathVariable Long id) {
		Email email = repositorio.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		adicionadorLink.adicionarLink(email);
		return new ResponseEntity<>(email, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR', 'CLIENTE')")
	@GetMapping
	public List<Email> obterEmails(){
		List<Email> emails = repositorio.findAll();
		adicionadorLink.adicionarLink(emails);
		return emails;
	}
	
	@PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
	@PostMapping("/cadastro/{idUsuario}")
	public ResponseEntity<Email> cadastrarEmail(@RequestBody Email email, @PathVariable Long idUsuario) {
		Usuario usuario = usuarioRepositorio.findById(idUsuario).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		usuario.getEmails().add(email);
		usuarioRepositorio.save(usuario);
		repositorio.save(email);
		return new ResponseEntity<>(email, HttpStatus.CREATED);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
	@PutMapping("/atualizar/{idEmail}")
	public ResponseEntity<Email> atualizarEmail(@RequestBody Email email, @PathVariable Long idEmail) {
		Email emailAtual = repositorio.findById(idEmail)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Email not found"));
		emailAtual.setEndereco(email.getEndereco());
		repositorio.save(emailAtual);
		return ResponseEntity.ok().build();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
	@DeleteMapping("/excluir/{id}")
	public ResponseEntity<Email> excluirEmail(@PathVariable Long id) {
		Email email = repositorio.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		List<Usuario> usuarios = usuarioRepositorio.findAll();
		for(Usuario usuario : usuarios) {
			if(usuario.getEmails().contains(email)){
				usuario.getEmails().remove(email);
				usuarioRepositorio.save(usuario);
			}
		}
		repositorio.delete(email);
		return new ResponseEntity<>(email, HttpStatus.OK);
	}
}
