package com.autobots.automanager.controles;

import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.modelo.adicionadorLink.AdicionadorLinkDocumento;
import com.autobots.automanager.modelo.adicionadorLink.AdicionadorLinkUsuario;
import com.autobots.automanager.modelo.atualizadores.DocumentoAtualizador;
import com.autobots.automanager.repositorios.DocumentoRepositorio;
import com.autobots.automanager.repositorios.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.print.Doc;
import java.util.List;

@RestController
@RequestMapping("/documentos")
public class DocumentoControle {
	
	@Autowired
	public DocumentoRepositorio repositorio;
	
	@Autowired
	public AdicionadorLinkDocumento adicionadorLink;
	
	@Autowired
	public UsuarioRepositorio usuarioRepositorio;
	
	@Autowired
	private AdicionadorLinkUsuario adicionadorLinkUsuario;
	
	@PostMapping("/cadastrar/usuario/{idUsuario}")
	public ResponseEntity<Documento> cadastrarDocumentoUsuario(@RequestBody Documento documento, @PathVariable Long idUsuario) {
		Usuario usuario = usuarioRepositorio.findById(idUsuario).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
		usuario.getDocumentos().add(documento);
		Documento documentoSalvo = repositorio.save(documento);
		usuarioRepositorio.save(usuario);
		return new ResponseEntity<>(documentoSalvo, HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Documento> obterDocumento(@PathVariable Long id) {
		Documento documento = repositorio.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        adicionadorLink.adicionarLink(documento);
        return new ResponseEntity<>(documento, HttpStatus.OK);
	}
	
	@GetMapping
	public List<Documento> obterDocumentos(){
		List<Documento> documentos = repositorio.findAll();
		adicionadorLink.adicionarLink(documentos);
		return documentos;
	}
	
	@PutMapping("/atualizar/{idUsuario}")
		public ResponseEntity<Documento> atualizarDocumento(@RequestBody Documento doc, @PathVariable Long idUsuario) {
		Usuario usuario = usuarioRepositorio.findById(idUsuario)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario not found"));
		
		Documento documentoExistente = usuario.getDocumentos().stream()
				.findFirst()
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Documento not found"));
		
		DocumentoAtualizador atualizador = new DocumentoAtualizador();
		atualizador.atualizar(documentoExistente, doc);
		
		usuarioRepositorio.save(usuario);
		repositorio.save(documentoExistente);
		return new ResponseEntity<>(documentoExistente,HttpStatus.OK);
	}
	
	@DeleteMapping("/excluir/{id}")
	public ResponseEntity<Documento> excluirDocumento(@PathVariable Long id) {
		Documento documento = repositorio.getById(id);
		List<Usuario> usuarios = usuarioRepositorio.findAll();
		for(Usuario usuario : usuarios){
			if(usuario.getDocumentos().contains(documento)){
				usuario.getDocumentos().remove(documento);
                usuarioRepositorio.save(usuario);
			}
		}
		repositorio.delete(documento);
		return new ResponseEntity<>(documento, HttpStatus.OK);
	}
}
