package com.autobots.automanager.controles;

import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.modelo.adicionadorLink.AdicionadorLinkEmpresa;
import com.autobots.automanager.modelo.adicionadorLink.AdicionadorLinkTelefone;
import com.autobots.automanager.modelo.adicionadorLink.AdicionadorLinkUsuario;
import com.autobots.automanager.modelo.atualizadores.TelefoneAtualizador;
import com.autobots.automanager.repositorios.EmpresaRepositorio;
import com.autobots.automanager.repositorios.TelefoneRepositorio;
import com.autobots.automanager.repositorios.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/telefones")
public class TelefoneControle {
	
	@Autowired
	public TelefoneRepositorio repositorio;
	
	@Autowired
	public AdicionadorLinkTelefone adicionadorLink;
	
	@Autowired
	public EmpresaRepositorio empresaRepositorio;
	
	@Autowired
	private AdicionadorLinkEmpresa adicionadorLinkEmpresa;
	
	@Autowired
	private UsuarioRepositorio usuarioRepositorio;
	
	@Autowired
	private AdicionadorLinkUsuario adicionadorLinkUsuario;
	
	@PostMapping("/cadastrar/empresa/{idEmpresa}")
	public ResponseEntity<Telefone> cadastrarTelefoneEmpresa(@RequestBody Telefone telefone, @PathVariable Long idEmpresa) {
		Empresa empresa = empresaRepositorio.findById(idEmpresa).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		telefone = repositorio.save(telefone);
		empresa.getTelefones().add(telefone);
		empresaRepositorio.save(empresa);
		return new ResponseEntity<>(telefone, HttpStatus.CREATED);
	}
	
	@PostMapping("/cadastrar/usuario/{idUsuario}")
	public ResponseEntity<Telefone> cadastrarTelefoneUsuario(@RequestBody Telefone telefone, @PathVariable Long idUsuario) {
		Usuario usuario = usuarioRepositorio.findById(idUsuario).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		telefone = repositorio.save(telefone);
		usuario.getTelefones().add(telefone);
		usuarioRepositorio.save(usuario);
		return new ResponseEntity<>(telefone, HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Telefone> obterTelefone(@PathVariable Long id){
		Telefone telefones = repositorio.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		adicionadorLink.adicionarLink(telefones);
		return new ResponseEntity<>(telefones, HttpStatus.OK);
	}
	
	@GetMapping
	public List<Telefone> obterTelefones(){
		List<Telefone> telefones = repositorio.findAll();
		adicionadorLink.adicionarLink(telefones);
		return telefones;
	}

	@PutMapping("/atualizar/{id}")
	public ResponseEntity<Telefone> atualizarTelefone(@RequestBody Telefone telefone, @PathVariable Long id) {
		Telefone novoTelefone = repositorio.getById(id);
		TelefoneAtualizador atualizador = new TelefoneAtualizador();
		atualizador.atualizar(novoTelefone, telefone);
		repositorio.save(telefone);
		return new ResponseEntity<Telefone>(telefone,HttpStatus.CREATED);
	}

	@DeleteMapping("/excluir/{id}")
	public ResponseEntity<Telefone> excluirTelefone(@PathVariable Long id) {
		Telefone telefone = repositorio.getById(id);
		List<Empresa> empresas = empresaRepositorio.findAll();
		for(Empresa e : empresas){
			if(e.getTelefones().contains(telefone)){
				e.getTelefones().remove(telefone);
				empresaRepositorio.save(e);
			}
		}
		repositorio.delete(telefone);
		return new ResponseEntity<>(telefone, HttpStatus.OK);
	}
}
