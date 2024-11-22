package com.autobots.automanager.controles;

import com.autobots.automanager.dto.CredencialDto;
import com.autobots.automanager.entidades.Credencial;
import com.autobots.automanager.entidades.CredencialCodigoBarra;
import com.autobots.automanager.entidades.CredencialUsuarioSenha;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.modelo.adicionadorLink.AdicionadorLinkCredencialCodigo;
import com.autobots.automanager.modelo.adicionadorLink.AdicionadorLinkCredencialSenha;
import com.autobots.automanager.modelo.adicionadorLink.AdicionadorLinkUsuario;
import com.autobots.automanager.modelo.selecionadores.CredencialSelecionador;
import com.autobots.automanager.repositorios.CredencialRepositorio;
import com.autobots.automanager.repositorios.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/credenciais")
public class CredencialControle {
	
	@Autowired
	public CredencialRepositorio repositorio;
	
	@Autowired
	public CredencialSelecionador selecionador;
	
	@Autowired
	public com.autobots.automanager.modelo.adicionadorLink.AdicionadorLinkCredencial adicionadorLink;
	
	@Autowired
	private UsuarioRepositorio usuarioRepositorio;
	
	@Autowired
	private CredencialRepositorio credencialRepositorio;
	
	@Autowired
	private AdicionadorLinkUsuario adicionadorLinkUsuario;
	
	@Autowired
	private AdicionadorLinkCredencialSenha adicionadorLinkCredencialSenha;
	
	@Autowired
	private AdicionadorLinkCredencialCodigo adicionadorLinkCredencialCodigo;
	
	@PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR', 'CLIENTE')")
	@GetMapping("/{id}")
	public ResponseEntity<Credencial> obterCredencial(@PathVariable Long id) {
		Credencial credencial = repositorio.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Credencial not found"));
		
		if (credencial instanceof CredencialUsuarioSenha) {
			adicionadorLinkCredencialSenha.adicionarLink((CredencialUsuarioSenha) credencial);
		} else if (credencial instanceof CredencialCodigoBarra) {
			adicionadorLinkCredencialCodigo.adicionarLink((CredencialCodigoBarra) credencial);
		}
		adicionadorLink.adicionarLink(credencial);
		return ResponseEntity.ok(credencial);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','GERENTE', 'VENDEDOR', 'CLIENTE')")
	@GetMapping
	public List<EntityModel<Credencial>> obterCredenciais() {
		List<Credencial> credenciais = repositorio.findAll();
		return credenciais.stream()
				.map(credencial -> {
					adicionadorLink.adicionarLinkParaListagem(credencial);
					return EntityModel.of(credencial);
				})
				.collect(Collectors.toList());
	}
	
	@PreAuthorize("hasAnyRole('ADMIN', 'GERENTE','VENDEDOR')")
	@PostMapping("/cadastro/{idUsuario}")
	public ResponseEntity<Credencial> cadastrarCredencial(@RequestBody CredencialDto credencialDto, @PathVariable Long idUsuario) {
		Usuario usuario = usuarioRepositorio.findById(idUsuario)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
		BCryptPasswordEncoder codificador = new BCryptPasswordEncoder();
		
		Credencial novaCredencial;
		if (credencialDto.getCodigo() != null && credencialDto.getNomeUsuario() == null && credencialDto.getSenha() == null) {
			novaCredencial = credencialDto.toEntityCodigoBarra();
		} else if (credencialDto.getNomeUsuario() != null && credencialDto.getSenha() != null && credencialDto.getCodigo() == null) {
			CredencialUsuarioSenha credencialUsuarioSenha = credencialDto.toEntityUsuarioSenha();
			credencialUsuarioSenha.setSenha(codificador.encode(credencialDto.getSenha()));
			novaCredencial = credencialUsuarioSenha;
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Formato de credencial inválido");
		}
		
		novaCredencial.setCriacao(credencialDto.getCriacao() != null ? credencialDto.getCriacao() : new Date());
		novaCredencial.setUltimoAcesso(credencialDto.getUltimoAcesso());
		novaCredencial.setInativo(credencialDto.isInativo());
		novaCredencial = repositorio.save(novaCredencial);
		
		usuario.getCredenciais().add(novaCredencial);
		usuarioRepositorio.save(usuario);
		
		CredencialDto responseDto = new CredencialDto();
		responseDto.setId(novaCredencial.getId());
		responseDto.setCriacao(novaCredencial.getCriacao());
		responseDto.setUltimoAcesso(novaCredencial.getUltimoAcesso());
		responseDto.setInativo(novaCredencial.isInativo());
		
		if (novaCredencial instanceof CredencialCodigoBarra) {
			responseDto.setCodigo(((CredencialCodigoBarra) novaCredencial).getCodigo());
		} else if (novaCredencial instanceof CredencialUsuarioSenha) {
			responseDto.setNomeUsuario(((CredencialUsuarioSenha) novaCredencial).getNomeUsuario());
			responseDto.setSenha(null);
		}
		
		return new ResponseEntity<>(novaCredencial, HttpStatus.CREATED);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN', 'GERENTE','VENDEDOR')")
	@PutMapping("/atualizar/{idCredencial}/{idUsuario}")
	public ResponseEntity<CredencialDto> atualizarCredencial(@RequestBody CredencialDto credencialAtualizada, @PathVariable Long idCredencial, @PathVariable Long idUsuario) {
		Credencial credencialExistente = repositorio.findById(idCredencial)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Credencial not found"));
		Usuario usuario = usuarioRepositorio.findById(idUsuario).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		
		if (credencialAtualizada.getCodigo() != null && credencialAtualizada.getNomeUsuario() == null && credencialAtualizada.getSenha() == null) {
			if (credencialExistente instanceof CredencialCodigoBarra) {
				credencialAtualizada.updateEntityCodigoBarra((CredencialCodigoBarra) credencialExistente);
			} else {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Credencial não é do tipo CredencialCodigoBarra");
			}
		} else if (credencialAtualizada.getNomeUsuario() != null && credencialAtualizada.getSenha() != null) {
			if (credencialExistente instanceof CredencialUsuarioSenha) {
				credencialAtualizada.updateEntityUsuarioSenha((CredencialUsuarioSenha) credencialExistente);
			} else {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Credencial não é do tipo CredencialUsuarioSenha");
			}
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Formato de credencial inválido");
		}
		
		repositorio.save(credencialExistente);
		usuario.getCredenciais().add(credencialExistente);
		usuarioRepositorio.save(usuario);
		
		return new ResponseEntity<>(credencialAtualizada, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN', 'GERENTE','VENDEDOR')")
	@DeleteMapping("/excluir/{id}")
	public ResponseEntity<Void> excluirCredencial(@PathVariable Long id) {
		Credencial credencial = repositorio.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Credencial not found"));
		
		Usuario usuario = usuarioRepositorio.findAll().stream()
				.filter(u -> u.getCredenciais().contains(credencial))
				.findFirst()
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario not found for the given credencial"));
		
		usuario.getCredenciais().remove(credencial);
		usuarioRepositorio.save(usuario);
		repositorio.delete(credencial);
		return ResponseEntity.noContent().build();
	}
}